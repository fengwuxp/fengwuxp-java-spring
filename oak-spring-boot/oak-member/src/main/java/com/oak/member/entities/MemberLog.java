package com.oak.member.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Schema(description = "会员日志")
@Table(name = "t_member_log",indexes = {
        @Index(columnList = "member_id")
})
@Data
public class MemberLog implements java.io.Serializable {

    private static final long serialVersionUID = -1916404656887028631L;

    @Schema(description = "id")
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Schema(description = "会员ID")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "会员显示名称")
    @Column(name = "show_name", nullable = false, length = 32)
    private String showName;

    @Schema(description = "操作类型")
    @Column(name = "type", nullable = false, length = 16)
    private String type;

    @Schema(description = "操作日期")
    @Column(name = "operating_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date operatingTime;

    @Schema(description = "IP")
    @Column(name = "ip", length = 32)
    private String ip;

    @Schema(description = "操作者")
    @Column(name = "operator", nullable = false, length = 32)
    private String operator;

    @Schema(description = "操作描述")
    @Column(name = "description", nullable = false, length = 256)
    private String description;


}
