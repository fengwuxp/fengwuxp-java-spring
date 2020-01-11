package com.wuxp.security.authenticate;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码验证异常
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public CaptchaException(String msg) {
        super(msg);
    }
}
