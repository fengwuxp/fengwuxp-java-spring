package com.oak.api.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "配置值类型")
public enum SettingValueType implements DescriptiveEnum {

    @Schema(description = "文本")
    TEXT,

    @Schema(description = "图片")
    IMAGE,

    @Schema(description = "附件")
    ATTACHMENT,

    @Schema(description = "文本域")
    TEXT_AREA,

    @Schema(description = "布尔值")
    BOOLEAN,

    @Schema(description = "item")
    ITEM,

    @Schema(description = "数组")
    ARRAY,

    @Schema(description = "url")
    URL,

    @Schema(description = "整数")
    INT,

    @Schema(description = "金额")
    DECIMAL,

    @Schema(description = "日期")
    DATE,

    @Schema(description = "日期时间")
    DATETIME,

    @Schema(description = "html")
    HTML,

    @Schema(description = "脚本")
    SCRIPT;


}
