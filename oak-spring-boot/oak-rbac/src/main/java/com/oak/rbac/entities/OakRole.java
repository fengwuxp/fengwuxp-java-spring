package com.oak.rbac.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Table(name = "t_rbac_role", indexes = {
        @Index(columnList = "name")
})
@Entity
@Schema(description = "角色")
@Data
@EqualsAndHashCode(callSuper = true, of = {"id"})
@ToString(exclude = {"permissions"})
@Accessors(chain = true)
public class OakRole extends AbstractNamedEntityObject<Long> {

    private static final long serialVersionUID = 1344679353498978994L;

    @Id
    @Schema(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Schema(description = "包涵的角色")
//    @Column(name = "include_roles", nullable = false)
//    private String includeRoles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            // 用来指定中间表的名称
            name = "t_rbac_role_permission",
            // joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            // inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
    @Schema(description = "关联的权限列表")
    private Set<OakPermission> permissions;

}
