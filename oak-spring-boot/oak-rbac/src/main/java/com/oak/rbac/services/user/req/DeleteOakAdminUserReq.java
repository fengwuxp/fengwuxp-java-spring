package com.oak.rbac.services.user.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;

/**
 *  删除管理员用户
 *  2020-1-16 18:28:37
 */
@Schema(description = "删除管理员用户")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteOakAdminUserReq extends ApiBaseReq {

    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "管理员ID集合")
    @In("id")
    private Long[] ids;

    public DeleteOakAdminUserReq() {
    }

    public DeleteOakAdminUserReq(Long id) {
        this.id = id;
    }

}
