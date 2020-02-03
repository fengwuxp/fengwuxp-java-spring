package com.oak.cms.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "下级栏目模式")
public enum ChannelNextMode implements DescriptiveEnum {

    @Schema(description = "子栏目")
    SUB,
    @Schema(description = "专题栏目")
    SPECIAL,
    @Schema(description = "文章列表")
    ARTICLE_LIST,

    @Schema(description = "单个文章")
    ARTICLE,

    @Schema(description = "单文档/资料")
    DOCUMENT,

    @Schema(description = "自定义")
    CUSTOM;
}
