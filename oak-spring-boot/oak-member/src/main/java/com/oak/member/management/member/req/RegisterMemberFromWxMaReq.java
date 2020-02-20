package com.oak.member.management.member.req;

import com.levin.commons.service.domain.Desc;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-06 17:11
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "小程序用户注册")
public class RegisterMemberFromWxMaReq extends ApiBaseReq {

    @Desc("临时登录凭证")
    @NotNull
    private String code;

    @Desc("小程序appid")
    private String wxMaAppId;

    @Desc(value = "获取手机号加密数据")
    @NotNull
    private String encryptedData;

    @Desc(value = "获取手机号加密算法的初始向量")
    @NotNull
    private String ivStr;

    @Desc(value = "获取用户信息加密数据")
    @NotNull
    private String userEncryptedData;

    @Desc(value = "获取用户信息加密算法的初始向量")
    @NotNull
    private String userIvStr;

    @Desc(value = "昵称")
    @NotNull
    private String nickName;

    @Desc(value = "头像")
    @NotNull
    private String avatarUrl;

    @Desc(value = "性别 0 未知 1 男 2 女")
    @NotNull
    private Gender gender;

    @Schema(description =  "区域编码")
    private String areaId;

    @Schema(description =  "区域名称")
    private String areaName;

    @Schema(description =  "地址")
    private String address;

}
