package com.oak.organization.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Schema(description = "组织扩展信息")
@Entity
@Table(name = "t_organization_extended_info")
@Data
@Accessors(chain = true)
public class OrganizationExtendedInfo implements Serializable {


    private static final long serialVersionUID = -2530325182810446731L;
    @Schema(description = "机构ID")
    @Id
    protected Long id;

    @Schema(description = "LOGO")
    @Column(name = "logo")
    private String logo;

    @Schema(description = "版权")
    @Column(name = "copyright")
    private String copyright;

    @Schema(description = "技术支持")
    @Column(name = "technical_support")
    private String technicalSupport;

    @Schema(description = "客服热线")
    @Column(name = "service_tel")
    private String serviceTel;

    @Schema(description = "绑定域名")
    @Column(name = "domain")
    private String domain;

    @Schema(description = "浏览器图标")
    @Column(name = "favicon")
    private String favicon;


}
