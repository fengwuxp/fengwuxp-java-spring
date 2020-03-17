package com.fengwuxp.wo.multiple;

import me.chanjar.weixin.open.api.WxOpenService;

/**
 * @Classname WxOpenServiceManager
 * @Description 获取当前请求上下文WxOpenService对象
 * @Date 2020/3/16 19:17
 * @Created by 44487
 */
public interface WxOpenServiceManager {

    WxOpenService getWxOpenService();

    WxOpenService getWxOpenService(String appId);

    void removeWxOpenService(String appId);

    void clearAll();

}
