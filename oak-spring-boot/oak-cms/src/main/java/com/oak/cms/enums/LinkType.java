package com.oak.cms.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenPC
 */

@Schema(description = "链接类型")
public enum LinkType implements DescriptiveEnum {

    @Schema(description = "不跳转")
    NONE,

    @Schema(description = "跳转链接")
    JUMP_LINK,

    @Schema(description = "跳转页面")
    JUMP_PAGE;

}
