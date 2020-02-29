package com.fengwuxp.multiple.miniapp;

import cn.binarywang.wx.miniapp.config.WxMaConfig;

/**
 * 用于提供微信小程序的配置
 */
public interface WeChatMaConfigProvider {

    /**
     * 获取微信公众号的配置
     *
     * @param appId
     * @return
     */
    WxMaConfig getWxMpConfigStorage(String appId);

}
