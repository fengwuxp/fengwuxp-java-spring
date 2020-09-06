package com.wuxp.api.exception;


/**
 * 业务异常错误码，建议使用枚举实现
 *
 * @param <T>
 * @author wuxp
 */
public interface BusinessErrorCode<T> {

    /**
     * 用于获取业务异常错误码
     *
     * @return 字符串、数值
     */
    T getErrorCode();

    /**
     * 错误消息
     *
     * @return
     */
    String getErrorMessage();
}
