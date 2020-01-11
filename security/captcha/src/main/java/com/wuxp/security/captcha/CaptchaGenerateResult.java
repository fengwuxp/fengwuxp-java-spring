package com.wuxp.security.captcha;

public interface CaptchaGenerateResult {

    String getKey();

    CaptchaValue getValue();

    boolean isSuccess();

    String getErrorMessage();
}
