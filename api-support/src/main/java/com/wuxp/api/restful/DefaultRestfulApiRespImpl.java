package com.wuxp.api.restful;

import com.wuxp.api.ApiResp;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.beans.Transient;


/**
 * @param <T>
 */
@Getter
@ToString(exclude = "httpStatus")
public final class DefaultRestfulApiRespImpl<T> implements ApiResp<T> {

    public static final int BUSINESS_SUCCESS_CODE = 0;

    public static final int BUSINESS_FAILURE_CODE = -1;

    /**
     * 响应数据
     */
    private final T data;

    /**
     * 响应的http status
     */
    private transient final HttpStatus httpStatus;

    /**
     * 响应的消息
     */
    private final String message;

    /**
     * 响应的页面编码
     */
    private final int code;

    protected DefaultRestfulApiRespImpl(T data, HttpStatus httpStatus, String message, int code) {
        this.data = data;
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }

    @Override
    @Transient
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return BUSINESS_SUCCESS_CODE == code;
    }
}
