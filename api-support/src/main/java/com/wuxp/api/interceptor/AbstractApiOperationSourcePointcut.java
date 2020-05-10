package com.wuxp.api.interceptor;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 抽象的 接口操作切点
 * @author wxup
 */
public abstract class AbstractApiOperationSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {


    public AbstractApiOperationSourcePointcut() {
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        ApiOperationSource apiOperationSource = getApiOperationSource();
        return Objects.requireNonNull(apiOperationSource).isCandidateClass(method, targetClass);
    }

    /**
     * Obtain the underlying {@link ApiOperationSource} (may be {@code null}).
     * To be implemented by subclasses.
     *
     * @return ApiOperationSource
     */
    @Nullable
    protected abstract ApiOperationSource getApiOperationSource();
}
