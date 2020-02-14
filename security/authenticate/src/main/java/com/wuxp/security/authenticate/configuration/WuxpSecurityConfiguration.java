package com.wuxp.security.authenticate.configuration;

import com.wuxp.security.authenticate.CaptchaWebAuthenticationDetailsSource;
import com.wuxp.security.authenticate.JwtAuthenticationFilter;
import com.wuxp.security.authenticate.form.PasswordLoginEnvironmentHolder;
import com.wuxp.security.authenticate.mobile.MobileCaptchaAuthenticationFailureHandler;
import com.wuxp.security.authenticate.scancode.ScanCodeAuthenticationFailureHandler;
import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.openid.SocialOpenIdAuthenticationFailureHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableConfigurationProperties(WuxpSecurityProperties.class)
@ConditionalOnProperty(prefix = WuxpSecurityProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class WuxpSecurityConfiguration {


    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CaptchaWebAuthenticationDetailsSource captchaWebAuthenticationDetailsSource() {
        return new CaptchaWebAuthenticationDetailsSource();
    }

    @Bean
    @ConditionalOnMissingBean(PasswordLoginEnvironmentHolder.class)
    public PasswordLoginEnvironmentHolder passwordLoginEnvironmentHolder() {
        return new PasswordLoginEnvironmentHolder();
    }

    @Bean
    //@ConditionalOnMissingBean(JwtProperties.class)
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

}
