package com.oak.organization.enums;


import com.levin.commons.service.domain.Desc;
import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description ="组织类型")
public enum OrganizationType implements DescriptiveEnum {

    @Desc("平台")
    PLATFORM,

    @Desc("代理商")
    AGENT,

    @Desc("企业")
    ENTERPRISE
}
