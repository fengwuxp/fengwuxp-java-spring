package com.oak.payment.management.payment.rsp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: zhuox
 * @create: 2020-02-07
 * @description: 微信jsApi预下单响应参数
 **/
@Schema(description = "微信jsApi预下单响应参数")
@Data
@Accessors(chain = true)
public class WechatJsPaymentPreOrderRsp {

    /**
     * 应用appid
     */
    @Schema(description = "应用appid")
    private String appId;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    private String timeStamp;

    /**
     * 随机字符串
     */
    @Schema(description = "随机字符串")
    private String nonceStr;
    /**
     * 由于package为java保留关键字，因此改为packageValue. 前端使用时记得要更改为package
     */
    @Schema(description = "由于package为java保留关键字，因此改为packageValue. 前端使用时记得要更改为package")
    private String packageValue;

    /**
     * 签名类型
     */
    @Schema(description = "签名类型")
    private String signType;

    /**
     * 签名
     */
    @Schema(description = "签名")
    private String paySign;

    /**
     * 是否沙箱环境
     */
    @Schema(description = "是否沙箱环境")
    private boolean useSandboxEnv;
}
