package com.oak.member.services.member.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *  查询会员信息
 *  2020-2-6 15:32:43
 */
@Schema(description = "查询会员信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberReq extends ApiBaseQueryReq {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "会员编号")
    private String no;

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "会员昵称")
    private String nickName;

    @Schema(description = "区域编码")
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "性别")
    private Gender gender;

    @Schema(description = "最小生日")
    @Gte("birthday")
    private Date minBirthday;

    @Schema(description = "最大生日")
    @Lte("birthday")
    private Date maxBirthday;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "手机认证")
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    private Boolean idAuth;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "最小注册时间")
    @Gte("regDateTime")
    private Date minRegDateTime;

    @Schema(description = "最大注册时间")
    @Lte("regDateTime")
    private Date maxRegDateTime;

    @Schema(description = "注册时间")
    private String regSource;

    @Schema(description = "会员类型")
    private String memberType;

    @Schema(description = "是否未设置登录密码")
    private Boolean notPassword;

    @Schema(description = "是否未设置支付密码")
    private Boolean notPayPassword;

    @Schema(description = "最小封闭到期时间")
    @Gte("frozenDate")
    private Date minFrozenDate;

    @Schema(description = "最大封闭到期时间")
    @Lte("frozenDate")
    private Date maxFrozenDate;

    @Schema(description = "审核状态")
    private MemberVerifyStatus verify;

    @Schema(description = "加载帐户信息")
    @Fetch(value = "memberAccount", condition = "#_val==true")
    private Boolean loadMemberAccount;

    @Schema(description = "加载安全信息")
    @Fetch(value = "memberSecure", condition = "#_val==true")
    private Boolean loadMemberSecure;

    @Schema(description = "推广二维码")
    private String qrCode;

    @Schema(description = "名称")
    private String name;

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

    public QueryMemberReq() {
    }

    public QueryMemberReq(Long id) {
        this.id = id;
    }
}
