package com.oak.rbac.services.role.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "删除角色")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteRoleReq extends ApiBaseReq {

    @Schema(name = "id")
    @NotNull
    private Long id;

    @Schema(description = "名称")
    @NotNull
    private String name;
}
