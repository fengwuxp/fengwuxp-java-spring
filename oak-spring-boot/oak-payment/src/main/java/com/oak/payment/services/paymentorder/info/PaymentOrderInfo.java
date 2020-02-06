package com.oak.payment.services.paymentorder.info;

import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 支付订单对象
* 2020-2-6 11:31:05
*/
@Schema(description ="支付订单对象")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"sn"})
@ToString(exclude = {})
public class PaymentOrderInfo implements Serializable {

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

        @Schema(description = "到期时间")
        private Date expirationTime;

        @Schema(description = "已删除")
        private Boolean deleted;

        @Schema(description = "创建时间")
        private Date createTime;

        @Schema(description = "修改时间")
        private Date updateTime;


}
