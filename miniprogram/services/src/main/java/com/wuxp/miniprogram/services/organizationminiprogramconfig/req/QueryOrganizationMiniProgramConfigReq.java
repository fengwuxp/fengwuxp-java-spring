package com.wuxp.miniprogram.services.organizationminiprogramconfig.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import com.levin.commons.dao.annotation.misc.Fetch;
import java.util.Date;
/**
 *  查询组织小程序发布配置
 *  2020-3-2 17:28:20
 */
@Schema(description = "查询组织小程序发布配置")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryOrganizationMiniProgramConfigReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "组织编号")
    private Long organizationId;

    @Schema(description = "组织代码")
    private String organizationCode;

    @Schema(description = "(小程序)代码库中的代码模版ID")
    private String miniProgramTemplateId;

    @Schema(description = "(小程序)第三方自定义的配置")
    private String miniProgramExtJson;

    @Schema(description = "(小程序)代码版本号")
    private String miniProgramUserVersion;

    @Schema(description = "(小程序)代码描述")
    private String miniProgramUserDesc;

    @Schema(description = "(小程序)类目对象配置")
    private String miniProgramItemList;

    @Schema(description = "（小程序）request 合法域名")
    private String miniProgramRequestdomain;

    @Schema(description = "(小程序)socket 合法域名")
    private String miniProgramWsrequestdomain;

    @Schema(description = "(小程序)uploadFile合法域名")
    private String miniProgramUploaddomain;

    @Schema(description = "(小程序)downloadFile 合法域名")
    private String miniProgramDownloaddomain;

    @Schema(description = "(小程序)小程序业务域名")
    private String miniProgramWebviewdomain;

    @Schema(description = "公司业务域名")
    private String organizationDomainName;

    public QueryOrganizationMiniProgramConfigReq() {
    }

    public QueryOrganizationMiniProgramConfigReq(Long id) {
        this.id = id;
    }
}
