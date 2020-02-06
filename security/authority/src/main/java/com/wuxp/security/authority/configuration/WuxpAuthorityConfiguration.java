package com.wuxp.security.authority.configuration;

import com.wuxp.security.authenticate.configuration.WuxpSecurityProperties;
import com.wuxp.security.authority.url.RequestUrlSecurityMetadataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

@Configuration
@EnableConfigurationProperties(WuxpSecurityProperties.class)
@ConditionalOnProperty(prefix = WuxpSecurityProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class WuxpAuthorityConfiguration {


    @Bean
    @ConditionalOnMissingBean(FilterInvocationSecurityMetadataSource.class)
    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {

        return new RequestUrlSecurityMetadataSource();
    }
}
