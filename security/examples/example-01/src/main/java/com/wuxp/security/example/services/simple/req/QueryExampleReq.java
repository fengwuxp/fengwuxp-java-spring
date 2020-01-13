package com.wuxp.security.example.services.simple.req;


import com.oak.springboot.model.ApiBaseQueryReq;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Schema(description = "查询example的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class QueryExampleReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    private String name;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "创建日期")
    private Week week;

    public QueryExampleReq() {
    }

    public QueryExampleReq(Long id) {
        this.id = id;
    }
}
