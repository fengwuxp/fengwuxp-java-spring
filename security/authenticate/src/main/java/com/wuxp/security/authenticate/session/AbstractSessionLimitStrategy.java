package com.wuxp.security.authenticate.session;

import com.wuxp.security.authenticate.PasswordUserDetails;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wuxp
 */
public abstract class AbstractSessionLimitStrategy implements SessionLimitStrategy {

    protected WuxpAuthenticateProperties authenticateProperties;

    protected AuthenticateSessionManager authenticateSessionManager;


    @Override
    public void tryAcquire(UserDetails userDetails) {

        PasswordUserDetails user = (PasswordUserDetails) userDetails;
        String username = userDetails.getUsername();
        int maximumSessions = authenticateProperties.getMaximumSessions(user.getClientCode());
        int currentSessions = authenticateSessionManager.getCurrentSessions(username, user.getClientCode());
        int i = currentSessions - maximumSessions;
        if (i < 0) {
            return;
        }
        /**
         * 登录用户数量已经等于当前允许的最大数量，需要踢出用户
         * 获取当前账号所有已登录的用户信息，
         */
        Collection<String> tokens = authenticateSessionManager.getTokens(username, user.getClientCode());
        if (tokens == null || tokens.size() < maximumSessions) {
            return;
        }
        // 防止在使用本地缓存时，出现集合出现并发移除的异常
        List<String> removeTokens = new ArrayList<>(i);
        // 移除用户
        for (String token : tokens) {
            if (i < 0) {
                break;
            }
            removeTokens.add(token);
            i--;
        }
        for (String token : removeTokens) {
            authenticateSessionManager.tryKickOut(token, "账号同时登录数量过多，踢出用户");
        }

    }

    public void setAuthenticateProperties(WuxpAuthenticateProperties authenticateProperties) {
        this.authenticateProperties = authenticateProperties;
    }

    public void setAuthenticateSessionManager(AuthenticateSessionManager authenticateSessionManager) {
        this.authenticateSessionManager = authenticateSessionManager;
    }
}
