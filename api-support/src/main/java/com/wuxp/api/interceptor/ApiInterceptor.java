package com.wuxp.api.interceptor;

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
        // 构建spel 执行上下文
        EvaluationContext evaluationContext = this.createEvaluationContext(method, arguments, target, targetClass);

        // 尝试参数注入
        this.tryInjectParamsValue(method, arguments, targetClass, evaluationContext);

        // 参数验证
        this.tryValidationParams(target,targetClass,method, arguments);

        // 签名验证
        this.checkApiSignature(arguments, method.getParameters());

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
