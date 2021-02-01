package com.wuxp.api;

/**
 * mark interface
 * 接口请求对象
 *
 * @author wxup
 */
public interface ApiRequest extends java.io.Serializable{

    String APP_ID_KEY = "appId";

    String APP_SECRET_KEY = "appSecret";

    String NONCE_STR_KEY = "nonceStr";

    String TIME_STAMP = "timeStamp";

    String CHANNEL_CODE = "channelCode";

    String INJECT_APP_ID_KEY = "#appId";

    String INJECT_NONCE_STR_KEY = "#nonceStr";

    String INJECT_TIME_STAMP = "#timeStamp";

    String INJECT_CHANNEL_CODE = "#channelCode";


}
