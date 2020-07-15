package com.wuxp.api.signature;


/**
 * app info
 *
 * @author wuxp
 */
public interface AppInfo extends java.io.Serializable {

    /**
     * @return 应用app id
     */
    String getAppId();

    /**
     * @return 应用app秘钥
     */
    String getAppSecret();

    /**
     * @return 应用渠道号
     */
    String getChannelCode();

}
