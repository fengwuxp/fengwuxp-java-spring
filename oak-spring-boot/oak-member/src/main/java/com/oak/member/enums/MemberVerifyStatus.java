package com.oak.member.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description =  "会员审核状态")
public enum MemberVerifyStatus implements DescriptiveEnum {

    @Schema(description =  "待审核")
    PENDING,

    @Schema(description =  "审核通过")
    APPROVED,//审核通过

    @Schema(description =  "审核拒绝")
    REFUSE;//拒绝

}
