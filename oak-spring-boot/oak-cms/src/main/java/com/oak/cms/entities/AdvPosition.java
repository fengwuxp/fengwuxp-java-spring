package com.oak.cms.entities;

import com.oak.api.entities.system.Area;
import com.oak.cms.enums.AdvDisplayType;
import com.oak.cms.enums.AdvType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Schema(description = "广告位信息")
@Table(name = "t_adv_position", indexes = {
        @Index(columnList = "code", unique = true)
})
@Data
public class AdvPosition implements java.io.Serializable {

    private static final long serialVersionUID = 8467304686652214057L;

    @Schema(description = "广告位置id")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;


    @Schema(description = "引用编码")
    @Column(name = "code", unique = true, nullable = false, length = 32)
    private String code;


    @Schema(description = "广告位置名称")
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Schema(description = "广告位简介")
    @Column(name = "description", nullable = false)
    private String description;

    @Schema(description = "广告类别")
    @Column(name = "ad_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdvType type;

    @Schema(description = "广告展示方式")
    @Column(name = "display_type", nullable = false)
    private AdvDisplayType displayType;

    @Schema(description = "广告位是否启用")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Schema(description = "广告位宽度")
    @Column(name = "`width`", nullable = false)
    private Integer width;

    @Schema(description = "广告位高度")
    @Column(name = "`height`", nullable = false)
    private Integer height;

    @Schema(description = "广告位单价")
    @Column(name = "price", nullable = false)
    private Integer price;

    @Schema(description = "拥有的广告数")
    @Column(name = "num", nullable = false)
    private Integer num;

    @Schema(description = "广告位点击率")
    @Column(name = "click_num", nullable = false)
    private Integer clickNum;

    @Schema(description = "广告位默认内容")
    @Column(name = "default_content", nullable = false, length = 512)
    private String defaultContent;

    @Schema(description = "归属地区id")
    @Column(name = "area_id", length = 50)
    private String areaId;

    @Schema(description = "地区")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;

    @Schema(description = "广告规格")
    @Column(name = "spec", length = 64)
    private String spec;

    @Schema(description = "创建日期")
    @Column(name = "crate_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Schema(description =  "更新日期")
    @Column(name = "update_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
