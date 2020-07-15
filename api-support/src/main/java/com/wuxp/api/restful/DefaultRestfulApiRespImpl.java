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
     * 业务失败时的响应消息
     */
    private final String errorMessage;

    /**
     * 业务失败时的错误响应码
     */
    private final int errorCode;

    protected DefaultRestfulApiRespImpl(T data, HttpStatus httpStatus, String errorMessage, int errorCode) {
        this.data = data;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
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
        return BUSINESS_SUCCESS_CODE == errorCode;
    }
}
