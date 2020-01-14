package com.oak.admin.entities;

import com.levin.commons.dao.domain.support.AbstractTreeObject;
import com.oak.admin.enums.MenuIAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Schema(description = "菜单")
@Table(name = "t_admin_menu", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "path"),
})
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"parent", "children"})
public class Menu extends AbstractTreeObject<Long, Menu> {

    private static final long serialVersionUID = 4033282524978662257L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Schema(description = "图标")
    @Column(name = "icon")
    private String icon;

    @Schema(description = "动作")
    @Column(name = "action", length = 16, nullable = false)
    private MenuIAction action;

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





}
