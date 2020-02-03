package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description =  "帐户状态")
public enum AccountStatus implements DescriptiveEnum {

    @Schema(description =  "可用")
    AVAILABLE,
    @Schema(description =  "冻结")
    FREEZE;

}
