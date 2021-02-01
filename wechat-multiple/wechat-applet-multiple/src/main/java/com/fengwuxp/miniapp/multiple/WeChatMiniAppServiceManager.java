package com.fengwuxp.miniapp.multiple;


import cn.binarywang.wx.miniapp.api.WxMaService;
import com.fengwuxp.wechat.multiple.HttpRequestWeChatServiceAppIdProvider;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

/**
 * 微信小程序服务管理
 *
 * @author wuxp
 */
public interface WeChatMiniAppServiceManager {


    /**
     * 获取当前请求上下文的小程序服务
     *
     * @return WxMaService
     * @see RequestScope
     * @see SessionScope
     * @see HttpRequestWeChatServiceAppIdProvider
     */
    WxMaService getWxMaService();

    WxMaService getWxMaService(String appId);

    void removeWxMpService(String appId);

    void clearAll();
}
