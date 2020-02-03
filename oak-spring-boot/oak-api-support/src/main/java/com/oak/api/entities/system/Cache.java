package com.oak.api.entities.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Schema(description = "缓存")
@Entity
@Table(name = "t_system_cache")
@Data
@EqualsAndHashCode(of = {"name"})
public class Cache implements java.io.Serializable {

    private static final long serialVersionUID = -8635388281503865893L;

    @Schema(description = "配置名称")
    @Id
    @Column(name = "`name`", length = 80)
    private String name;

    @Schema(description = "配置值")
    @Column(name = "`value`",nullable = false)
    @Lob
    private String value;


    @Schema(description = "更新时间")
    @Column(name = "update_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Schema(description = "到期时间")
    @Column(name = "expiration_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;

    public Cache() {
    }

    public Cache(String name, String value, Date updateTime) {
        this.name = name;
        this.value = value;
        this.updateTime = updateTime;
    }

    public Cache(String name, String value, Date updateTime, Date expirationTime) {
        this.name = name;
        this.value = value;
        this.updateTime = updateTime;
        this.expirationTime = expirationTime;
    }
}
