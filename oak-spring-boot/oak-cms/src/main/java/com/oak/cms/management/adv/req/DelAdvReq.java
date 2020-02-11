package com.oak.cms.management.adv.req;

/**
 * 操作--启用状态
 *
 * @author chenPC
 */

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 是否启用
 *
 * @author chenPC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "启用状态")
public class DelAdvReq extends ApiBaseReq {

    @Schema(description = "广告id")
    @NotNull
    @Eq(require = true)
    private Long id;


}
