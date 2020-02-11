package com.oak.member.entities;

import com.levin.commons.dao.domain.support.AbstractBaseEntityObject;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Schema(description = "会员账户信息")
@Entity
@Table(name = "t_member_account")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"member"})
public class MemberAccount extends AbstractBaseEntityObject<Long> {

    private static final long serialVersionUID = 374796685682830177L;

    /**
     * 会员ID.
     */
    @Id
    @Schema(description = "id")
    private Long id;

    /**
     * 可用余额.
     */
    @Schema(description = "可用余额（单位分）")
    @Column(name = "money", nullable = false)
    private Integer money;

    /**
     * 冻结余额.
     */
    @Schema(description = "冻结余额（单位分）")
    @Column(name = "frozen_money", nullable = false)
    private Integer frozenMoney;

    /**
     * 积分.
     */
    @Schema(description = "积分")
    @Column(name = "points", nullable = false)
    private Integer points;

    /**
     * 冻结积分.
     */
    @Schema(description = "冻结积分")
    @Column(name = "frozen_points", nullable = false)
    private Integer frozenPoints;

    /**
     * 代金券
     */
    @Schema(description = "代金券")
    @Column(name = "coupon", nullable = false)
    private Integer coupon;

    /**
     * 冻结代金券
     */
    @Schema(description = "冻结代金券")
    @Column(name = "frozen_coupon", nullable = false)
    private Integer frozenCoupon;

    /**
     * 账户状态.
     */
    @Schema(description = "账户状态")
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;


    @Schema(description = "用户会员vip级别")
    @Column(name = "vip_grade", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private VipGrade vipGrade;

    @Schema(description = "会员信息")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "id"))
    private Member member;

    //@Schema(description = "校验码")
    //@Column(name = "check_code", nullable = false)
    //private String checkCode;

    /**
     * default constructor
     */
    public MemberAccount() {
    }


    @Transient
    //可用余额
    public Integer getAvailableMoney() {
        return money - frozenMoney;
    }

    @Transient
    //可用积分
    public Integer getAvailablePoints() {
        return points - frozenPoints;
    }

    @Transient
    //可用代金券金额
    public Integer getAvailableCoupon() {
        return coupon - frozenCoupon;
    }


}
