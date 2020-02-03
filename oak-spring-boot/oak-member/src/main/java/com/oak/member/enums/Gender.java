package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description =  "性别")
public enum Gender implements DescriptiveEnum {

    @Schema(description =  "男")
    MAN,
    @Schema(description =  "女")
    WOMEN,
    @Schema(description =  "保密")
    SECRET;

}
