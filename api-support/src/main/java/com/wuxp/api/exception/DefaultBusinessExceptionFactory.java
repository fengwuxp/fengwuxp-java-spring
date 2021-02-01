package com.wuxp.api.exception;

/**
 * 默认的业务异常工厂
 *
 * @author wuxp
 * @see BusinessServiceException
 */
public class DefaultBusinessExceptionFactory implements BusinessExceptionFactory<Integer> {

    public static final BusinessExceptionFactory<Integer> DEFAULT_EXCEPTION_FACTORY = new DefaultBusinessExceptionFactory();

    @Override
    public void factory(Throwable cause, String message, BusinessErrorCode<Integer> errorCode) {
        throw new BusinessServiceException(message, cause, errorCode.getErrorCode());
    }

    @Override
    public BusinessErrorCode<Integer> getSuccessCode() {
        return DefaultBusinessErrorCode.BUSINESS_SUCCESS_CODE;
    }

    @Override
    public BusinessErrorCode<Integer> getErrorCode() {
        return DefaultBusinessErrorCode.BUSINESS_FAILURE_CODE;
    }
}
