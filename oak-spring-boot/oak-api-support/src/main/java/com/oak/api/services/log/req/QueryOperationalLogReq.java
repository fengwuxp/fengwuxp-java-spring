package com.oak.api.services.log.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 查询操作日志
 * 2019-6-24 10:17:24
 */
@Schema(description = "查询操作日志")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryOperationalLogReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "操作类型")
    private String type;

    @Schema(description = "操作名称")
    private String action;

    @Schema(description = "操作内容")
    private String content;

    @Schema(description = "请求参数")
    private String req;

    @Schema(description = "响应")
    private String resp;

    @Schema(description = "最小发生时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大发生时间")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "操作人id")
    @NotNull
    private Long operationalId;

    @Schema(description = "操作人名称")
    @NotNull
    private String operationalName;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "Url")
    private String url;

    @Schema(description = "来源编码")
    private String sourceCode;

    public QueryOperationalLogReq() {
    }

    public QueryOperationalLogReq(Long id) {
        this.id = id;
    }
}
