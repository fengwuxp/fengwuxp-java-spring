package com.wuxp.security.authenticate.restful;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 在认证失败的时候返回401
 * <p>
 * 如果是匿名用户权限不足
 *
 * @see ExceptionTranslationFilter#doFilter
 */
@Slf4j
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final static String DEFAULT_MESSAGE_KEY = "message";
    private final static String ANONYMOUS_ERROR_MESSAGE = "登录状态已失效或未登录";

    /**
     * 在认证失败的时候返回响应内容
     */
    private String unAuthorizedResp;

    /**
     * 匿名用户验证失败的响应
     */
    private String anonymousResp;

    public RestfulAuthenticationEntryPoint(Map<String, Object> unAuthorizedResp) {
        this.unAuthorizedResp = JSON.toJSONString(unAuthorizedResp);
        this.anonymousResp = JSON.toJSONString(unAuthorizedResp);
    }

    public RestfulAuthenticationEntryPoint(String errorMessage) {

        HashMap<String, Object> unAuthorizedResp = new HashMap<>();
        unAuthorizedResp.put(DEFAULT_MESSAGE_KEY, errorMessage);
        this.unAuthorizedResp = JSON.toJSONString(unAuthorizedResp);
        unAuthorizedResp.put(DEFAULT_MESSAGE_KEY, ANONYMOUS_ERROR_MESSAGE);
        this.anonymousResp = JSON.toJSONString(unAuthorizedResp);
    }

    public RestfulAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException {

        if (log.isDebugEnabled()) {
            log.debug("用户鉴权失败 {}", exception.getMessage());
        }

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (exception instanceof InsufficientAuthenticationException) {
            // 匿名用户
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().println(this.anonymousResp);
            return;
        }

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        String unAuthorizedResp = this.unAuthorizedResp;
        if (unAuthorizedResp != null) {
            httpServletResponse.getWriter().println(unAuthorizedResp);
        }
    }
}
