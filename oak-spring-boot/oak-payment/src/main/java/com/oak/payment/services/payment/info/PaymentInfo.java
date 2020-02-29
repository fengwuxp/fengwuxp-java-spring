package com.oak.payment.services.payment.info;

import com.levin.commons.service.domain.Desc;
import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.PaymentType;
import com.oak.payment.services.paymentorder.info.PaymentOrderInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


/**
 * 支付单对象
 * 2020-2-6 11:21:49
 */
@Schema(description = "支付单对象")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"sn"})
@ToString(exclude = {"paymentOrderInfo",})
public class PaymentInfo implements Serializable {

    @Schema(description = "支付交易流水号")
    private String sn;

    @Schema(description = "支付单号，支付订单号的sn")
    private String payOrderSn;

    @Desc(value = "", code = "paymentOrder")
    @Schema(description = "支付单对象")
    private PaymentOrderInfo paymentOrderInfo;

    @Schema(description = "支付方式名称")
    private String paymentMethodName;

    @Schema(description = "支付类型")
    private PaymentType type;

    @Schema(description = "支付说明")
    private String note;

    @Schema(description = "付款人姓名")
    private String payerName;

    @Schema(description = "付款账号")
    private String payerAccount;

    @Schema(description = "付款人手机号")
    private String payerMobilePhone;

    @Schema(description = "支付金额")
    private Integer amount;

    @Schema(description = "支付手续费")
    private Integer fee;

    @Schema(description = "已退金额")
    private Integer refundAmount;

    @Schema(description = "支付方式")
    private String paymentMethod;

    @Schema(description = "退款优先级")
    private Integer refundPriority;

    @Schema(description = "付款时间")
    private Date payTime;

    @Schema(description = "交易完成时间")
    private Date finishedTime;

    @Schema(description = "支付操作者")
    private String operator;

    @Schema(description = "交易结果")
    private PaymentStatus status;

    @Schema(description = "第三方或银行交易流水号")
    private String returnSn;

    @Schema(description = "交易返回码")
    private String returnCode;

    @Schema(description = "交易返回信息")
    private String returnInfo;

    @Schema(description = "交易返回凭证")
    private String returnStub;

    @Schema(description = "对账状态")
    private String checkStatus;

    @Schema(description = "对账结果")
    private String checkResult;

    @Schema(description = "对账时间")
    private Date checkTime;

    @Schema(description = "校验码")
    private String checkCode;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Transient
    public Integer getRefundableAmount() {
        return amount - refundAmount;
    }
}
