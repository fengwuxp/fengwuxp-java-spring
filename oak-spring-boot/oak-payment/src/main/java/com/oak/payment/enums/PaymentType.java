package com.oak.payment.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "支付类型")
public enum PaymentType implements DescriptiveEnum {

    @Schema(description = "支付")
    PAYMENT,

    @Schema(description = "充值")
    RECHARGE;
}
