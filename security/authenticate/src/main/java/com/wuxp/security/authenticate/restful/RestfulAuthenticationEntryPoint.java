package com.wuxp.security.authenticate.restful;

import com.alibaba.fastjson.JSON;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.authenticate.HttpMessageResponseWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在认证失败的时候返回401
 * <p>
 * 如果是匿名用户权限不足
 * </p>
 *
 * @author wxup
 */
@Slf4j
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint, HttpMessageResponseWriter {

    private final static String ANONYMOUS_ERROR_MESSAGE = "登录状态已失效或未登录";

    /**
     * 在认证失败的时候返回响应内容
     */
    private String unAuthorizedResp;

    /**
     * 匿名用户验证失败的响应
     */
    private String anonymousResp;

    public RestfulAuthenticationEntryPoint(String errorMessage) {
        this.unAuthorizedResp = JSON.toJSONString(RestfulApiRespFactory.error(errorMessage));
        this.anonymousResp = JSON.toJSONString(RestfulApiRespFactory.error(ANONYMOUS_ERROR_MESSAGE));
    }

    public RestfulAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException {

        if (log.isDebugEnabled()) {
            log.debug("用户鉴权失败 {}", exception.getMessage());
        }

        if (exception instanceof InsufficientAuthenticationException) {
            // 匿名用户
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            this.writeJsonString(httpServletResponse, this.anonymousResp);
            return;
        }

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        String unAuthorizedResp = this.unAuthorizedResp;
        if (unAuthorizedResp != null) {
            this.writeJsonString(httpServletResponse, this.anonymousResp);
        }

    }
}
