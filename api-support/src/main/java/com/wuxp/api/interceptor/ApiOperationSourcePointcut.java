package com.wuxp.api.interceptor;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.reflect.Method;

abstract class ApiOperationSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {


    protected ApiOperationSourcePointcut() {
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        ApiOperationSource apiOperationSource = getApiOperationSource();
        return apiOperationSource.isCandidateClass(method, targetClass);
    }

    /**
     * Obtain the underlying {@link ApiOperationSource} (may be {@code null}).
     * To be implemented by subclasses.
     */
    @Nullable
    protected abstract ApiOperationSource getApiOperationSource();
}
