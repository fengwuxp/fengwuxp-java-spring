package com.oak.member.services.account.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *  查询会员账户信息
 *  2020-2-6 15:42:46
 */
@Schema(description = "查询会员账户信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberAccountReq extends ApiBaseQueryReq {

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

    @Schema(description = "加载会员信息")
    @Fetch(value = "member", condition = "#_val==true")
    private Boolean loadMember;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "是否允许")
    private Boolean enable;

    @Schema(description = "是否可编辑")
    private Boolean editable;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "最小更新时间")
    @Gte("lastUpdateTime")
    private Date minLastUpdateTime;

    @Schema(description = "最大更新时间")
    @Lte("lastUpdateTime")
    private Date maxLastUpdateTime;

    @Schema(description = "备注")
    private String remark;

    public QueryMemberAccountReq() {
    }

    public QueryMemberAccountReq(Long id) {
        this.id = id;
    }
}
