package com.wuxp.security.captcha;

import java.io.Serializable;

/**
 * @author wxup
 * 验证码生成结果
 */
public interface CaptchaGenerateResult extends Serializable {

    String getKey();

    CaptchaValue getValue();

    boolean isSuccess();

    String getErrorMessage();
}
