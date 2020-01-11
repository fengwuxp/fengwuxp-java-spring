package com.wuxp.api.signature;

import javax.validation.constraints.NotNull;

/**
 * api 接口签名策略
 */
public interface ApiSignatureStrategy {


//    ApiSignatureStrategy NONE = (@NotNull ApiSignatureRequest request) -> {
//    };


    /**
     * 签名检查
     *
     * @param request
     * @throws ApiSignatureException
     */
    void check(@NotNull ApiSignatureRequest request) throws ApiSignatureException;


}
