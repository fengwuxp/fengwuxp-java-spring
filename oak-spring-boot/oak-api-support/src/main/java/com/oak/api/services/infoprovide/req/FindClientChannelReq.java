package com.oak.api.services.infoprovide.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;


@Schema(description = "查找渠道")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class FindClientChannelReq extends ApiBaseReq {

    @Schema(description = "渠道号")
    private String code;

}
