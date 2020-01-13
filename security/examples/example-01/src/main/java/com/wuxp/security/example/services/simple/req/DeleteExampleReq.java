package com.wuxp.security.example.services.simple.req;


import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

@Schema(description = "编辑example的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DeleteExampleReq extends ApiBaseReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    @Size(max = 16)
    private String name;

}
