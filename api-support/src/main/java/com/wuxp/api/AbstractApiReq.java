package com.wuxp.api;


import com.wuxp.api.signature.ApiSignatureRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * @author wxup
 */
@Data
@Schema(name = "ApiBaseReq", description = "api基础请求对象")
public abstract class AbstractApiReq implements ApiSignatureRequest {

}
