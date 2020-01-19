package com.oak.organization.services.staff.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;

import com.oak.organization.enums.StaffAccountType;


/**
 *  查找员工
 *  2020-1-19 14:23:00
 */
@Schema(description = "查找员工")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindStaffReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

}
