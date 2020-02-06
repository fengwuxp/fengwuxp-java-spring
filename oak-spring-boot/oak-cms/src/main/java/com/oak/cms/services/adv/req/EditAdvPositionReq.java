package com.oak.cms.services.adv.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.AdvDisplayType;
import com.oak.cms.enums.AdvType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 编辑广告位信息
 * 2020-2-6 16:50:22
 *
 * @author chenPC
 */
@Schema(description = "编辑广告位信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditAdvPositionReq extends ApiBaseReq {

    @Schema(description = "广告位置id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 32)
    @Schema(description = "引用编码")
    @UpdateColumn
    private String code;

    @Size(max = 64)
    @Schema(description = "广告位置名称")
    @UpdateColumn
    private String name;

    @Schema(description = "广告位简介")
    @UpdateColumn
    private String description;

    @Schema(description = "广告类别")
    @UpdateColumn
    private AdvType type;

    @Schema(description = "广告展示方式")
    @UpdateColumn
    private AdvDisplayType displayType;

    @Schema(description = "广告位是否启用")
    @UpdateColumn
    private Boolean enabled;

    @Schema(description = "广告位宽度")
    @UpdateColumn
    private Integer width;

    @Schema(description = "广告位高度")
    @UpdateColumn
    private Integer height;

    @Schema(description = "广告位单价")
    @UpdateColumn
    private Integer price;

    @Schema(description = "拥有的广告数")
    @UpdateColumn
    private Integer num;

    @Schema(description = "广告位点击率")
    @UpdateColumn
    private Integer clickNum;

    @Size(max = 512)
    @Schema(description = "广告位默认内容")
    @UpdateColumn
    private String defaultContent;

    @Size(max = 50)
    @Schema(description = "归属地区id")
    @UpdateColumn
    private String areaId;

    @Size(max = 64)
    @Schema(description = "广告规格")
    @UpdateColumn
    private String spec;

    @Schema(description = "创建日期")
    @UpdateColumn
    private Date crateTime;

    public EditAdvPositionReq() {
    }

    public EditAdvPositionReq(Long id) {
        this.id = id;
    }
}
