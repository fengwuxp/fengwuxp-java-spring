package com.fengwuxp.miniapp.multiple;

import cn.binarywang.wx.miniapp.config.WxMaConfig;

/**
 * 用于提供微信小程序的配置
 *
 * @author wuxp
 */
public interface WeChatMaConfigProvider {

    /**
     * 获取微信公众号的配置
     *
     * @param appId 微信公众appId
     * @return 微信Map配置实例
     */
    WxMaConfig getWxMpConfigStorage(String appId);

}
