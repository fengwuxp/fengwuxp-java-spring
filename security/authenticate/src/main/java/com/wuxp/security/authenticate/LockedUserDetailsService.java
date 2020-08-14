package com.wuxp.security.authenticate;


import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Duration;

/**
 * 临时锁定用户
 * @author wuxp
 */
public interface LockedUserDetailsService {


    /**
     * @param username        锁定的账号
     * @param limitLoginTimes 锁定的时长
     * @throws UsernameNotFoundException
     */
    void lockUserByUsername(String username, Duration limitLoginTimes) throws UsernameNotFoundException;

}
