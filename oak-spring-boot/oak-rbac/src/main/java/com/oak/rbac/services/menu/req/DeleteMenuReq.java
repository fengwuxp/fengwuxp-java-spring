package com.oak.rbac.services.menu.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 删除菜单
 * 2020-1-14 16:32:16
 *
 * @author chenPC
 */
@Schema(description = "删除菜单")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMenuReq extends ApiBaseReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ID集合")
    @In("id")
    private Long[] ids;

    public DeleteMenuReq() {
    }

    public DeleteMenuReq(Long id) {
        this.id = id;
    }

}
