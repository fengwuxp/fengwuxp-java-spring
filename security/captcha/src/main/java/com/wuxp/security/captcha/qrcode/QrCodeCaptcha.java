package com.wuxp.security.captcha.qrcode;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CaptchaType;
import com.wuxp.security.captcha.CaptchaUseType;
import lombok.NonNull;

import java.util.UUID;

/**
 * 二维码验证码
 *
 * @author wuxp
 */
public interface QrCodeCaptcha extends Captcha<QrCodeCaptchaValue, QrCodeCaptchaGenerateResult> {


    @Override
    default CaptchaType getCaptchaType() {
        return CaptchaType.QR_CODE;
    }


    default QrCodeCaptchaGenerateResult generate(@NonNull CaptchaUseType useType) {
        return generate(useType.name(), UUID.randomUUID().toString());
    }

    @Override
    QrCodeCaptchaGenerateResult generate(String useType, String key);

    /**
     * 获取二维码的状态
     *
     * @param key store key
     * @return current state
     */
    QrCodeState getQrCodeState(String key);

    /**
     * 更新二维码状态
     *
     * @param key         store key
     * @param targetState current state
     * @throws QrCodeUpdateStateCaptchaException 状态更新失败
     */
    void updateQrCodeState(String key, QrCodeState targetState) throws QrCodeUpdateStateCaptchaException;


}
