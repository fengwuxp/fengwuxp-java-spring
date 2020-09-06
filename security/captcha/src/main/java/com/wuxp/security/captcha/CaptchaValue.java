package com.wuxp.security.captcha;


import java.io.Serializable;


/**
 * {@link CaptchaStore}
 *
 * @author wxup
 * 验证码值
 */
public interface CaptchaValue extends Serializable {

    /**
     * captcha type  {@link CaptchaType}
     *
     * @return
     */
    String getCaptchaType();

    /**
     * captcha user type {@link CaptchaUseType}
     *
     * @return
     */
    String getUseType();

    /**
     * captcha value
     *
     * @return
     */
    String getValue();

    /**
     * captcha is expired
     *
     * @return
     */
    boolean isExpired();
}
