package com.oak.api.model;

import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.beans.Transient;
import java.util.List;

@Data
@Schema(description = "分页对象")
public class PageInfo<T> implements Pagination<T> {

    @Schema(description = "总数")
    private long total;

    @Schema(description = "查询类型")
    private QueryType queryType;

    @Schema(description = "查询结果列表")
    private List<T> records;

    @Schema(description = "查询页码")
    private int queryPage;

    @Schema(description = "查询大小")
    private int querySize;

    private PageInfo() {
    }

    public static <T> PageInfo<T> newInstance(int queryPage, int querySize, QueryType queryType) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setQueryPage(queryPage);
        pageInfo.setQuerySize(querySize);
        pageInfo.setQueryType(queryType);
        return pageInfo;
    }

    public static <T> PageInfo<T> newInstance(ApiBaseQueryReq req) {

        return newInstance(req.getQueryPage(), req.getQuerySize(), req.getQueryType());
    }

    @Transient
    @Override
    public T getFirst() {
        List<T> records = this.records;
        if (records == null) {
            return null;
        }

        if (records.isEmpty()) {
            return null;
        }
        return records.get(0);
    }

    @Override
    public boolean isEmpty() {
        List<T> records = this.records;
        if (records == null) {
            return true;
        }

        return records.isEmpty();
    }
}
