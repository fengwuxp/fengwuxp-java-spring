package com.oak.rbac.services.menu.req;

import com.oak.rbac.entities.E_Menu;
import com.oak.rbac.enums.MenuShowType;
import com.oak.rbac.enums.MenuType;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.rbac.enums.MenuIAction;

import java.util.Date;

/**
 * 查询菜单
 * 2020-1-14 16:32:16
 */
@Schema(description = "查询菜单")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMenuReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "动作")
    private MenuIAction action;

    @Schema(description = "菜单类型")
    private MenuType type;

    @Schema(description = "菜单的显示类型")
    private MenuShowType showType;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "是否叶子目录")
    private Boolean leaf;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "加载父对象")
    @Fetch(value = "parent", condition = "#_val==true")
    private Boolean loadParent;


    @Schema(description = "路径")
    @Like(prefix = "", value = E_Menu.idPath)
    private String startsWithIdPath;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "模糊搜索名称")
    @Like(value = E_Menu.name)
    private String likeName;

    @Schema(description = "是否允许")
    private Boolean enable;

    @Schema(description = "是否可编辑")
    private Boolean editable;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryMenuReq() {
    }

    public QueryMenuReq(Long id) {
        this.id = id;
    }
}
