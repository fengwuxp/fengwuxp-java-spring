package com.fengwuxp.multiple.wechat;

/**
 * 用于获取微信小程序或公众号的配置
 *
 * @param <T>
 */
public interface WeChatConfigStorageProvider<T> {

    /**
     * 获取微信小程序或公众号的配置
     *
     * @param appId
     * @return
     */
    T getWeChatAppConfiguration(String appId);
}
