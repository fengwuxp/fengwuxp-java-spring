package com.wuxp.security.example.services.simple.req;

import com.wuxp.api.ApiBaseReq;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Schema(description = "创建example的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateExampleReq extends ApiBaseReq {

    @Schema(description = "name")
    @NotNull
    @Size(max = 16)
    private String name;

    @Schema(description = "年龄")
    @NotNull
    @Size(max = 200)
    private Integer age;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "创建日期")
    private Date addTime;

    @Schema(description = "创建日期")
    private Week week;
}
