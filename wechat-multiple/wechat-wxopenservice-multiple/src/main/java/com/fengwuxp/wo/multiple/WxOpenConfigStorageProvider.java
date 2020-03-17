package com.fengwuxp.wo.multiple;

import me.chanjar.weixin.open.api.WxOpenConfigStorage;

/**
 * @Classname WxOpenConfigProvider
 * @Description 获取微信配置WxOpenConfigStorage配置对象
 * @Date 2020/3/16 19:26
 * @Created by 44487
 *
 * {@link WxOpenConfigStorage}
 */
public interface WxOpenConfigStorageProvider {

    WxOpenConfigStorage getWxOpenConfigStorage(String appId);

}
