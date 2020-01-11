package com.oak.rbac.services.permission.info;

import com.levin.commons.service.domain.Desc;
import com.oak.rbac.enums.PermissionType;
import com.oak.rbac.services.role.info.RoleInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Schema(description = "查询权限")
public class PermissionInfo implements Serializable {

    private static final long serialVersionUID = -5768684537903557933L;

    @Schema(name = "id")
    private Long id;

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "权限编码")
    private String code;

    @Schema(description = "权限类型")
    private PermissionType type;

    @Schema(description = "权限值")
    private String value;

    @Schema(description = "资源标识")
    private String resourceId;

//    //配置多对多
//    @Schema(description = "角色")
//    @Desc(value = "", code = "roles")
//    private Set<RoleInfo> roles;
}
