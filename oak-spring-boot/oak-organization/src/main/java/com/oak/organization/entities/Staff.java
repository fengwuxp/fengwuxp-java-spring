package com.oak.organization.entities;

import com.levin.commons.dao.domain.support.AbstractNamedEntityObject;
import com.levin.commons.service.domain.Desc;
import com.oak.organization.enums.StaffAccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Schema(description = "员工")
@Entity
@Table(name = "t_organization_staff", indexes = {
        @Index(columnList = "user_name"),
        @Index(columnList = "department_id"),
        @Index(columnList = "organization_code"),
})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Staff extends AbstractNamedEntityObject<Long> {

    private static final long serialVersionUID = 571113416942500918L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Schema(description = "用户名称")
    @Column(name = "user_name", length = 32, nullable = false, unique = true)
    private String userName;

    @Schema(description = "部门ID")
    @Column(name = "department_id")
    private Long departmentId;

    @Schema(description = "部门")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;

    @Schema(description = "组织id")
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Schema(description = "归属组织")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private Organization organization;

    @Schema(description = "组织编码")
    @Column(name = "organization_code", nullable = false, length = 20)
    private String organizationCode;

    @Schema(description = "员工头像")
    @Column(name = "avatar_url", length = 128)
    private String avatarUrl;

    @Schema(description = "员工手机号")
    @Column(name = "mobile_phone", length = 12)
    private String mobilePhone;

    @Schema(description = "账号类型")
    @Column(name = "account_type", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private StaffAccountType accountType;

    @Schema(description = "创建者")
    @Column(name = "creator_id")
    private Long creatorId;


}

