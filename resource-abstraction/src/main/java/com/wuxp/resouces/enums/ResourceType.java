package com.wuxp.resouces.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wuxp
 */

@Schema(description = "资源类型")
public enum ResourceType implements DescriptiveEnum {

    @Schema(description = "数据")
    DATA,

    @Schema(description = "url")
    URL,

    @Schema(description = "图片")
    IMAGE,

    @Schema(description = "文件")
    FILE
}
