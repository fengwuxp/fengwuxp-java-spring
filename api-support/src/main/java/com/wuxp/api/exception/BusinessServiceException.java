package com.wuxp.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.wuxp.api.restful.DefaultRestfulApiRespImpl.BUSINESS_FAILURE_CODE;


/**
 * 业务服务异常
 *
 * @author wxup
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessServiceException extends RuntimeException {

    private static final long serialVersionUID = 7030081082091890804L;

    private Integer errorCode = BUSINESS_FAILURE_CODE;

    public BusinessServiceException() {
    }

    public BusinessServiceException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessServiceException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessServiceException(String message, Throwable cause, Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BusinessServiceException(Throwable cause, Integer errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BusinessServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public BusinessServiceException(String message) {
        super(message);
    }

    public BusinessServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessServiceException(Throwable cause) {
        super(cause);
    }

    public BusinessServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
