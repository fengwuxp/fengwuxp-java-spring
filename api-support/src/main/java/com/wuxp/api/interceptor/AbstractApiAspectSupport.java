package com.wuxp.api.interceptor;

import com.wuxp.api.ApiRequest;
import com.wuxp.api.context.ApiRequestContextFactory;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.log.ApiLogModel;
import com.wuxp.api.log.ApiLogRecorder;
import com.wuxp.api.log.SimpleApiAction;
import com.wuxp.api.signature.*;
import com.wuxp.basic.utils.IpAddressUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.groups.Default;
import java.lang.reflect.*;
import java.util.*;

import static com.wuxp.api.ApiRequest.APP_ID_KEY;
import static com.wuxp.api.context.ApiRequestContextFactory.AUTHENTICATE;
import static com.wuxp.api.interceptor.ApiEvaluationContext.*;
import static com.wuxp.api.log.ApiLogModel.USER_AGENT_HEADER;
import static com.wuxp.api.signature.InternalApiSignatureRequest.*;

/**
 * api aspect support
 *
 * @author wxup
 */
@Slf4j
@Setter
public abstract class AbstractApiAspectSupport implements BeanFactoryAware, InitializingBean,/* SmartInitializingSingleton,*/ DisposableBean {


    protected final Map<AbstractApiAspectSupport.ApiOperationCacheKey, AbstractApiAspectSupport.ApiOperationMetadata> metadataCache = new ConcurrentReferenceHashMap<>(1024);

    /**
     * 标记方法是否需要注入
     */
    protected final Map<Method, Boolean> injectFields = new ConcurrentReferenceHashMap<>(1024);

    protected final Map<Method, Boolean> injectParams = new ConcurrentReferenceHashMap<>(1024);

    protected final Map<Class<?>, Field[]> fieldCache = new ConcurrentReferenceHashMap<>(1024);

    protected BeanFactory beanFactory;

    protected ThreadPoolTaskScheduler threadPoolTaskScheduler;

    protected ApiRequestContextFactory apiRequestContextFactory;

    protected ApiSignatureStrategy apiSignatureStrategy;

    protected ApiLogRecorder apiLogRecorder;

    protected Class<?> injectSupperClazz = ApiRequest.class;

    protected Class<?> checkSignatureSupperClazz = ApiSignatureRequest.class;

    protected ApiOperationExpressionEvaluator evaluator = new ApiOperationExpressionEvaluator();

    protected Validator validator;


    /**
     * 尝试对参数注入
     *
     * @param target      被代理的目标对象
     * @param targetClass 被代理的目标对象类类型
     * @param method      被代理的方法
     * @param arguments   被代理的方法参数
     */
    protected EvaluationContext tryInjectParamsValue(Object target,
                                                     Class<?> targetClass,
                                                     Method method,
                                                     Object[] arguments) {

        // Spel 执行上下文
        EvaluationContext evaluationContext = null;

        Map<Method, Boolean> injectFields = this.injectFields;
        Map<Method, Boolean> injectParams = this.injectParams;
        Boolean needField = injectFields.get(method);
        Boolean needParam = injectParams.get(method);
        if (needField == null || needParam == null) {
            Class<?> aClass = Arrays.stream(method.getParameterTypes())
                    .filter(parameterType -> parameterType == injectSupperClazz)
                    .findFirst()
                    .orElse(null);
            if (aClass != null) {
                needField = Arrays.stream(aClass.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(InjectField.class))
                        .findFirst()
                        .orElse(null) != null;
            } else {
                needField = false;
            }
            needParam = Arrays.stream(method.getParameterAnnotations())
                    .map(Arrays::asList)
                    .flatMap(Collection::stream)
                    .filter(annotation -> InjectField.class == annotation.annotationType())
                    .findFirst()
                    .orElse(null) != null;
            injectFields.put(method, needField);
            injectParams.put(method, needParam);
        }

        if (!needField && !needParam) {
            return evaluationContext;
        }
        AnnotatedElementKey methodKey = null;
        if (needField) {
            evaluationContext = this.createEvaluationContext(target, targetClass, method, arguments);
            ApiOperationMetadata apiOperationMetadata = this.metadataCache.get(new ApiOperationCacheKey(method, targetClass));
            methodKey = apiOperationMetadata.methodKey;
            this.tryInjectApiRequestFiledValue(arguments, evaluationContext, methodKey);
        }
        if (needParam) {
            if (methodKey == null) {
                ApiOperationMetadata apiOperationMetadata = this.metadataCache.get(new ApiOperationCacheKey(method, targetClass));
                methodKey = apiOperationMetadata.methodKey;
            }
            if (evaluationContext == null) {
                evaluationContext = this.createEvaluationContext(target, targetClass, method, arguments);
            }
            this.tryInjectParameterValue(arguments, method.getParameters(), evaluationContext, methodKey);
        }

        return evaluationContext;
    }


