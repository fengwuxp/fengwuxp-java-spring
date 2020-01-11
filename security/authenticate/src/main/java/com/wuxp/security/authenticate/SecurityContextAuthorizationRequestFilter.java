package com.wuxp.security.authenticate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过请求头的token用于构建spring security context 上下文的filter
 */
@Slf4j
@Data
public class SecurityContextAuthorizationRequestFilter extends OncePerRequestFilter {

    /**
     * 通过token加载用户的服务
     */
    private RequestHeaderAuthorizationDetailsService authorizationDetailsService;

    /**
     * 存放authorizationToken的 header name
     */
    public String tokenHeaderName = "Authorization";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationToken = request.getHeader(tokenHeaderName);
        log.info("加载spring security context {}", authorizationToken);
        if (StringUtils.hasText(authorizationToken)) {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.authorizationDetailsService.loadUserByAuthorizationToken(authorizationToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

}
