package com.oak.rbac.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import com.oak.rbac.enums.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Table(name = "t_rbac_resource", indexes = {
        @Index(columnList = "module_name"),
        @Index(columnList = "module_code"),
})
@Entity
@Schema(description = "系统资源")
@Data
@Accessors(chain = true)
@ToString(exclude = "permissions")
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class  OakResource extends AbstractNamedEntityObject<String> {

    private static final long serialVersionUID = 6566419064985790620L;

    @Id
    @Schema(name = "资源编码")
    private String id;

    @Schema(description = "资源类型")
    @Column(name = "type", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Schema(description = "模块名称")
    @Column(name = "module_name", length = 64, nullable = false)
    private String moduleName;

    @Schema(description = "模块代码")
    @Column(name = "module_code", length = 64, nullable = false)
    private String moduleCode;

    @Schema(description = "资源操作列表")
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "resource",
            fetch = FetchType.EAGER)
    private Set<OakPermission> permissions;
}
