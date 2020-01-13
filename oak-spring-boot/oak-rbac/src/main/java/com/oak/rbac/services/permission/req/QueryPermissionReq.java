package com.oak.rbac.services.permission.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询权限")
public class QueryPermissionReq extends ApiBaseQueryReq {

    @Schema(name = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "名称模糊查询")
    private String likeName;

    @Schema(description = "资源标识")
    private String resourceId;
}
