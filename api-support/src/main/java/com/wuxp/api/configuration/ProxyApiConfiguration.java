package com.wuxp.api.configuration;

import com.wuxp.api.interceptor.AnnotationApiOperationSource;
import com.wuxp.api.interceptor.ApiInterceptor;
import com.wuxp.api.interceptor.ApiOperationSource;
import com.wuxp.api.interceptor.BeanFactoryApiOperationSourceAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ProxyApiConfiguration implements ImportAware {


    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {

    }


    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnMissingBean
    public BeanFactoryApiOperationSourceAdvisor beanFactoryApiOperationSourceAdvisor() {
        BeanFactoryApiOperationSourceAdvisor advisor = new BeanFactoryApiOperationSourceAdvisor();
        advisor.setApiOperationSource(apiOperationSource());
        advisor.setAdvice(apiInterceptor());
//        if (this.enableApiSupport != null) {
//            advisor.setOrder(this.enableApiSupport.<Integer>getNumber("order"));
//        }
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApiOperationSource apiOperationSource() {
        return new AnnotationApiOperationSource();
    }

    @Bean
    @ConditionalOnMissingBean(ApiInterceptor.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApiInterceptor apiInterceptor() {
        return new ApiInterceptor();
    }


    @Bean
    @ConditionalOnMissingBean(ThreadPoolTaskScheduler.class)
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
    }


//    @Bean
//    @ConditionalOnMissingBean(Validator.class)
//    public Validator validator() {
//        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//                .configure()
//                //开启快速校验--默认校验所有参数，false校验全部
//                .addProperty("hibernate.validator.fail_fast", "true")
//                .buildValidatorFactory();
//
//        return validatorFactory.getValidator();
//    }
}
