package com.oak.api.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Schema(name = "AppAuthAccount", description = "api接入账号")
@Entity
@Table(
        name = "t_app_auth_account",
        indexes = {
                @Index(columnList = "app_id")
        }
)
@Data
public class AppAuthAccount implements java.io.Serializable {

    private static final long serialVersionUID = 2096523073101917523L;

    @Id
    @Schema(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(name = "appId", description = "app接入id")
    @Column(name = "app_id", unique = true, nullable = false, length = 50)
    private String appId;

    @Schema(name = "appSecret", description = "接入密钥")
    @Column(name = "app_secret", nullable = false, length = 128)
    private String appSecret;

    @Schema(name = "name", description = "应用名称")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Schema(name = "type", description = "应用类型")
    @Column(name = "type", length = 32)
    private String type;

    @Schema(name = "enabled", description = "是否启用")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Schema(name = "deleted", description = "是否删除")
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Schema(name = "addTime", description = "创建日期")
    @Column(name = "add_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Schema(name = "updateTime", description = "更新日期")
    @Column(name = "update_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
