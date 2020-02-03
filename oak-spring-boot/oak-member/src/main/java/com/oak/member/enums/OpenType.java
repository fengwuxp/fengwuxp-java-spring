package com.oak.member.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "开放平台类型")
public enum OpenType implements DescriptiveEnum {

    @Schema(description = "微信公众平台")
    WEIXIN,

    @Schema(description = "微信开放平台")
    WEIXIN_OPEN,

    @Schema(description = "微信小程序")
    WEIXIN_MA,

    @Schema(description = "支付宝")
    ALIPAY,

    @Schema(description = "QQ")
    QZONE,

    @Schema(description = "微博")
    WEIBO,

    @Schema(description = "APPLE")
    APPLE;


}
