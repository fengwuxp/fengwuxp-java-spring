package com.oak.member.management.member.info;

import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.enums.VipGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author laiy
 * create at 2020-02-11 15:47
 * @Description
 */
@Data
@Accessors(chain = true)
public class AccountInfo {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "会员编号")
    private String no;

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "Email")
    private String email;

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

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "手机认证")
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    private Boolean idAuth;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "注册时间")
    private Date regDateTime;

    @Schema(description = "会员类型")
    private String memberType;

    @Schema(description = "是否未设置登录密码")
    private Boolean notPassword;

    @Schema(description = "是否未设置支付密码")
    private Boolean notPayPassword;

    @Schema(description = "审核状态")
    private MemberVerifyStatus verify;

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

}
