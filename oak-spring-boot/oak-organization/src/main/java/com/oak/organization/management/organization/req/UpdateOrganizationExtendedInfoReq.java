package com.oak.organization.management.organization.req;


import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "编辑机构拓展信息")
public class UpdateOrganizationExtendedInfoReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    private Long id;

    @Schema(description = "域名")
    @NotNull
    private String domain;

    @Schema(description = "技术支持")
    private String technicalSupport;

    @Schema(description = "版权说明")
    private String copyright;

    @Schema(description = "icp备案号")
    private String icp;

    @Schema(description = "LOGO")
    private String logo;

    @Schema(description = "登录页LOGO")
    private String loginLogo;

    @Schema(description = "登录页背景")
    private String backgroundImage;

    @Schema(description = "商户登录页背景")
    private String merchantBackgroundImage;
}

