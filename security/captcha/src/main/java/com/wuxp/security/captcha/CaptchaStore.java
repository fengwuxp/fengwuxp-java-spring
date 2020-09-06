package com.wuxp.security.captcha;

/**
 * Used to save the captcha
 * @author wuxp
 */
public interface CaptchaStore {


    /**
     * Save the captcha to the store
     *
     * @param key
     * @param captchaValue
     */
    void storeCaptcha(String key, CaptchaValue captchaValue);

    /**
     * Read captcha from store
     *
     * @param key
     * @param captchaTyp captcha type {@link CaptchaType}
     * @return
     */
    <T extends CaptchaValue> T readCaptcha(String key, String captchaTyp);

    /**
     * remove captcha
     *
     * @param key
     * @param captchaTyp captcha type
     */
    void removeCaptcha(String key, String captchaTyp);

}
