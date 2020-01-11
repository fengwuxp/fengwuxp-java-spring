package com.oak.api.services.system.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 查找设置分组
 * 2018-3-30 21:30:58
 */
@Schema(description = "查找设置分组")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FindSettingGroupReq extends ApiBaseReq {

    @Schema(description = "分组名称")
    @NotNull
    private String name;

}
