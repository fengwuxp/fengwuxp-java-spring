package com.oak.rbac.services.role.info;

import com.levin.commons.service.domain.Desc;
import com.oak.rbac.services.permission.info.PermissionInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Schema(description = "角色")
public class RoleInfo implements Serializable {
    private static final long serialVersionUID = -7114546018928573966L;

    @Schema(name = "id")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "权限列表")
    @Desc(value = "", code = "permissions")
    private Set<PermissionInfo> permissions;
}
