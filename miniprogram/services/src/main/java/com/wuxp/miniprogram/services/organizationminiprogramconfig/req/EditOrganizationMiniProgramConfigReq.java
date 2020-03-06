package com.wuxp.miniprogram.services.organizationminiprogramconfig.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑组织小程序发布配置
 *  2020-3-2 17:28:20
 */
@Schema(description = "编辑组织小程序发布配置")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditOrganizationMiniProgramConfigReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "组织编号")
    @UpdateColumn
    private Long organizationId;

    @Size(max = 32)
    @Schema(description = "组织代码")
    @UpdateColumn
    private String organizationCode;

    @Schema(description = "(小程序)代码库中的代码模版ID")
    @UpdateColumn
    private String miniProgramTemplateId;

    @Schema(description = "(小程序)第三方自定义的配置")
    @UpdateColumn
    private String miniProgramExtJson;

    @Schema(description = "(小程序)代码版本号")
    @UpdateColumn
    private String miniProgramUserVersion;

    @Schema(description = "(小程序)代码描述")
    @UpdateColumn
    private String miniProgramUserDesc;

    @Schema(description = "(小程序)类目对象配置")
    @UpdateColumn
    private String miniProgramItemList;

    @Schema(description = "（小程序）request 合法域名")
    @UpdateColumn
    private String miniProgramRequestdomain;

    @Schema(description = "(小程序)socket 合法域名")
    @UpdateColumn
    private String miniProgramWsrequestdomain;

    @Schema(description = "(小程序)uploadFile合法域名")
    @UpdateColumn
    private String miniProgramUploaddomain;

    @Schema(description = "(小程序)downloadFile 合法域名")
    @UpdateColumn
    private String miniProgramDownloaddomain;

    @Schema(description = "(小程序)小程序业务域名")
    @UpdateColumn
    private String miniProgramWebviewdomain;

    @Schema(description = "公司业务域名")
    private String organizationDomainName;

    @Schema(description = "排序代码")
    @UpdateColumn
    private Integer orderCode;

    @Schema(description = "是否允许")
    @UpdateColumn
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @UpdateColumn
    private Boolean editable;

    @Schema(description = "更新时间")
    @UpdateColumn
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @UpdateColumn
    private String remark;

    public EditOrganizationMiniProgramConfigReq() {
    }

    public EditOrganizationMiniProgramConfigReq(Long id) {
        this.id = id;
    }
}
