package com.oak.api.services.log.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "操作日志信息")
public class OperationalLogInfo implements Serializable {

    private static final long serialVersionUID = -3880775051518733317L;
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

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "操作人id")
    private Long operationalId;

    @Schema(description = "操作人名称")
    private String operationalName;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "Url")
    private String url;

    @Schema(description = "来源编码")
    private String sourceCode;

}

