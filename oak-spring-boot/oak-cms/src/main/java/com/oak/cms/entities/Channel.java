package com.oak.cms.entities;

import com.levin.commons.service.domain.Desc;
import com.oak.cms.enums.ChannelNextMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Schema(description = "栏目")
@Entity
@Table(name = "t_ncms_channel")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"parent", "children", "images"})
public class Channel implements java.io.Serializable {

    private static final long serialVersionUID = 173899870540428601L;

    @Schema(description = "栏目ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "栏目编号")
    @Column(name = "`code`", length = 50, unique = true, nullable = false)
    private String code;

    @Schema(description = "名称")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "图标")
    @Column(name = "icon")
    private String icon;

    @Schema(description = "排序")
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 1;

    @Schema(description = "上级栏目ID")
    @Column(name = "parent_id")
    private Long parentId;

    @Schema(description = "上级栏目信息")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Channel parent;

    @Desc("层级")
    @Column(name = "`level`", nullable = false)
    private Integer level;

    @Schema(description = "层级排序")//格式：#level_index#level_index#
    @Column(name = "level_path")
    private String levelPath;

    @Schema(description = "层级关系")//格式：#id#id#id#id#
    @Column(name = "`path`")
    private String path;

    @Schema(description = "下级栏目模式")
    @Column(name = "next_mode", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ChannelNextMode nextMode;

    @Schema(description = "启用分域筛查，栏目下内容将按分域筛查")
    @Column(name = "enable_domain", nullable = false)
    private Boolean enableDomain = false;

    @Schema(description = "是否需要审批，栏目下内容是否需要审批")
    @Column(name = "need_approve", nullable = false)
    private Boolean needApprove = false;

    @Schema(description = "是否可用")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Schema(description = "是否删除")
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Schema(description = "创建日期")
    @Column(name = "crate_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Schema(description = "更新日期")
    @Column(name = "update_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Schema(description = "轮播图")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ChannelImage> images;

    @Schema(description = "下级栏目")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<Channel> children;

}
