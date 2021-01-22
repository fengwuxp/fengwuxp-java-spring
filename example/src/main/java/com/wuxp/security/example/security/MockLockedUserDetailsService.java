package com.wuxp.security.example.security;

import com.wuxp.security.authenticate.LockedUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author wuxp
 */
@Component
public class MockLockedUserDetailsService implements LockedUserDetailsService {

    @Override
    public void lockUserByUsername(String username, Duration limitLoginTimes) throws UsernameNotFoundException {

    }
}
