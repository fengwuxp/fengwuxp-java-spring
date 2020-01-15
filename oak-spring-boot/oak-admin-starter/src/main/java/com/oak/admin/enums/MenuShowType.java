package com.oak.admin.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "菜单显示类型")
public enum MenuShowType implements DescriptiveEnum {


    @Schema(description = "无路如何都会显示")
    ALWAYS,

    @Schema(description = "仅在有权限的时候显示")
    ONLY_PERMISSION,

    @Schema(description = "继承上级菜单")
    EXTENDS,

    @Schema(description = "默认")
    DEFAULT


}
