package com.wuxp.security.captcha.mobile;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CaptchaType;

/**
 * mobile phone captcha
 */
public interface MobileCaptcha extends Captcha {


    @Override
    default CaptchaType getCaptchaType() {
        return CaptchaType.MOBILE;
    }

    /**
     *
     * @param useType captcha scenes to be used
     * @param mobilePhone mobilePhone
     * @return
     */
    @Override
    MobileCaptchaGenerateResult generate(String useType, String mobilePhone);

}
