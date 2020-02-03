package com.oak.member.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import com.oak.api.entities.system.ClientChannel;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Schema(description =  "会员信息")
@Entity
@Table(name = "t_member")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"memberAccount", "memberSecure"})
public class Member extends AbstractNamedEntityObject<Long> {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "会员ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description =  "会员编号")
    @Column(name = "no", nullable = false, unique = true, length = 20)
    private String no;

    @Schema(description =  "手机号")
    @Column(name = "mobile_phone", nullable = true, length = 11)
    private String mobilePhone;

    @Schema(description =  "Email")
    @Column(name = "email")
    private String email;

    @Schema(description =  "用户名")
    @Column(name = "user_name", nullable = false, unique = true, length = 20)
    private String userName;

    @Schema(description =  "会员昵称")
    @Column(name = "nick_name", length = 100)
    private String nickName;

    @Schema(description =  "区域编码")
    @Column(name = "area_id", length = 50)
    private String areaId;

    @Schema(description =  "区域名称")
    @Column(name = "area_name")
    private String areaName;

    @Schema(description =  "地址")
    @Column(name = "address", length = 200)
    private String address;

    @Schema(description =  "性别")
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 16)
    private Gender gender;

    @Schema(description =  "生日")
    @Column(name = "birthday", length = 10)
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Schema(description =  "标签")
    @Column(name = "tags", length = 100)
    private String tags;

    @Schema(description =  "手机认证")
    @Column(name = "mobile_auth", nullable = false)
    private Boolean mobileAuth;

    @Schema(description =  "实名认证")
    @Column(name = "id_auth", nullable = false)
    private Boolean idAuth;

    @Schema(description =  "头像URL")
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Schema(description =  "注册时间")
    @Column(name = "reg_date_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDateTime;

    @Schema(description =  "注册时间")
    @Column(name = "reg_source", nullable = false, length = 32)
    private String regSource;

    @Schema(description =  "注册来源")
    @JoinColumn(name = "reg_source", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private ClientChannel regClientChannel;

    @Schema(description =  "会员类型")
    @Column(name = "member_type")
    private String memberType;

    @Schema(description =  "是否未设置登录密码")
    @Column(name = "not_password", nullable = false)
    private Boolean notPassword = false;

    @Schema(description =  "是否未设置支付密码")
    @Column(name = "not_pay_password", nullable = false)
    private Boolean notPayPassword = true;

    @Schema(description =  "封闭到期时间")
    @Column(name = "frozen_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date frozenDate;

    @Schema(description =  "审核状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "verify", nullable = false)
    private MemberVerifyStatus verify;

    @Schema(description =  "帐户信息")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private MemberAccount memberAccount;

    @Schema(description =  "安全信息")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private MemberSecure memberSecure;

    @Schema(description =  "推广二维码")
    @Column(name = "qr_code")
    private String qrCode;

    @Transient
    public String getShowName() {
        if (nickName != null && !nickName.isEmpty()) {
            return nickName;
        } else if (name != null && !name.isEmpty()) {
            return name;
        } else if (mobilePhone != null && mobilePhone.length() == 11) {
            return MessageFormat.format("{0}****{1}", mobilePhone.substring(0, 3), mobilePhone.substring(7));
        }
        return userName;
    }
}
