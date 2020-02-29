package com.oak.organization.services.organizationextendedinfo.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  编辑组织扩展信息
 *  2020-2-2 15:59:04
 */
@Schema(description = "编辑组织扩展信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditOrganizationExtendedInfoReq extends ApiBaseReq {

    @Schema(description = "机构ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "LOGO")
    @UpdateColumn
    private String logo;

    @Schema(description = "版权")
    @UpdateColumn
    private String copyright;

    @Schema(description = "技术支持")
    @UpdateColumn
    private String technicalSupport;

    @Schema(description = "客服热线")
    @UpdateColumn
    private String serviceTel;

    @Schema(description = "绑定域名")
    @UpdateColumn
    private String domain;

    @Schema(description = "浏览器图标")
    @UpdateColumn
    private String favicon;

    @Schema(description = "icp备案号")
    @UpdateColumn
    private String icp;

    @Schema(description = "登录页背景")
    @UpdateColumn
    private String backgroundImage;

    @Schema(description = "商户登录页背景")
    @UpdateColumn
    private String merchantBackgroundImage;

    @Schema(description = "登录页LOGO")
    @UpdateColumn
    private String loginLogo;

    public EditOrganizationExtendedInfoReq() {
    }

    public EditOrganizationExtendedInfoReq(Long id) {
        this.id = id;
    }
}
