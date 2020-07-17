package com.wuxp.security.authenticate.handlers;

import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.authenticate.HttpMessageResponseWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 简单的鉴权失败响应处理，将错误消息返回给前端
 *
 * @author wxup
 */
@Slf4j
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler, HttpMessageResponseWriter {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        this.writeJson(response, RestfulApiRespFactory.error(exception.getMessage()));
    }
}
