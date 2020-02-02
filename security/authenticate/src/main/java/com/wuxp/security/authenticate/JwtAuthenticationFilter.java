package com.wuxp.security.authenticate;


import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * jwt 认证拦截器 用于拦截 请求 提取jwt 认证
 */
@Slf4j
@Setter
public class JwtAuthenticationFilter extends OncePerRequestFilter implements BeanFactoryAware {


    private BeanFactory beanFactory;

    private RequestHeaderAuthorizationDetailsService authorizationDetailsService;

    /**
     * 认证如果失败由该端点进行响应
     */
    private AuthenticationEntryPoint authenticationEntryPoint;

    private JwtTokenProvider jwtTokenProvider;

    private JwtProperties jwtProperties;


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
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(headerPrefix)) {
//            String jwtToken = authorizationHeader.replace(headerPrefix, "");
            if (StringUtils.hasText(authorizationHeader)) {
//                Jws<Claims> claimsJws = jwtTokenProvider.parse(jwtToken);
                UserDetails userDetails = this.authorizationDetailsService.loadUserByAuthorizationToken(authorizationHeader);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // 带安全头 没有带token
                authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is empty"));
            }

        }
        chain.doFilter(request, response);
    }


    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        if (this.authorizationDetailsService == null) {
            this.authorizationDetailsService = beanFactory.getBean(RequestHeaderAuthorizationDetailsService.class);
        }
        if (this.jwtProperties == null) {
            this.jwtProperties = beanFactory.getBean(JwtProperties.class);
        }
        if (this.authenticationEntryPoint == null) {
            this.authenticationEntryPoint = beanFactory.getBean(AuthenticationEntryPoint.class);
        }
        if (this.jwtTokenProvider == null) {
            this.jwtTokenProvider = beanFactory.getBean(JwtTokenProvider.class);
        }

        if (this.jwtProperties == null) {
            this.jwtProperties = beanFactory.getBean(JwtProperties.class);
        }
    }
}
