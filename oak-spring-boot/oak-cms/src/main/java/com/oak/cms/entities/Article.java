package com.oak.cms.entities;


import com.oak.cms.enums.ArticleStatus;
import com.oak.cms.enums.ArticleContentType;
import com.oak.cms.enums.CoverMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Schema(description =  "文章")
@Entity
@Table(name = "t_ncms_article")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"channel"})
public class Article implements java.io.Serializable {

    private static final long serialVersionUID = -1334742252818621384L;
    @Schema(description =  "文章ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description =  "栏目ID")
    @Column(name = "channel_id", nullable = false)
    private Long channelId;

    @Schema(description =  "栏目编号")
    @Column(name = "channel_code", length = 50)
    private String channelCode;

    @Schema(description =  "栏目信息")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private Channel channel;

    @Schema(description =  "标题")
    @Column(name = "title", nullable = false)
    private String title;

    @Schema(description = "文章摘要")
    @Column(name = "description")
    @Lob
    private String description;

    @Schema(description =  "导图模式")
    @Column(name = "cover_mode", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private CoverMode coverMode;

    @Schema(description =  "导图")//url,url,url
    @Column(name = "cover_image", length = 1000)
    private String coverImage;

    @Schema(description =  "排序")
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 1;

    @Schema(description =  "发布人")
    @Column(name = "author")
    private String author;

    @Schema(description =  "发布人ID")
    @Column(name = "author_id")
    private Long authorId;

    @Schema(description =  "来源")
    @Column(name = "`source`")
    private String source;

    @Schema(description =  "阅读数")
    @Column(name = "views", nullable = false)
    private Integer views = 0;

    @Schema(description =  "赞人数")
    @Column(name = "likes", nullable = false)
    private Integer likes = 0;

    @Schema(description =  "评论数")
    @Column(name = "comments")
    private Integer comments = 0;

    @Schema(description =  "收藏数")
    @Column(name = "collections")
    private Integer collections = 0;

    @Schema(description =  "分享数")
    @Column(name = "shares")
    private Integer shares = 0;

    @Schema(description =  "文章模式")
    @Column(name = "content_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ArticleContentType contentType;

    @Schema(description =  "文章内容")
    @Column(name = "content")
    @Lob
    private String content;

    @Schema(description =  "附件")
    @Column(name = "attachment")
    private String attachment;

    @Schema(description =  "是否删除")
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Schema(description =  "状态")
    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Schema(description =  "发布日期")
    @Column(name = "publish_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;

    @Schema(description = "创建日期")
    @Column(name = "crate_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Schema(description =  "更新日期")
    @Column(name = "update_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Schema(description =  "关联来源")
    @Column(name = "source_code")
    private String sourceCode;

    @Schema(description =  "备注")
    @Column(name = "remark")
    private String remark;

}
