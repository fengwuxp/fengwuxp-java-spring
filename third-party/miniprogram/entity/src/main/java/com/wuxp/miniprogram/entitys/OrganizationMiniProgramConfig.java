package com.wuxp.miniprogram.entitys;

import com.levin.commons.dao.domain.support.AbstractBaseEntityObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @Classname OrganizationMiniProgramConfig
 * @Description 组织小程序发布配置对象
 * @Date 2020/3/2 16:36
 * @Created by 44487
 */



@Schema(description = "组织小程序发布配置")
@Entity
@Table(name = "t_organization_miniprogram_sysconfig", indexes = {
        @Index(columnList = "organization_id")
})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class OrganizationMiniProgramConfig extends AbstractBaseEntityObject<Long> {

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "组织编号")
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Schema(description = "组织代码")
    @Column(name = "organization_code", length = 32, nullable = false, unique = true)
    private String organizationCode;

    @Schema(description = "(小程序)代码库中的代码模版ID")
    @Column(name = "mini_program_template_id")
    private String miniProgramTemplateId;

    @Schema(description = "(小程序)第三方自定义的配置")
    @Column(name = "mini_program_ext_json")
    private String miniProgramExtJson;

    @Schema(description = "(小程序)代码版本号")
    @Column(name = "mini_program_user_version")
    private String miniProgramUserVersion;

    @Schema(description = "(小程序)代码描述")
    @Column(name = "mini_program_user_desc")
    private String miniProgramUserDesc;

    @Schema(description = "(小程序)类目对象配置")
    @Column(name = "mini_program_item_list")
    private String miniProgramItemList;

    @Schema(description = "（小程序）request 合法域名")
    @Column(name = "mini_program_requestdomain")
    private String miniProgramRequestdomain;

    @Schema(description = "(小程序)socket 合法域名")
    @Column(name = "mini_program_wsrequestdomain")
    private String miniProgramWsrequestdomain;

    @Schema(description = "(小程序)uploadFile合法域名")
    @Column(name = "mini_program_uploaddomain")
    private String miniProgramUploaddomain;

    @Schema(description = "(小程序)downloadFile 合法域名")
    @Column(name = "mini_program_downloaddomain")
    private String miniProgramDownloaddomain;

    @Schema(description = "(小程序)小程序业务域名")
    @Column(name = "mini_program_webviewdomain")
    private String miniProgramWebviewdomain;

    @Schema(description = "公司业务域名")
    @Column(name = "organization_domain_name")
    private String organizationDomainName;

}
