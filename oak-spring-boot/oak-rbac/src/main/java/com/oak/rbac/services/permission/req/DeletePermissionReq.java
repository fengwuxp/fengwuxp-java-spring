package com.oak.rbac.services.permission.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "删除权限")
public class DeletePermissionReq extends ApiBaseReq {

    @Schema(description = "资源名称")
    private Long id;

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "资源标识")
    private String resourceId;
}
