package com.wuxp.miniprogram.services.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Classname WxResultBaseBO
 * @Description TODO
 * @Date 2020/3/17 19:46
 * @Created by 44487
 */

@Schema(description = "微信请求结果")
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class WxResultBaseBO {

    @Schema(description = "0正常 非0异常")
    private Integer errcode;
    @Schema(description = "错误信息")
    private String errmsg;

}
