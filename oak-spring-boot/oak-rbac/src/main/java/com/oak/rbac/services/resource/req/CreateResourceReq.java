package com.oak.rbac.services.resource.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建资源")
public class CreateResourceReq extends ApiBaseReq {

    @Schema(description = "资源标识")
    @NotNull
    private String code;

    @Schema(description = "资源名称")
    @NotNull
    private String name;

    @Schema(description = "资源类型")
    @NotNull
    private ResourceType type;

    @Schema(description = "模块名称")
    @NotNull
    private String moduleName;

    @Schema(description = "模块代码")
    @NotNull
    private String moduleCode;

    @Schema(description = "排序索引")
    private Integer orderIndex;

    @Schema(description = "介绍")
    private String remark;

    @Schema(description = "权限列表")
    private CreatePermissionReq[] permissions;

}
