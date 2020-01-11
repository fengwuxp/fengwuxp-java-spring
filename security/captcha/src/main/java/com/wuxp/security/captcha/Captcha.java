package com.wuxp.security.captcha;

import lombok.Getter;

/**
 * Used to generate verification codes and verification
 */
public interface Captcha {


    CaptchaType getCaptchaType();


    /**
     * generate captcha
     *
     * @param useType captcha scenes to be used
     * @param key     captcha key   key="CaptchaType_useType_uuid"
     * @return
     */
    CaptchaGenerateResult generate(String useType, String key);

    /**
     * 验证码是否有效
     *
     * @param key
     * @return
     */
    boolean isEffective(String key);

    /**
     * verify captcha
     *
     * @param captchaValue
     * @return
     */
    CaptchaVerifyResult verify(String key, CaptchaValue captchaValue);


    /**
     * captcha verify result
     */
    @Getter
    class CaptchaVerifyResult {
        private final boolean success;
        private final String errorMessage;

        private CaptchaVerifyResult(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }

        public static CaptchaVerifyResult success() {
            return new CaptchaVerifyResult(true, null);
        }

        public static CaptchaVerifyResult error(String errorMessage) {
            return new CaptchaVerifyResult(false, errorMessage);
        }
    }
}

