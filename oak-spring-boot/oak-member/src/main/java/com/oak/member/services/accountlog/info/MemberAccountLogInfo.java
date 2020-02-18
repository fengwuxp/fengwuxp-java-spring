package com.oak.member.services.accountlog.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.service.domain.Desc;
        import com.oak.member.enums.AccountStatus;


import java.io.Serializable;
import java.util.Date;


/**
* 会员账户信息日志
* 2020-2-18 14:06:41
*/
@Schema(description ="会员账户信息日志")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"memberInfo",})
public class MemberAccountLogInfo implements Serializable {

        @Schema(description = "日志ID")
        private Long id;

        @Schema(description = "流水号")
        private String sn;

        @Schema(description = "操作类型")
        private String type;

        @Schema(description = "会员ID")
        private Long memberId;

          @Desc(value = "",code = "member")
        @Schema(description = "会员")
        private MemberInfo memberInfo;

        @Schema(description = "调整余额")
        private Integer money;

        @Schema(description = "当前余额")
        private Integer currMoney;

        @Schema(description = "调整冻结余额")
        private Integer frozenMoney;

        @Schema(description = "当前冻结余额")
        private Integer currFrozenMoney;

        @Schema(description = "积分")
        private Integer points;

        @Schema(description = "当前可用积分")
        private Integer currPoints;

        @Schema(description = "调整冻结积分")
        private Integer frozenPoints;

        @Schema(description = "当前冻结积分")
        private Integer currFrozenPoints;

        @Schema(description = "调整代金券")
        private Integer coupon;

        @Schema(description = "当前代金券金额")
        private Integer currCoupon;

        @Schema(description = "调整冻结代金券")
        private Integer frozenCoupon;

        @Schema(description = "当前冻结代金券金额")
        private Integer currFrozenCoupon;

        @Schema(description = "账户状态")
        private AccountStatus status;

        @Schema(description = "操作者")
        private String operator;

        @Schema(description = "日志描述")
        private String description;

        @Schema(description = "订单流水号")
        private String orderSn;

        @Schema(description = "日志日期")
        private Date createTime;

        @Schema(description = "校验码")
        private String checkCode;


}
