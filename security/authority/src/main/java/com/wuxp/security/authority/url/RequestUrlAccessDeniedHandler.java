package com.wuxp.security.authority.url;

import com.alibaba.fastjson.JSON;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * url 访问鉴权失败处理
 */
@Slf4j
public class RequestUrlAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiResp<Void> resp = RestfulApiRespFactory.forBidden(accessDeniedException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(resp.getHttpStatus().value());
        response.getWriter().write(JSON.toJSONString(resp));
    }
}
