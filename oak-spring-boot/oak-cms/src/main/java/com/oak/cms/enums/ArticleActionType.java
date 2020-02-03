package com.oak.cms.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "文章互动类型")
public enum ArticleActionType implements DescriptiveEnum {

    @Schema(description = "阅读")
    VIEW,

    @Schema(description = "点赞")
    LIKE,

    @Schema(description = "评论")
    COMMENT,

    @Schema(description = "收藏")
    COLLECTION,

    @Schema(description = "分享")
    SHARE;

}
