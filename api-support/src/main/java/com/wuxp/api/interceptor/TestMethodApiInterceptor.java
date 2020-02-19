package com.wuxp.api.interceptor;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.wuxp.api.ApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static com.wuxp.api.ApiRequest.*;
import static com.wuxp.api.signature.ApiSignatureRequest.APP_SIGNATURE_KEY;
import static com.wuxp.api.signature.InternalApiSignatureRequest.*;
import static com.wuxp.api.signature.InternalApiSignatureRequest.TIME_STAMP_HEADER_KEY;

/**
 * 用于测试方法拦截
 */
@Slf4j
public class TestMethodApiInterceptor extends ApiAspectSupport implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object target = invocation.getThis();
        Class<?> targetClass = this.getTargetClass(target);
        Object[] arguments = invocation.getArguments();
        Method method = invocation.getMethod();
        // 构建spel 执行上下文
        EvaluationContext evaluationContext = this.createEvaluationContext(method, arguments, target, targetClass);

        //注入 appId等参数
        Class<?> injectSupperClazz = ApiRequest.class;
        Object request = Arrays.stream(arguments)
                .filter(Objects::nonNull)
                .filter(o -> injectSupperClazz.isAssignableFrom(o.getClass()))
                .findFirst()
                .orElse(null);
        if (request == null) {
            return invocation.proceed();
        }

        MethodAccess methodAccess = MethodAccess.get(request.getClass());
        methodAccess.invoke(request, "setAppId", evaluationContext.lookupVariable(APP_ID_KEY));
        methodAccess.invoke(request, "setNonceStr", evaluationContext.lookupVariable(NONCE_STR_KEY));
        methodAccess.invoke(request, "setTimeStamp", evaluationContext.lookupVariable(TIME_STAMP));
        methodAccess.invoke(request, "setApiSignature", evaluationContext.lookupVariable("apiSignature"));


        // 尝试参数注入
        this.tryInjectParamsValue(method, arguments, targetClass, evaluationContext);

        // 参数验证
        this.tryValidationParams(target, targetClass, method, arguments);

        // 签名验证
//        this.checkApiSignature(arguments, method.getParameters());

        Object result = null;
        try {
            result = invocation.proceed();
            this.tryRecordLog(method, targetClass, evaluationContext, result, null);
        } catch (Throwable throwable) {
            this.tryRecordLog(method, targetClass, evaluationContext, null, throwable);
            throw throwable;
        }

        return result;
    }

    /**
     * 创建 spel执行上下文
     *
     * @param method
     * @param args
     * @param target
     * @return
     */
    @Override
    protected EvaluationContext createEvaluationContext(Method method,
                                                        Object[] args,
                                                        Object target,
                                                        Class<?> targetClass) {
        EvaluationContext evaluationContext = super.createEvaluationContext(method, args, target, targetClass);
        evaluationContext.setVariable(APP_ID_KEY, "mock_app_id");
        evaluationContext.setVariable(APP_SECRET_KEY, "mock_s");
        evaluationContext.setVariable(NONCE_STR_KEY, RandomStringUtils.randomAlphabetic(32));
        evaluationContext.setVariable(TIME_STAMP, System.currentTimeMillis());
        evaluationContext.setVariable("apiSignature", RandomStringUtils.randomAlphabetic(32));
        return evaluationContext;
    }
}
