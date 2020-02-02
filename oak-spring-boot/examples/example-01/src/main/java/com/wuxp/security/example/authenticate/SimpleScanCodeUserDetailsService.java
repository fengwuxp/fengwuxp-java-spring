package com.wuxp.security.example.authenticate;

import com.wuxp.security.authenticate.RequestHeaderAuthorizationDetailsService;
import com.wuxp.security.example.model.StudyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleScanCodeUserDetailsService implements RequestHeaderAuthorizationDetailsService {

    @Override
    public UserDetails loadUserByAuthorizationToken(String authorizationToken) throws UsernameNotFoundException {
        log.info("扫码登录用户加载 {}", authorizationToken);
        return new StudyUserDetails("", "");
    }
}
