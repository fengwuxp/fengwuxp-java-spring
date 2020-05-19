package com.wuxp.api.interceptor;

import com.wuxp.api.ApiRequest;
import com.wuxp.api.context.ApiRequestContextFactory;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


/**
 * 用于测试方法拦截
 *
 * @author wxup
 */
@Slf4j
public class TestMethodApiInterceptor extends ApiInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object target = invocation.getThis();
        Class<?> targetClass = this.getTargetClass(target);
        Object[] arguments = invocation.getArguments();
        Method method = invocation.getMethod();

        // 构建spel 执行上下文
        EvaluationContext evaluationContext = this.createEvaluationContext(target, targetClass, method, arguments);

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

        // 尝试参数注入
        this.tryInjectParamsValue(target, targetClass, method, arguments);

        // 参数验证
        this.tryValidationParams(target, targetClass, method, arguments);

        Object result;
        try {
            result = invocation.proceed();
            this.tryRecordLog(targetClass, method, result, evaluationContext, null);
        } catch (Throwable throwable) {
            this.tryRecordLog(targetClass, method, null, evaluationContext, null);
            throw throwable;
        }

        return result;
    }


    @Override
    protected void fillRequestContext(EvaluationContext evaluationContext, HttpServletRequest httpServletRequest) {
        ApiRequestContextFactory apiRequestContextFactory = this.apiRequestContextFactory;
        if (apiRequestContextFactory == null) {
            return;
        }
        Map<String, Object> context = apiRequestContextFactory.factory(httpServletRequest);
        if (context != null) {
            context.forEach(evaluationContext::setVariable);
        }

    }
}
