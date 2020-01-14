package com.oak.admin.services.menu.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.service.domain.Desc;
import com.oak.admin.enums.MenuIAction;


import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;


/**
 * 菜单
 * 2020-1-14 16:32:16
 */
@Schema(description = "菜单")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"parentInfo",})
public class MenuInfo implements Serializable {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "动作")
    private MenuIAction action;

    @Schema(description = "动作值")
    private String value;

    @Schema(description = "动作参数")
    private String param;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "是否叶子目录")
    private Boolean leaf;

    @Schema(description = "父ID")
    private Long parentId;

    @Desc(value = "", code = "parent")
    @Schema(description = "父对象")
    private MenuInfo parentInfo;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "")
    private String idPath;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "是否允许")
    private Boolean enable;

    @Schema(description = "是否可编辑")
    private Boolean editable;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "备注")
    private String remark;


}
