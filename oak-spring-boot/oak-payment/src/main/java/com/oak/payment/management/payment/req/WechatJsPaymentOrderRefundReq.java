package com.oak.payment.management.payment.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: zhuox
 * @create: 2020-02-10
 * @description: 微信js api订单退款请求
 **/
@Schema(description = "微信js api订单退款请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class WechatJsPaymentOrderRefundReq extends ApiBaseReq {

    /**
     * 应用内的交易流水号
     */
    @Schema(description = "应用内的交易流水号（支付订单对象SN编号）")
    @NotNull
    private String tradeNo;

    /**
     * 退款金额
     * 单位：分
     */
    @Schema(description = "退款金额，单位：分")
    @NotNull
    @Min(value = 1)
    private Integer refundAmount;

    /**
     * 退款通知url
     */
    @Schema(description = "退款通知url")
    @NotBlank
    private String refundNotifyUrl;

    /**
     * 退款原因
     */
    @Schema(description = "退款原因")
    private String refundReason;
}
