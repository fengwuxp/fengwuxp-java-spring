package com.oak.organization.enums;


import com.levin.commons.service.domain.Desc;
import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description ="组织类型")
public enum OrganizationType implements DescriptiveEnum {

    @Desc("平台")
    PLATFORM,

    @Desc("服务商")
    OEM,

    @Desc("一级代理商")
    FIRST_AGENT,

    @Desc("二级代理商")
    SECOND_AGENT,

    @Desc("三级代理商")
    THIRD_AGETN,

    @Desc("商户")
    MERCHANT
}
