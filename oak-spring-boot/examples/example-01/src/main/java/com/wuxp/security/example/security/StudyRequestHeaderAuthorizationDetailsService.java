package com.wuxp.security.example.security;

import com.wuxp.security.authenticate.RequestHeaderAuthorizationDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 从缓存通过token 加载用户信息
 */
@Slf4j
public class StudyRequestHeaderAuthorizationDetailsService implements RequestHeaderAuthorizationDetailsService {


    @Override
    public UserDetails loadUserByAuthorizationToken(String authorizationToken) throws UsernameNotFoundException {


        return null;
    }
}
