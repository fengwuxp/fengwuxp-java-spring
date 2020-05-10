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

    String CHANNEL_CODE = "channelCode";

    String INJECT_APP_ID_KEY = "#appId";

    String INJECT_NONCE_STR_KEY = "#nonceStr";

    String INJECT_TIME_STAMP = "#timeStamp";

    String INJECT_CHANNEL_CODE = "#channelCode";


    /**
     * 获取签名的AppId
     */
//    @Transient
//    String getAppId();

//    void setAppId(String appId);

    /**
     * 一次性随机字符
     *
     * @return
     */
//    @Transient
//    String getNonceStr();

//    void setNonceStr(String nonceStr);

    /**
     * 时间戳
     *
     * @return
     */
//    @Transient
//    Long getTimeStamp();

//    void setTimeStamp(Long timeStamp);

    /**
     * 获取渠道号
     * @return
     */
//    String getChannelCode();

//    void setChannelCode(String channelCode);
}
