package com.oak.rbac.services.permission.info;

import com.levin.commons.service.domain.Desc;
import com.oak.rbac.services.role.info.RoleInfo;
import com.wuxp.resouces.AntUrlResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Set;

@Data
@Schema(description = "权限信息")
public class PermissionInfo implements AntUrlResource<Long>, Serializable {

    private static final long serialVersionUID = -5768684537903557933L;

    @Schema(name = "id")
    private Long id;

    @Schema(description = "权限名称(操作)")
    private String name;

    @Schema(description = "权限值")
    private String value;

    @Schema(description = "支持的http method")
    private String httpMethod;

    @Schema(description = "资源标识")
    private String resourceId;

    @Schema(description = "关联角色")
    @Desc(value = "", code = "roles")
    private Set<RoleInfo> roles;

    @Override
    public String getPattern() {
        return this.value;
    }

    @Override
    public Class<?> getClassType() {
        return PermissionInfo.class;
    }
}
