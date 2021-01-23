package com.wuxp.security.example.config;


import com.wuxp.api.interceptor.ApiSignatureRequestFilter;
import com.wuxp.security.authenticate.CaptchaWebAuthenticationDetailsSource;
import com.wuxp.security.authenticate.JwtAuthenticationFilter;
import com.wuxp.security.authenticate.PasswordAuthenticationProvider;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import com.wuxp.security.authenticate.form.FormAuthenticationFailureHandler;
import com.wuxp.security.authenticate.form.FormLoginProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.accept.ContentNegotiationStrategy;

import java.util.Collections;


/**
 * @author wxup
 */
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private CaptchaWebAuthenticationDetailsSource authenticationDetailsAuthenticationDetailsSource;

    @Autowired
    private WuxpAuthenticateProperties wuxpSecurityProperties;


    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(passwordAuthenticationProvider());
    }

    @Override
    public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
        super.setContentNegotationStrategy(contentNegotiationStrategy);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 配置登录页面
        FormLoginProperties formLoginProperties = wuxpSecurityProperties.getForm();
        http.csrf().disable()
                .exceptionHandling()
                //匿名用户访问无权限资源时的异常处理
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin()
                .permitAll()
                .loginPage(formLoginProperties.getLoginPage())
                .loginProcessingUrl(formLoginProperties.getLoginProcessingUrl())
                .authenticationDetailsSource(authenticationDetailsAuthenticationDetailsSource)
                .successHandler(this.authenticationSuccessHandler)
                .failureHandler(this.formAuthenticationFailureHandler())
                .and()
                .authorizeRequests()
                .antMatchers(
                        // 登录路径
                        formLoginProperties.getLoginPage(),
                        formLoginProperties.getLoginProcessingUrl()
                )
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .and()
                // 登出处理
                .logout()
                .permitAll()
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                .antMatchers("/**")
                .authenticated()
                .and()
                .securityContext()
                .securityContextRepository(securityContextRepository)
                .and()
                // jwt 必须配置于 UsernamePasswordAuthenticationFilter 之前
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(apiSignatureRequestFilter(), JwtAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(wuxpSecurityProperties.getMaximumSessions());


    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //静态资源不拦截
        // http://localhost:8090/api/swagger-ui.html
        web.ignoring().antMatchers(
//                 "/captcha/**",
                 "/example/**"
//                "/js/**",
//                "/css/**",
//                "/images/**",
//                "/v3/api-docs",
//                "/v3/api-docs/**",
//                "/swagger-ui/**",
//                "/v3/api-docs/**",
//                "/configuration/ui",
//                "/swagger-resources",
//                "/configuration/security",
//                "/swagger-ui.html",
//                "/webjars/**",
//                "/webjars/**"
                );
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setTryAuthenticationPaths(Collections.singletonList("/example/**"));
        return jwtAuthenticationFilter;
    }


    @Bean
    protected ApiSignatureRequestFilter apiSignatureRequestFilter() {
        return new ApiSignatureRequestFilter();
    }


    @Bean
    public PasswordAuthenticationProvider passwordAuthenticationProvider() {
        PasswordAuthenticationProvider passwordAuthenticationProvider = new PasswordAuthenticationProvider();
        passwordAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return passwordAuthenticationProvider;
    }


    @Bean
    public AuthenticationFailureHandler formAuthenticationFailureHandler() {
        return new FormAuthenticationFailureHandler();
    }


}
