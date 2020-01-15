package com.oak.admin.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import com.wuxp.basic.enums.DescriptiveEnum;

@Schema(description = "菜单动作")
public enum MenuIAction implements DescriptiveEnum {


    @Schema(description = "显示下级菜单")
    SHOW_SUB_MENU,

    @Schema(description = "在当前打开页面")
    OPEN_LINK_DEFAULT,

    @Schema(description = "在新窗口打开页面")
    OPEN_LINK_BLANK,

    @Schema(description = "关闭当前")
    CLOSE_CURRENT,

    @Schema(description = "弹出对话框")
    POP_DIALOG ,

    @Schema(description = "弹出页面")
    POP_VIEW,

    @Schema(description = "执行js脚本")
    EXECUTE_JS_SCRIPT


}
