package com.oak.payment.entities;


import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Schema(description = "支付单对象")
@Table(name = "t_payment", indexes = {
        @Index(columnList = "sn"),
        @Index(columnList = "pay_order_sn")
})
@Data
@EqualsAndHashCode(of = "sn")
public class Payment implements Serializable {

    private static final long serialVersionUID = 6009175733978355355L;


    @Id
    @Schema(description = "支付交易流水号")
    @Column(name = "sn", nullable = false, length = 20, unique = true)
    private String sn;

    @Schema(description = "支付单号，支付订单号的sn")
    @Column(name = "pay_order_sn", nullable = false, length = 32)
    private String payOrderSn;

    @Schema(description = "支付单对象")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_order_sn", referencedColumnName = "sn", updatable = false, insertable = false)
    private PaymentOrder paymentOrder;


    @Schema(description = "支付方式名称")
    @Column(name = "payment_method_name", nullable = false, length = 100)
    private String paymentMethodName;

    @Schema(description = "支付类型")
    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Schema(description = "支付说明")
    @Column(name = "note", length = 200)
    private String note;

    @Schema(description = "付款人姓名")
    @Column(name = "payer_name", length = 100)
    private String payerName;

    @Schema(description = "付款账号")
    @Column(name = "payer_account", length = 255)
    private String payerAccount;

    @Schema(description = "付款人手机号")
    @Column(name = "payer_mobile_phone", length = 20)
    private String payerMobilePhone;

    @Schema(description = "支付金额")
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Schema(description = "支付手续费")
    @Column(name = "fee", nullable = false)
    private Integer fee = 0;

    @Schema(description = "已退金额")
    @Column(name = "refund_amount", nullable = false)
    private Integer refundAmount = 0;


    @Schema(description = "支付方式")
    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Schema(description = "退款优先级")
    @Column(name = "refund_priority")
    private Integer refundPriority;


    @Schema(description = "付款时间")
    @Column(name = "pay_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payTime;


    @Schema(description = "交易完成时间")
    @Column(name = "finished_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedTime;


    @Schema(description = "支付操作者")
    @Column(name = "operator", nullable = false, length = 50)
    private String operator;

    @Schema(description = "交易结果")
    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;


    @Schema(description = "第三方或银行交易流水号")
    @Column(name = "return_sn", length = 100)
    private String returnSn;


    @Schema(description = "交易返回码")
    @Column(name = "return_code", length = 100)
    private String returnCode;

    @Schema(description = "交易返回信息")
    @Column(name = "return_info", length = 500)
    private String returnInfo;


    @Schema(description = "交易返回凭证")
    @Column(name = "return_stub", length = 255)
    private String returnStub;


    @Schema(description = "对账状态")
    @Column(name = "check_status", length = 50)
    private String checkStatus;

    @Schema(description = "对账结果")
    @Column(name = "check_result", length = 50)
    private String checkResult;

    @Schema(description = "对账时间")
    @Column(name = "check_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkTime;

    @Schema(description = "校验码")
    @Column(name = "check_code")
    private String checkCode;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "修改时间")
    @Column(name = "update_time", updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date updateTime;

    @Schema(description = "退款单号")
    @Column(name = "refund_sn", nullable = false, length = 32)
    private String refundSn;

    @Schema(description = "第三方退款单号")
    @Column(name = "out_refund_sn", nullable = false, length = 32)
    private String outRefundSn;

    @Transient
    public Integer getRefundableAmount() {
        return amount - refundAmount;
    }


}
