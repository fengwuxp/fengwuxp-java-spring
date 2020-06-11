package com.wuxp.api.model;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wxup
 */

@Schema(description = "查询排序类型")
public enum QueryOrderType implements DescriptiveEnum {

    @Schema(description = "降序")
    DESC,

    @Schema(description = "升序")
    ASC;


}
