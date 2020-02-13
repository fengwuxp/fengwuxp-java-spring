package com.oak.rbac.services.menu.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.MenuIAction;
import com.oak.rbac.enums.MenuShowType;
import com.oak.rbac.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 创建Menu
 * 2020-1-14 16:32:16
 *
 * @author chenPC
 */
@Schema(description = "创建CreateMenuReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMenuReq extends ApiBaseReq {

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "动作")
    @NotNull
    private MenuIAction action;

    @Schema(description = "是否叶子目录")
    @NotNull
    private Boolean leaf;

    @Schema(description = "菜单类型")
    private MenuType type;

    @Schema(description = "菜单的显示类型")
    private MenuShowType showType;

    @Schema(description = "动作值")
    @Size(max = 256)
    private String value;

    @Schema(description = "动作参数")
    @Size(max = 512)
    private String param;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "名称")
    @NotNull
    private String name;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;


}
