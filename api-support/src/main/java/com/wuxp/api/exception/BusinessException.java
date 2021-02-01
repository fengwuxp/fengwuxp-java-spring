package com.wuxp.api.exception;

import java.io.Serializable;

/**
 * 业务异常接口定义
 * {@link BusinessExceptionFactory}
 *
 * @param <T> 字符串或数值
 * @author wuxp
 */
public interface BusinessException<T extends Serializable> extends BusinessErrorCode<T> {


}
