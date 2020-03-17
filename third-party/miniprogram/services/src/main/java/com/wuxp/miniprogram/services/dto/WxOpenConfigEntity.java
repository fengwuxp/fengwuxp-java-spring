package com.wuxp.miniprogram.services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Classname WxOpenConfigEntity
 * @Description TODO
 * @Date 2020/3/17 19:11
 * @Created by 44487
 */
@Schema(description = "商户统计首页总收益返回信息")
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class WxOpenConfigEntity {

    private String componentAppId;
    private String componentAppSecret;
    private String componentToken;
    private String componentAesKey;

}
