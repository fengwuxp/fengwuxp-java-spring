package com.wuxp.security.authenticate;

/**
 * 认证类型
 */
public enum AuthenticateType {

    /**
     * 密码认证
     */
    PASSWORD,

    /**
     * 手机验证码认证
     */
    MOBILE_CAPTCHA,

    /**
     * 扫码认证
     */
    SCAN_CODE,

    /**
     * 第三方平台 openId 认证
     */
    OPEN_ID
}
