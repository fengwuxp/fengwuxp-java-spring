package com.wuxp.security.authenticate.form;

import com.alibaba.fastjson.JSON;
import com.wuxp.security.authenticate.LoginEnvironmentContext;
import com.wuxp.security.authenticate.configuration.WuxpSecurityProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {


    private WuxpSecurityProperties wuxpSecurityProperties;

    private PasswordLoginEnvironmentHolder loginEnvironmentHolder;

    public FormAuthenticationFailureHandler(WuxpSecurityProperties wuxpSecurityProperties, PasswordLoginEnvironmentHolder loginEnvironmentHolder) {
        this.wuxpSecurityProperties = wuxpSecurityProperties;
        this.loginEnvironmentHolder = loginEnvironmentHolder;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        LoginEnvironmentContext loginEnvironmentContext = loginEnvironmentHolder.getContextAndIncreaseFailureCount(request);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, Object> map = new HashMap<>();
        map.put("message", exception.getMessage());
        //是否需要图片验证码验证
        map.put("needPictureCaptcha", loginEnvironmentContext.isNeedPictureCaptcha());
        //返回Json数据
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(JSON.toJSONString(map));

    }
}
