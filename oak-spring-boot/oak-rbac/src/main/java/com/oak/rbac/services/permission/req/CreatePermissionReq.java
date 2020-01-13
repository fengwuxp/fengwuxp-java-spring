package com.oak.rbac.services.permission.req;


import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建权限")
public class CreatePermissionReq extends ApiBaseReq {

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限值")
    private String value;

    @Schema(description = "资源标识")
    private String resourceId;

    @Schema(description = "排序")
    private Integer orderCode = 0;

    @Schema(description = "备注")
    protected String remark;
}
