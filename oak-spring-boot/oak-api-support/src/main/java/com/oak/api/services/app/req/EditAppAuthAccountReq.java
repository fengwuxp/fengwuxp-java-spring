package com.oak.api.services.app.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "编辑接入账号")
public class EditAppAuthAccountReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    private Long id;

    @Schema(description = "接入密钥")
    @UpdateColumn
    private String appSecret;

    @Schema(description = "账号名称")
    @UpdateColumn
    private String name;

    @Schema(description = "是否启用")
    @UpdateColumn
    private Boolean enabled;

    @Schema(description = "是否删除")
    @UpdateColumn
    private Boolean deleted;

}
