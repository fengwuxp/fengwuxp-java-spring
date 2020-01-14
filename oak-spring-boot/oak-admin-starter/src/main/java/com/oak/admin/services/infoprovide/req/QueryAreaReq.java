package com.oak.admin.services.infoprovide.req;

import com.levin.commons.dao.annotation.Ignore;
import com.levin.commons.dao.annotation.In;
import com.levin.commons.dao.annotation.Like;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.levin.commons.service.domain.Desc;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 查询Area
 * 2020-1-14 17:54:42
 */
@Schema(description = "查询Area")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryAreaReq extends ApiBaseQueryReq {

    @Desc(value = "地区编码")
    private String id;

    @Desc(value = "ids")
    @In("id")
    private String[] ids;

    @Desc(value = "地区名称")
    @Like
    private String name;

    @Desc(value = "地区父ID")
    private String parentId;

    @Desc(value = "地区深度，从1开始")
    private Integer level;

    @Desc(value = "地区层级")
    @In("level")
    private Integer[] levels;

    @Desc(value = "第三方地区编码")
    private String thirdCode;

    @Desc(value = "城市的")
    private Boolean urban;

    @Desc(value = "是否启用")
    private Boolean status;

    @Desc(value = "分割id")
    @Ignore
    private Boolean splitIdToIds = Boolean.FALSE;

    @Desc(value = "查询上级地区")
    @Fetch(value = "parent", condition = "#_val==true")
    private Boolean loadPrent = Boolean.TRUE;

    @Desc(value = "查询下级地区")
    @Fetch(value = "children", condition = "#_val==true")
    private Boolean loadChildren = Boolean.FALSE;

    public QueryAreaReq() {
    }

    public QueryAreaReq(String id) {
        this.id = id;
    }

    public QueryAreaReq(String id, Boolean fromCache) {
        this.id = id;
        if (fromCache != null) {
            setFromCache(fromCache);
        }
    }
}
