package com.oak.api.model;

import com.levin.commons.dao.annotation.Ignore;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.signature.ApiSignatureRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Data
@Schema(name = "ApiBaseReq", description = "api基础请求对象")
@Validated
public class ApiBaseReq implements ApiSignatureRequest {

    @Schema(name = "name", description = "应用分配到的appId")
    @NotNull(message = "appId不能为空")
    @Ignore
    @InjectField(value = INJECT_APP_ID_KEY)
    protected String appId;

    @Schema(name = "nonceStr", description = "用于签名的一次性随机字符串")
    @NotNull
    @Ignore
    @NotNull(message = "nonceStr不能为空")
    @InjectField(value = INJECT_NONCE_STR_KEY)
    protected String nonceStr;

    @Schema(name = "timeStamp", description = "请求时间戳")
    @NotNull(message = "timeStamp不能为空")
    @Ignore
    @InjectField(value = INJECT_TIME_STAMP)
    protected Long timeStamp;

    @Schema(name = "apiSignature", description = "接口签名")
    @NotNull(message = "apiSignature不能为空")
    @Ignore
    @InjectField(value = INJECT_APP_SIGNATURE)
    protected String apiSignature;

    @Schema(name = "channelCode", description = "渠道号")
    @NotNull(message = "渠道号不能为空")
    @Ignore
    @InjectField(value = INJECT_CHANNEL_CODE)
    protected String channelCode;

}
