package com.wuxp.security.authenticate;


import com.wuxp.api.ApiResp;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


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
     * 2：请求头中没有token，跳过
     * 3：请求头中存在token，校验token的合法性
     * 4：交换用户信息，可能会抛出token过期异常或用户不存在的异常（token是伪造的）
     * 5：携带token的路径不是必须鉴权的，只是做尝试性的获取，则进行跳过
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
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }
        // 获取 header 解析出 jwt 并进行认证 无token 直接进入下一个过滤器  因为  SecurityContext 的缘故 如果无权限并不会放行
        String authorizationHeader = request.getHeader(jwtProperties.getHeaderName());
        String headerPrefix = jwtProperties.getHeaderPrefix();
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith(headerPrefix)) {
            chain.doFilter(request, response);
            return;
        }
        authorizationHeader = authorizationHeader.trim();
        if (authorizationHeader.equals(headerPrefix)) {
            if (this.isTryAuthenticationPath(request)) {
                chain.doFilter(request, response);
                return;
            }
            // 带安全头 没有带token
            authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is empty"));
            return;
        }

        UserDetails userDetails;
        try {
            userDetails = this.authenticateSessionManager.get(authorizationHeader);
            if (userDetails == null) {
                if (this.isTryAuthenticationPath(request)) {
                    chain.doFilter(request, response);
                    return;
                }
                // 用户被踢出的信息存在
                ApiResp<Void> kickOutResp = authenticateSessionManager.tryGetKickOutReason(authorizationHeader);
                if (kickOutResp != null) {
                    this.writeJson(response, kickOutResp);
                    return;
                }
                authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("user not get"));
            } else {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }

        } catch (UsernameNotFoundException exception) {
            exception.printStackTrace();
            if (this.isTryAuthenticationPath(request)) {
                chain.doFilter(request, response);
                return;
            }
            // 用户被踢出的信息存在
            ApiResp<Void> kickOutResp = authenticateSessionManager.tryGetKickOutReason(authorizationHeader);
            if (kickOutResp != null) {
                this.writeJson(response, kickOutResp);
                return;
            }

            // 通过获取用户信息异常
            authenticationEntryPoint.commence(request, response, exception);
        } catch (ExpiredJwtException exception) {
            exception.printStackTrace();
            if (this.isTryAuthenticationPath(request)) {
                chain.doFilter(request, response);
                return;
            }
            // token过期
            authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is expired"));
        }
    }


    /**
     * 是否仅仅只是尝试做鉴权
     *
     * @param request
     * @return
     */
    protected boolean isTryAuthenticationPath(HttpServletRequest request) {
        if (this.tryAuthenticationPaths == null) {
            return false;
        }
        String uri = request.getRequestURI()
                .replace(request.getContextPath(), "");
        return this.tryAuthenticationPaths.stream()
                .map((item) -> item.equals(uri)
                ).filter(r -> r)
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
    }
}
