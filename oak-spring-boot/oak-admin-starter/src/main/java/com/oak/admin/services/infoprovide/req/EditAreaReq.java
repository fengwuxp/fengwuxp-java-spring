package com.oak.admin.services.infoprovide.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑Area
 *  2020-1-14 17:54:42
 */
@Schema(description = "编辑Area")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditAreaReq extends ApiBaseReq {

    @Schema(description = "地区编码")
    @NotNull
    @Eq(require = true)
    private String id;

    @Size(max = 50)
    @Schema(description = "地区父ID")
    @UpdateColumn
    private String parentId;

    @Size(max = 200)
    @Schema(description = "地区名称")
    @UpdateColumn
    private String name;

    @Size(max = 200)
    @Schema(description = "地区简称")
    @UpdateColumn
    private String shortName;

    @Size(max = 500)
    @Schema(description = "地区全称")
    @UpdateColumn
    private String fullName;

    @Schema(description = "经度")
    @UpdateColumn
    private Float longitude;

    @Schema(description = "纬度")
    @UpdateColumn
    private Float latitude;

    @Schema(description = "地区深度，从1开始")
    @UpdateColumn
    private Integer level;

    @Size(max = 20)
    @Schema(description = "层级排序")
    @UpdateColumn
    private String levelPath;

    @Schema(description = "排序")
    @UpdateColumn
    private Short sort;

    @Schema(description = "地区启用状态")
    @UpdateColumn
    private Boolean status;

    @Schema(description = "省直区县")
    @UpdateColumn
    private Boolean directly;

    @Schema(description = "第三方地区编码")
    @UpdateColumn
    private String thirdCode;

    @Schema(description = "城市的")
    @UpdateColumn
    private Boolean urban;

    public EditAreaReq() {
    }

    public EditAreaReq(String id) {
        this.id = id;
    }
}
