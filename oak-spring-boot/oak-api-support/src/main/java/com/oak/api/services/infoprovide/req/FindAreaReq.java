package com.oak.api.services.infoprovide.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;


/**
 * 查找Area
 * 2020-1-14 17:54:42
 */
@Schema(description = "查找Area")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class FindAreaReq extends ApiBaseReq {

    @Schema(description = "区域编码")
    private String areaCode;

    @Schema(description = "第三方区域编码")
    private String thirdCode;

}
