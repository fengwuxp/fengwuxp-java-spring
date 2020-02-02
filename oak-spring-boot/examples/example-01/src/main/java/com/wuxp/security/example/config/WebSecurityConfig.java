package com.wuxp.security.example.config;

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
import com.wuxp.security.authority.url.RequestUrlAccessDecisionVoter;
import com.wuxp.security.authority.url.RequestUrlAccessDeniedHandler;
import com.wuxp.security.example.authority.MockFilterInvocationSecurityMetadataSource;
import com.wuxp.security.example.security.StudyUserDetailsService;
import com.wuxp.security.example.security.handlers.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CaptchaWebAuthenticationDetailsSource authenticationDetailsAuthenticationDetailsSource;

    @Autowired
    private WuxpSecurityProperties wuxpSecurityProperties;

    @Autowired
    private PasswordLoginEnvironmentHolder passwordLoginEnvironmentHolder;

    @Autowired
    private ScanCodeAuthenticationSecurityConfig scanCodeAuthenticationSecurityConfig;

//    @Autowired
//    private MockAuthoritySecurityInterceptor mockAuthoritySecurityInterceptor;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    //实现权限拦截
    @Autowired
    private MockFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(passwordAuthenticationProvider());
    }


    @Bean
    public PasswordAuthenticationProvider passwordAuthenticationProvider() {
        PasswordAuthenticationProvider passwordAuthenticationProvider = new PasswordAuthenticationProvider();
        passwordAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        passwordAuthenticationProvider.setUserDetailsService(studyUserDetailsService());
        return passwordAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 配置登录页面
        FormLoginProperties formLoginProperties = wuxpSecurityProperties.getForm();
        http.csrf().disable()
                .authorizeRequests()
                // url 权限检查
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //决策管理器
                        o.setAccessDecisionManager(affirmativeBased());
                        //安全元数据源
                        o.setSecurityMetadataSource(securityMetadataSource);
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
                .formLogin()
                .loginPage(formLoginProperties.getLoginPage())
                .loginProcessingUrl(formLoginProperties.getLoginProcessingUrl())
                .authenticationDetailsSource(authenticationDetailsAuthenticationDetailsSource)
                .permitAll()
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new FormAuthenticationFailureHandler(wuxpSecurityProperties, passwordLoginEnvironmentHolder))
                .and()
                //添加扫码登录
                .apply(scanCodeAuthenticationSecurityConfig).and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new RequestUrlAccessDeniedHandler())
                //匿名用户访问无权限资源时的异常处理
                .authenticationEntryPoint(new RestfulAuthenticationEntryPoint("请先登陆"))
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                // jwt 必须配置于 UsernamePasswordAuthenticationFilter 之前
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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
                "/login",

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

    @Bean
    public UserDetailsService studyUserDetailsService() {
        return new StudyUserDetailsService();
    }

    @Bean
    @ConditionalOnMissingBean(AffirmativeBased.class)
    public AffirmativeBased affirmativeBased() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new RequestUrlAccessDecisionVoter());
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_");
        decisionVoters.add(new RoleHierarchyVoter(hierarchy));
        return new AffirmativeBased(decisionVoters);
    }

//    @Bean
//    public RequestUrlSecurityMetadataSource securityMetadataSource() {
//
//        return new RequestUrlSecurityMetadataSource();
//    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}

