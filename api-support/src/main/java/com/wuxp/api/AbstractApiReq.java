package com.wuxp.api;


import com.wuxp.api.signature.ApiSignatureRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Schema(name = "ApiBaseReq", description = "api基础请求对象")
public class AbstractApiReq implements ApiSignatureRequest {

    @Schema(name = "name", description = "应用分配到的appId")
    @NotNull(message = "appId不能为空")
    protected String appId;

    @Schema(name = "nonceStr", description = "用于签名的一次性随机字符串")
    @NotNull(message = "nonceStr不能为空")
    protected String nonceStr;

    @Schema(name = "timeStamp", description = "请求时间戳")
    @NotNull(message = "timeStamp不能为空")
    protected Long timeStamp;

    @Schema(name = "apiSignature", description = "接口签名")
    @NotNull(message = "apiSignature不能为空")
    protected String apiSignature;


}
