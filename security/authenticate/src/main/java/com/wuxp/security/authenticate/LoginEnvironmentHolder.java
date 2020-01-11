package com.wuxp.security.authenticate;


import javax.servlet.http.HttpServletRequest;

/**
 * 登录环境持有者
 */
public interface LoginEnvironmentHolder {


    /**
     * 获取登录环境的上下文
     *
     * @param request
     * @return
     */
    LoginEnvironmentContext getContext(HttpServletRequest request);

    /**
     * 获取登录环境的上下文并且增加一次登录失败的次数
     *
     * @param request
     * @return
     */
    LoginEnvironmentContext getContextAndIncreaseFailureCount(HttpServletRequest request);

}
