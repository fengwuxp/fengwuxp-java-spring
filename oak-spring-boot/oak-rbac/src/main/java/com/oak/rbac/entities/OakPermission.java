package com.oak.rbac.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import com.oak.rbac.enums.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Table(name = "t_rbac_permission", indexes = {
        @Index(columnList = "code"),
        @Index(columnList = "resource_id")
})
@Entity
@Schema(description = "权限")
@Data
@Accessors(chain = true)
@ToString(exclude = "roles")
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class OakPermission extends AbstractNamedEntityObject<Long> {

    private static final long serialVersionUID = -2236535431871280682L;

    @Id
    @Schema(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "权限编码(资源标识加资源操作的组合)")
    @Column(name = "code", length = 128, nullable = false)
    private String code;

    @Schema(description = "权限类型")
    @Column(name = "type", length = 16, nullable = false)
    private PermissionType type;

    @Schema(description = "权限值")
    @Column(name = "value", length = 512, nullable = false)
    private String value;

    @Schema(description = "资源标识")
    @Column(name = "resource_id", length = 128, nullable = false)
    private String resourceId;

    //配置多对多
    @ManyToMany(mappedBy = "permissions")
    private Set<OakRole> roles;


}
