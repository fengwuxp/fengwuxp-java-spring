package com.wuxp.miniprogram.services.wxopenconfig.dto;

import com.wuxp.miniprogram.enums.MiniProgramAuditResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import me.chanjar.weixin.open.bean.result.WxOpenMaQueryAuditResult;


/**
 * @Classname MiniProgramQueryAuditResult
 * @Description 小程序查询结果返回对象
 * @Date 2020/3/3 10:30
 * @Created by 44487
 */
@Schema(description = "小程序审核结果对象")
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class MiniProgramQueryAuditResultDto {

    @Schema(description = "审核编号信息")
    private Long auditId;

    @Schema(description = "审核状态")
    private MiniProgramAuditResultEnum status;

    @Schema(description = "审核失败原因")
    private String  reason;

    @Schema(description = "审核失败截图：当status=1，审核被拒绝时，会返回审核失败的小程序截图示例。 xxx丨yyy丨zzz是media_id可通过获取永久素材接口 拉取截图内容）")
    private String  screenShot;

    public static MiniProgramQueryAuditResultDto convertFrom (WxOpenMaQueryAuditResult auditResult ){

        MiniProgramQueryAuditResultDto miniProgramQueryAuditResultDto = new MiniProgramQueryAuditResultDto();
        miniProgramQueryAuditResultDto.setAuditId(auditResult.getAuditId())
                .setReason(auditResult.getReason())
                .setScreenShot(auditResult.getScreenShot())
                .setStatus(MiniProgramAuditResultEnum.getStatus(auditResult.getStatus()));

        return miniProgramQueryAuditResultDto;
    }



}
