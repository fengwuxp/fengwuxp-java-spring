package com.wuxp.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "查询排序类型")
public enum QuerySortType {

    @Schema(description = "降序")
    DESC,

    @Schema(description = "升序")
    ASC;


}
