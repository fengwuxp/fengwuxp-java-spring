package com.wuxp.security.captcha;

import lombok.Getter;
import lombok.NonNull;

/**
 * Used to generate verification codes and verification
 *
 * @param <V> {@link CaptchaValue}
 * @param <T> {@link CaptchaGenerateResult}
 * @author wuxp
 */
public interface Captcha<V extends CaptchaValue, T extends CaptchaGenerateResult<V>> {

    /**
     * generate captcha type
     *
     * @return
     */
    CaptchaType getCaptchaType();


    /**
     * generate captcha
     *
     * @param useType captcha scenes to be used
     * @param key     captcha store key,example: key="CaptchaType_useType_uuid"
     * @return generate result
     */
    T generate(@NonNull String useType, @NonNull String key);

    /**
     * generate captcha
     *
     * @param useType {@link CaptchaUseType}
     * @param key     captcha store key
     * @return generate result
     */
    default T generate(@NonNull CaptchaUseType useType, @NonNull String key) {
        return generate(useType.name(), key);
    }


    /**
     * 验证码是否有效
     *
     * @param key captcha store key
     * @return if return <code>true</code> {@param key} is effective
     */
    boolean isEffective(@NonNull String key);

    /**
     * verify captcha
     *
     * @param key          captcha store key
     * @param captchaValue captcha value
     * @return verify result
     */
    CaptchaVerifyResult verify(@NonNull String key, @NonNull CaptchaValue captchaValue);


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

