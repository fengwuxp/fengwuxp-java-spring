package com.oak.member.services.account.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  创建MemberAccount
 *  2020-2-6 15:42:45
 */
@Schema(description = "创建CreateMemberAccountReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberAccountReq extends ApiBaseReq {

    @Schema(description = "会员id")
    private Long id;

    @Schema(description = "可用余额（单位分）")
    @NotNull
    private Integer money;

    @Schema(description = "冻结余额（单位分）")
    @NotNull
    private Integer frozenMoney;

    @Schema(description = "积分")
    @NotNull
    private Integer points;

    @Schema(description = "冻结积分")
    @NotNull
    private Integer frozenPoints;

    @Schema(description = "代金券")
    @NotNull
    private Integer coupon;

    @Schema(description = "冻结代金券")
    @NotNull
    private Integer frozenCoupon;

    @Schema(description = "账户状态")
    @NotNull
    private AccountStatus status;

    @Schema(description = "用户会员vip级别")
    @NotNull
    private VipGrade vipGrade;

    //@Schema(description = "校验码")
    //@NotNull
    //private String checkCode;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

}
