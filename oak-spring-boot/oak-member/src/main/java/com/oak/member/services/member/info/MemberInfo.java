package com.oak.member.services.member.info;

import com.levin.commons.service.domain.Desc;
import com.oak.api.services.infoprovide.info.ClientChannelInfo;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.secure.info.MemberSecureInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 会员信息
 * 2020-2-6 15:32:42
 */
@Schema(description = "会员信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"regClientChannelInfo", "memberAccountInfo", "memberSecureInfo",})
public class MemberInfo implements Serializable {

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

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "手机认证")
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    private Boolean idAuth;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "注册时间")
    private Date regDateTime;

    @Schema(description = "注册时间")
    private String regSource;

    @Desc(value = "", code = "regClientChannel")
    @Schema(description = "注册来源")
    private ClientChannelInfo regClientChannelInfo;

    @Schema(description = "会员类型")
    private String memberType;

    @Schema(description = "是否未设置登录密码")
    private Boolean notPassword;

    @Schema(description = "是否未设置支付密码")
    private Boolean notPayPassword;

    @Schema(description = "封闭到期时间")
    private Date frozenDate;

    @Schema(description = "审核状态")
    private MemberVerifyStatus verify;

    @Desc(value = "", code = "memberAccount")
    @Schema(description = "帐户信息")
    private MemberAccountInfo memberAccountInfo;

    @Desc(value = "", code = "memberSecure")
    @Schema(description = "安全信息")
    private MemberSecureInfo memberSecureInfo;

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

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "备注")
    private String remark;


}
