package com.oak.rbac.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenPC
 */

@Schema(description = "资源类型")
public enum ResourceType implements DescriptiveEnum {

    @Schema(description = "数据")
    DATA,

//    @Schema(description = "视图url")
//    VIEW_URL,
//
//    @Schema(description = "api url")
//    API_URL,

    @Schema(description = "URL")
    URL,

    @Schema(description = "图片")
    IMAGE,

    @Schema(description = "文件")
    FILE
}
