package com.wuxp.security.authenticate;


import com.wuxp.api.ApiResp;
import com.wuxp.security.authenticate.context.TokenSecurityContextImpl;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
     * 认证如果失败由该端点进行响应
     */
    private AuthenticationEntryPoint authenticationEntryPoint;

    private JwtTokenProvider jwtTokenProvider;

    private JwtProperties jwtProperties;

    private AuthenticateSessionManager authenticateSessionManager;

    /**
     * 尝试获取鉴权信息的路径
     * 必须是完整的路径（不包含contentPath）,
     * 暂时不支持有存在路径参数
     */
    private List<String> tryAuthenticationPaths;


    /**
     * 使用鉴权token交换用户信息
     * 存在如下几种情况
     * <>
     * 1：{@link SecurityContextHolder} 中已经存在用户信息，跳过
     * 2：携带token的路径不是必须鉴权的，只是做尝试性的获取，则进行跳过
     * 3：判断用户是否被踢出
     * </>
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 如果已经通过认证
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }
        if (!this.isTryAuthenticationPath(request)){
            authenticationEntryPoint.commence(request, response,
                    new AuthenticationCredentialsNotFoundException("request must authentication",
                    (Exception)request.getAttribute(ERROR_EXCEPTION_ATTRIBUTE)));
            return;
        }

        if (context instanceof TokenSecurityContextImpl){
            String token = ((TokenSecurityContextImpl) context).getToken();
            // 用户被踢出的信息存在
            ApiResp<Void> kickOutResp = authenticateSessionManager.tryGetKickOutReason(token);
            if (kickOutResp != null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                this.writeJson(response, kickOutResp);
                return;
            }
        }

        // 其他情况
        chain.doFilter(request, response);
    }


    /**
     *
     * @param request
     * @return 是否只是尝试做鉴权
     */
    protected boolean isTryAuthenticationPath(HttpServletRequest request) {
        if (this.tryAuthenticationPaths == null) {
            return false;
        }
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        return this.tryAuthenticationPaths.stream()
                .map((item) -> item.equals(uri))
                .filter(r -> r)
                .findFirst()
                .orElse(false);
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
        Assert.notNull(authenticateSessionManager,"authenticateSessionManager must not null");
    }
}