    /**
     * 尝试签名验证
     *
     * @param target      被代理的目标对象
     * @param targetClass 被代理的目标对象类类型
     * @param method      被代理的方法
     * @param arguments   被代理的方法参数
     * @throws ConstraintViolationException 验证异常
     */
    protected void tryValidationParams(Object target,
                                       Class<?> targetClass,
                                       Method method,
                                       Object[] arguments) throws ConstraintViolationException {
        // Avoid Validator invocation on FactoryBean.getObjectType/isSingleton
        if (isFactoryBeanMetadataMethod(method)) {
            return;
        }

        Optional<Object> optional = Arrays.stream(arguments)
                .filter(Objects::nonNull)
                .filter(o -> ApiRequest.class.isAssignableFrom(o.getClass()))
                .findFirst();
        Set<ConstraintViolation<Object>> result;
        if (optional.isPresent()) {
            result = this.validator.validate(optional.get(), Default.class);
            if (!result.isEmpty()) {
                throw new ConstraintViolationException(result);
            }
        }

        Class<?>[] groups = determineValidationGroups(method, targetClass);

        // Standard Bean Validation 1.1 API
        ExecutableValidator execVal = this.validator.forExecutables();
        Method methodToValidate = method;

        try {
            result = execVal.validateParameters(
                    target, methodToValidate, arguments, groups);
        } catch (IllegalArgumentException ex) {
            // Probably a generic type mismatch between interface and impl as reported in SPR-12237 / HV-1011
            // Let's try to find the bridged method on the implementation class...
            methodToValidate = BridgeMethodResolver.findBridgedMethod(
                    ClassUtils.getMostSpecificMethod(method, targetClass));
            result = execVal.validateParameters(
                    target, methodToValidate, arguments, groups);
        }
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    /**
     * Determine the validation groups to validate against for the given method invocation.
     * <p>Default are the validation groups as specified in the {@link Validated} annotation
     * on the containing target class of the method.
     *
     * @param method      the current MethodInvocation
     * @param targetClass the current MethodInvocation
     * @return the applicable validation groups as a Class array
     */
    protected Class<?>[] determineValidationGroups(Method method, Class<?> targetClass) {
        Validated validatedAnn = AnnotationUtils.findAnnotation(method, Validated.class);
        if (validatedAnn == null) {
            validatedAnn = AnnotationUtils.findAnnotation(targetClass, Validated.class);
        }
        return (validatedAnn != null ? validatedAnn.value() : new Class<?>[0]);
    }

    /**
     * 签名验证
     *
     * @param args       方法参数
     * @param parameters 方法参数类型
     * @throws ApiSignatureException 接口签名验证异常
     */
    protected void checkApiSignature(Object[] args,
                                     Parameter[] parameters) throws ApiSignatureException {
        ApiSignatureStrategy apiSignatureStrategy = this.apiSignatureStrategy;
        if (apiSignatureStrategy == null) {
            return;
        }

        Object request = getClazzTypeObject(args, this.checkSignatureSupperClazz);
        InternalApiSignatureRequest signatureRequest;

        if (request == null) {
            // 非签名对象的子类或聚合参数列表
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            if (httpServletRequest == null) {
                return;
            }
            signatureRequest = new InternalApiSignatureRequest(httpServletRequest);
            Map<String, Object> map = new HashMap<>();
            int length = parameters.length;
            for (int i = 0; i < length; i++) {
                Parameter parameter = parameters[i];
                ApiSignature apiSignature = parameter.getAnnotation(ApiSignature.class);
                if (apiSignature == null) {
                    continue;
                }
                String name = apiSignature.name();
                if (StringUtils.isEmpty(name)) {
                    name = parameter.getName();
                }
                map.put(name, args[i]);
            }
            signatureRequest.setApiSignatureValues(map);
        } else {
            ApiSignatureRequest apiSignatureRequest = (ApiSignatureRequest) request;
            Map<String, Object> signatureMap = this.getSignatureMap((ApiSignatureRequest) request);
            signatureRequest = new InternalApiSignatureRequest();
            signatureRequest.setAppId(apiSignatureRequest.getAppId());
            signatureRequest.setNonceStr(apiSignatureRequest.getNonceStr());
            signatureRequest.setApiSignature(apiSignatureRequest.getApiSignature());
            signatureRequest.setTimeStamp(apiSignatureRequest.getTimeStamp());
            signatureRequest.setChannelCode(apiSignatureRequest.getChannelCode());
            signatureRequest.setApiSignatureValues(signatureMap);
        }

        apiSignatureStrategy.check(signatureRequest);

    }


    /**
     * 尝试记录日志
     *
     * @param targetClass       被代理的类类型
     * @param method            目标方法
     * @param result            方法执行结果  如果执行异常则为空
     * @param evaluationContext 执行上下文
     * @param throwable         方法执行异常
     */
    protected void tryRecordLog(Class<?> targetClass,
                                Method method,
                                Object result,
                                EvaluationContext evaluationContext,
                                Throwable throwable) {
        ApiLogRecorder apiLogRecorder = this.apiLogRecorder;
        if (apiLogRecorder == null) {
            return;
        }
        ApiOperationCacheKey cacheKey = new ApiOperationCacheKey(method, targetClass);
        ApiOperationMetadata apiOperationMetadata = this.metadataCache.get(cacheKey);
        ApiLog apiLog = apiOperationMetadata.targetMethod.getAnnotation(ApiLog.class);
        if (apiLog == null) {
            return;
        }
        if (result != null) {
            ((ApiEvaluationContext) evaluationContext).setEvaluationContextMethodResult(result);
        }

        HttpServletRequest httpServletRequest = getHttpServletRequest();
        //使用异步队列记录日志
        this.threadPoolTaskScheduler.execute(() -> {
            ApiLogModel apiLogModel = genApiLogModel(apiLog, httpServletRequest, apiOperationMetadata, evaluationContext);
            SimpleApiAction simpleApiAction = SimpleApiAction.valueOfByMethod(method);
            if (simpleApiAction != null) {
                apiLogModel.setAction(simpleApiAction.name());
            }
            AbstractApiAspectSupport.this.apiLogRecorder.log(apiLogModel, evaluationContext, throwable);
        });
    }

    /**
     * 创建 Spel执行上下文
     *
     * @param target      被代理的目标对象
     * @param targetClass 被代理的目标对象类类型
     * @param method      被代理的方法
     * @param arguments   被代理的方法参数
     * @return Spel执行上下文
     */
    protected EvaluationContext createEvaluationContext(Object target,
                                                        Class<?> targetClass,
                                                        Method method,
                                                        Object[] arguments) {

        ApiOperationCacheKey cacheKey = new ApiOperationCacheKey(method, targetClass);
        Map<ApiOperationCacheKey, ApiOperationMetadata> metadataCache = this.metadataCache;
        ApiOperationMetadata apiOperationMetadata = metadataCache.get(cacheKey);

        if (apiOperationMetadata == null) {
            apiOperationMetadata = new ApiOperationMetadata(method, targetClass);
            metadataCache.put(cacheKey, apiOperationMetadata);
        }

        EvaluationContext evaluationContext = this.evaluator.createEvaluationContext(
                method,
                arguments,
                target,
                targetClass,
                apiOperationMetadata.targetMethod,
                this.beanFactory);
        //注入参数
        HttpServletRequest httpServletRequest = this.getHttpServletRequest();
        if (httpServletRequest != null) {
            fillRequestContext(evaluationContext, httpServletRequest);
        }
        return evaluationContext;
    }

    /**
     * 填充 request context
     *
     * @param evaluationContext  Spel 上下文
     * @param httpServletRequest HttpServletRequest
     */
    protected void fillRequestContext(EvaluationContext evaluationContext, HttpServletRequest httpServletRequest) {
        if (apiRequestContextFactory == null) {
            return;
        }
        Map<String, Object> context = apiRequestContextFactory.factory(httpServletRequest);

        context.put(APP_ID_KEY, httpServletRequest.getHeader(APP_ID_HEADER_KEY));
        context.put(NONCE_STR_KEY, httpServletRequest.getHeader(NONCE_STR_HEADER_KEY));
        context.put(CHANNEL_CODE, httpServletRequest.getHeader(CHANNEL_CODE_HEADER_KEY));
        context.put(APP_SIGNATURE_KEY, httpServletRequest.getHeader(APP_SIGN_HEADER_KEY));
        String timeStamp = httpServletRequest.getHeader(TIME_STAMP_HEADER_KEY);
        if (StringUtils.hasText(timeStamp)) {
            context.put(TIME_STAMP, Long.parseLong(timeStamp));
        }
        context.forEach(evaluationContext::setVariable);
    }


    /**
     * 获取签名需要 map
     *
     * @param request 签名请求对象
     * @return 用于签名的Map（经过字典排序）
     */
    private Map<String, Object> getSignatureMap(ApiSignatureRequest request) {
        Class<? extends ApiSignatureRequest> aClass = request.getClass();
        final Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getFields(aClass);

        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(ApiSignature.class))
                .sorted()
                .forEach(field -> {
                    ApiSignature apiSignature = field.getAnnotation(ApiSignature.class);
                    String name = apiSignature.name();
                    if (StringUtils.isEmpty(name)) {
                        name = field.getName();
                    }
                    map.put(name, ReflectionUtils.getField(field, this));
                });
        return map;
    }

