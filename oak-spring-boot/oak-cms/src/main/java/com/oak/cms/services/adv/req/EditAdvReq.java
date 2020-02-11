package com.oak.cms.services.adv.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import com.oaknt.ncms.enums.AdvCheckState;


/**
 *  编辑广告信息
 *  2020-2-10 18:55:01
 */
@Schema(description = "编辑广告信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditAdvReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "广告位id")
    @UpdateColumn
    private Long apId;

    @Schema(description = "广告内容描述")
    @UpdateColumn
    private String title;

    @Size(max = 1024)
    @Schema(description = "广告内容")
    @UpdateColumn
    private String content;

    @Size(max = 200)
    @Schema(description = "广告URL")
    @UpdateColumn
    private String url;

    @Schema(description = "广告开始时间")
    @UpdateColumn
    private Date startDate;

    @Schema(description = "广告结束时间")
    @UpdateColumn
    private Date endDate;

    @Schema(description = "幻灯片排序")
    @UpdateColumn
    private Integer slideSort;

    @Schema(description = "广告拥有者")
    @UpdateColumn
    private Long memberId;

    @Size(max = 50)
    @Schema(description = "会员用户名")
    @UpdateColumn
    private String memberName;

    @Schema(description = "广告点击率")
    @UpdateColumn
    private Integer clickNum;

    @Schema(description = "会员购买的广告是否通过审核")
    @UpdateColumn
    private AdvCheckState state;

    @Size(max = 16)
    @Schema(description = "购买方式")
    @UpdateColumn
    private String buyStyle;

    @Schema(description = "购买所支付的金币")
    @UpdateColumn
    private Integer goldpay;

    @Size(max = 50)
    @Schema(description = "归属地区id")
    @UpdateColumn
    private String areaId;

    @Schema(description = "是否启用")
    @UpdateColumn
    private Boolean enabled;

    @Schema(description = "创建日期")
    @UpdateColumn
    private Date crateTime;

    public EditAdvReq() {
    }

    public EditAdvReq(Long id) {
        this.id = id;
    }
}
