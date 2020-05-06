package com.wuxp.api.configuration;

import com.wuxp.api.interceptor.AnnotationApiOperationSource;
import com.wuxp.api.interceptor.ApiInterceptor;
import com.wuxp.api.interceptor.ApiOperationSource;
import com.wuxp.api.interceptor.BeanFactoryApiOperationSourceAdvisor;
import com.wuxp.api.signature.ApiSignatureStrategy;
import com.wuxp.api.signature.MD5ApiSignatureStrategy;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@Configuration
@EnableConfigurationProperties(WuxpApiSupportProperties.class)
@ConditionalOnProperty(prefix = WuxpApiSupportProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ApiSupportAutoConfiguration implements ImportAware {


    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {

    }


    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnBean(ApiInterceptor.class)
    public BeanFactoryApiOperationSourceAdvisor beanFactoryApiOperationSourceAdvisor(ApiInterceptor apiInterceptor) {
        BeanFactoryApiOperationSourceAdvisor advisor = new BeanFactoryApiOperationSourceAdvisor();
        advisor.setApiOperationSource(apiOperationSource());
        advisor.setAdvice(apiInterceptor);
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

    @Bean
    @ConditionalOnProperty(
            prefix = WuxpApiSupportProperties.PREFIX,
            name = "enabled-api-signature",
            havingValue = "true",
            matchIfMissing = true
    )
    @ConditionalOnMissingBean(ApiSignatureStrategy.class)
    public ApiSignatureStrategy mD5ApiSignatureStrategy() {
        return new MD5ApiSignatureStrategy();
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
