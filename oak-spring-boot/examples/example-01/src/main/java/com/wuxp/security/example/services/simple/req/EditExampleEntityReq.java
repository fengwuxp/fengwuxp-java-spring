package com.wuxp.security.example.services.simple.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import com.wuxp.security.example.enums.Week;


/**
 *  编辑example例子
 *  2020-2-16 10:20:18
 */
@Schema(description = "编辑example例子")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditExampleEntityReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(min = 0 , max = 16)
    @Schema(description = "name")
    @UpdateColumn
    private String name;

    @Size(min = 0 , max = 200)
    @Schema(description = "年龄")
    @UpdateColumn
    private Integer age;

    @Schema(description = "头像")
    @UpdateColumn
    private String avatarUrl;

    @Schema(description = "账户余额")
    @UpdateColumn
    private Integer money;

    @Schema(description = "生日")
    @UpdateColumn
    private Date birthday;

    @Schema(description = "星期")
    @UpdateColumn
    private Week week;

    @Schema(description = "例子id")
    @UpdateColumn
    private Long exampleId;

    @Schema(description = "是否删除")
    @UpdateColumn
    private Boolean deleted;

    @Schema(description = "排序代码")
    @UpdateColumn
    private Integer orderCode;

    @Schema(description = "是否允许")
    @UpdateColumn
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @UpdateColumn
    private Boolean editable;

    @Schema(description = "更新时间")
    @UpdateColumn
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @UpdateColumn
    private String remark;

    public EditExampleEntityReq() {
    }

    public EditExampleEntityReq(Long id) {
        this.id = id;
    }
}
