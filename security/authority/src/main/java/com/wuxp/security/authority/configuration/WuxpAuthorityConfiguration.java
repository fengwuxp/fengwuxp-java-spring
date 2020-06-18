package com.wuxp.security.authority.configuration;

import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import com.wuxp.security.authority.url.RequestUrlSecurityMetadataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxup
 */
@Configuration
@EnableConfigurationProperties(WuxpAuthenticateProperties.class)
@ConditionalOnProperty(prefix = WuxpAuthenticateProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class WuxpAuthorityConfiguration {


    @Bean
    @ConditionalOnMissingBean(RequestUrlSecurityMetadataSource.class)
    public RequestUrlSecurityMetadataSource filterInvocationSecurityMetadataSource() {

        return new RequestUrlSecurityMetadataSource();
    }
}
