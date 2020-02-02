package com.oak.organization.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "审核状态")
public enum ApprovalStatus implements DescriptiveEnum {

    @Schema(description = "待提交")
    WAIT,

    @Schema(description = "审核中")
    AUDIT,

    @Schema(description = "拒绝")
    REFUSE,

    @Schema(description = "已签约")
    AGREE,

    @Schema(description = "禁用")
    DISABLED,

    @Schema(description = "注销")
    CANCELLED

}
