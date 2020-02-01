package com.wuxp.api.interceptor;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.wuxp.api.ApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import static com.wuxp.api.ApiRequest.*;

/**
 * 用于测试方法拦截
 */
@Slf4j
public class TestMethodApiInterceptor extends ApiAspectSupport implements MethodInterceptor, Serializable {

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
}
