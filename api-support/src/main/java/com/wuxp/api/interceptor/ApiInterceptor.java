package com.wuxp.api.interceptor;

import com.wuxp.api.log.ApiLog;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;

import java.io.Serializable;
import java.lang.reflect.Method;


/**
 * 用于拦截方法
 */
@Slf4j
public class ApiInterceptor extends ApiAspectSupport implements MethodInterceptor, Serializable {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object target = invocation.getThis();
        Class<?> targetClass = this.getTargetClass(target);
        Object[] arguments = invocation.getArguments();
        Method method = invocation.getMethod();

        // 尝试参数注入
        EvaluationContext evaluationContext = this.tryInjectParamsValue(method, arguments, targetClass, target);
        // 参数验证
        this.tryValidationParams(target, targetClass, method, arguments);

        // 签名验证
        this.checkApiSignature(arguments, method.getParameters());

        Object result = null;
        try {
            result = invocation.proceed();
        } catch (Throwable throwable) {
            if (evaluationContext == null) {
                evaluationContext = this.createEvaluationContext(method, arguments, target, targetClass);
            }
            this.tryRecordLog(method, targetClass, evaluationContext, null, throwable);
            throw throwable;
        }
        if (method.isAnnotationPresent(ApiLog.class)) {
            if (evaluationContext == null) {
                evaluationContext = this.createEvaluationContext(method, arguments, target, targetClass);
            }
            this.tryRecordLog(method, targetClass, evaluationContext, result, null);
        }

        return result;
    }
}
