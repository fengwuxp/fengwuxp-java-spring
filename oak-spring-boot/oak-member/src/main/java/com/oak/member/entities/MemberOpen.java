package com.oak.member.entities;

import com.oak.member.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Schema(description = "会员绑定信息")
@Entity
@Table(name = "t_member_open", indexes = {
        @Index(columnList = "member_id")
})
@Data
public class MemberOpen implements java.io.Serializable {

    private static final long serialVersionUID = -6109256167636219636L;
    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "会员ID")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "会员信息")
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Schema(description = "平台类型")
    @Column(name = "open_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OpenType openType;

    @Schema(description = "OPENID")
    @Column(name = "open_id")
    private String openId;

    @Schema(description = "UNIONID")
    @Column(name = "union_id")
    private String unionId;

    @Schema(description = "绑定信息")
    @Column(name = "bind_info", length = 500)
    private String bindInfo;

    @Schema(description = "到期日期")
    @Column(name = "expiration_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Schema(description = "是否关注")
    @Column(name = "subscribe")
    private Boolean subscribe;

    @Schema(description = "登记日期")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "变更日期")
    @Column(name = "update_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

}
