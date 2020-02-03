package com.oak.member.entities;

import com.levin.commons.service.domain.Desc;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Desc(value = "会员安全信息")
@Table(name = "t_member_secure")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"pwdKey","totpSecret", "loginPassword", "payPassword", "member"})
public class MemberSecure implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Desc(value = "会员ID")
    @Id
    private Long id;

    @Desc(value = "加密key")
    @Column(name = "pwd_key", nullable = false)
    private String pwdKey;

    @Desc(value = "动态口令安全密钥")
    @Column(name = "totp_secret", length = 16, nullable = false)
    @Size(min = 16, max = 16)
    private String totpSecret;

    @Desc(value = "开启二次验证")
    @Column(name = "totp_enabled", nullable = false)
    private Boolean totpEnabled = false;

    @Desc(value = "登录密码")
    @Column(name = "login_password", nullable = false, length = 128)
    private String loginPassword;

    @Desc(value = "支付密码")
    @Column(name = "pay_password", length = 128)
    private String payPassword;

    @Desc(value = "登录次数")
    @Column(name = "login_times")
    private Integer loginTimes = 0;

    @Desc(value = "最后登录时间")
    @Column(name = "last_login_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;

    @Desc(value = "最后登录IP")
    @Column(name = "last_login_ip", length = 16)
    private String lastLoginIp;

    @Desc(value = "登录密码设置日期")
    @Column(name = "login_pwd_update_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginPwdUpdateTime;

    @Desc(value = "支付密码设置日期")
    @Column(name = "pay_pwd_update_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date payPwdUpdateTime;

    @Desc(value = "需要更改登录密码")
    @Column(name = "change_password")
    private Boolean changeLoginPassword = false;

    @Desc(value = "需要修改支付密码")
    @Column(name = "change_pay_password")
    private Boolean changePayPassword = false;

    @Desc(value = "最后登录密码失败时间")
    @Column(name = "last_login_fail_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginFailTime;

    @Desc(value = "登录密码失败数")
    @Column(name = "login_pwd_fail")
    private Integer loginPwdFail = 0;

    @Desc(value = "最后登录密码锁定时间")
    @Column(name = "last_login_fail_lock_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginFailLockTime;

    @Desc(value = "最后支付密码失败时间")
    @Column(name = "last_pay_fail_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPayFailTime;

    @Desc(value = "支付密码失败数")
    @Column(name = "pay_pwd_fail")
    private Integer payPwdFail = 0;

    @Desc(value = "最后支付密码锁定时间")
    @Column(name = "last_pay_fail_lock_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPayFailLockTime;

    @Desc(value = "会员")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Member member;

    @Desc(value = "更新日期")
    @Column(name = "update_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

}
