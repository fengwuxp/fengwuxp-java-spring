package com.oak.rbac.security;

import antlr.collections.List;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Slf4j
public class OakUserDetailsService implements UserDetailsService {

    @Autowired
    private OakAdminUserService oakAdminUserService;




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

        Collection<GrantedAuthority> authorities = new ArrayList<>(AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_APP"));

        OakUser oakUser = new OakUser(adminUserInfo.getName(),
                adminUserInfo.getPassword(),
                adminUserInfo.getEnable(),
                true,
                true,
                true,
                authorities);
        oakUser.setCryptoSalt(adminUserInfo.getCryptoSalt())
                .setEmail(adminUserInfo.getEmail())
                .setId(adminUserInfo.getId())
                .setMobilePhone(adminUserInfo.getMobilePhone())
                .setRoot(adminUserInfo.getRoot());


        return oakUser;
    }
}
