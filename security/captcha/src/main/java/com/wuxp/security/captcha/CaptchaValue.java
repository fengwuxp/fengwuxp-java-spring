package com.wuxp.security.captcha;


public interface CaptchaValue {

    String getCaptchaType();

    String getUseType();

    String getValue();

//    long getExpireTime();

    boolean isExpired();
}
