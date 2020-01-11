package com.wuxp.security.captcha.qrcode;

/**
 * 更新状态异常
 */
public class QrCodeUpdateStateCaptchaRuntimeException extends RuntimeException {
    public QrCodeUpdateStateCaptchaRuntimeException() {
    }

    public QrCodeUpdateStateCaptchaRuntimeException(String message) {
        super(message);
    }
}
