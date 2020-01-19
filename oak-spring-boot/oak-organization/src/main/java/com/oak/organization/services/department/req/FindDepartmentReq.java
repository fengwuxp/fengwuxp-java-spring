package com.oak.organization.services.department.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;



/**
 *  查找部门
 *  2020-1-19 14:02:11
 */
@Schema(description = "查找部门")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindDepartmentReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

}
