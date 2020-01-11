package com.oak.rbac.services.role.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建role")
public class CreateRoleReq extends ApiBaseReq {

    @Schema(description = "角色名称")
    @NotNull
    private String name;

    @Schema(description = "权限编码")
    @NotNull
    private String[] permissionCodes;

}
