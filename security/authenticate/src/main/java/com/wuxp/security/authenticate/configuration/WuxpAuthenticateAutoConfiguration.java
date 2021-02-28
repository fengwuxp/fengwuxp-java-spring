package com.wuxp.security.authenticate.configuration;

import com.wuxp.security.authenticate.CaptchaWebAuthenticationDetailsSource;
import com.wuxp.security.authenticate.JwtAuthenticationFilter;
import com.wuxp.security.authenticate.context.JwtSecurityContextRepository;
import com.wuxp.security.authenticate.form.PasswordLoginEnvironmentHolder;
import com.wuxp.security.authenticate.mobile.MobileCaptchaAuthenticationFailureHandler;
import com.wuxp.security.authenticate.restful.RestfulAuthenticationEntryPoint;
import com.wuxp.security.authenticate.scancode.ScanCodeAuthenticationFailureHandler;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenProvider;
import com.wuxp.security.openid.SocialOpenIdAuthenticationFailureHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * @author wxup
 */
@Configuration
@EnableConfigurationProperties(WuxpAuthenticateProperties.class)
@ConditionalOnProperty(prefix = WuxpAuthenticateProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class WuxpAuthenticateAutoConfiguration {


    @Bean
    public CaptchaWebAuthenticationDetailsSource captchaWebAuthenticationDetailsSource() {
        return new CaptchaWebAuthenticationDetailsSource();
    }

    @Bean
    @ConditionalOnMissingBean(PasswordLoginEnvironmentHolder.class)
    public PasswordLoginEnvironmentHolder passwordLoginEnvironmentHolder(CacheManager cacheManager) {
        return new PasswordLoginEnvironmentHolder(cacheManager);
    }

    @Bean
    @ConditionalOnMissingBean(SecurityContextRepository.class)
    public SecurityContextRepository securityContextRepository(JwtTokenProvider jwtTokenProvider,
                                                               JwtProperties jwtProperties,
                                                               AuthenticateSessionManager authenticateSessionManager) {
        return new JwtSecurityContextRepository(jwtTokenProvider, jwtProperties, authenticateSessionManager);
    }

    @Bean
    @ConditionalOnMissingBean(JwtAuthenticationFilter.class)
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }


    @Bean
    @ConditionalOnMissingBean(MobileCaptchaAuthenticationFailureHandler.class)
    public MobileCaptchaAuthenticationFailureHandler mobileCaptchaAuthenticationFailureHandler() {
        return new MobileCaptchaAuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean(ScanCodeAuthenticationFailureHandler.class)
    public ScanCodeAuthenticationFailureHandler scanCodeAuthenticationFailureHandler() {

        return new ScanCodeAuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean(SocialOpenIdAuthenticationFailureHandler.class)
    public SocialOpenIdAuthenticationFailureHandler socialOpenIdAuthenticationFailureHandler() {
        return new SocialOpenIdAuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestfulAuthenticationEntryPoint("请先登陆");
    }


}
