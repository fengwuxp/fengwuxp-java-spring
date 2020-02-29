package com.wuxp.security.example.services.simple.info;

import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* example例子
* 2020-2-16 10:20:18
*/
@Schema(description ="example例子")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class ExampleEntityInfo implements Serializable {

        @Schema(description = "id")
        private Long id;

        @Schema(description = "name")
        private String name;

        @Schema(description = "年龄")
        private Integer age;

        @Schema(description = "头像")
        private String avatarUrl;

        @Schema(description = "账户余额")
        private Integer money;

        @Schema(description = "生日")
        private Date birthday;

        @Schema(description = "星期")
        private Week week;

        @Schema(description = "例子id")
        private Long exampleId;

        @Schema(description = "是否删除")
        private Boolean deleted;

        @Schema(description = "排序代码")
        private Integer orderCode;

        @Schema(description = "是否允许")
        private Boolean enable;

        @Schema(description = "是否可编辑")
        private Boolean editable;

        @Schema(description = "创建时间")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;

        @Schema(description = "备注")
        private String remark;


}
