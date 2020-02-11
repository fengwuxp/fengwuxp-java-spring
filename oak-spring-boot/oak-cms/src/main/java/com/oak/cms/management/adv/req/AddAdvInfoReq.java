package com.oak.cms.management.adv.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Schema(description = "标题")
    @NotNull
    private String title;

    @Schema(description = "投放对象")
    private Long memberId;

    @Schema(description = "投放范围")
    @NotNull
    private String areaId;

    @Schema(description = "展示图片")
    private String content;

    @Schema(description = "广告图片链接")
    private String url;

    @Schema(description = "有效日期-开始")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Schema(description = "有效日期-结束")
    @Temporal(TemporalType.DATE)
    private Date endDate;

}
