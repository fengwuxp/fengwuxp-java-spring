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

    private static final Integer DEFAULT_ERROR_CODE = DefaultBusinessErrorCode.BUSINESS_SUCCESS_CODE.getErrorCode();

    private final Integer errorCode;

    public BusinessServiceException() {
        this(DEFAULT_ERROR_CODE);
    }

    public BusinessServiceException(Integer errorCode) {
        this("", errorCode);
    }

    public BusinessServiceException(String message) {
        this(message, DEFAULT_ERROR_CODE);
    }

    public BusinessServiceException(Throwable cause) {
        this(cause, DEFAULT_ERROR_CODE);
    }

    public BusinessServiceException(String message, Throwable cause) {
        this(message, cause, DEFAULT_ERROR_CODE);
    }

    public BusinessServiceException(Throwable cause, Integer errorCode) {
        this(null, cause, errorCode);
    }

    public BusinessServiceException(String message, Integer errorCode) {
        this(message, null, errorCode);
    }

    public BusinessServiceException(String message, Throwable cause, Integer errorCode) {
        this(message, cause, true, true, errorCode);
    }

    public BusinessServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_ERROR_CODE);
    }

    public BusinessServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
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
