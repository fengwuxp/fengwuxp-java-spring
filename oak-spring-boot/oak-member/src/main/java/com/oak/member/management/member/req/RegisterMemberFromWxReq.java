package com.oak.member.management.member.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author laiy
 * create at 2020-02-12 10:33
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegisterMemberFromWxReq {

    @Schema(name = "openId", description =  "OpenID")
    private String openId;

    @Schema(name = "nickname", description =  "昵称")
    private String nickname;

    @Schema(name = "sex", description =  "性别")
    private Integer sex;

    @Schema(name = "headImgUrl", description =  "头像")
    private String headImgUrl;

    @Schema(name = "unionId", description =  "unionId")
    private String unionId;

    @Schema(description =  "区域编码")
    private String areaId;

    @Schema(description =  "区域名称")
    private String areaName;

    @Schema(description =  "地址")
    private String address;

}
