package com.oak.rbac.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "权限类型")
public enum PermissionType implements DescriptiveEnum {

    @Schema(description = "视图")
    VIEW,

    @Schema(description = "接口")
    API,

    @Schema(description = "数据")
    DATA;

}
