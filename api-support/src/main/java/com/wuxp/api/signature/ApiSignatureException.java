package com.wuxp.api.signature;

/**
 * api 签名验证异常
 */
public class ApiSignatureException extends RuntimeException {

    public ApiSignatureException() {
    }

    public ApiSignatureException(String message) {
        super(message);
    }

    public ApiSignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiSignatureException(Throwable cause) {
        super(cause);
    }

    public ApiSignatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
