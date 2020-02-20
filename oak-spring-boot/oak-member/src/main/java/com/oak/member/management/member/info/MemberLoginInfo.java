package com.oak.member.management.member.info;

import com.levin.commons.dao.annotation.Ignore;
import com.levin.commons.service.domain.Desc;
import com.oak.member.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author laiy
 * create at 2020-02-17 10:00
 * @Description
 */

@Data
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginInfo {

    @Desc(value = "会员ID")
    private Long id;

    @Desc(value = "会员类型")
    private String memberType;

    @Desc(value = "会员编号")
    private String no;

    @Desc(value = "用户名")
    private String userName;

    @Desc(value = "手机号")
    private String mobilePhone;

    @Desc(value = "Email")
    private String email;

    @Desc(value = "会员昵称")
    private String nickName;

    @Desc(value = "会员姓名")
    private String realName;

    @Desc(value = "性别")
    private Gender gender;

    @Desc(value = "生日")
    private Date birthday;

    @Desc(value = "区域编码")
    private String areaId;

    @Desc(value = "区域名称")
    private String areaName;

    @Desc(value = "地址")
    private String address;

    @Desc(value = "手机认证")
    private Boolean mobileAuth;

    @Desc(value = "实名认证")
    private Boolean idAuth;

    @Desc(value = "头像URL")
    private String avatarUrl;

    @Desc(value = "推广二维码")
    private String qrCode;

    @Desc(value = "登录后token")
    @Ignore
    private String token;

    @Desc(value = "token剩余有效秒数，为-1表示不限制")
    @Ignore
    private Long tokenExpireTimeSeconds;

    @Desc(value = "是否未设置登录密码")
    private Boolean notPassword = false;

    @Desc(value = "是否未设置支付密码")
    private Boolean notPayPassword = true;

    public String getShowName() {
        if (nickName != null && !nickName.isEmpty()) {
            return nickName;
        } else if (realName != null && !realName.isEmpty()) {
            return realName;
        } else if (mobilePhone != null && mobilePhone.length() == 11) {
            return mobilePhone.substring(0, 3) + "****" + mobilePhone.substring(7);
        }
        return userName;
    }

    public String getFullName() {
        return realName != null
                ? realName + "[" + userName + "]"
                : (nickName != null ? nickName + "[" + userName + "]" : userName);
    }
}
