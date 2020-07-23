package com.wuxp.security.authenticate.session;


import org.springframework.security.core.userdetails.UserDetails;

/**
 * 会话安全策略
 * <p>
 * 用于检查用户登录流程的链路是否安全
 * </p>
 *
 * @author wuxp
 */
public interface SessionSecurityStrategy {


    /**
     * 尝试获取登录许可
     *
     * @param userDetails 登录的用信息
     */
    void tryAcquire(UserDetails userDetails);

    /**
     * 尝试释放一个登录许可
     * @param userDetails 登录的用信息
     */
    void tryRelease(UserDetails userDetails);
}
