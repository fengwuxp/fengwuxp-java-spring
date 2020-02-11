package com.oak.member.services.account.info;

import com.levin.commons.service.domain.Desc;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
import com.oak.member.services.member.info.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 会员账户信息
* 2020-2-6 15:42:45
*/
@Schema(description ="会员账户信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"memberInfo",})
public class MemberAccountInfo implements Serializable {

        @Schema(description = "id")
        private Long id;

        @Schema(description = "可用余额（单位分）")
        private Integer money;

        @Schema(description = "冻结余额（单位分）")
        private Integer frozenMoney;

        @Schema(description = "积分")
        private Integer points;

        @Schema(description = "冻结积分")
        private Integer frozenPoints;

        @Schema(description = "代金券")
        private Integer coupon;

        @Schema(description = "冻结代金券")
        private Integer frozenCoupon;

        @Schema(description = "账户状态")
        private AccountStatus status;

        @Schema(description = "用户会员vip级别")
        private VipGrade vipGrade;

          @Desc(value = "",code = "member")
        @Schema(description = "会员信息")
        private MemberInfo memberInfo;

        //@Schema(description = "校验码")
        //private String checkCode;

        @Schema(description = "排序代码")
        private Integer orderCode;

        @Schema(description = "是否允许")
        private Boolean enable;

        @Schema(description = "是否可编辑")
        private Boolean editable;

        @Schema(description = "创建时间")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;

        @Schema(description = "备注")
        private String remark;


}
