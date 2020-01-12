package com.oak.rbac.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_rbac_role_permission", indexes = {
        @Index(columnList = "role_id")
})
@Entity
@Schema(description = "角色权限关联表")
@Data
@Accessors(chain = true)
public class OakRolePermission implements Serializable {

    private static final long serialVersionUID = 1459933116532222251L;

    @EmbeddedId
    private OakRolePermissionPrimaryKey permissionPrimary;


    public OakRolePermission() {
    }

    public OakRolePermission(OakRolePermissionPrimaryKey permissionPrimary) {
    }

    public static OakRolePermission newInstance(Long roleId, Long permissionId) {
        return new OakRolePermission(OakRolePermissionPrimaryKey.newInstance(roleId, permissionId));
    }

    @Embeddable
    @Data
    public static class OakRolePermissionPrimaryKey implements Serializable {

        private static final long serialVersionUID = -5227637028053610545L;

        @Schema(description = "角色id")
        @Column(name = "role_id", length = 20, nullable = false)
        private Long roleId;

        @Schema(description = "权限类型")
        @Column(name = "permission_id", length = 20, nullable = false)
        private Long permissionId;

        public OakRolePermissionPrimaryKey(Long roleId, Long permissionId) {
            this.roleId = roleId;
            this.permissionId = permissionId;
        }

        public OakRolePermissionPrimaryKey() {
        }


        public static OakRolePermissionPrimaryKey newInstance(Long roleId, Long permissionId) {
            return new OakRolePermissionPrimaryKey(roleId, permissionId);
        }
    }
}
