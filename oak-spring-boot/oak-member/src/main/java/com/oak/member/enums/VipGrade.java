package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description =  "用户会员vip级别")
public enum VipGrade implements DescriptiveEnum {

    //不享有vip特权
    @Schema(description =  "普通用户")
    M_COMMON,

    @Schema(description =  "VIP一")
    M_VIP_1,

    @Schema(description =  "VIP二")
    M_VIP_2,

    @Schema(description =  "VIP三")
    M_VIP_3,

    @Schema(description =  "VIP四")
    M_VIP_4,

    @Schema(description =  "VIP五")
    M_VIP_5;

    /*是否是vip用户*/
    public Boolean isVip() {
        return !this.equals(M_COMMON);
    }


}
