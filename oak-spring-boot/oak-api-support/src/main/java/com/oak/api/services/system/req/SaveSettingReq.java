package com.oak.api.services.system.req;

import com.oak.api.enums.SettingValueType;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

;


@Schema(description ="保存系统设置")
@Data
public class SaveSettingReq extends ApiBaseReq {

    private static final long serialVersionUID = 9216037239323647271L;

    @Schema(description ="配置名称")
    @NotNull
    private String name;

    @Schema(description ="配置值")
    private String value;

    @Schema(description ="配置标题名称")
    private String label;

    @Schema(description = "配置名称后缀")
    private String labelSuffix;

    @Schema(description ="配置描述")
    private String description;

    @Schema(description ="配置类型")
    private SettingValueType type = SettingValueType.TEXT;

    @Schema(description ="是否显示")
    private Boolean show;

    @Schema(description  = "允许客户端获取")
    private Boolean open = true;

    @Schema(description ="可选值（多个以#分隔）")
    private String items;

    @Schema(description ="值正则式")
    private String regex;

    @Schema(description ="分组名称")
    private String groupName;

    @Schema(description  = "排序")
    private Integer orderIndex;

}
