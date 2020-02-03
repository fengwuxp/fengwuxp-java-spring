package com.oak.member.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "登录模式")
public enum LoginModel implements DescriptiveEnum {

    @Schema(description = "密码")
    PASSWORD,
    @Schema(description = "手机验证码")
    PHONE_CODE,
    @Schema(description = "一键获取手机")
    PHONE_QUICK,
    @Schema(description = "扫码登陆")
    SCAN_CODE,
    @Schema(description = "开放平台快捷")
    OPEN_ID,
    @Schema(description = "刷新TOKEN")
    REFRESH_TOKEN,
    @Schema(description = "内部登录")
    INTERNAL;


}
