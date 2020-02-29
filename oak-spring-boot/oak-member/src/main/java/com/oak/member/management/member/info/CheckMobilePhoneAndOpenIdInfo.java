package com.oak.member.management.member.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author laiy
 * create at 2020-02-19 15:07
 * @Description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class CheckMobilePhoneAndOpenIdInfo {

    @Schema(name = "mobilePhone", description = "手机号")
    private String mobilePhone;

    @Schema(name = "registed", description = "上传手机号是否已注册")
    private Boolean registed;

    @Schema(name = "mobBindOpen", description = "上传手机号是否已有第三方绑定")
    private Boolean mobBindOpen;

    @Schema(name = "bindOpen", description = "上传OPENID是否已绑定第三方")
    private Boolean bindOpen;
}
