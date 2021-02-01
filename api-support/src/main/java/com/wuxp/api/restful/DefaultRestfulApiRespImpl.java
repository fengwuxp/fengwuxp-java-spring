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
    private static Serializable businessSuccessCodeErrorCode = DefaultBusinessErrorCode.BUSINESS_SUCCESS_CODE.getErrorCode();


    /**
     * 响应数据
     *
     * @serialField
     */
    private final T data;

    /**
     * 响应的http status
     */
    private final transient HttpStatus httpStatus;

    /**
     * 业务失败时的响应消息
     */
    private final String errorMessage;

    /**
     * 业务失败时的错误响应码
     */
    private final Serializable errorCode;

    protected DefaultRestfulApiRespImpl(T data, HttpStatus httpStatus, String errorMessage, BusinessErrorCode<? extends Serializable> errorCode) {
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
        return businessSuccessCodeErrorCode.equals(errorCode);
    }


    public static void setBusinessSuccessCode(BusinessErrorCode<? extends Serializable> businessSuccessCodeErrorCode) {
        DefaultRestfulApiRespImpl.businessSuccessCodeErrorCode = businessSuccessCodeErrorCode.getErrorCode();
    }
}
