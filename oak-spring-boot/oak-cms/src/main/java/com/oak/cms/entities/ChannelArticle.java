package com.oak.cms.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author chenPC
 */
@Schema(description = "专题文章")
@Entity
@Table(name = "t_ncms_channel_article")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"channel", "article"})
public class ChannelArticle implements java.io.Serializable {

    private static final long serialVersionUID = 2123878741554430277L;
    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "栏目ID")
    @Column(name = "channel_id", nullable = false)
    private Long channelId;

    @Schema(description = "栏目信息")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private Channel channel;

    @Schema(description = "文章ID")
    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Schema(description = "文章信息")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;

    @Schema(description = "排序")
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 1;

    @Schema(description = "创建日期")
    @Column(name = "crate_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Schema(description = "更新日期")
    @Column(name = "update_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();

}
