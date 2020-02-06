package com.oak.cms.entities;

import com.oak.cms.enums.LinkType;
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
@Schema(description = "栏目轮播图")
@Entity
@Table(name = "t_ncms_channel_image")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"channel"})
public class ChannelImage implements java.io.Serializable {

    private static final long serialVersionUID = 161409941007406125L;
    @Schema(description = "轮播图ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "标题")
    @Column(name = "title", nullable = false)
    private String title;

    @Schema(description = "栏目ID")
    @Column(name = "channel_id", nullable = false)
    private Long channelId;

    @Schema(description = "栏目信息")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private Channel channel;

    @Schema(description = "开始时间")
    @Column(name = "begin_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginTime;

    @Schema(description = "结束时间")
    @Column(name = "end_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Schema(description = "是否上线")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Schema(description = "标题图片")
    @Column(name = "image", nullable = false)
    private String image;

    @Schema(description = "跳转内容")
    @Column(name = "`type`", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private LinkType linkType;

    @Schema(description = "广告链接")
    @Column(name = "link_url")
    private String linkUrl;

    @Schema(description = "链接内容")
    @Column(name = "link_content")
    @Lob
    private String linkContent;

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
    private Date updateTime;

    @Schema(description = "备注")
    @Column(name = "remark", length = 500)
    private String remark;

}
