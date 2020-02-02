package com.oak.organization.services.organizationextendedinfo.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;



/**
 *  创建OrganizationExtendedInfo
 *  2020-2-2 15:59:04
 */
@Schema(description = "创建CreateOrganizationExtendedInfoReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateOrganizationExtendedInfoReq extends ApiBaseReq {

    @Schema(description = "LOGO")
    private String logo;

    @Schema(description = "版权")
    private String copyright;

    @Schema(description = "技术支持")
    private String technicalSupport;

    @Schema(description = "客服热线")
    private String serviceTel;

    @Schema(description = "绑定域名")
    private String domain;

    @Schema(description = "浏览器图标")
    private String favicon;

    @Schema(description = "icp备案号")
    private String icp;

    @Schema(description = "登录页背景")
    private String backgroundImage;

    @Schema(description = "商户登录页背景")
    private String merchantBackgroundImage;

    @Schema(description = "登录页LOGO")
    private String loginLogo;

}
