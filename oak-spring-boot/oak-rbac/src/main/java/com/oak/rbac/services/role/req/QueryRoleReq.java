package com.oak.rbac.services.role.req;

import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询角色")
public class QueryRoleReq extends ApiBaseQueryReq {

    @Schema(name = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "名称模糊查询")
    private String likeName;

    @Schema(description = "是否加载权限列表")
    @Fetch(value = "permissions", condition = "#_val==true")
    private Boolean fetchPermission;

    @Schema(description = "是否启用")
    protected Boolean enable;

    public QueryRoleReq() {
    }

    public QueryRoleReq(Long id) {
        this.id = id;
    }

    public QueryRoleReq(String name) {
        this.name = name;
    }
}
