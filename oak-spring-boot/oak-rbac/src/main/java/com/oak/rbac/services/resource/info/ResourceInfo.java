package com.oak.rbac.services.resource.info;

import com.oak.rbac.enums.PermissionValueType;
import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.permission.info.PermissionInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@Schema(description = "资源信息")
public class ResourceInfo implements Serializable {

    private static final long serialVersionUID = 7823885583221539301L;


    @Schema(description = "资源编码")
    @NotNull
    private String id;

    @Schema(description = "资源名称")
    @NotNull
    private String name;

    @Schema(description = "权限类型")
    @NotNull
    private PermissionValueType valueType;

    @Schema(description = "资源类型")
    @NotNull
    private ResourceType type;

    @Schema(description = "模块名称")
    @NotNull
    private String moduleName;

    @Schema(description = "模块代码")
    @NotNull
    private String moduleCode;

    @Schema(description = "排序代码")
    private Integer orderCode;

    //    @Desc(value = "", code = "permissions")
    @Schema(description = "资源对应的权限(操作)列表")
    private Set<PermissionInfo> permissions;
}
