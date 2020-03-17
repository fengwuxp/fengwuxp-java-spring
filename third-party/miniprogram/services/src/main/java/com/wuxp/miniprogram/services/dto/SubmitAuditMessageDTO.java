package com.wuxp.miniprogram.services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import me.chanjar.weixin.open.bean.ma.WxOpenMaSubmitAudit;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Classname SubmitAuditMessageDTO
 * @Description TODO
 * @Date 2020/3/17 19:18
 * @Created by 44487
 */

@Schema(description = "提交审核代码")
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class SubmitAuditMessageDTO {

    @Schema(
            description = "小程序appid",
            name = "appid"
    )
    @NotNull
    @NotBlank
    private String appid;
    @Schema(
            description = "审核项列表",
            name = "itemList"
    )
    @NotNull
    @Min(1L)
    @Max(5L)
    private List<WxOpenMaSubmitAudit> itemList;
    @Schema(
            description = "反馈内容，至多 200 字",
            name = "feedback_info"
    )
    private String feedbackInfo;
    @Schema(
            description = "用 | 分割的 media_id 列表，至多 5 张图片, 可以通过新增临时素材接口上传而得到",
            name = "feedback_stuff"
    )
    private String feedbackStuff;
}
