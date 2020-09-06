package com.wuxp.security.captcha.picture;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CaptchaType;

/**
 * picture captcha
 * support type {@link PictureCaptchaType}
 *
 * @author wuxp
 */
public interface PictureCaptcha extends Captcha<PictureCaptchaValue, PictureCaptchaGenerateResult> {

    @Override
    default CaptchaType getCaptchaType() {
        return CaptchaType.PICTURE;
    }


    /**
     * generate captcha
     *
     * @param type    captcha scenes to be used
     * @param useType captcha key
     * @return
     */
    @Override
    default PictureCaptchaGenerateResult generate(String type, String useType) {
        return generate(type, useType, null);
    }

    /**
     * generate captcha
     *
     * @param type
     * @param useType
     * @param value
     * @return
     */
    default PictureCaptchaGenerateResult generate(String type, String useType, String value) {
        return generate(type, useType, null, value);
    }

    /**
     * generate captcha
     *
     * @param type
     * @param useType
     * @param key
     * @param value
     * @return
     */
    PictureCaptchaGenerateResult generate(String type, String useType, String key, String value);
}
