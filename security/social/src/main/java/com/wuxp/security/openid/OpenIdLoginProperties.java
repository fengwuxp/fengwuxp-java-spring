package com.wuxp.security.openid;

import lombok.Data;

/**
 * openid 登录相关
 * @author wuxp
 */
@Data
public class OpenIdLoginProperties {

    /**
     * 登录处理url
     */
    private String loginProcessingUrl = "/social_openid/{social_type}/login";
}
