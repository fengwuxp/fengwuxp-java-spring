package com.oak.rbac.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenPC
 */

@Schema(description = "权限值类型")
public enum PermissionValueType implements DescriptiveEnum {

    @Schema(description = "视图")
    VIEW,

    @Schema(description = "接口")
    API,

    @Schema(description = "数据标识列表")
    DATA_IDS,
}
