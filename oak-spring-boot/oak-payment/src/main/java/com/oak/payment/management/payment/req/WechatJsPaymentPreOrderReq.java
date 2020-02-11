package com.oak.payment.management.payment.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: zhuox
 * @create: 2020-02-07
 * @description: 微信jsApi预下单请求
 **/
@Schema(description = "微信jsApi预下单请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class WechatJsPaymentPreOrderReq extends ApiBaseReq {



    /**
     * 应用内的交易流水号
     */
    @Schema(description = "应用内的交易流水号（支付订单对象SN编号）")
    @NotNull
    @Size(max = 32)
    private String tradeNo;

    /**
     * 支付用户标识
     */
    @Schema(description = "支付用户标识")
    @NotBlank
    private String user;

    /**
     * 支付金额，单位分
     */
    @Schema(description = "支付金额，单位分")
    @NotNull
    @Min(value = 1)
    private Integer orderAmount;

    /**
     * 支付请求发起ip
     */
    @Schema(description = "支付请求发起ip")
    @NotBlank
    private String remoteIp;

    /**
     * 异步回调url
     */
    @Schema(description = "异步回调url")
    @NotBlank
    private String notifyUrl;


    /**
     * 支付说明
     */
    @Schema(description = "支付说明")
    private String description;


    /**
     * 交易结束时间
     * 使用阿里规则
     * 1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）
     * <p>
     * 默认：30m，  30分钟过期
     */
    @Schema(description = "交易结束时间")
    private String timeExpire;

    /**
     * 主题
     */
    @Schema(description = "主题")
    private String subject;
}
