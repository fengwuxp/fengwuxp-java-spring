package com.oak.organization.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "审核状态")
public enum ApprovalStatus implements DescriptiveEnum {

    @Schema(description = "等待")
    WAIT,

    @Schema(description = "拒绝")
    REFUSE,

    @Schema(description = "同意")
    AGREE

}
