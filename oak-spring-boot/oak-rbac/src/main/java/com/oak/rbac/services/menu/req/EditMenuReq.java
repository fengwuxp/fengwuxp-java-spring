package com.oak.rbac.services.menu.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.MenuIAction;
import com.oak.rbac.enums.MenuShowType;
import com.oak.rbac.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 编辑菜单
 * 2020-1-14 16:32:16
 *
 * @author chenPC
 */
@Schema(description = "编辑菜单")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMenuReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "图标")
    @UpdateColumn
    private String icon;

    @Schema(description = "动作")
    @UpdateColumn
    private MenuIAction action;

    @Schema(description = "菜单类型")
    @UpdateColumn
    private MenuType type;

    @Schema(description = "菜单的显示类型")
    @UpdateColumn
    private MenuShowType showType;

    @Size(max = 256)
    @Schema(description = "动作值")
    @UpdateColumn
    private String value;

    @Size(max = 512)
    @Schema(description = "动作参数")
    @UpdateColumn
    private String param;

    @Schema(description = "名称")
    @UpdateColumn
    private String name;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "排序代码")
    @UpdateColumn
    private Integer orderCode;

    @Schema(description = "是否允许")
    @UpdateColumn
    private Boolean enable;

    @Size(max = 1000)
    @Schema(description = "备注")
    @UpdateColumn
    private String remark;

    public EditMenuReq() {
    }

    public EditMenuReq(Long id) {
        this.id = id;
    }
}
