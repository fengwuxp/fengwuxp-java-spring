package com.wuxp.api.restful;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import org.springframework.http.HttpStatus;

import static com.wuxp.api.restful.DefaultRestfulApiRespImpl.BUSINESS_FAILURE_CODE;
import static com.wuxp.api.restful.DefaultRestfulApiRespImpl.BUSINESS_SUCCESS_CODE;

public final class RestfulApiRespFactory {



    /*-------------------- 2xx -------------------*/

    public static <T> ApiResp<T> ok() {

        return newInstance(HttpStatus.OK, null, BUSINESS_SUCCESS_CODE, null);
    }

    public static <T> ApiResp<T> ok(T data) {

        return newInstance(HttpStatus.OK, null, BUSINESS_SUCCESS_CODE, data);
    }

    /**
     * 返回查询成功
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> ApiResp<Pagination<T>> queryOk(Pagination<T> list) {

        return newInstance(HttpStatus.OK, null, BUSINESS_SUCCESS_CODE, list);
    }

    public static <T> ApiResp<T> created(T data) {

        return newInstance(HttpStatus.CREATED, null, BUSINESS_SUCCESS_CODE, data);
    }


    /*-------------------- 4xx -------------------*/

    public static <T> ApiResp<T> badRequest(String errorMessage) {

        return newInstance(HttpStatus.BAD_REQUEST, errorMessage, BUSINESS_FAILURE_CODE, null);
    }

    public static <T> ApiResp<T> notFound(String errorMessage) {

        return newInstance(HttpStatus.NOT_FOUND, errorMessage, BUSINESS_FAILURE_CODE, null);
    }

    public static <T> ApiResp<T> unAuthorized(String errorMessage) {

        return newInstance(HttpStatus.UNAUTHORIZED, errorMessage, BUSINESS_FAILURE_CODE, null);
    }

    public static <T> ApiResp<T> unAuthorized(String errorMessage, int code) {

        return newInstance(HttpStatus.UNAUTHORIZED, errorMessage, code, null);
    }

    public static <T> ApiResp<T> unAuthorized(String errorMessage, T data) {

        return newInstance(HttpStatus.UNAUTHORIZED, errorMessage, BUSINESS_FAILURE_CODE, data);
    }

    public static <T> ApiResp<T> forBidden(String errorMessage) {

        return newInstance(HttpStatus.FORBIDDEN, errorMessage, BUSINESS_FAILURE_CODE, null);
    }

    /*-------------------- business handle error -------------------*/

    public static <T> ApiResp<T> error(String errorMessage, int code, T data) {

        return newInstance(HttpStatus.OK, errorMessage, code, data);
    }

    public static <T> ApiResp<T> error(String errorMessage, T data) {

        return error(errorMessage, BUSINESS_FAILURE_CODE, data);
    }

    public static <T> ApiResp<T> error(String errorMessage) {

        return error(errorMessage, null);
    }

    private static <T> ApiResp<T> newInstance(HttpStatus httpStatus, String errorMessage, int code, T data) {
        return new DefaultRestfulApiRespImpl<>(data, httpStatus, errorMessage, code);
    }

}
