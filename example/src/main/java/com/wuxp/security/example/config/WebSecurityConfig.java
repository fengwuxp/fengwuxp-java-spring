package com.wuxp.security.example.config;

import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.security.authenticate.CaptchaWebAuthenticationDetailsSource;
import com.wuxp.security.authenticate.JwtAuthenticationFilter;
import com.wuxp.security.authenticate.PasswordAuthenticationProvider;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import com.wuxp.security.authenticate.form.FormAuthenticationFailureHandler;
import com.wuxp.security.authenticate.form.FormLoginProperties;
import com.wuxp.security.authenticate.restful.RestfulAuthenticationEntryPoint;
import com.wuxp.security.authenticate.scancode.ScanCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.accept.ContentNegotiationStrategy;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CaptchaWebAuthenticationDetailsSource authenticationDetailsAuthenticationDetailsSource;

    @Autowired
    private WuxpAuthenticateProperties wuxpSecurityProperties;

    @Autowired
    private ScanCodeAuthenticationSecurityConfig scanCodeAuthenticationSecurityConfig;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    // 实现权限拦截
    @Autowired
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;


    @Override
    public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
        super.setContentNegotationStrategy(contentNegotiationStrategy);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //静态资源不拦截
        // http://localhost:8090/api/swagger-ui.html
        web.ignoring().antMatchers(
                "/captcha/**",
                "/scan_code/**",
                "/log/**",
                "/version/**",
                "/example/**",
                "/v1/example/test",
                "/error",
                "/article_action/**",
                "/example_cms/**",

                "/js/**",
                "/css/**",
                "/images/**",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public UserDetailsService studyUserDetailsService() {
//        return new StudyUserDetailsService();
//    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestfulAuthenticationEntryPoint("请先登陆");
    }


    @Bean
    public FormAuthenticationFailureHandler formAuthenticationFailureHandler() {
        return new FormAuthenticationFailureHandler();
    }


    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}

