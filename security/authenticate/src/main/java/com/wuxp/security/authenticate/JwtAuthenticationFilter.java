package com.wuxp.security.authenticate;


import com.wuxp.api.ApiResp;
import com.wuxp.security.authenticate.context.TokenSecurityContextImpl;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationFilterChain;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.util.WebUtils.ERROR_EXCEPTION_ATTRIBUTE;


/**
 * jwt 认证拦截器 用于拦截 请求 提取jwt 认证
 *
 * @author wuxp
 */
@Slf4j
@Setter
public class JwtAuthenticationFilter extends OncePerRequestFilter implements BeanFactoryAware, HttpMessageResponseWriter {


    private BeanFactory beanFactory;

    /**
     * 认证如果失败由该端点响应错误信息
     */
    private AuthenticationEntryPoint authenticationEntryPoint;

    private JwtTokenProvider jwtTokenProvider;

    private JwtProperties jwtProperties;

    private AuthenticateSessionManager authenticateSessionManager;


    private List<RequestMatcher> tryAuthenticationRequestMatchers;


    /**
     * 使用鉴权token交换用户信息
     * 存在如下几种情况
     * <>
     * 1：{@link SecurityContextHolder} 中已经存在用户信息，跳过
     * 2：携带token的路径不是必须鉴权的，只是做尝试性的获取，则进行跳过
     * 3：判断用户是否被踢出
     * </>
     * 注意：
     * 由于 {@link org.springframework.boot.web.servlet.ServletContextInitializerBeans#addServletContextInitializerBean}方法会将
     * 类型为{@link javax.servlet.Filter} {@link javax.servlet.Servlet} {@link java.util.EventListener} 类型的bean注册到
     * {@link javax.servlet.ServletContext}中
     * 这样导致的结果是在某些情况下 {@link javax.servlet.Filter}可能会被执行2次
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (chain instanceof ApplicationFilterChain) {
            // 如果是在tomcat的过滤器链中，不处理
            chain.doFilter(request, response);
            return;
        }
        if (this.isTryAuthenticationPath(request)) {
            chain.doFilter(request, response);
            return;
        }

        // 必须认证的路径
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null) {
            // 认证失败
            if (context instanceof TokenSecurityContextImpl) {
                String token = ((TokenSecurityContextImpl) context).getToken();
                // 存在用户被踢出的信息
                ApiResp<Void> kickOutResp = authenticateSessionManager.tryGetKickOutReason(token);
                if (kickOutResp != null) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    this.writeJson(response, kickOutResp);
                    return;
                }
            }
            // 如果已经未认证
            authenticationEntryPoint.commence(request, response,
                    new AuthenticationCredentialsNotFoundException("request must authentication",
                            (Exception) request.getAttribute(ERROR_EXCEPTION_ATTRIBUTE)));
            return;
        }
        // 认证通过或其他情况
        chain.doFilter(request, response);
    }


    /**
     * 匹配是否为尝试鉴权的路径
     *
     * @param request http 请求对象
     * @return if return<code>true</code> 表示该路径只需要尝试做鉴权
     */
    protected boolean isTryAuthenticationPath(HttpServletRequest request) {
        List<RequestMatcher> tryAuthenticationRequestMatchers = this.tryAuthenticationRequestMatchers;
        if (tryAuthenticationRequestMatchers == null || this.tryAuthenticationRequestMatchers.isEmpty()) {
            return false;
        }
        return tryAuthenticationRequestMatchers
                .stream()
                .anyMatch(requestMatcher -> requestMatcher.matches(request));
    }

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();

        if (this.authenticationEntryPoint == null) {
            this.authenticationEntryPoint = beanFactory.getBean(AuthenticationEntryPoint.class);
        }
        if (this.jwtTokenProvider == null) {
            this.jwtTokenProvider = beanFactory.getBean(JwtTokenProvider.class);
        }

        if (this.jwtProperties == null) {
            this.jwtProperties = beanFactory.getBean(JwtProperties.class);
        }
        if (this.authenticateSessionManager == null) {
            this.authenticateSessionManager = beanFactory.getBean(AuthenticateSessionManager.class);
        }
        Assert.notNull(authenticateSessionManager, "authenticateSessionManager must not null");
    }

    /**
     * @param tryAuthenticationPaths 尝试用于认证的路径
     */
    public void setTryAuthenticationPaths(List<String> tryAuthenticationPaths) {
        this.tryAuthenticationRequestMatchers = tryAuthenticationPaths.stream()
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());
    }
}
