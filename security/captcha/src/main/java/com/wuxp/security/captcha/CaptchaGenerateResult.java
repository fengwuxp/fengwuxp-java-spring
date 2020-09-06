package com.wuxp.security.captcha;

import java.io.Serializable;

/**
 * {@link CaptchaStore}
 *
 * @author wxup
 * 验证码生成结果
 */
public interface CaptchaGenerateResult<T extends CaptchaValue> extends Serializable {

    /**
     * store key
     *
     * @return
     */
    String getKey();

    /**
     * store value
     *
     * @return
     */
    T getValue();

    /**
     * generate is success
     *
     * @return
     */
    boolean isSuccess();

    /**
     * generate error message
     *
     * @return
     */
    String getErrorMessage();
}
