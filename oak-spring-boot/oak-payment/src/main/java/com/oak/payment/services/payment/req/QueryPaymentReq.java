package com.oak.payment.services.payment.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询支付单对象
 *  2020-2-6 11:21:49
 */
@Schema(description = "查询支付单对象")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryPaymentReq extends ApiBaseQueryReq {

    @Schema(description = "支付交易流水号")
    private String sn;

    @Schema(description = "支付单号，支付订单号的sn")
    private String payOrderSn;

    @Schema(description = "加载支付单对象")
    @Fetch(value = "paymentOrder", condition = "#_val==true")
    private Boolean loadPaymentOrder;

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

    @Schema(description = "最小付款时间")
    @Gte("payTime")
    private Date minPayTime;

    @Schema(description = "最大付款时间")
    @Lte("payTime")
    private Date maxPayTime;

    @Schema(description = "最小交易完成时间")
    @Gte("finishedTime")
    private Date minFinishedTime;

    @Schema(description = "最大交易完成时间")
    @Lte("finishedTime")
    private Date maxFinishedTime;

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

    @Schema(description = "最小对账时间")
    @Gte("checkTime")
    private Date minCheckTime;

    @Schema(description = "最大对账时间")
    @Lte("checkTime")
    private Date maxCheckTime;

    @Schema(description = "校验码")
    private String checkCode;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "最小修改时间")
    @Gte("updateTime")
    private Date minUpdateTime;

    @Schema(description = "最大修改时间")
    @Lte("updateTime")
    private Date maxUpdateTime;

    public QueryPaymentReq() {
    }

    public QueryPaymentReq(String sn) {
        this.sn = sn;
    }
}
