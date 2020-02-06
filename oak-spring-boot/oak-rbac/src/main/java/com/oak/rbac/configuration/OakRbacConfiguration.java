package com.oak.rbac.configuration;

import com.oak.rbac.security.OakAuthenticationSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class OakRbacConfiguration {


    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler oakAuthenticationSuccessHandler() {
        return new OakAuthenticationSuccessHandler();
    }
}
