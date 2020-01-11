package com.wuxp.security.openid;

import lombok.Data;

/**
 * openid 登录相关
 */
@Data
public class OpenIdLoginProperties {

    /**
     * 登录处理url
     */
    private String loginProcessingUrl = "/social_openid/login";
}
