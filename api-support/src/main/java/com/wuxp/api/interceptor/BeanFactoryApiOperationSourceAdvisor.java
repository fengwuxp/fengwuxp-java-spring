package com.wuxp.api.interceptor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author wxup
 */
@Slf4j
public class BeanFactoryApiOperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {


    @Getter
    private final ApiOperationSource apiOperationSource;

    public BeanFactoryApiOperationSourceAdvisor(ApiOperationSource apiOperationSource) {
        this.apiOperationSource = apiOperationSource;
    }

    @Getter
    private final AbstractApiOperationSourcePointcut pointcut = new AbstractApiOperationSourcePointcut() {

        @Override
        protected ApiOperationSource getApiOperationSource() {
            return apiOperationSource;
        }
    };




}
