package com.wuxp.security.authenticate.session;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户同时可登录的最大次数的限制策略
 *
 * @author wuxp
 */
public interface SessionLimitStrategy extends SessionSecurityStrategy {



    @Override
    default void tryRelease(UserDetails userDetails) {

    }
}
