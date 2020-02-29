package com.oak.payment.services.payment.req;

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
 *  创建Payment
 *  2020-2-6 11:21:49
 */
@Schema(description = "创建CreatePaymentReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreatePaymentReq extends ApiBaseReq {

//    @Schema(description = "支付交易流水号")
//    @NotNull
//    @Size(max = 20)
//    private String sn;

    @Schema(description = "支付单号，支付订单号的sn")
    @NotNull
    @Size(max = 32)
    private String payOrderSn;

    @Schema(description = "支付方式名称")
    @NotNull
    @Size(max = 100)
    private String paymentMethodName;

    @Schema(description = "支付类型")
    @NotNull
    private PaymentType type;

    @Schema(description = "支付说明")
    @Size(max = 200)
    private String note;

    @Schema(description = "付款人姓名")
    @Size(max = 100)
    private String payerName;

    @Schema(description = "付款账号")
    private String payerAccount;

    @Schema(description = "付款人手机号")
    @Size(max = 20)
    private String payerMobilePhone;

    @Schema(description = "支付金额")
    @NotNull
    private Integer amount;

    @Schema(description = "支付方式")
    @NotNull
    @Size(max = 50)
    private String paymentMethod;

    @Schema(description = "退款优先级")
    private Integer refundPriority;

    @Schema(description = "付款时间")
    private Date payTime;

    @Schema(description = "交易完成时间")
    private Date finishedTime;

    @Schema(description = "支付操作者")
    @NotNull
    @Size(max = 50)
    private String operator;

    @Schema(description = "交易结果")
    @NotNull
    private PaymentStatus status;

    @Schema(description = "第三方或银行交易流水号")
    @Size(max = 100)
    private String returnSn;

    @Schema(description = "交易返回码")
    @Size(max = 100)
    private String returnCode;

    @Schema(description = "交易返回信息")
    @Size(max = 500)
    private String returnInfo;

    @Schema(description = "交易返回凭证")
    private String returnStub;

    @Schema(description = "对账状态")
    @Size(max = 50)
    private String checkStatus;

    @Schema(description = "对账结果")
    @Size(max = 50)
    private String checkResult;

    @Schema(description = "对账时间")
    private Date checkTime;

    @Schema(description = "校验码")
    private String checkCode;

}
