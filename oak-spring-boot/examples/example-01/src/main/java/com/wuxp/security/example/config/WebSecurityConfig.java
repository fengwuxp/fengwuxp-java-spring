package com.wuxp.security.example.config;

import com.oak.rbac.security.OakSessionInformationExpiredStrategy;
import com.oak.rbac.security.OakUserDetailsService;
import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.security.authenticate.CaptchaWebAuthenticationDetailsSource;
import com.wuxp.security.authenticate.JwtAuthenticationFilter;
import com.wuxp.security.authenticate.PasswordAuthenticationProvider;
import com.wuxp.security.authenticate.configuration.WuxpSecurityProperties;
import com.wuxp.security.authenticate.form.FormAuthenticationFailureHandler;
import com.wuxp.security.authenticate.form.FormLoginProperties;
import com.wuxp.security.authenticate.form.PasswordLoginEnvironmentHolder;
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
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CaptchaWebAuthenticationDetailsSource authenticationDetailsAuthenticationDetailsSource;

    @Autowired
    private WuxpSecurityProperties wuxpSecurityProperties;

    @Autowired
    private ScanCodeAuthenticationSecurityConfig scanCodeAuthenticationSecurityConfig;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private OakSessionInformationExpiredStrategy oakSessionInformationExpiredStrategy;

    // 实现权限拦截
    @Autowired
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(passwordAuthenticationProvider());
    }


    @Bean
    public PasswordAuthenticationProvider passwordAuthenticationProvider() {
        PasswordAuthenticationProvider passwordAuthenticationProvider = new PasswordAuthenticationProvider();
        passwordAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        passwordAuthenticationProvider.setUserDetailsService(oakUserDetailsService());
        return passwordAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 配置登录页面
        FormLoginProperties formLoginProperties = wuxpSecurityProperties.getForm();
        http.csrf().disable()
//                .cors()
//                .and()
                .exceptionHandling()
//                .accessDeniedHandler(this.accessDeniedHandler())
                //匿名用户访问无权限资源时的异常处理
                .authenticationEntryPoint(this.authenticationEntryPoint())
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
                ).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .and()
                // 登出处理
                .logout()
                .permitAll()
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                // url 权限检查
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //决策管理器
                        o.setAccessDecisionManager(affirmativeBased());
                        //安全元数据源
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                        return o;
                    }
                })
                // 方法拦截
//                .withObjectPostProcessor(new ObjectPostProcessor<MethodSecurityInterceptor>() {
//                    @Override
//                    public <O extends MethodSecurityInterceptor> O postProcess(O o) {
//                        //决策管理器
//                        o.setAccessDecisionManager(affirmativeBased());
//                        //安全元数据源
//                        o.setSecurityMetadataSource(null);
//                        return o;
//                    }
//                })
                .and()
                //添加扫码登录
                .apply(scanCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                // jwt 必须配置于 UsernamePasswordAuthenticationFilter 之前
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .maximumSessions(wuxpSecurityProperties.getMaximumSessions())
                .expiredSessionStrategy(oakSessionInformationExpiredStrategy);


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
                "/error",

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
    public UserDetailsService oakUserDetailsService() {
        return new OakUserDetailsService();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestfulAuthenticationEntryPoint("请先登陆");
    }

    @Bean
    @ConditionalOnMissingBean(AffirmativeBased.class)
    public AffirmativeBased affirmativeBased() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_");
        decisionVoters.add(new RoleHierarchyVoter(hierarchy));
        return new AffirmativeBased(decisionVoters);
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

