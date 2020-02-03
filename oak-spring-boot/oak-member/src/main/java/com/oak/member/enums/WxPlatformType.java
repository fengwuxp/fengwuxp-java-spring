package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description =  "微信平台类型")
public enum WxPlatformType implements DescriptiveEnum {

    @Schema(description =  "公众平台")
    MP,
    @Schema(description =  "开放平台")
    OPEN,
    @Schema(description =  "小程序平台")
    MA;

}
