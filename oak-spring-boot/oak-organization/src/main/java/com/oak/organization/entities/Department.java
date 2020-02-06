package com.oak.organization.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * @author
 */
@Schema(description = "部门")
@Entity
@Table(name = "t_organization_department", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "parent_id"),
        @Index(columnList = "organization_id"),
        @Index(columnList = "id_path"),
})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"parent", "children"})
@Accessors(chain = true)
public class Department extends AbstractNamedEntityObject<Long> {

    private static final long serialVersionUID = -5085157629557481143L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Schema(description = "上级部门")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Department department;

    @Schema(description = "组织id")
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Schema(description = "归属组织")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private Organization organization;

    //从0开始
    @Schema(description = "层级")
    @Column(name = "`level`", nullable = false)
    private Integer level;

    //格式：#level_index#level_index#
    @Schema(description = "层级排序")
    @Column(name = "level_path")
    private String levelPath;

    @Schema(description = "父ID")
    @Column(name = "parent_id")
    private Long parentId;

    @Schema(description = "上级部门")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Department parent;

    @Schema(description = "下级部门")
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REMOVE})
    @OrderBy("orderCode DESC,name ASC")
    private Set<Department> children;

    @Schema(description = "ID路径")
    @Column(name = "id_path")
    private String idPath;

    @Schema(description = "是否删除")
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}

