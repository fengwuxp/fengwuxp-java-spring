package com.wuxp.security.authenticate.scancode;

import com.wuxp.security.authenticate.RequestHeaderAuthorizationDetailsService;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import com.wuxp.security.captcha.qrcode.QrCodeCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 扫码二维码登录
 */
@Component
@Slf4j
public class ScanCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private ScanCodeAuthenticationFailureHandler scanCodeAuthenticationFailureHandler;

    @Autowired
    private WuxpAuthenticateProperties wuxpSecurityProperties;

    @Autowired(required = false)
    private RequestHeaderAuthorizationDetailsService requestHeaderAuthorizationDetailsService;

    @Autowired
    private QrCodeCaptcha qrCodeCaptcha;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        if (requestHeaderAuthorizationDetailsService == null) {
            //未配置表示不启用
            return;
        }

        ScanCodeLoginProperties scanCodeLoginProperties = wuxpSecurityProperties.getScanCode();
        ScanCodeAuthenticationFilter scanCodeAuthenticationFilter = new ScanCodeAuthenticationFilter(scanCodeLoginProperties.getLoginProcessingUrl());
        scanCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        scanCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        scanCodeAuthenticationFilter.setAuthenticationFailureHandler(scanCodeAuthenticationFailureHandler);
        scanCodeAuthenticationFilter.setQrCodeCaptcha(qrCodeCaptcha);

        ScanCodeAuthenticationProvider scanCodeAuthenticationProvider = new ScanCodeAuthenticationProvider();
        scanCodeAuthenticationProvider.setRequestHeaderAuthorizationDetailsService(requestHeaderAuthorizationDetailsService);
        scanCodeAuthenticationProvider.setQrCodeCaptcha(qrCodeCaptcha);
        http.authenticationProvider(scanCodeAuthenticationProvider)
                .addFilterAfter(scanCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
