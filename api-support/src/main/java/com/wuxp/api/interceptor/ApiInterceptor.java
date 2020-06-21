package com.wuxp.api.interceptor;

import com.wuxp.api.log.ApiLog;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.lang.reflect.Method;


/**
 * 用于拦截方法
 *
 * @author wxup
 */
@Slf4j
public class ApiInterceptor extends AbstractApiAspectSupport implements MethodInterceptor, Serializable {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object target = invocation.getThis();
        Class<?> targetClass = this.getTargetClass(target);
        Object[] arguments = invocation.getArguments();
        Method method = invocation.getMethod();

        // 尝试参数注入
        EvaluationContext evaluationContext = this.tryInjectParamsValue(target, targetClass, method, arguments);

        // 参数验证
        this.tryValidationParams(target, targetClass, method, arguments);

        if (targetClass.isAnnotationPresent(RestController.class) || method.isAnnotationPresent(ResponseBody.class)) {
            //ResponseBody 需要验证签名验证
            this.checkApiSignature(arguments, method.getParameters());
        }

        Object result;
        try {
            result = invocation.proceed();
        } catch (Throwable throwable) {
            if (evaluationContext == null) {
                evaluationContext = this.createEvaluationContext(target, targetClass, method, arguments);
            }
            this.tryRecordLog(targetClass, method, null, evaluationContext, throwable);
            throw throwable;
        }
        if (method.isAnnotationPresent(ApiLog.class)) {
            if (evaluationContext == null) {
                evaluationContext = this.createEvaluationContext(target, targetClass, method, arguments);
            }
            this.tryRecordLog(targetClass, method, result, evaluationContext, null);
        }

        return result;
    }

}
