package com.oak.rbac.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_rbac_user_role", indexes = {
        @Index(columnList = "user_id")
})
@Entity
@Schema(description = "用户角色关联表")
@Accessors(chain = true)
@Data
public class OakUserRole implements Serializable {


    private static final long serialVersionUID = -2132438258476925501L;
    @EmbeddedId
    private OakUserRole.OakUserRolePrimaryKey userRolePrimaryKey;


    public OakUserRole() {
    }

    public OakUserRole(OakRolePermission.OakRolePermissionPrimaryKey permissionPrimary) {
    }

    public static OakRolePermission newInstance(Long roleId, Long permissionId) {
        return new OakRolePermission(OakRolePermission.OakRolePermissionPrimaryKey.newInstance(roleId, permissionId));
    }

    @Embeddable
    @Data
    public static class OakUserRolePrimaryKey implements Serializable {

        private static final long serialVersionUID = 7839437693500065818L;


        @Schema(description = "用户id")
        @Column(name = "user_id", length = 20, nullable = false)
        private Long userId;

        @Schema(description = "角色id")
        @Column(name = "role_id", length = 20, nullable = false)
        private Long roleId;


        public OakUserRolePrimaryKey(Long userId, Long roleId) {
            this.userId = userId;
            this.roleId = roleId;
        }

        public OakUserRolePrimaryKey() {
        }


        public static OakUserRole.OakUserRolePrimaryKey newInstance(Long roleId, Long userId) {
            return new OakUserRole.OakUserRolePrimaryKey(roleId, userId);
        }
    }
}
