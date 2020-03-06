package com.wuxp.miniprogram.services.wxopenconfig.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;



/**
 *  创建WxOpenConfig
 *  2020-3-2 14:28:21
 */
@Schema(description = "创建CreateWxOpenConfigReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateWxOpenConfigReq extends ApiBaseReq {

    @Schema(description = "组织编号")
    @NotNull
    private Long organizationId;

    @Schema(description = "组织代码")
    @NotNull
    @Size(max = 32)
    private String organizationCode;

    @Schema(description = "小程序AppId")
    private String miniProgramAppId;

    @Schema(description = "小程序AppSecret")
    private String miniProgramAppSecret;

    @Schema(description = "小程序Token")
    private String miniProgramToken;

    @Schema(description = "小程序消息加密解密key")
    private String miniProgramMsgKey;

    @Schema(description = "小程序额外参数json格式")
    private String miniProgramExtra;

    @Schema(description = "小程序额外文件名")
    private String miniProgramFilename;

    @Schema(description = "小程序额外文件内容")
    private String miniProgramFilecontent;

    @Schema(description = "公众号Appid")
    private String officialAccountAppId;

    @Schema(description = "公众号AppSecret")
    private String officialAccountAppSecret;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

}
