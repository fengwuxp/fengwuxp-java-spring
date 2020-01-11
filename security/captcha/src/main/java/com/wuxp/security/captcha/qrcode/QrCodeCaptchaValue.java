package com.wuxp.security.captcha.qrcode;

import com.wuxp.security.captcha.CaptchaType;
import com.wuxp.security.captcha.CaptchaValue;
import lombok.Getter;

@Getter
public class QrCodeCaptchaValue implements CaptchaValue {

    private final static CaptchaType CAPTCHA_TYPE = CaptchaType.QR_CODE;

    private final String useType;

    private final String value;

    private final long expireTime;

    private QrCodeState qrCodeState = QrCodeState.WAIT;

    public QrCodeCaptchaValue(String value, String useType, long expireTime) {
        this.value = value;
        this.useType = useType;
        this.expireTime = expireTime;
    }

    @Override
    public String getCaptchaType() {
        return CAPTCHA_TYPE.name();
    }

    @Override
    public boolean isExpired() {
        return this.getExpireTime() <= System.currentTimeMillis();
    }

    public void setQrCodeState(QrCodeState qrCodeState) {
        this.qrCodeState = qrCodeState;
    }
}
