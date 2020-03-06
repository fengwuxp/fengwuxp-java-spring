package com.wuxp.miniprogram.services.organizationminiprogramconfig.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;



/**
 *  创建OrganizationMiniProgramConfig
 *  2020-3-2 17:28:20
 */
@Schema(description = "创建CreateOrganizationMiniProgramConfigReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateOrganizationMiniProgramConfigReq extends ApiBaseReq {

    @Schema(description = "组织编号")
    @NotNull
    private Long organizationId;

    @Schema(description = "组织代码")
    @NotNull
    @Size(max = 32)
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

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

}
