package com.oak.payment.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "支付订单状态")
public enum PaymentStatus implements DescriptiveEnum {

    @Schema(description = "未支付")
    UNPAID,

    @Schema(description = "部分支付")
    PARTIAL_PAYMENT,

    @Schema(description = "已支付")
    PAID,

    @Schema(description = "部分退款")
    PARTIAL_REFUNDS,

    @Schema(description = "已退款")
    REFUNDED,

    @Schema(description = "已取消")
    CANCEL,

    @Schema(description = "支付失败")
    FAILURE;

}
