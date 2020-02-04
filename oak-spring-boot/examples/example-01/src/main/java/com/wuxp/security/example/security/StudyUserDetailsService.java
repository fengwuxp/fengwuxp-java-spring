package com.wuxp.security.example.security;


import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.oak.rbac.services.user.req.QueryOakAdminUserReq;
import com.wuxp.security.example.constant.OakAdminUserConstant;
import com.wuxp.security.example.model.StudyUserDetails;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class StudyUserDetailsService implements UserDetailsService {

    @Resource
    private OakAdminUserService oakAdminUserService;

    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

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
        String token = jwtTokenProvider.generateAccessToken(username);
        adminUserInfo.setToken(token);
        //token失效时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, OakAdminUserConstant.LOGIN_TOKEN_VALID_HOUR);
        adminUserInfo.setTokenExpired(calendar.getTime());
        EditOakAdminUserReq editOakAdminUserReq = new EditOakAdminUserReq();
        editOakAdminUserReq.setId(adminUserInfo.getId())
                .setToken(adminUserInfo.getToken())
                .setTokenExpired(adminUserInfo.getTokenExpired());
        oakAdminUserService.edit(editOakAdminUserReq);
        return new StudyUserDetails(username, adminUserInfo.getPassword());
    }
}
