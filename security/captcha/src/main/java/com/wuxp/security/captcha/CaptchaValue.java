package com.wuxp.security.captcha;


import java.io.Serializable;


/**
 * @author wxup
 * 验证码值
 */
public interface CaptchaValue extends Serializable {

    String getCaptchaType();

    String getUseType();

    String getValue();

//    long getExpireTime();

    boolean isExpired();
}
