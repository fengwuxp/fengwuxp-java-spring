package com.wuxp.security.example.security;

import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.wuxp.security.authenticate.RequestHeaderAuthorizationDetailsService;
import com.wuxp.security.example.model.StudyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 从缓存通过token 加载用户信息
 */
@Slf4j
@Service
public class StudyRequestHeaderAuthorizationDetailsService implements RequestHeaderAuthorizationDetailsService {

    @Resource
    private OakAdminUserService oakAdminUserService;

    @Override
    public UserDetails loadUserByAuthorizationToken(String authorizationToken) throws UsernameNotFoundException {
        OakAdminUserInfo adminUserInfo = oakAdminUserService.findByToken(authorizationToken);
        //token失效
        if (adminUserInfo == null) {
           throw new UsernameNotFoundException("登录过期");
        }
        return new StudyUserDetails(adminUserInfo.getUserName(), adminUserInfo.getPassword());
    }
}
