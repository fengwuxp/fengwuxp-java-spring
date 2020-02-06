package com.oak.rbac.security;

import com.alibaba.fastjson.JSON;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.jwt.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话信息过期策略
 */
@Component
@Slf4j
public class OakSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    private UserSessionCacheHelper userSessionCacheHelper;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {

        String authorizationHeader = sessionInformationExpiredEvent.getRequest().getHeader(jwtProperties.getHeaderName());

        if (authorizationHeader != null) {
            userSessionCacheHelper.remove(authorizationHeader);
        }


        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JSON.toJSONString(RestfulApiRespFactory.badRequest("登录已失效")));

    }
}
