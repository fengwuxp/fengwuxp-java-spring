package com.wuxp.security.captcha.mobile;

import lombok.Getter;


/**
 * 手机验证码 发送者
 * @author wxup
 */
public interface MobileCaptchaSender {

    /**
     * send mobile captcha
     * {@link MobileCaptchaType}
     *
     * @param useType     captcha scenes to be used
     * @param mobilePhone captcha key
     * @param value       captcha value
     * @return
     */
    MobileCaptchaSenderResult send(String useType, String mobilePhone, String value);

    /**
     * send mobile captcha
     *
     * @param useType     captcha scenes to be used
     * @param mobilePhone captcha key
     * @return
     */
    default MobileCaptchaSenderResult send(String useType, String mobilePhone) {
        return send(useType, mobilePhone, null);
    }


    @Getter
    class MobileCaptchaSenderResult {

        private final boolean success;

        private final String message;


        public MobileCaptchaSenderResult() {
            this.success = true;
            this.message = null;
        }

        public MobileCaptchaSenderResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
