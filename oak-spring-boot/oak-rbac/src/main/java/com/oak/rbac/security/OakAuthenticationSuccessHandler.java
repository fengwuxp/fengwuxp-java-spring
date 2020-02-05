package com.oak.rbac.security;

import com.alibaba.fastjson.JSON;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.security.authenticate.form.PasswordLoginEnvironmentHolder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Setter
public class OakAuthenticationSuccessHandler implements AuthenticationSuccessHandler, BeanFactoryAware, InitializingBean {

    private PasswordLoginEnvironmentHolder loginEnvironmentHolder;

    private BeanFactory beanFactory;

    @Autowired
    private OakAdminUserService oakAdminUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        if (log.isDebugEnabled()) {
            log.debug("登录成功 {}", authentication);
        }

        OakUser details = (OakUser) authentication.getDetails();

        EditOakAdminUserReq editOakAdminUserReq = new EditOakAdminUserReq();
        editOakAdminUserReq.setId(details.getId())
                .setToken(details.getToken())
                .setTokenExpired(details.getTokenExpired());
        ApiResp<Void> editResp = oakAdminUserService.edit(editOakAdminUserReq);
        if (!editResp.isSuccess()) {
            throw new UsernameNotFoundException("更新用户登录状态失败");
        }

        loginEnvironmentHolder.remove(request);
        //返回Json数据
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JSON.toJSONString(authentication));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.loginEnvironmentHolder == null) {
            this.loginEnvironmentHolder = this.beanFactory.getBean(PasswordLoginEnvironmentHolder.class);
        }
    }
}
