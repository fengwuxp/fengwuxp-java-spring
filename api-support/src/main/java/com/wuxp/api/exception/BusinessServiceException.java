package com.wuxp.api.exception;

import lombok.EqualsAndHashCode;


/**
 * 业务服务异常
 *
 * @author wxup
 */
@EqualsAndHashCode(of = "errorCode", callSuper = true)
public class BusinessServiceException extends RuntimeException implements BusinessException<Integer> {

    private static final long serialVersionUID = 7030081082091890804L;

    private Integer errorCode = DefaultBusinessErrorCode.BUSINESS_FAILURE_CODE.getErrorCode();

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

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return getMessage();
    }

}
