package com.wuxp.api;

import com.wuxp.api.restful.DefaultRestfulApiRespImpl;
import com.wuxp.api.restful.RestfulResponseBodyAdvice;
import org.springframework.http.HttpStatus;

import java.beans.Transient;

/**
 * api 统一响应
 *
 * @param <T>
 * @see DefaultRestfulApiRespImpl
 * @see RestfulResponseBodyAdvice
 */
public interface ApiResp<T> {

    /**
     * 获取本次请求响应的 http状态码
     *
     * @return
     */
    @Transient
    HttpStatus getHttpStatus();

    /**
     * 本次响应的数据
     *
     * @return
     */
    T getData();

    /**
     * 本次请求业务上是否成功
     *
     * @return
     */
    boolean isSuccess();
}
