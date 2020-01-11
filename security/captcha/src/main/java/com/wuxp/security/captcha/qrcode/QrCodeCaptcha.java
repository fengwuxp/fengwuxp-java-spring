package com.wuxp.security.captcha.qrcode;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CaptchaType;

import java.util.UUID;

/**
 * 二维码验证码
 */
public interface QrCodeCaptcha extends Captcha {


    @Override
    default CaptchaType getCaptchaType() {
        return CaptchaType.QR_CODE;
    }


    default QrCodeCaptchaGenerateResult generate(String useType) {
        return generate(useType, UUID.randomUUID().toString());
    }

    @Override
    QrCodeCaptchaGenerateResult generate(String useType, String key);

    /**
     * 获取二维码的状态
     *
     * @param key
     * @return
     */
    QrCodeState getQrCodeState(String key);

    /**
     * 更新二维码状态
     *
     * @param key
     * @param targetState
     * @throws QrCodeUpdateStateCaptchaRuntimeException
     */
    void updateQrCodeState(String key, QrCodeState targetState) throws QrCodeUpdateStateCaptchaRuntimeException;


}
