package com.oak.payment.services.paymentorder.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
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
 *  查询支付订单对象
 *  2020-2-6 11:31:05
 */
@Schema(description = "查询支付订单对象")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryPaymentOrderReq extends ApiBaseQueryReq {

    @Schema(description = "支付单号")
    private String sn;

    @Schema(description = "支付类型")
    private PaymentType type;

    @Schema(description = "支付关联的订单类型")
    private String orderTypes;

    @Schema(description = "归属的卖家用户")
    private Long sellerId;

    @Schema(description = "购买用户")
    private Long buyerId;

    @Schema(description = "应付金额")
    private Integer amount;

    @Schema(description = "已付金额")
    private Integer paidAmount;

    @Schema(description = "支付订单支付")
    private PaymentStatus status;

    @Schema(description = "最小到期时间")
    @Gte("expirationTime")
    private Date minExpirationTime;

    @Schema(description = "最大到期时间")
    @Lte("expirationTime")
    private Date maxExpirationTime;

    @Schema(description = "已删除")
    private Boolean deleted;

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

    public QueryPaymentOrderReq() {
    }

    public QueryPaymentOrderReq(String sn) {
        this.sn = sn;
    }
}
