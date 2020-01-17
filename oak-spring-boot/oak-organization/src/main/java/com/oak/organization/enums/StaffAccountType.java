package com.oak.organization.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "员工账号类型")
public enum StaffAccountType implements DescriptiveEnum {

    @Schema(description = "超级账号")
    ROOT,

    @Schema(description = "主账号")
    MAIN,

    @Schema(description = "子账号")
    SUB;

}
