package com.oak.organization.services.staff.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;
import com.oak.organization.enums.StaffAccountType;

/**
 *  删除员工
 *  2020-1-19 14:23:00
 */
@Schema(description = "删除员工")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteStaffReq extends ApiBaseReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ID集合")
    @In("id")
    private Long[] ids;

    public DeleteStaffReq() {
    }

    public DeleteStaffReq(Long id) {
        this.id = id;
    }

}
