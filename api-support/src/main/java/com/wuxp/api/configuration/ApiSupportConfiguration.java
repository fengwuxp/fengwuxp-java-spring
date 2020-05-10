package com.wuxp.api.configuration;

import com.wuxp.api.interceptor.*;
import com.wuxp.api.signature.ApiSignatureStrategy;
import com.wuxp.api.signature.AppInfoStore;
import com.wuxp.api.signature.Md5ApiSignatureStrategy;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 * @author wxup
 */
@Configuration
@EnableConfigurationProperties(WuxpApiSupportProperties.class)
//@ImportResource("classpath:/api-support.xml")
@ConditionalOnProperty(prefix = WuxpApiSupportProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ApiSupportConfiguration {

    /**
     * 在多个表达式之间使用  || , or 表示  或 ，使用  && , and 表示  与 ， ！ 表示 非
     */
    private static final String DEFAULT_POINT_CUT = StringUtils.join(
            "@annotation(org.springframework.web.bind.annotation.GetMapping) ",
            "or @annotation(org.springframework.web.bind.annotation.PostMapping) ",
            "or @annotation(org.springframework.web.bind.annotation.PutMapping) ",
            "or @annotation(org.springframework.web.bind.annotation.DeleteMapping) ",
            "or @annotation(org.springframework.web.bind.annotation.RequestMapping)"
    );

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
        BeanFactoryApiOperationSourceAdvisor advisor = new BeanFactoryApiOperationSourceAdvisor();
        advisor.setAdvice(apiInterceptor);
        advisor.setApiOperationSource(apiOperationSource);
        return advisor;
    }


//    @Bean
//    @ConditionalOnBean(ApiInterceptor.class)
//    public DefaultPointcutAdvisor logAopPointCutAdvice() {
//        //声明一个AspectJ切点
//        AspectJExpressionPointcut pointcut = new ApiSupportAspectJExpressionPointcut();
//        //设置切点表达式
//        pointcut.setExpression(DEFAULT_POINT_CUT);
//
//        // 配置增强类advisor, 切面=切点+增强
//        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
//        //设置切点
//        advisor.setPointcut(pointcut);
//        //设置增强（Advice）
//        advisor.setAdvice(apiInterceptor());
//        //设置增强拦截器执行顺序
//        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return advisor;
//    }



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
    @ConditionalOnMissingBean(ThreadPoolTaskScheduler.class)
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
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
}
