package com.oak.rbac.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenPC
 */

@Schema(description = "菜单类型")
public enum MenuType implements DescriptiveEnum {


    @Schema(description = "hover弹出菜单")
    HOVER_POP,

    @Schema(description = "抽屉式菜单")
    DRAWER,

    @Schema(description = "默认菜单")
    DEFAULT
}
