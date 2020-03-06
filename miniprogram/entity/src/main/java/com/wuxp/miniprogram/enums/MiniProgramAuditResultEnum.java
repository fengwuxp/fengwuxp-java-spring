package com.wuxp.miniprogram.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Classname MiniProgramAuditResultEnum
 * @Description 小程序审核结果状态
 * @Date 2020/3/3 10:39
 * @Created by 44487
 */
@Schema(description = "小程序审核结果状态")
public enum MiniProgramAuditResultEnum implements DescriptiveEnum {

    @Schema(description = "审核通过")
    SUCCESS,

    @Schema(description = "审核中")
    AUDIT,

    @Schema(description = "审核失败")
    FAIL;

    /**
     * 审核状态:2-审核中,0-审核通过,1-审核失败.
     */
    public static  MiniProgramAuditResultEnum getStatus( Integer number ){

        if( 2 == number ){
            return MiniProgramAuditResultEnum.AUDIT;
        }else if( 0 == number ){
            return MiniProgramAuditResultEnum.SUCCESS;
        }else {
            return MiniProgramAuditResultEnum.FAIL;
        }
    }

}
