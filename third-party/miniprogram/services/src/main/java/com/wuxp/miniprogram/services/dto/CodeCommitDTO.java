package com.wuxp.miniprogram.services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Classname CodeCommitDTO
 * @Description TODO
 * @Date 2020/3/17 19:16
 * @Created by 44487
 */
@Schema(description = "一键提交代码")
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class CodeCommitDTO {

    @Schema(
            description = "小程序appid",
            name = "appid"
    )
    @NotBlank
    private String appid;
    @Schema(
            description = "代码库中的代码模版ID",
            name = "template_id"
    )
    @Min(0L)
    @NotNull
    private Long templateId;
    @Schema(
            description = "第三方自定义的配置",
            name = "ext_json"
    )
    private String extJson;
    @Schema(
            description = "代码版本号，开发者可自定义（长度不要超过64个字符",
            name = "user_version"
    )
    @Size(
            max = 64,
            message = "长度不要超过64个字符"
    )
    @NotNull
    private String userVersion;
    @Schema(
            description = "代码描述，开发者可自定义",
            name = "user_desc"
    )
    @NotBlank
    private String userDesc;
}
