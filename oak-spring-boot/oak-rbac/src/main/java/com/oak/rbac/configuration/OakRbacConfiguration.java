package com.oak.rbac.configuration;

import com.oak.rbac.authority.OakRequestUrlResourceProvider;
import com.oak.rbac.security.OakAuthenticationSuccessHandler;
import com.oak.rbac.security.OakLockedUserDetailsService;
import com.oak.rbac.security.OakLogoutSuccessHandler;
import com.oak.rbac.security.UserSessionCacheHelper;
import com.wuxp.security.authenticate.LockedUserDetailsService;
import com.wuxp.security.authority.AntRequestUrlResourceProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class OakRbacConfiguration {


    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler oakAuthenticationSuccessHandler() {
        return new OakAuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new OakLogoutSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(OakLockedUserDetailsService.class)
    public LockedUserDetailsService lockedUserDetailsService() {
        return new OakLockedUserDetailsService();
    }

    @Bean
    @ConditionalOnMissingBean(AntRequestUrlResourceProvider.class)
    public AntRequestUrlResourceProvider antRequestUrlResourceProvider() {
        return new OakRequestUrlResourceProvider();
    }


}