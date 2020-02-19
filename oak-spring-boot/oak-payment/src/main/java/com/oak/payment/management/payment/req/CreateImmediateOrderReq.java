package com.oak.payment.management.payment.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.payment.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: zhuox
 * @create: 2020-02-07
 * @description: 生成订单请求
 **/
@Schema(description = "生成订单请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateImmediateOrderReq extends ApiBaseReq {

    @Schema(description = "支付类型")
    @NotNull
    private PaymentType type;

    @Schema(description = "支付关联的订单类型")
    @NotNull
    @Size(max = 32)
    private String orderTypes;

    @Schema(description = "归属的卖家用户")
    @NotNull
    private Long sellerId;

    @Schema(description = "购买用户")
    @NotNull
    private Long buyerId;

    @Schema(description = "应付金额")
    @NotNull
    private Integer amount;

    @Schema(description = "支付方式名称")
    @NotNull
    @Size(max = 100)
    private String paymentMethodName;

    @Schema(description = "支付方式")
    @NotNull
    @Size(max = 50)
    private String paymentMethod;
}
