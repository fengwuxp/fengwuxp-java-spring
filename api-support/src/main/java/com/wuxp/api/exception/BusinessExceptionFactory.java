package com.wuxp.api.exception;

/**
 * 业务系统自定义的的业务异常创建工厂
 * {@link BusinessException}
 * {@link BusinessErrorCode}
 * @author wuxp
 */
public interface BusinessExceptionFactory<T, E extends RuntimeException> {

    /**
     * 创建一个业务异常对象
     *
     * @param cause
     * @param message
     * @param errorCode
     * @throws E
     */
    void factory(Throwable cause, String message, BusinessErrorCode<T> errorCode) throws E;

    /**
     * 获取业务成功的code
     *
     * @return BusinessErrorCode
     */
    BusinessErrorCode<T> getSuccessCode();

    /**
     * 获取业务失败的code
     *
     * @return BusinessErrorCode
     */
    BusinessErrorCode<T> getErrorCode();
}
