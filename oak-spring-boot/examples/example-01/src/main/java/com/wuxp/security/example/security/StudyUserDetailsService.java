package com.wuxp.security.example.security;


import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.oak.rbac.services.user.req.QueryOakAdminUserReq;
import com.wuxp.security.example.constant.OakAdminUserConstant;
import com.wuxp.security.example.model.StudyUserDetails;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
public class StudyUserDetailsService implements UserDetailsService {

    @Autowired
    private OakAdminUserService oakAdminUserService;

    @Autowired
    private UserSessionCacheHelper userSessionCacheHelper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        /**
         * 登录流程概述
         * 1： 从数据加载用户信息和rbac账号信息
         * 2:  生成jwt token
         * 3： 将用户信息加入到缓存中（缓存使用spring cache）
         *
         *
         *
         */
        //账号信息
        QueryOakAdminUserReq queryOakAdminUserReq = new QueryOakAdminUserReq();
        queryOakAdminUserReq.setUserName(username);
        OakAdminUserInfo adminUserInfo = oakAdminUserService.query(queryOakAdminUserReq).getFirst();
        if (adminUserInfo == null) {
            throw new UsernameNotFoundException(username);
        }
        JwtTokenPair.JwtTokenPayLoad jwtTokenPayLoad = jwtTokenProvider.generateAccessToken(username);
        adminUserInfo.setToken(jwtTokenPayLoad.getToken());
        adminUserInfo.setTokenExpired(jwtTokenPayLoad.getTokenExpireTimes());
        EditOakAdminUserReq editOakAdminUserReq = new EditOakAdminUserReq();
        editOakAdminUserReq.setId(adminUserInfo.getId())
                .setToken(adminUserInfo.getToken())
                .setTokenExpired(adminUserInfo.getTokenExpired());
        oakAdminUserService.edit(editOakAdminUserReq);
        StudyUserDetails studyUserDetails = new StudyUserDetails(username, null);
        userSessionCacheHelper.join(jwtTokenPayLoad.getToken(), studyUserDetails);
        return studyUserDetails;
    }
}
