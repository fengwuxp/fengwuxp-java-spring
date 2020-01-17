package com.oak.rbac.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "admin用户类型")
public enum AdminUserType implements DescriptiveEnum {

    @Schema(description = "root用户")
    ROOT,

    @Schema(description = "默认")
    DEFAULT,

    @Schema(description = "子账号")
    SUB
}
