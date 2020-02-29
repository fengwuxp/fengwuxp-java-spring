package com.oak.api.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "客户端类型")
public enum ClientType implements DescriptiveEnum {

    @Schema(description = "默认")
    DEFAULT,

    @Schema(description = "移动端")
    MOBILE,

    @Schema(description = "安卓客户端")
    ANDROID,

    @Schema(description = "IOS客户端")
    IOS,

    @Schema(description = "PC端")
    PC;

}
