package com.oaknt.ncms.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 广告审核状态
 *
 * @author chenPC
 * @date 2016/5/18
 */
@Schema(description = "广告审核状态")
public enum AdvCheckState implements DescriptiveEnum {

    @Schema(description = "未审核的")
    UNAUDITED,  //未审核的

    @Schema(description = "审核通过")
    APPROVED,   //审核通过

    @Schema(description = "拒绝")
    REFUSE;     //拒绝


}
