package com.oak.api.services.app.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建接入账号")
public class CreateAppAuthAccountReq extends ApiBaseReq {

    @Schema(name = "appId", description = "app接入id")
    private String appId;

    @Schema(name = "appSecret", description = "接入密钥")
    private String appSecret;

    @Schema(name = "name", description = "应用名称")
    private String name;

    @Schema(name = "type", description = "应用类型")
    @Size(max = 32)
    private String type;

    @Schema(name = "enabled", description = "是否启用")
    private Boolean enabled = Boolean.TRUE;


}
