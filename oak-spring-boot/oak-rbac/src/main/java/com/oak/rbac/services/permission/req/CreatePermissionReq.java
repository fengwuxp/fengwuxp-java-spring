package com.oak.rbac.services.permission.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建权限")
public class CreatePermissionReq extends ApiBaseReq {

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "资源code")
    private String code;

    @Schema(description = "权限类型")
    private PermissionType type;

    @Schema(description = "权限值")
    private String value;

    @Schema(description = "资源标识")
    private String resourceId;

    @Schema(description = "排序")
    private Integer orderCode = 0;
}
