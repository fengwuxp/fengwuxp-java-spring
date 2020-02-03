package com.oak.member.entities;

import com.oak.member.enums.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Schema(description = "会员账户信息日志")
@Entity
@Table(name = "t_member_account_log", indexes = {
        @Index(columnList = "member_id")
})
@Data
@ToString(exclude = {"member"})
public class MemberAccountLog implements java.io.Serializable {

    private static final long serialVersionUID = -5168172827598435580L;


    @Id
    @Schema(description = "日志ID")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "流水号")
    @Column(name = "sn", nullable = false)
    private String sn;

    @Schema(description = "操作类型")
    @Column(name = "type", length = 100)
    private String type;


    @Schema(description = "会员ID")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "会员")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;


    @Schema(description = "调整余额")
    @Column(name = "money", nullable = false)
    private Integer money;


    @Schema(description = "当前余额")
    @Column(name = "curr_money")
    private Integer currMoney;


    @Schema(description = "调整冻结余额")
    @Column(name = "frozen_money", nullable = false)
    private Integer frozenMoney;


    @Schema(description = "当前冻结余额")
    @Column(name = "curr_frozen_money", nullable = false)
    private Integer currFrozenMoney;


    @Schema(description = "积分")
    @Column(name = "points", nullable = false)
    private Integer points;


    @Schema(description = "当前可用积分")
    @Column(name = "curr_points")
    private Integer currPoints;


    @Schema(description = "调整冻结积分")
    @Column(name = "frozen_points", nullable = false)
    private Integer frozenPoints;

    @Schema(description = "当前冻结积分")
    @Column(name = "curr_frozen_points", nullable = false)
    private Integer currFrozenPoints;


    @Schema(description = "调整代金券")
    @Column(name = "coupon", nullable = false)
    private Integer coupon;

    @Schema(description = "当前代金券金额")
    @Column(name = "curr_coupon", nullable = false)
    private Integer currCoupon;


    @Schema(description = "调整冻结代金券")
    @Column(name = "frozen_coupon", nullable = false)
    private Integer frozenCoupon;


    @Schema(description = "当前冻结代金券金额")
    @Column(name = "curr_frozen_coupon", nullable = false)
    private Integer currFrozenCoupon;


    @Schema(description = "账户状态")
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    /**
     * 操作者.
     */
    @Schema(description = "操作者")
    @Column(name = "operator", nullable = false, length = 100)
    private String operator;


    @Schema(description = "日志描述")
    @Column(name = "description", length = 1000)
    private String description;


    @Schema(description = "订单流水号")
    @Column(name = "order_sn", length = 100)
    private String orderSn;


    @Schema(description = "日志日期")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "校验码")
    @Column(name = "check_code")
    private String checkCode;


}
