package com.oak.payment.management.payment.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: zhuox
 * @create: 2020-02-10
 * @description: 订单退款完成请求
 **/
@Schema(description = "订单退款完成请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OrderRefundDoneReq extends ApiBaseReq {


    /**
     * 交易状态
     */
    @Schema(description = "交易状态")
    private String tradeStatus;

    /**
     * 退款的金额
     * 单位：分
     */
    @Schema(description = "退款的金额")
    private Integer refundAmount;

    /**
     * 订单金额
     * 单位分
     */
    @Schema(description = "订单金额")
    private Integer orderAmount;

    /**
     * 应用内的退款流水号
     */
    @Schema(description = "应用内的退款流水号")
    private String tradeRefundNo;

    /**
     * 第三方退款流水号
     */
    @Schema(description = "第三方退款流水号")
    private String outTradeRefundNo;

    @Schema(description = "订单交易流水号")
    private String tradeNo;

    @Schema(description = "交易返回码")
    private String returnCode;

    @Schema(description = "交易返回信息")
    private String returnInfo;
    /**
     * 是否全额退款
     *
     * @return
     */
    public boolean isFullRefund() {
        return refundAmount.equals(orderAmount);
    }
}
