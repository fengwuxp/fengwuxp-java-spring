package com.oak.rbac.security;

import com.alibaba.fastjson.JSON;
import com.oak.rbac.services.role.RoleService;
import com.oak.rbac.services.role.req.QueryRoleReq;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.security.authenticate.form.PasswordLoginEnvironmentHolder;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
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
import java.io.PrintWriter;
import java.util.Date;

@Slf4j
@Setter
public class OakAuthenticationSuccessHandler implements AuthenticationSuccessHandler, BeanFactoryAware, InitializingBean {


    private BeanFactory beanFactory;

    private PasswordLoginEnvironmentHolder loginEnvironmentHolder;

    private OakAdminUserService oakAdminUserService;

    private UserSessionCacheHelper userSessionCacheHelper;

    private JwtTokenProvider jwtTokenProvider;

    private RoleService roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 更新用户登录状态
        OakUser principal = (OakUser) authentication.getPrincipal();
        EditOakAdminUserReq editOakAdminUserReq = new EditOakAdminUserReq();
        editOakAdminUserReq.setId(principal.getId())
                .setToken(principal.getToken())
                .setTokenExpired(principal.getTokenExpired())
                .setLastLoginTime(new Date());
        ApiResp<Void> editResp = oakAdminUserService.edit(editOakAdminUserReq);
        AssertThrow.assertResp(editResp);


        //TODO 加载权限

//        QueryRoleReq req = new QueryRoleReq();
//        req.setFetchPermission();
//        roleService.queryRole(req);

        JwtTokenPair.JwtTokenPayLoad jwtTokenPayLoad = jwtTokenProvider.generateAccessToken(principal.getUsername());
        principal.setToken(jwtTokenPayLoad.getToken());
        principal.setTokenExpired(jwtTokenPayLoad.getTokenExpireTimes());

        // 加入缓存
        userSessionCacheHelper.join(principal.getToken(), principal);

        loginEnvironmentHolder.remove(request);
        if (log.isDebugEnabled()) {
            log.debug("登录成功 {}", authentication);
        }
        //返回Json数据
        String string = JSON.toJSONString(jwtTokenPayLoad);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(string);

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.loginEnvironmentHolder == null) {
            this.loginEnvironmentHolder = this.beanFactory.getBean(PasswordLoginEnvironmentHolder.class);
        }

        if (this.oakAdminUserService == null) {
            this.oakAdminUserService = this.beanFactory.getBean(OakAdminUserService.class);
        }

        if (this.userSessionCacheHelper == null) {
            this.userSessionCacheHelper = this.beanFactory.getBean(UserSessionCacheHelper.class);
        }
        if (this.jwtTokenProvider == null) {
            this.jwtTokenProvider = this.beanFactory.getBean(JwtTokenProvider.class);
        }

        if (this.roleService == null) {
            this.roleService = this.beanFactory.getBean(RoleService.class);
        }
    }
}
