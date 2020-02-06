package com.oak.payment.entities;


import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Schema(description = "支付订单对象")
@Table(name = "t_payment_order", indexes = {
        @Index(columnList = "sn"),
        @Index(columnList = "seller_id"),
        @Index(columnList = "buyer_id"),
})
@Data
@EqualsAndHashCode(of = "sn")
@ToString(exclude = {"payments"})
@Accessors(chain = true)
public class PaymentOrder implements Serializable {


    private static final long serialVersionUID = -2143797615708677981L;

    @Id
    @Schema(description = "支付单号")
    @Column(name = "sn", unique = true, nullable = false, length = 32)
    private String sn;

    @Schema(description = "支付类型")
    @Column(name = "type", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Schema(description = "支付关联的订单类型")
    @Column(name = "order_types", nullable = false, length = 32)
    private String orderTypes;

    @Schema(description = "归属的卖家用户")
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Schema(description = "购买用户")
    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Schema(description = "应付金额")
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Schema(description = "已付金额")
    @Column(name = "paid_amount", nullable = false)
    private Integer paidAmount;

    @Schema(description = "支付订单支付")
    @Column(name = "status", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Schema(description = "到期时间")
    @Column(name = "expiration_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expirationTime;

    @Schema(description = "已删除")
    @Column(name = "is_deleted", nullable = false)
    protected Boolean deleted = Boolean.FALSE;


    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "修改时间")
    @Column(name = "update_time", updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date updateTime;

    @Schema(description = "支付单列表")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "paymentOrder")
    private Set<Payment> payments;

}
