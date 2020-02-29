package com.fengwuxp.multiple.wechat;

/**
 * 调用服务时AppId提供的提供者
 */
public interface WeChatAppIdProvider {

    /**
     * 获取调用目标的AppId
     * @return
     */
    String getTargetAppId();
}
