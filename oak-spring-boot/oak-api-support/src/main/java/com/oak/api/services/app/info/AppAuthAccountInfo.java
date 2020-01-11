package com.oak.api.services.app.info;

import com.wuxp.api.signature.AppInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "查询接入账号信息")
public class AppAuthAccountInfo implements AppInfo, Serializable {

    private static final long serialVersionUID = 985141266202307566L;

    @Schema(name = "id")
    private Long id;

    @Schema(name = "appId", description = "app接入id")
    private String appId;

    @Schema(name = "appSecret", description = "接入密钥")
    private String appSecret;

    @Schema(name = "name", description = "应用名称")
    private String name;

    @Schema(name = "type", description = "应用类型")
    @Column(name = "type", length = 32)
    private String type;

    @Schema(name = "enabled", description = "是否启用")
    private Boolean enabled;

    @Schema(name = "deleted", description = "是否删除")
    private Boolean deleted;

    @Schema(name = "addTime", description = "创建日期")
    private Date addTime;

    @Schema(name = "updateTime", description = "更新日期")
    private Date updateTime;
}
