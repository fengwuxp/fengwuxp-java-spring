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
 * 创建Adv
 * 2020-2-10 18:55:01
 *
 * @author chenPC
 */
@Schema(description = "创建广告位")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class AddAdvPositionReq extends ApiBaseReq {

    @Schema(description = "广告位id")
    @NotNull
    private Long apId;

    @Schema(description = "广告内容描述")
    @NotNull
    private String title;

    @Schema(description = "广告内容")
    @NotNull
    @Size(max = 1024)
    private String content;

    @Schema(description = "广告URL")
    @Size(max = 200)
    private String url;

    @Schema(description = "广告开始时间")
    private Date startDate;

    @Schema(description = "广告结束时间")
    private Date endDate;

    @Schema(description = "幻灯片排序")
    @NotNull
    private Integer slideSort;

    @Schema(description = "广告拥有者")
    private Long memberId;

    @Schema(description = "会员用户名")
    @Size(max = 50)
    private String memberName;

    @Schema(description = "广告点击率")
    @NotNull
    private Integer clickNum;

    @Schema(description = "会员购买的广告是否通过审核")
    @NotNull
    private AdvCheckState state;

    @Schema(description = "购买方式")
    @NotNull
    @Size(max = 16)
    private String buyStyle;

    @Schema(description = "购买所支付的金币")
    @NotNull
    private Integer goldpay;

    @Schema(description = "归属地区id")
    @Size(max = 50)
    private String areaId;

    @Schema(description = "是否启用")
    @NotNull
    private Boolean enabled;


}
