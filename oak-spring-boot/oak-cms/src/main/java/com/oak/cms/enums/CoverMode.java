package com.oak.cms.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "文章导图模式")
public enum CoverMode implements DescriptiveEnum {

    @Schema(description = "左小图模式")
    LEFT,

    @Schema(description = "右小图模式")
    RIGHT,

    @Schema(description = "大图模式")
    BIG,

    @Schema(description = "三图模式")
    THREE,

    @Schema(description = "无图模式")
    NONE;

}