    /**
     * 尝试注入继承了 {@code injectSupperClazz}的子类
     *
     * @param args              被拦截的方法参数
     * @param evaluationContext 被拦截的执行上下文
     * @param methodKey         方法元数据key
     */
    private void tryInjectApiRequestFiledValue(Object[] args,
                                               EvaluationContext evaluationContext,
                                               AnnotatedElementKey methodKey) {
        Object request = getClazzTypeObject(args, this.injectSupperClazz);
        if (request == null) {
            return;
        }

        Class<?> aClass = request.getClass();
        Field[] fields = getFields(aClass);
        evaluationContext.setVariable(REQUEST_OBJECT_VARIABLE, request);
        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(InjectField.class))
                .forEach(field -> {
                    InjectField injectField = field.getAnnotation(InjectField.class);
                    evaluationContext.setVariable(CURRENT_VALUE_VARIABLE, ReflectionUtils.getField(field, request));
                    boolean condition = evaluator.condition(injectField.condition(), methodKey, evaluationContext);
                    if (condition) {
                        try {
                            Object value = evaluator.value(injectField.value(), methodKey, evaluationContext);
                            ReflectionUtils.setField(field, request, value);
                        } catch (Exception e) {
                            if (log.isDebugEnabled()) {
                                log.debug("参数注入失败 {}", injectField.value(), e);
                            }
                        }
                    }
                    evaluationContext.setVariable(CURRENT_VALUE_VARIABLE, null);
                });
        evaluationContext.setVariable(REQUEST_OBJECT_VARIABLE, null);

    }

    /**
     * 根据类类型获取参数对象
     *
     * @param args  被拦截发方法参数
     * @param clazz 预期的类类型
     * @return 和 {@param clazz} 类类型相同的对象
     */
    private Object getClazzTypeObject(Object[] args, Class<?> clazz) {
        return Arrays.stream(args)
                .filter(Objects::nonNull)
                .filter(o -> clazz.isAssignableFrom(o.getClass()))
                .findFirst()
                .orElse(null);
    }


    /**
     * 尝试注入简单对象参数的值
     *
     * @param args              被拦截的方法参数
     * @param parameters        被拦截的方法参数定义
     * @param evaluationContext 被拦截的执行上下文
     * @param methodKey         方法元数据key
     */
    private void tryInjectParameterValue(Object[] args,
                                         Parameter[] parameters,
                                         EvaluationContext evaluationContext,
                                         AnnotatedElementKey methodKey) {
        ApiOperationExpressionEvaluator evaluator = this.evaluator;
        int length = parameters.length;
        for (int i = 0; i < length; i++) {
            InjectField injectField = parameters[i].getAnnotation(InjectField.class);
            if (injectField == null) {
                continue;
            }
            evaluationContext.setVariable(CURRENT_VALUE_VARIABLE, args[i]);
            boolean condition = evaluator.condition(injectField.condition(), methodKey, evaluationContext);
            if (condition) {
                args[i] = evaluator.value(injectField.value(), methodKey, evaluationContext);
            }
            evaluationContext.setVariable(CURRENT_VALUE_VARIABLE, null);
        }

    }

    /**
     * 获取 api log model
     *
     * @param apiLog               日志注解的示例
     * @param request              HttpServletRequest 示例
     * @param apiOperationMetadata 接口方法元数据
     * @param evaluationContext    被拦截方法执行上下文
     * @return api log 数据模型，用于记录日志 {@link ApiLogRecorder}
     */
    private ApiLogModel genApiLogModel(ApiLog apiLog,
                                       HttpServletRequest request,
                                       ApiOperationMetadata apiOperationMetadata,
                                       EvaluationContext evaluationContext) {

        ApiOperationExpressionEvaluator evaluator = this.evaluator;
        Method targetMethod = apiOperationMetadata.targetMethod;
        AnnotatedElementKey methodKey = apiOperationMetadata.methodKey;

        ApiLogModel apiLogModel = new ApiLogModel();
        apiLogModel.setContent(evaluator.log(apiLog.value(), methodKey, evaluationContext));
        Object ip = evaluationContext.lookupVariable(REQUEST_IP_VARIABLE);
        if (ip == null) {
            ip = IpAddressUtils.try2GetUserRealIPAddr(request);
        }
        apiLogModel.setIp(ip.toString());
        apiLogModel.setClientUserAgent(request.getHeader(USER_AGENT_HEADER));

        Operation operation = targetMethod.getAnnotation(Operation.class);
        boolean operationIsNotNull = operation != null;
        String type = apiLog.type();
        if (StringUtils.isEmpty(type) && operationIsNotNull) {
            type = operation.method();
        }
        String action = apiLog.action();
        if (StringUtils.isEmpty(action) && operationIsNotNull) {
            action = operation.summary();
        }
        String uri = request.getRequestURI();
        apiLogModel.setUri(uri);
        apiLogModel.setAction(action);
        apiLogModel.setType(type);
        if (operationIsNotNull) {
            apiLogModel.setMethodDesc(operation.summary());
        } else {
            apiLogModel.setMethodDesc(uri);
        }

        Object authentication = evaluationContext.lookupVariable(AUTHENTICATE);
        if (authentication != null) {
            apiLogModel.setAuthentication(authentication);
        }

        return apiLogModel;

    }


    protected Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

    /**
     * Clear the cached metadata.
     */
    protected void clearMetadataCache() {
        this.metadataCache.clear();
        this.fieldCache.clear();
        this.evaluator.clear();
    }


    protected HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }


    /**
     * 获取一个类所有的 Field，优先从缓存中获取，包括私有的Field，并且会递归的获取超类的字段
     *
     * @param aClass 类类型
     * @return fields
     */
    private Field[] getFields(Class<?> aClass) {
        Map<Class<?>, Field[]> fieldCache = this.fieldCache;
        Field[] cacheFields = fieldCache.get(aClass);
        if (cacheFields == null) {
            cacheFields = this.getAllFields(aClass)
                    .stream()
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .toArray(Field[]::new);
            Field.setAccessible(cacheFields, true);
            fieldCache.put(aClass, cacheFields);
        }
        return cacheFields;
    }

    /**
     * 获取一个类所有的 Field，包括私有的Field，并且会递归的获取超类的字段
     *
     * @param aClass 类类型
     * @return fields
     */
    private List<Field> getAllFields(Class<?> aClass) {
        List<Field> fields = new ArrayList<>(16);
        fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
        Class<?> superclass = aClass.getSuperclass();
        if (!Object.class.equals(superclass)) {
            fields.addAll(this.getAllFields(superclass));
        }
        return fields;
    }


    private boolean isFactoryBeanMetadataMethod(Method method) {
        Class<?> clazz = method.getDeclaringClass();

        // Call from interface-based proxy handle, allowing for an efficient check?
        if (clazz.isInterface()) {
            return ((clazz == FactoryBean.class || clazz == SmartFactoryBean.class) &&
                    !"getObject".equals(method.getName()));
        }

        // Call from CGLIB proxy handle, potentially implementing a FactoryBean method?
        Class<?> factoryBeanType = null;
        if (SmartFactoryBean.class.isAssignableFrom(clazz)) {
            factoryBeanType = SmartFactoryBean.class;
        } else if (FactoryBean.class.isAssignableFrom(clazz)) {
            factoryBeanType = FactoryBean.class;
        }
        return (factoryBeanType != null && !"getObject".equals(method.getName()) &&
                ClassUtils.hasMethod(factoryBeanType, method.getName(), method.getParameterTypes()));
    }


    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        lazyInit();
    }

    @Override
    public void destroy() {
        this.clearMetadataCache();
    }

