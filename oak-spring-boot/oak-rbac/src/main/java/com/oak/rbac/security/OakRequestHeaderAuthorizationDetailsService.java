package com.oak.rbac.security;


import com.wuxp.security.authenticate.RequestHeaderAuthorizationDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 从缓存通过token 加载用户信息
 */
@Slf4j
@Component
public class OakRequestHeaderAuthorizationDetailsService implements RequestHeaderAuthorizationDetailsService {


    @Autowired
    private UserSessionCacheHelper userSessionCacheHelper;

    @Override
    public UserDetails loadUserByAuthorizationToken(String authorizationToken) throws UsernameNotFoundException {
        OakUser oakUser = userSessionCacheHelper.get(authorizationToken);
        // token失效
        if (oakUser == null) {
            throw new UsernameNotFoundException("登录过期");
        }
        return oakUser;
    }
}
