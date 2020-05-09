package com.wuxp.api.signature;


/**
 * app info
 */
public interface AppInfo {

    String getAppId();

    String getAppSecret();

    /**
     * 渠道号
     *
     * @return
     */
    String getChannelCode();

}
