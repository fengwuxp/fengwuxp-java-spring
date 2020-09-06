package com.wuxp.security.captcha;


/**
 * simple captcha type
 *
 * @author wxup
 */
public enum SimpleCaptchaUseType implements CaptchaUseType {

    // 登陆
    LOGIN,

    // 注册
    REGISTER,

    // 重置密码
    REST_PASSWORD,

    // 支付
    PAY,

    // 绑定手机
    BIND_MOBILE_PHONE
}
