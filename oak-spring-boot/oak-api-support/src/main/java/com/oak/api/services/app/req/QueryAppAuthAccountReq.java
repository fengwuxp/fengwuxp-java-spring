package com.oak.api.services.app.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询接入账号")
public class QueryAppAuthAccountReq extends ApiBaseQueryReq {

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

    public QueryAppAuthAccountReq() {
    }

    public QueryAppAuthAccountReq(String appId) {
        this.appId = appId;
    }
}
