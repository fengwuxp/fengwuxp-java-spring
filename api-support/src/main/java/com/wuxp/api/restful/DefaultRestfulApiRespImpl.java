package com.wuxp.api.restful;

import com.wuxp.api.ApiResp;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.beans.Transient;
import java.io.Serializable;


/**
 * @param <T>
 * @author wxup
 */
@Getter
@ToString(exclude = "httpStatus")
public final class DefaultRestfulApiRespImpl<T> implements ApiResp<T>, Serializable {


    /**
     * 业务成功响应码
     */
    public static final int BUSINESS_SUCCESS_CODE = 0;

    /**
     * 业务失败响应码
     */
    public static final int BUSINESS_FAILURE_CODE = -1;


    private static final long serialVersionUID = -7557721954943132992L;

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
