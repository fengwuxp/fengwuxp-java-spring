package com.oak.rbac.management.menu.req;

import com.levin.commons.dao.annotation.Like;
import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.entities.E_Menu;
import com.oak.rbac.enums.MenuShowType;
import com.oak.rbac.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 菜单查询
 *
 * @author chenPC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "菜单查询")
public class QueryMenuInfoReq extends ApiBaseReq {


    @Schema(description = "菜单类型")
    private MenuType type;

    @Schema(description = "菜单的显示类型")
    private MenuShowType showType;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "模糊搜索名称")
    @Like(value = E_Menu.name)
    private String likeName;

}
