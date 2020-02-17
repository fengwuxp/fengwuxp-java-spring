package com.oak.payment.management.payment.req;


import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 订单支付完成
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
public class PaymentDoneReq extends ApiBaseReq {


    /**
     * 支付交易流水号(系统内的)
     */
    @NotNull
    @Schema(description = "支付交易流水号(系统内的)")
    private String tradeNo;

    /**
     * 第三方交易流水号
     */
    @Schema(description = "第三方交易流水号")
    private String outTradeNo;

    /**
     * 订单金额
     * 单位：分
     */
    @Schema(description = "订单金额")
    private Integer orderAmount;

    /**
     * 实付金额
     * 单元分
     * 买家实际付款的金额
     */
    @Schema(description = "实付金额")
    private Integer buyerPayAmount;

    /**
     * 实收金额
     * 单位分
     * 该金额为本笔交易，商户账户能够实际收到的金额
     */
    @Schema(description = "实收金额")
    private Integer receiptAmount;

    /**
     * 交易状态
     */
    @Schema(description = "交易状态")
    private String tradeStatus;

    /**
     * 付款账号
     */
    @Schema(description = "付款账号")
    private String payerAccount;

    /**
     * 支付方式名称
     */
    @Schema(description = "支付方式名称")
    private String paymentMethodName;

    @Schema(description = "支付方式")
    private String paymentMethod;

    @Schema(description = "交易返回码")
    private String returnCode;

    @Schema(description = "交易返回信息")
    private String returnInfo;
}
