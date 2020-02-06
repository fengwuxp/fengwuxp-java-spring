package com.oak.payment.services.paymentorder.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  创建PaymentOrder
 *  2020-2-6 11:31:05
 */
@Schema(description = "创建CreatePaymentOrderReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreatePaymentOrderReq extends ApiBaseReq {

//    @Schema(description = "支付单号")
//    @NotNull
//    @Size(max = 32)
//    private String sn;

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

    @Schema(description = "已付金额")
    @NotNull
    private Integer paidAmount;

    @Schema(description = "支付订单支付")
    @NotNull
    private PaymentStatus status;

    @Schema(description = "到期时间")
    @NotNull
    private Date expirationTime;

}
