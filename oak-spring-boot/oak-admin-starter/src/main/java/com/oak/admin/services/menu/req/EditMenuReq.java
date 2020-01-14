package com.oak.admin.services.menu.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import com.oak.admin.enums.MenuIAction;


/**
 *  编辑菜单
 *  2020-1-14 16:32:16
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

    @Size(max = 256)
    @Schema(description = "动作值")
    @UpdateColumn
    private String value;

    @Size(max = 512)
    @Schema(description = "动作参数")
    @UpdateColumn
    private String param;

    @Schema(description = "类型")
    @UpdateColumn
    private String type;

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