//    @Override
//    public void afterSingletonsInstantiated() {
//        lazyInit();
//    }

    private void lazyInit() throws BeansException {
        if (this.apiRequestContextFactory == null) {
            try {
                this.setApiRequestContextFactory(this.beanFactory.getBean(ApiRequestContextFactory.class));
            } catch (BeansException e) {
                log.warn("not found ApiRequestContextFactory Bean");
            }
        }
        if (this.apiSignatureStrategy == null) {
            ApiSignatureStrategy bean;
            try {
                bean = this.beanFactory.getBean(ApiSignatureStrategy.class);
                this.setApiSignatureStrategy(bean);
            } catch (BeansException e) {
                log.warn("not found ApiSignatureStrategy Bean");
            }
        }
        if (this.apiLogRecorder == null) {
            try {
                ApiLogRecorder apiLogRecorder = this.beanFactory.getBean(ApiLogRecorder.class);
                this.setApiLogRecorder(apiLogRecorder);
            } catch (BeansException e) {
                log.warn("not found ApiLogRecorder Bean");
            }
        }
        if (this.threadPoolTaskScheduler == null) {
            this.setThreadPoolTaskScheduler(this.beanFactory.getBean(ThreadPoolTaskScheduler.class));
        }

        if (this.validator == null) {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
    }

    /**
     * Metadata of a cache operation that does not depend on a particular invocation
     * which makes it a good candidate for caching.
     */
    protected static class ApiOperationMetadata {


        protected final Method method;

        protected final Class<?> targetClass;

        protected final Method targetMethod;

        protected final AnnotatedElementKey methodKey;

        public ApiOperationMetadata(Method method, Class<?> targetClass) {

            this.method = BridgeMethodResolver.findBridgedMethod(method);
            this.targetClass = targetClass;
            this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
                    AopUtils.getMostSpecificMethod(method, targetClass) : this.method);
            this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
        }
    }


    protected static final class ApiOperationCacheKey implements Comparable<AbstractApiAspectSupport.ApiOperationCacheKey> {

        private final AnnotatedElementKey methodCacheKey;

        protected ApiOperationCacheKey(Method method, Class<?> targetClass) {

            this.methodCacheKey = new AnnotatedElementKey(method, targetClass);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AbstractApiAspectSupport.ApiOperationCacheKey)) {
                return false;
            }
            AbstractApiAspectSupport.ApiOperationCacheKey otherKey = (AbstractApiAspectSupport.ApiOperationCacheKey) other;
            return this.methodCacheKey.equals(otherKey.methodCacheKey);
        }

        @Override
        public int hashCode() {
            return (this.methodCacheKey.hashCode() * 31 + this.methodCacheKey.hashCode());
        }

        @Override
        public String toString() {
            return this.methodCacheKey.toString();
        }

        @Override
        public int compareTo(AbstractApiAspectSupport.ApiOperationCacheKey other) {
            return this.methodCacheKey.compareTo(other.methodCacheKey);
        }
    }


}