package com.oak.organization.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import com.levin.commons.dao.domain.support.AbstractTreeObject;
import com.levin.commons.service.domain.Desc;
import com.oak.organization.enums.ApprovalStatus;
import com.oak.organization.enums.OrganizationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Schema(description = "组织")
@Entity
@Table(name = "t_organization", indexes = {
        @Index(columnList = "code"),
        @Index(columnList = "name"),
        @Index(columnList = "id_path"),
        @Index(columnList = "pinyin_initials")
})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"parent", "children"})
@Accessors(chain = true)
public class Organization extends AbstractNamedEntityObject<Long> {


    private static final long serialVersionUID = 4994282131396859799L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "编号")
    @Column(name = "code", length = 32, nullable = false, unique = true)
    private String code;

    @Schema(description = "审核状态")
    @Column(name = "status", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Schema(description = "联系人")
    @Column(name = "contacts", length = 50)
    private String contacts;

    @Schema(description = "联系电话")
    @Column(name = "contacts_mobile_phone", length = 50)
    private String contactMobilePhone;

    @Schema(description = "LOGO")
    @Column(name = "logo")
    private String logo;

    @Schema(description = "区域ID")
    @Column(name = "area_id", length = 20)
    private String areaId;

    @Schema(description = "区域名称")
    @Column(name = "area_name")
    private String areaName;

    @Schema(description = "详细地址")
    @Column(name = "address")
    private String address;

    @Schema(description = "最后到期日期")
    @Column(name = "last_auth_end_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAuthEndDate;

    @Schema(description = "层级")//从0开始
    @Column(name = "`level`", nullable = false)
    private Integer level;

    @Schema(description = "层级路径")//格式：#level_index#level_index#
    @Column(name = "level_path", nullable = false)
    private String levelPath;

    @Schema(description = "机构拼音首字母")
    @Column(name = "pinyin_initials", length = 64, updatable = false)
    private String pinyinInitials;

    @Schema(description = "父ID")
    @Column(name = "parent_id")
    private Long parentId;

    @Schema(description = "上级组织")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Organization parent;

    @Schema(description = "下级组织")
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REMOVE})
    @OrderBy("orderCode DESC,name ASC")
    private Set<Organization> children;

    @Schema(description = "类型")
    @Column(name = "type", nullable = false, length = 16)
    private String type;

    @Schema(description = "ID路径")
    @Column(name = "id_path")
    private String idPath;

    @Schema(description = "是否删除")
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}
