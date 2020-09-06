package com.wuxp.security.captcha.qrcode;

/**
 * 更新状态异常
 * @author wuxp
 */
public class QrCodeUpdateStateCaptchaException extends RuntimeException {
    public QrCodeUpdateStateCaptchaException() {
    }

    public QrCodeUpdateStateCaptchaException(String message) {
        super(message);
    }
}
