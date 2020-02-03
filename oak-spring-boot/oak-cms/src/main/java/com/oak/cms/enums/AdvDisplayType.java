package com.oak.cms.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "广告展示方式")
public enum AdvDisplayType implements DescriptiveEnum {

    @Schema(description = "幻灯片")
    SLIDE,

    @Schema(description = "多广告展示")
    MULTIPLE,

    @Schema(description = "单广告展示")
    SINGLE;

}
