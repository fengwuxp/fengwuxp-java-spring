package com.wuxp.api;

import com.wuxp.api.restful.DefaultRestfulApiRespImpl;
import com.wuxp.api.restful.RestfulResponseBodyAdvice;
import org.springframework.http.HttpStatus;

import java.beans.Transient;

/**
 * api 统一响应
 *
 * @param <T>
 * @author wuxp
 * @see DefaultRestfulApiRespImpl
 * @see RestfulResponseBodyAdvice
 */
public interface ApiResp<T> {

    /**
     * 获取本次请求响应的 http状态码
     *
     * @return {@link HttpStatus} <code>{@link HttpStatus#OK}</code> 通讯层面表示成功
     */
    @Transient
    HttpStatus getHttpStatus();

    /**
     * {@link #isSuccess()} 如果返回false,则表明业务处理失败，该数据则为业务失败时的数据
     *
     * @return 本次响应的数据
     */
    T getData();

    /**
     * 本次请求业务上是否成功
     *
     * @return <code>true</code> 业务处理成功
     */
    boolean isSuccess();

    /**
     * 获取消息描述
     *
     * @return 业务处理失败时返回的消息描述
     */
    String getErrorMessage();
}
