package com.oak.cms.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "文章模式")
public enum ArticleContentType implements DescriptiveEnum {

    @Schema(description = "文本")
    TEXT,

    @Schema(description = "图文")
    HTML,

//    @Schema(description = "附件")
//    ATTACHMENT,

    @Schema(description = "视频")
    VIDEO,

    @Schema(description = "外链")
    LINK;

}
