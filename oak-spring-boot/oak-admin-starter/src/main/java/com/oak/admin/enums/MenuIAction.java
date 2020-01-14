package com.oak.admin.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import com.wuxp.basic.enums.DescriptiveEnum;

@Schema(description = "菜单动作")
public enum MenuIAction implements DescriptiveEnum {

    @Schema(description = "打开页面")
    OPEN_VIEW,

    @Schema(description = "打开对话框")
    OPEN_DIALOG


}
