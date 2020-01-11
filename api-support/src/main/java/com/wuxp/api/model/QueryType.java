package com.wuxp.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "查询类型")
public enum QueryType {

    @Schema(description = "查询总数")
    QUERY_NUM,

    @Schema(description = "查询结果集")
    QUERY_RESET,

    @Schema(description = "查询总数和结果集")
    QUERY_BOTH;

    public boolean isQueryNum() {
        return this.equals(QUERY_NUM) || this.equals(QUERY_BOTH);
    }

    public boolean isQueryResult() {
        return this.equals(QUERY_RESET) || this.equals(QUERY_BOTH);
    }
}
