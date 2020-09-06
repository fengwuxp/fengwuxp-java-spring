package com.wuxp.security.captcha.mobile;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CaptchaType;

/**
 * mobile phone captcha
 *
 * @author wuxp
 */
public interface MobileCaptcha extends Captcha<MobileCaptchaValue, MobileCaptchaGenerateResult> {


    @Override
    default CaptchaType getCaptchaType() {
        return CaptchaType.MOBILE;
    }


}
