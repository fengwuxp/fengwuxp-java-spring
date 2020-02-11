package com.oak.member.services.member.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑会员信息
 *  2020-2-6 15:32:43
 */
@Schema(description = "编辑会员信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 20)
    @Schema(description = "会员编号")
    @UpdateColumn
    private String no;

    @Size(max = 11)
    @Schema(description = "手机号")
    @UpdateColumn
    private String mobilePhone;

    @Schema(description = "Email")
    @UpdateColumn
    private String email;

    @Size(max = 20)
    @Schema(description = "用户名")
    @UpdateColumn
    private String userName;

    @Size(max = 100)
    @Schema(description = "会员昵称")
    @UpdateColumn
    private String nickName;

    @Size(max = 50)
    @Schema(description = "区域编码")
    @UpdateColumn
    private String areaId;

    @Schema(description = "区域名称")
    @UpdateColumn
    private String areaName;

    @Size(max = 200)
    @Schema(description = "地址")
    @UpdateColumn
    private String address;

    @Schema(description = "性别")
    @UpdateColumn
    private Gender gender;

    @Schema(description = "生日")
    @UpdateColumn
    private Date birthday;

    @Size(max = 100)
    @Schema(description = "标签")
    @UpdateColumn
    private String tags;

    @Schema(description = "手机认证")
    @UpdateColumn
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    @UpdateColumn
    private Boolean idAuth;

    @Schema(description = "头像URL")
    @UpdateColumn
    private String avatarUrl;

    @Schema(description = "注册时间")
    @UpdateColumn
    private Date regDateTime;

    @Size(max = 32)
    @Schema(description = "注册时间")
    @UpdateColumn
    private String regSource;

    @Schema(description = "会员类型")
    @UpdateColumn
    private String memberType;

    @Schema(description = "是否未设置登录密码")
    @UpdateColumn
    private Boolean notPassword;

    @Schema(description = "是否未设置支付密码")
    @UpdateColumn
    private Boolean notPayPassword;

    @Schema(description = "封闭到期时间")
    @UpdateColumn
    private Date frozenDate;

    @Schema(description = "审核状态")
    @UpdateColumn
    private MemberVerifyStatus verify;

    @Schema(description = "推广二维码")
    @UpdateColumn
    private String qrCode;

    @Schema(description = "名称")
    @UpdateColumn
    private String name;

    @Schema(description = "排序代码")
    @UpdateColumn
    private Integer orderCode;

    @Schema(description = "是否允许")
    @UpdateColumn
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @UpdateColumn
    private Boolean editable;

    @Schema(description = "更新时间")
    @UpdateColumn
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @UpdateColumn
    private String remark;

    public EditMemberReq() {
    }

    public EditMemberReq(Long id) {
        this.id = id;
    }
}
