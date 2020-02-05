package com.wuxp.security.example.security;


import com.wuxp.security.authenticate.RequestHeaderAuthorizationDetailsService;
import com.wuxp.security.example.model.StudyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 从缓存通过token 加载用户信息
 */
@Slf4j
@Service
public class StudyRequestHeaderAuthorizationDetailsService implements RequestHeaderAuthorizationDetailsService {


    @Autowired
    private UserSessionCacheHelper userSessionCacheHelper;

    @Override
    public UserDetails loadUserByAuthorizationToken(String authorizationToken) throws UsernameNotFoundException {
        StudyUserDetails studyUserDetails = userSessionCacheHelper.get(authorizationToken);
        //token失效
        if (studyUserDetails == null) {
            throw new UsernameNotFoundException("登录过期");
        }
        return studyUserDetails;
    }
}
