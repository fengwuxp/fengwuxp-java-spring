package com.oak.api.services.system.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 创建设置分组
 */
@Schema(description  = "创建设置分组")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateSettingGroupReq extends ApiBaseReq {

    @Schema(description  = "分组名称")
    @NotNull
    private String name;

    @Schema(description  = "是否显示")
    private Boolean show;

    @Schema(description  = "排序")
    private Integer orderIndex;

}
