package com.wuxp.api.restful;

import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.BusinessErrorCode;
import com.wuxp.api.exception.BusinessExceptionFactory;
import com.wuxp.api.exception.DefaultBusinessExceptionFactory;
import com.wuxp.api.model.Pagination;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


/**
 * @author wuxp
 */
public final class RestfulApiRespFactory {

    private RestfulApiRespFactory() {
    }

    /**
     * 业务异常工厂
     */
    private static BusinessExceptionFactory<? extends Serializable> businessExceptionFactory = DefaultBusinessExceptionFactory.DEFAULT_EXCEPTION_FACTORY;


    /*-------------------- 2xx -------------------*/

    public static <T> ApiResp<T> ok(T data) {

        return newInstance(HttpStatus.OK, null, getSuccessCode(), data);
    }

    public static <T> ApiResp<T> ok() {

        return ok(null);
    }

    /**
     * 返回查询成功
     *
     * @param pagination 分页信息
     * @param <T>        分页内容类型
     * @return ApiResp<T>
     */
    public static <T> ApiResp<Pagination<T>> queryOk(Pagination<T> pagination) {
        return ok(pagination);
    }

    /**
     * 创建操作处理成功
     *
     * @param data 需要返回的数据
     * @param <T> 数据
     * @return ApiResp<T>
     */
    public static <T> ApiResp<T> created(T data) {
        return newInstance(HttpStatus.CREATED, null, getSuccessCode(), data);
    }

    public static <T> ApiResp<T> created() {
        return created(null);
    }


    /*-------------------- 4xx -------------------*/

    public static <T> ApiResp<T> badRequest(String errorMessage) {

        return newInstance(HttpStatus.BAD_REQUEST, errorMessage, getErrorCode(), null);
    }

    public static <T> ApiResp<T> notFound(String errorMessage) {

        return newInstance(HttpStatus.NOT_FOUND, errorMessage, getErrorCode(), null);
    }

    public static <T> ApiResp<T> unAuthorized(String errorMessage) {

        return newInstance(HttpStatus.UNAUTHORIZED, errorMessage, getErrorCode(), null);
    }

    public static <T> ApiResp<T> unAuthorized(String errorMessage, BusinessErrorCode<? extends Serializable> code) {

        return newInstance(HttpStatus.UNAUTHORIZED, errorMessage, code, null);
    }

    public static <T> ApiResp<T> unAuthorized(String errorMessage, T data) {

        return newInstance(HttpStatus.UNAUTHORIZED, errorMessage, getErrorCode(), data);
    }

    public static <T> ApiResp<T> forBidden(String errorMessage) {

        return newInstance(HttpStatus.FORBIDDEN, errorMessage, getErrorCode(), null);
    }

    /*-------------------- business handle error -------------------*/

    public static <T> ApiResp<T> error(String errorMessage, BusinessErrorCode<? extends Serializable> code, T data) {

        return newInstance(HttpStatus.OK, errorMessage, code, data);
    }

    public static <T> ApiResp<T> error(String errorMessage, T data) {

        return error(errorMessage, getErrorCode(), data);
    }

    public static <T> ApiResp<T> error(String errorMessage) {

        return error(errorMessage, null);
    }

    private static <T> ApiResp<T> newInstance(HttpStatus httpStatus, String errorMessage, BusinessErrorCode<? extends Serializable> code, T data) {
        return new DefaultRestfulApiRespImpl<>(data, httpStatus, errorMessage, code);
    }


    private static BusinessErrorCode<? extends Serializable> getErrorCode() {
        return RestfulApiRespFactory.businessExceptionFactory.getErrorCode();
    }

    private static BusinessErrorCode<? extends Serializable> getSuccessCode() {
        return RestfulApiRespFactory.businessExceptionFactory.getSuccessCode();
    }

    public static void setBusinessExceptionFactory(BusinessExceptionFactory<? extends Serializable> businessExceptionFactory) {
        RestfulApiRespFactory.businessExceptionFactory = businessExceptionFactory;
    }
}
