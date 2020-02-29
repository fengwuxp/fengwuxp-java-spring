package com.oak.organization.services.organizationextendedinfo.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 *  查询组织扩展信息
 *  2020-2-2 15:59:04
 */
@Schema(description = "查询组织扩展信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryOrganizationExtendedInfoReq extends ApiBaseQueryReq {

    @Schema(description = "机构ID")
    private Long id;

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

    public QueryOrganizationExtendedInfoReq() {
    }

    public QueryOrganizationExtendedInfoReq(Long id) {
        this.id = id;
    }
}
