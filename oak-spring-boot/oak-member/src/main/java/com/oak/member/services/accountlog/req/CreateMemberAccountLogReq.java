package com.oak.member.services.accountlog.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import com.oak.member.enums.AccountStatus;


/**
 *  创建MemberAccountLog
 *  2020-2-18 14:06:41
 */
@Schema(description = "创建CreateMemberAccountLogReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberAccountLogReq extends ApiBaseReq {

    @Schema(description = "操作类型")
    @Size(max = 100)
    private String type;

    @Schema(description = "会员ID")
    @NotNull
    private Long memberId;

    @Schema(description = "调整余额")
    @NotNull
    private Integer money;

    @Schema(description = "当前余额")
    private Integer currMoney;

    @Schema(description = "调整冻结余额")
    @NotNull
    private Integer frozenMoney;

    @Schema(description = "当前冻结余额")
    @NotNull
    private Integer currFrozenMoney;

    @Schema(description = "积分")
    @NotNull
    private Integer points;

    @Schema(description = "当前可用积分")
    private Integer currPoints;

    @Schema(description = "调整冻结积分")
    @NotNull
    private Integer frozenPoints;

    @Schema(description = "当前冻结积分")
    @NotNull
    private Integer currFrozenPoints;

    @Schema(description = "调整代金券")
    @NotNull
    private Integer coupon;

    @Schema(description = "当前代金券金额")
    @NotNull
    private Integer currCoupon;

    @Schema(description = "调整冻结代金券")
    @NotNull
    private Integer frozenCoupon;

    @Schema(description = "当前冻结代金券金额")
    @NotNull
    private Integer currFrozenCoupon;

    @Schema(description = "账户状态")
    @NotNull
    private AccountStatus status;

    @Schema(description = "操作者")
    @NotNull
    @Size(max = 100)
    private String operator;

    @Schema(description = "日志描述")
    @Size(max = 1000)
    private String description;

    @Schema(description = "订单流水号")
    @Size(max = 100)
    private String orderSn;

    @Schema(description = "校验码")
    private String checkCode;

}
