package com.fengwuxp.mp.multiple;

import com.fengwuxp.wechat.multiple.HttpRequestWeChatServiceAppIdProvider;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;


/**
 * 微信公众号服务管理
 */
public interface WeChatMpServiceManager {

    /**
     * 获取当前请求上下文的微信公众号服务
     *
     * @return
     * @see RequestScope
     * @see SessionScope
     * @see HttpRequestWeChatServiceAppIdProvider
     */
    WxMpService getWxMpService();

    WxMpService getWxMpService(String appId);

    void removeWxMpService(String appId);

    void clearAll();
}
