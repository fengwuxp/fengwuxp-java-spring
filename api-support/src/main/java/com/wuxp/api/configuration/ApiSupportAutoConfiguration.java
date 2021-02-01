package com.wuxp.api.configuration;

import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.exception.BusinessExceptionFactory;
import com.wuxp.api.exception.DefaultBusinessExceptionFactory;
import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.api.interceptor.AnnotationApiOperationSource;
import com.wuxp.api.interceptor.ApiInterceptor;
import com.wuxp.api.interceptor.ApiOperationSource;
import com.wuxp.api.interceptor.BeanFactoryApiOperationSourceAdvisor;
import com.wuxp.api.restful.DefaultRestfulApiRespImpl;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.api.signature.ApiSignatureStrategy;
import com.wuxp.api.signature.AppInfoStore;
import com.wuxp.api.signature.Md5ApiSignatureStrategy;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;


/**
 * @author wxup
 */
@Configuration
@EnableConfigurationProperties(WuxpApiSupportProperties.class)
@ConditionalOnProperty(prefix = WuxpApiSupportProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ApiSupportAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ApiInterceptor.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApiInterceptor apiInterceptor() {
        return new ApiInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(ApiOperationSource.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ApiOperationSource apiOperationSource() {
        return new AnnotationApiOperationSource();
    }

    @Bean
    @ConditionalOnBean(value = {ApiOperationSource.class, ApiInterceptor.class})
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryApiOperationSourceAdvisor beanFactoryApiOperationSourceAdvisor(ApiInterceptor apiInterceptor, ApiOperationSource apiOperationSource) {
        BeanFactoryApiOperationSourceAdvisor advisor = new BeanFactoryApiOperationSourceAdvisor(apiOperationSource);
        advisor.setAdvice(apiInterceptor);
        return advisor;
    }




    @Bean
    @ConditionalOnProperty(
            prefix = WuxpApiSupportProperties.PREFIX,
            name = "enabled-api-signature",
            havingValue = "true",
            matchIfMissing = true
    )
    @ConditionalOnMissingBean(ApiSignatureStrategy.class)
    @ConditionalOnBean(AppInfoStore.class)
    public ApiSignatureStrategy apiSignatureStrategy() {
        return new Md5ApiSignatureStrategy();
    }


    @Bean
    @ConditionalOnMissingBean(Validator.class)
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //开启快速校验--默认校验所有参数，false校验全部
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }

    @Bean
    @ConditionalOnMissingBean(SpringContextHolder.class)
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean(BusinessExceptionFactory.class)
    public BusinessExceptionFactory<Integer> businessExceptionFactory() {
        return DefaultBusinessExceptionFactory.DEFAULT_EXCEPTION_FACTORY;
    }

    /**
     * 初始化异常相关处理信息
     */
    @PostConstruct
    public void init() {
        BusinessExceptionFactory<? extends Serializable> businessExceptionFactory = this.businessExceptionFactory();
        Assert.notNull(businessExceptionFactory, "BusinessExceptionFactory is null");
        AssertThrow.setBusinessExceptionFactory(businessExceptionFactory);
        RestfulApiRespFactory.setBusinessExceptionFactory(businessExceptionFactory);
        DefaultRestfulApiRespImpl.setBusinessSuccessCode(businessExceptionFactory.getSuccessCode());
    }

}
