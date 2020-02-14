package com.oak.rbac.management.menu.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.MenuIAction;
import com.oak.rbac.enums.MenuShowType;
import com.oak.rbac.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 菜单编辑
 *
 * @author chenPC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "菜单编辑")
public class UpdateMenuInfoReq extends ApiBaseReq {


    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "动作")
    private MenuIAction action;

    @Schema(description = "菜单类型")
    private MenuType type;

    @Schema(description = "菜单的显示类型")
    private MenuShowType showType;

    @Size(max = 256)
    @Schema(description = "动作值")
    private String value;

    @Size(max = 512)
    @Schema(description = "动作参数")
    private String param;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "是否允许")
    private Boolean enable;

    @Size(max = 1000)
    @Schema(description = "备注")
    private String remark;

}
