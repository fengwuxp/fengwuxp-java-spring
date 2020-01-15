package com.oak.admin.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import com.levin.commons.dao.domain.support.AbstractTreeObject;
import com.levin.commons.service.domain.Desc;
import com.oak.admin.enums.MenuIAction;
import com.oak.admin.enums.MenuShowType;
import com.oak.admin.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Schema(description = "菜单")
@Table(name = "t_admin_menu", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "id_path"),
})
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"parent", "children" })
public class Menu extends AbstractNamedEntityObject<Long> {

    private static final long serialVersionUID = 4033282524978662257L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Schema(description = "图标")
    @Column(name = "icon")
    private String icon;

    @Schema(description = "点击菜单执行的动作")
    @Column(name = "action", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuIAction action;

    @Schema(description = "菜单类型")
    @Column(name = "type", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuType type;

    @Schema(description = "菜单的显示类型")
    @Column(name = "show_type", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuShowType showType;

    @Schema(description = "动作值")
    @Column(name = "value", length = 256)
    private String value;

    @Schema(description = "动作参数")
    @Column(name = "param", length = 512)
    private String param;

    @Schema(description = "层级，默认从0开始")
    @Column(name = "`level`", nullable = false)
    private Integer level;

    @Schema(description = "是否叶子目录")
    @Column(name = "leaf", nullable = false)
    private Boolean leaf;

    @Schema(description = "是否删除")
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Schema(description = "父ID")
    @Column(
            name = "parent_id"
    )
    private Long parentId;

    @Schema(description = "父对象")
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "parent_id",
            insertable = false,
            updatable = false
    )
    protected Menu parent;
    @Desc
    @OneToMany(
            mappedBy = "parent",
            cascade = {CascadeType.REMOVE}
    )
    @OrderBy("orderCode DESC,name ASC")
    protected Set<Menu> children;

    @Schema(description = "ID路径", example = "id路径，使用#包围，如#1#3#15#")
    @Column(
            name = "id_path",
            nullable = false
    )
    protected String idPath;

}
