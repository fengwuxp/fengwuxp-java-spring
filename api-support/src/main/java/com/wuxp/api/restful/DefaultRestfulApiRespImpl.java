package com.wuxp.api.restful;

import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.BusinessErrorCode;
import com.wuxp.api.exception.DefaultBusinessErrorCode;
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


    private static final long serialVersionUID = -7557721954943132992L;


    /**
     * 业务成功响应码
     */
    private static Object BUSINESS_SUCCESS_CODE = DefaultBusinessErrorCode.BUSINESS_SUCCESS_CODE.getErrorCode();


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
    private final Object errorCode;

    protected DefaultRestfulApiRespImpl(T data, HttpStatus httpStatus, String errorMessage, BusinessErrorCode<?> errorCode) {
        this.data = data;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode.getErrorCode();
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
        return BUSINESS_SUCCESS_CODE.equals(errorCode);
    }


    public static void setBusinessSuccessCode(BusinessErrorCode businessSuccessCode) {
        BUSINESS_SUCCESS_CODE = businessSuccessCode.getErrorCode();
    }
}
