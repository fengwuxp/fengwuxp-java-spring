package com.wuxp.api;

import java.beans.Transient;

/**
 * 接口请求对象
 */
public interface ApiRequest {

    String APP_ID_KEY = "appId";

    String APP_SECRET_KEY = "appSecret";

    String NONCE_STR_KEY = "nonceStr";

    String TIME_STAMP = "timeStamp";

    /**
     * 获取签名的AppId
     */
    @Transient
    String getAppId();

    /**
     * 一次性随机字符
     *
     * @return
     */
    @Transient
    String getNonceStr();

    /**
     * 时间戳
     *
     * @return
     */
    @Transient
    Long getTimeStamp();

}
