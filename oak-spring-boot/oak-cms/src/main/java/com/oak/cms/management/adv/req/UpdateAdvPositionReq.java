package com.oak.cms.management.adv.req;

import com.oak.api.model.ApiBaseReq;
import com.oaknt.ncms.enums.AdvCheckState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 添加广告
 *
 * @author chenPC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "修改广告位")
public class UpdateAdvPositionReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    private Long id;

    @Schema(description = "广告位id")
    private Long apId;

    @Schema(description = "广告内容描述")
    private String title;

    @Size(max = 1024)
    @Schema(description = "广告内容")
    private String content;

    @Size(max = 200)
    @Schema(description = "广告URL")
    private String url;

    @Schema(description = "广告开始时间")
    private Date startDate;

    @Schema(description = "广告结束时间")
    private Date endDate;

    @Schema(description = "幻灯片排序")
    private Integer slideSort;

    @Schema(description = "广告拥有者")
    private Long memberId;

    @Size(max = 50)
    @Schema(description = "会员用户名")
    private String memberName;

    @Schema(description = "广告点击率")
    private Integer clickNum;

    @Schema(description = "会员购买的广告是否通过审核")
    private AdvCheckState state;

    @Size(max = 16)
    @Schema(description = "购买方式")
    private String buyStyle;

    @Schema(description = "购买所支付的金币")
    private Integer goldpay;

    @Size(max = 50)
    @Schema(description = "归属地区id")
    private String areaId;

    @Schema(description = "是否启用")
    private Boolean enabled;

}
