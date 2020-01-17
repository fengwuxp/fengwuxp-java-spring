package com.oak.rbac.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import com.oak.rbac.enums.PermissionValueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Table(name = "t_rbac_permission", indexes = {
        @Index(columnList = "resource_id")
})
@Entity
@Schema(description = "权限")
@Data
@Accessors(chain = true)
@ToString(exclude = {"roles", "resource"})
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class OakPermission extends AbstractNamedEntityObject<Long> {

    private static final long serialVersionUID = -2236535431871280682L;

    @Id
    @Schema(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "权限类型")
    @Column(name = "value_type", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionValueType valueType;

    @Schema(description = "权限值(操作代码)")
    @Column(name = "value", length = 512, nullable = false)
    private String value;

    @Schema(description = "资源标识")
    @Column(name = "resource_id", length = 128, nullable = false)
    private String resourceId;

    @Schema(description = "资源")
    @JoinColumn(name = "resource_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private OakResource resource;

    //配置多对多
    @ManyToMany(mappedBy = "permissions")
    private Set<OakRole> roles;


}
