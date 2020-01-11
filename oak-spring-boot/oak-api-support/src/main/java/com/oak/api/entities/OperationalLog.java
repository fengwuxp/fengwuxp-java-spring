package com.oak.api.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Schema(description = "操作日志")
@Table(
        name = "t_operational_log",
        indexes = {
                @Index(columnList = "type"),
                @Index(columnList = "operational_id"),
                @Index(columnList = "operational_name"),
        }
)
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class OperationalLog implements Serializable {


    private static final long serialVersionUID = -3309234304685373187L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Schema(description = "操作类型")
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Schema(description = "操作名称")
    @Column(name = "action")
    private String action;

    @Schema(description = "操作内容")
    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @Schema(description = "请求参数")
    @Column(name = "req")
    @Lob
    private String req;

    @Schema(description = "响应")
    @Column(name = "resp")
    @Lob
    private String resp;

    @Schema(description = "创建时间")
    @Column(name = "create_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "操作人id")
    @Column(name = "operational_id", nullable = false)
    private Long operationalId;

    @Schema(description = "操作人名称")
    @Column(name = "operational_name", nullable = false)
    private String operationalName;

    @Schema(description = "IP")
    @Column(name = "ip", nullable = false, length = 16)
    private String ip;

    @Schema(description = "Url")
    @Column(name = "url")
    private String url;

    @Schema(description = "来源编码")
    @Column(name = "source_code")
    private String sourceCode;

}
