package com.oak.api.model;

import com.levin.commons.dao.annotation.Ignore;
import com.wuxp.api.model.QuerySortType;
import com.wuxp.api.model.QueryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "ApiBaseQueryReq", description = "基础的查询请求对象")
@Validated
public class ApiBaseQueryReq extends ApiBaseReq {

    // alias
    public static final String TABLE_ALIAS="e";

    private static final Integer MAX_QUERY_SIZE = 1000;

    @Schema(title = "queryType", description = "查询类型,默认查询结果集")
    @NotNull(message = "queryType不能为空")
    @Ignore
    protected QueryType queryType = QueryType.QUERY_RESET;

    @Schema(title = "queryType", description = "查询页码，从1开始")
    @NotNull(message = "queryPag不能为空")
    @Ignore
    protected Integer queryPage = 1;

    @Schema(title = "queryType", description = "查询大小，默认20")
    @NotNull(message = "querySize不能为空")
    @Ignore
    protected Integer querySize = 20;

    @Schema(description = "排序字段")
    @Ignore
    private String[] orderBy;

    @Schema(description = "排序类型，\"asc\"升序，\"desc\"降序，必须与orderBy一一对应")
    @Ignore
    private String[] orderType;

    @Schema(description = "是否使用缓存")
    @Ignore
    private Boolean fromCache;

    public void setQuerySize(Integer querySize) {
        if (querySize == null || querySize <= MAX_QUERY_SIZE && querySize != -1) {
            this.querySize = querySize;
        } else {
            this.querySize = MAX_QUERY_SIZE;
        }
    }

    public boolean isOrderBy() {
        return this.getOrderBy() != null && this.getOrderBy().length > 0 && this.getOrderType() != null && this.getOrderType().length > 0 && this.getOrderBy().length == this.getOrderType().length && this.getOrderBy()[0] != null && !this.getOrderBy()[0].isEmpty() && this.getOrderType()[0] != null && !this.getOrderType()[0].isEmpty();
    }

    public void setDefaultOrderById() {
        this.setDefOrderBy("id", QuerySortType.DESC.name());
    }

    public void setDefaultOrderById(QuerySortType sortType) {
        this.setDefOrderBy("id", sortType.name());
    }

    public void setDefOrderBy(String... orderStr) {
        this.orderBy = new String[orderStr.length / 2];
        this.orderType = new String[orderStr.length / 2];

        for (int i = 0; i < orderStr.length / 2; ++i) {
            this.orderBy[i] = orderStr[i * 2];
            this.orderType[i] = orderStr[i * 2 + 1];
        }
    }


    public String[] getOrderByArr() {
        return this.getOrderByArr(null);
    }

    public String[] getOrderByArr(String alias) {
        if (!this.isOrderBy()) {
            return new String[0];
        } else {
            if (alias == null) {
                alias = "";
            } else {
                alias = alias + ".";
            }

            String[] arr = new String[this.orderBy.length];

            for (int i = 0; i < this.orderBy.length; ++i) {
                arr[i] = alias + this.orderBy[i] + " " + this.orderType[i];
            }

            return arr;
        }
    }

    public String getJoin(String alias, String... joins) {
        if (joins != null && joins.length != 0) {
            if (alias == null) {
                alias = "";
            } else {
                alias = alias + ".";
            }
            StringBuilder builder = new StringBuilder();

            for (String s : joins) {
                if (StringUtils.hasText(s)) {
                    builder.append(" left join fetch ")
                            .append(alias)
                            .append(s);
                }
            }

            return builder.toString();
        } else {
            return "";
        }
    }


}
