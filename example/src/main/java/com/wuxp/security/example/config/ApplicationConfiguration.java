package com.wuxp.security.example.config;


import com.wuxp.api.context.ApiRequestContextFactory;
import com.wuxp.api.signature.AppInfoStore;
import com.wuxp.basic.uuid.JdkUUIDGenerateStrategy;
import com.wuxp.basic.uuid.UUIDGenerateStrategy;
import com.wuxp.security.example.context.MockApiRequestContextFactory;
import com.wuxp.security.example.log.MockApiLogRecorder;
import com.wuxp.security.example.signature.MockAppInfoStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;

/**
 * @author wxup
 */
@Configuration
@EnableCaching
public class ApplicationConfiguration implements WebMvcConfigurer {


    @Value("${wuxp.db.enabled:false}")
    private boolean dbEnvEnabled;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
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
    public CacheManager cacheManager() {
        return new CaffeineCacheManager();
    }

    @Bean
    public UUIDGenerateStrategy uuidGenerateStrategy() {
        return new JdkUUIDGenerateStrategy();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
    }


    @Bean
    @ConditionalOnMissingBean(AppInfoStore.class)
    public AppInfoStore mockAppInfoStore() {
        return new MockAppInfoStore();
    }

    @Bean
    public MockApiLogRecorder mockApiLogRecorder() {
        return new MockApiLogRecorder();
    }
}
