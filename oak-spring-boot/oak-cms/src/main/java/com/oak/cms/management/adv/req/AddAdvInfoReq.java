package com.oak.cms.management.adv.req;

import com.oak.api.entities.system.Area;
import com.oak.api.model.ApiBaseReq;
import com.oaknt.ncms.enums.AdvCheckState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
@Schema(description = "添加广告")
public class AddAdvInfoReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    private Long id;

    @Schema(description = "广告位ID")
    @NotNull
    private Long apId;

    @Schema(description = "广告标题")
    @NotNull
    private String title;

    @Schema(description = "广告内容")
    private String content;

    @Schema(description = "URL")
    private String url;

    @Schema(description = "有效时间-开始")
    private Date startDate;

    @Schema(description = "有效时间-结束")
    private Date endDate;

    @Schema(description = "幻灯片排序")
    private Short slideSort;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "会员名")
    private String memberName;

    @Schema(description = "点击率")
    private Integer clickNum;

    @Schema(description = "购买广告审核状态")
    private AdvCheckState state;

    @Schema(description = "购买方式")
    private String buyStyle;

    @Schema(description = "购买支付的金币")
    private Integer goldpay;

    @Schema(description = "地区")
    private Area area;

}
