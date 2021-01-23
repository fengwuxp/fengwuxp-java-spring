package com.wuxp.security.authenticate.mobile;

import com.wuxp.security.authenticate.CaptchaWebAuthenticationDetailsSource;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
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
 * * 自定义验证密码或者验证码
 * https://blog.csdn.net/tzdwsy/article/details/50738043
 * https://blog.csdn.net/xiejx618/article/details/42609497
 * https://blog.csdn.net/jiangshanwe/article/details/73842143
 * https://longfeizheng.github.io/2018/01/14/Spring-Security%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90%E4%BA%94-Spring-Security%E7%9F%AD%E4%BF%A1%E7%99%BB%E5%BD%95/
 * https://cloud.tencent.com/developer/article/1040105
 * @author wuxp
 */
@Component
@Slf4j
public class MobileCaptchaAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private MobileCaptchaAuthenticationFailureHandler mobileCaptchaAuthenticationFailureHandler;

    @Autowired
    private WuxpAuthenticateProperties wuxpSecurityProperties;

    @Autowired
    private CaptchaWebAuthenticationDetailsSource captchaWebAuthenticationDetailsSource;

    @Autowired(required = false)
    private MobileCaptchaUserDetailsService mobilePhoneUserDetailsService;

    @Autowired
    private MobileCaptcha mobileCaptcha;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        if (mobilePhoneUserDetailsService == null) {
            // 未配置表示不启用
            return;
        }
        MobileCaptchaLoginProperties mobileCaptchaLoginProperties = wuxpSecurityProperties.getMobileCaptcha();
        MobileCaptchaAuthenticationFilter mobileCaptchaAuthenticationFilter = new MobileCaptchaAuthenticationFilter(mobileCaptchaLoginProperties.getLoginProcessingUrl());
        mobileCaptchaAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        mobileCaptchaAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        mobileCaptchaAuthenticationFilter.setAuthenticationFailureHandler(mobileCaptchaAuthenticationFailureHandler);
        mobileCaptchaAuthenticationFilter.setCaptchaWebAuthenticationDetailsSource(captchaWebAuthenticationDetailsSource);

        MobileCaptchaAuthenticationProvider smsCaptchaAuthenticationProvider = new MobileCaptchaAuthenticationProvider();
        smsCaptchaAuthenticationProvider.setUserDetailsService(mobilePhoneUserDetailsService);
        smsCaptchaAuthenticationProvider.setMobileCaptcha(mobileCaptcha);
        http.authenticationProvider(smsCaptchaAuthenticationProvider)
                .addFilterAfter(mobileCaptchaAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
