package com.oak.api.services.system.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 删除设置分组
 */
@Schema(description  = "删除设置分组")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelSettingGroupReq extends ApiBaseReq {

    @Schema(description  = "分组名称")
    private String name;

    @Schema(description  = "分组名称集合")
    @In("name")
    private String[] names;

    public DelSettingGroupReq(String name) {
        this.name = name;
    }

}
