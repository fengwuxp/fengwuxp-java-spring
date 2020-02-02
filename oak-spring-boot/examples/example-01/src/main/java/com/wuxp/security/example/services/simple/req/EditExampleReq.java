package com.wuxp.security.example.services.simple.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;

import com.oak.api.model.ApiBaseReq;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Schema(description = "编辑example的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class EditExampleReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    private Long id;

    @Schema(description = "name")
    @Size(max = 16)
    @UpdateColumn
    private String name;

    @Schema(description = "年龄")
    @Size(max = 200)
    @UpdateColumn
    private Integer age;

    @Schema(description = "是否删除")
    @UpdateColumn
    private Boolean deleted;

    @Schema(description = "创建日期")
    @UpdateColumn
    private Date addTime;

    @Schema(description = "创建日期")
    @UpdateColumn
    private Week week;
}
