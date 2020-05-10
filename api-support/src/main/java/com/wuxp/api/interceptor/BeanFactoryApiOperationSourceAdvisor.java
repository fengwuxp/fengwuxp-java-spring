package com.wuxp.api.interceptor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.Nullable;

@Slf4j
@Setter
public class BeanFactoryApiOperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {


    @Nullable
    private ApiOperationSource apiOperationSource;

    private final AbstractApiOperationSourcePointcut pointcut = new AbstractApiOperationSourcePointcut() {

        @Override
        protected ApiOperationSource getApiOperationSource() {
            return apiOperationSource;
        }
    };


    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }


}
