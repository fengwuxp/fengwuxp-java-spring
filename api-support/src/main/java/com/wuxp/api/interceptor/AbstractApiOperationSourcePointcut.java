package com.wuxp.api.interceptor;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 抽象的 接口操作切点
 *
 * @author wxup
 */
public abstract class AbstractApiOperationSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {


    public AbstractApiOperationSourcePointcut() {
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        // Avoid Validator invocation on FactoryBean.getObjectType/isSingleton
        if (isFactoryBeanMetadataMethod(method, targetClass)) {
            return false;
        }
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

    private boolean isFactoryBeanMetadataMethod(Method method, Class<?> clazz) {

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
}
