package com.wuxp.api.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.Nullable;

@Slf4j
public class BeanFactoryApiOperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {


    @Nullable
    private ApiOperationSource apiOperationSource;

    private final ApiOperationSourcePointcut pointcut = new ApiOperationSourcePointcut() {

        @Override
        protected ApiOperationSource getApiOperationSource() {
            return apiOperationSource;
        }
    };


    public void setApiOperationSource(@Nullable ApiOperationSource apiOperationSource) {
        this.apiOperationSource = apiOperationSource;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
