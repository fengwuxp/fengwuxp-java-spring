package com.oak.admin.services.menu.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;
import com.oak.admin.enums.MenuIAction;

/**
 *  删除菜单
 *  2020-1-14 16:32:16
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
