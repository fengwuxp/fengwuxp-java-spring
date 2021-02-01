package com.wuxp.api.exception;

import java.io.Serializable;

/**
 * 业务系统自定义的的业务异常创建工厂
 * {@link BusinessException}
 * {@link BusinessErrorCode}
 *
 * @author wuxp
 */
public interface BusinessExceptionFactory<T extends Serializable> {

    /**
     * 创建（转换）一个业务异常对象，并抛出
     *
     * @param cause     异常
     * @param message   错误消息
     * @param errorCode 异常吗
     * @see RuntimeException
     */
    void factory(Throwable cause, String message, BusinessErrorCode<T> errorCode);

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
