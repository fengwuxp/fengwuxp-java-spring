package com.wuxp.security.captcha.qrcode;

import com.wuxp.security.captcha.CaptchaGenerateResult;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
public class QrCodeCaptchaGenerateResult implements CaptchaGenerateResult {

    private final String key;

    private final QrCodeCaptchaValue value;

    private String errorMessage;


    private QrCodeCaptchaGenerateResult(String useType, String key, String value, long expireTime) {
        this.key = MessageFormat.format("{0}_{1}", useType, key);
        if (value == null) {
            value = this.key;
        }
        this.value = new QrCodeCaptchaValue(useType, value, expireTime);
    }

    @Override
    public boolean isSuccess() {
        return this.value != null;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static QrCodeCaptchaGenerateResult newInstance(String useType, String key, long expireTime) {
        return newInstance(useType, key, null, expireTime);
    }

    public static QrCodeCaptchaGenerateResult newInstance(String useType, String key, String value, long expireTime) {
        return new QrCodeCaptchaGenerateResult(useType, key, value, expireTime);
    }
}
