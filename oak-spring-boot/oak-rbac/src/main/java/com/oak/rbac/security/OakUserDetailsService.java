package com.oak.rbac.security;

import com.oak.rbac.services.permission.PermissionService;
import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.oak.rbac.services.role.RoleService;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.oak.rbac.services.user.req.QueryOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Slf4j
public class OakUserDetailsService implements UserDetailsService {

    @Autowired
    private OakAdminUserService oakAdminUserService;

    @Autowired
    private UserSessionCacheHelper userSessionCacheHelper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * 登录流程概述
         * 1： 从数据加载用户信息和rbac账号信息
         * 2:  生成jwt token
         * 3： 将用户信息加入到缓存中（缓存使用spring cache）
         */
        //账号信息
        QueryOakAdminUserReq queryOakAdminUserReq = new QueryOakAdminUserReq();
        queryOakAdminUserReq.setUserName(username);
        OakAdminUserInfo adminUserInfo = oakAdminUserService.query(queryOakAdminUserReq).getFirst();
        if (adminUserInfo == null) {
            throw new UsernameNotFoundException(username);
        }
        boolean isLocked = adminUserInfo.getLockExpired() != null && adminUserInfo.getLockExpired().getTime() > System.currentTimeMillis();
        if (isLocked) {
            throw new UsernameNotFoundException("用户被锁定");
        }

        JwtTokenPair.JwtTokenPayLoad jwtTokenPayLoad = jwtTokenProvider.generateAccessToken(username);
        adminUserInfo.setToken(jwtTokenPayLoad.getToken());
        adminUserInfo.setTokenExpired(jwtTokenPayLoad.getTokenExpireTimes());


        //TODO 加载权限


        OakUser oakUser = new OakUser(adminUserInfo.getName(),
                adminUserInfo.getPassword(),
                adminUserInfo.getEnable(),
                true,
                true,
                true,
                Collections.emptyList());
        oakUser.setCryptoSalt(adminUserInfo.getCryptoSalt())
                .setEmail(adminUserInfo.getEmail())
                .setId(adminUserInfo.getId())
                .setMobilePhone(adminUserInfo.getMobilePhone())
                .setRoot(adminUserInfo.getRoot())
                .setToken(adminUserInfo.getToken())
               .setTokenExpired(adminUserInfo.getTokenExpired());
        userSessionCacheHelper.join(jwtTokenPayLoad.getToken(), oakUser);
        return oakUser;
    }
}
