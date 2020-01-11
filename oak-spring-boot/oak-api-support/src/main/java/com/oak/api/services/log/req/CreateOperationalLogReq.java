package com.oak.api.services.log.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 创建操作日志
 * 2019-6-24 10:17:24
 */
@Schema(description = "创建操作日志")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class CreateOperationalLogReq extends ApiBaseReq {

    @Schema(description = "操作类型")
    @NotNull
    @Size(max = 100)
    private String type;

    @Schema(description = "操作名称")
    private String action;

    @Schema(description = "操作内容")
    @NotNull
    @Size(max = 4000)
    private String content;

    @Schema(description = "请求参数")
    @Size(max = 4000)
    private String req;

    @Schema(description = "响应")
    @Size(max = 4000)
    private String resp;

    @Schema(description = "操作人id")
    @NotNull
    private Long operationalId;

    @Schema(description = "操作人名称")
    @NotNull
    private String operationalName;

    @Schema(description = "IP")
    @NotNull
    @Size(max = 15)
    private String ip;

    @Schema(description = "Url")
    private String url;

    @Schema(description = "来源编码")
    private String sourceCode;

}
