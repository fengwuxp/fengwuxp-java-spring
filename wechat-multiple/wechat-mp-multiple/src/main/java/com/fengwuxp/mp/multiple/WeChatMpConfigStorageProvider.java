package com.fengwuxp.mp.multiple;

import me.chanjar.weixin.mp.config.WxMpConfigStorage;

/**
 * 用于提供微信公众号的配置
 */
public interface WeChatMpConfigStorageProvider {

    /**
     * 获取微信公众号的配置
     *
     * @param appId
     * @return
     */
    WxMpConfigStorage getWxMpConfigStorage(String appId);

}
