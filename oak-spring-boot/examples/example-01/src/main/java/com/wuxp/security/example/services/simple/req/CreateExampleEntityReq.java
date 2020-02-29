package com.wuxp.security.example.services.simple.req;

import com.oak.api.model.ApiBaseReq;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 创建ExampleEntity
 * 2020-2-16 10:20:18
 */
@Schema(description = "创建CreateExampleEntityReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateExampleEntityReq extends ApiBaseReq {

    @Schema(description = "name")
    @NotNull
    @Size(min = 0, max = 16)
    private String name;

    @Schema(description = "年龄")
    @Max(value = 200)
    private Integer age;

    @Schema(description = "头像")
    private String avatarUrl;

    @Schema(description = "账户余额")
    private Integer money;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "星期")
    @NotNull
    private Week week;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

}
