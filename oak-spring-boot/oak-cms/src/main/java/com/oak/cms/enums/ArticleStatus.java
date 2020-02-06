package com.oak.cms.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenPC
 */

@Schema(description = "文章状态")
public enum ArticleStatus implements DescriptiveEnum {

    @Schema(description = "未审核")
    WAIT,

    @Schema(description = "审核通过")
    PASS,

    @Schema(description = "审核不通过")
    OVERRULE;

}
