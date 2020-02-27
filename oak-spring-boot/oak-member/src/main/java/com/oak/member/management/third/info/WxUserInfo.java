package com.oak.member.management.third.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class WxUserInfo implements java.io.Serializable {

    @Schema(description = "是否已关注")
    private Boolean subscribe;

    @Schema(description = "openId")
    private String openId;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "语言")
    private String language;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "头像")
    private String headImgUrl;

    @Schema(description = "关注时间")
    private Long subscribeTime;

    @Schema(description = "unionId")
    private String unionId;

    private Integer sexId;

    private String remark;

    private Integer groupId;

    @Schema(description = "手机号")
    private String mobilePhone;

    private Long[] tagIds;

}
