package com.wuxp.security.captcha.picture;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CaptchaType;

/**
 * picture captcha
 * support type {@link PictureCaptchaType}
 */
public interface PictureCaptcha extends Captcha {

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
    default PictureCaptchaGenerateResult generate(String type, String useType) {
//        String[] types = useType.split("_");
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
     * @param value
     * @return
     */
    PictureCaptchaGenerateResult generate(String type, String useType, String key, String value);
}
