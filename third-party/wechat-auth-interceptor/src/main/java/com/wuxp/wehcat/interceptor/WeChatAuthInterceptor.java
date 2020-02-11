package com.wuxp.wehcat.interceptor;


/**
 * 微信拦截器
 */
public interface WeChatAuthInterceptor {


    /**
     * 是否为模拟环境
     * @return
     */
    boolean isMock();
}
