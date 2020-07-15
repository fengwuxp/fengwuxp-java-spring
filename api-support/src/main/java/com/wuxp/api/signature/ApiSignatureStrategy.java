package com.wuxp.api.signature;

import javax.validation.constraints.NotNull;

/**
 * api 接口签名策略
 *
 * @author wxup
 */
public interface ApiSignatureStrategy {


//    ApiSignatureStrategy NONE = (@NotNull ApiSignatureRequest request) -> {
//    };


    /**
     * 签名检查
     *
     * @param request 签名请求
     * @throws ApiSignatureException 签名验证失败抛出
     */
    void check(@NotNull ApiSignatureRequest request) throws ApiSignatureException;


}
