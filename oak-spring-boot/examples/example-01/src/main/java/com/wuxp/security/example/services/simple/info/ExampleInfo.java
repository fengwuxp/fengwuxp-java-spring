package com.wuxp.security.example.services.simple.info;

import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Schema(description = "example对象")
@Data
@Accessors(chain = true)
@EqualsAndHashCode()
public class ExampleInfo implements Serializable {
    private static final long serialVersionUID = -3450789080833732451L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    @NotNull
    @Size(max = 16)
    private String name;

    @Schema(description = "年龄")
    @Size(max = 200)
    private Integer age;

    @Schema(description = "是否删除")
    @NotNull
    private Boolean deleted;

    @Schema(description = "创建日期")
    private Date addTime;

    @Schema(description = "创建日期")
    private Week week;

    @Schema(description = "例子id")
    private Long exampleId;
}
