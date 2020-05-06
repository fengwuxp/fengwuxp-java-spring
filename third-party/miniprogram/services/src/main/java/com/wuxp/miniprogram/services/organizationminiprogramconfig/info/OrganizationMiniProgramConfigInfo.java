package com.oak.organization.miniprogram.services.organizationminiprogramconfig.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 组织小程序发布配置
* 2020-3-2 17:28:20
*/
@Schema(description ="组织小程序发布配置")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class OrganizationMiniProgramConfigInfo implements Serializable {

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

        @Schema(description = "排序代码")
        private Integer orderCode;

        @Schema(description = "是否允许")
        private Boolean enable;

        @Schema(description = "是否可编辑")
        private Boolean editable;

        @Schema(description = "创建时间")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;

        @Schema(description = "备注")
        private String remark;


}
