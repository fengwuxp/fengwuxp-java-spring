package com.wuxp.api.exception;

/**
 * @author wuxp
 */
public enum DefaultBusinessErrorCode implements BusinessErrorCode<Integer> {

    /**
     * 业务成功响应码
     */
    BUSINESS_SUCCESS_CODE(0, null),

    /**
     * 业务失败响应码
     */
    BUSINESS_FAILURE_CODE(-1, "业务处理失败"),

    /**
     * 踢出用户的业务错误编码
     */
    KICK_OUT_USER_ERROR_CODE(200001, "用户被踢出");


    private final int errorCode;

    private final String errorMessage;

    DefaultBusinessErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }


    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
