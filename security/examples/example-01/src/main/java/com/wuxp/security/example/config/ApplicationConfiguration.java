package com.wuxp.security.example.config;


import com.wuxp.api.context.ApiRequestContextFactory;
import com.wuxp.api.log.ApiLogRecorder;
import com.wuxp.security.example.advisor.OrderedAbstractBeanFactoryAwareAdvisingPostProcessor;
import com.wuxp.security.example.context.MockApiRequestContextFactory;
import com.wuxp.security.example.log.MockApiLogRecorder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableCaching
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Value("${spring.jpa.scan-packages}")
    private String[] entityScanPackages;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {

        return builder.dataSource(dataSource)
                .packages(entityScanPackages)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public SimpleJdbcInsertOperations simpleJdbcInsertOperations(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public SimpleJdbcCallOperations simpleJdbcCallOperations(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource);
    }


    @Bean
    @ConditionalOnMissingBean
    public JpaTransactionManager transactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    public ApiRequestContextFactory requestContextFactory() {
        return new MockApiRequestContextFactory();
    }

    @Bean
    public ApiLogRecorder apiLogRecorder() {

        return new MockApiLogRecorder();
    }

    @Bean
    public CacheManager cacheManager() {

        return new CaffeineCacheManager();
    }

//    @Bean
//    public ApiSignatureStrategy apiSignatureStrategy() {
//        return new MD5ApiSignatureStrategy(new MockAppInfoStore());
//    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
    }



    @Bean
    public OrderedAbstractBeanFactoryAwareAdvisingPostProcessor orderedAbstractBeanFactoryAwareAdvisingPostProcessor() {
        return new OrderedAbstractBeanFactoryAwareAdvisingPostProcessor();
    }
}
