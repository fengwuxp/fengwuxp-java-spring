package com.oak.cms.entities;

import com.oak.api.entities.system.Area;
import com.oaknt.ncms.enums.AdvCheckState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


/**
 * @author chenPC
 */
@Entity
@Schema(description = "广告信息")
@Table(name = "t_adv", indexes = {
        @Index(columnList = "ap_id")
})
@Data
@ToString(exclude = {"area"})
public class Adv implements java.io.Serializable {

    private static final long serialVersionUID = -7444767900426714890L;

    @Schema(description = "id")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "广告位id")
    @Column(name = "ap_id", nullable = false)
    private Long apId;

    @Schema(description = "广告位")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ap_id", insertable = false, updatable = false)
    private AdvPosition advPosition;

    @Schema(description = "广告内容描述")
    @Column(name = "title", nullable = false)
    private String title;

    @Schema(description = "广告内容")
    @Column(name = "content", nullable = false, length = 1024)
    private String content;

    @Schema(description = "广告URL")
    @Column(name = "`url`", nullable = true, length = 200)
    private String url;

    @Schema(description = "广告开始时间")
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", length = 10)
    private Date startDate;

    @Schema(description = "广告结束时间")
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", length = 10)
    private Date endDate;

    @Schema(description = "幻灯片排序")
    @Column(name = "slide_sort", nullable = false)
    private Integer slideSort;

    @Schema(description = "广告拥有者")
    @Column(name = "member_id")
    private Long memberId;

    @Schema(description = "会员用户名")
    @Column(name = "member_name", length = 50)
    private String memberName;

    @Schema(description = "广告点击率")
    @Column(name = "click_num", nullable = false)
    private Integer clickNum;

    @Schema(description = "会员购买的广告是否通过审核")
    @Column(name = "`state`", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private AdvCheckState state;

    @Schema(description = "购买方式")
    @Column(name = "buy_style", nullable = false, length = 16)
    private String buyStyle;

    @Schema(description = "购买所支付的金币")
    @Column(name = "goldpay", nullable = false)
    private Integer goldpay;

    @Schema(description = "归属地区id")
    @Column(name = "area_id", length = 50)
    private String areaId;

    @Schema(description = "广告投放地区")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;

    @Schema(description = "是否启用")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Schema(description = "创建日期")
    @Column(name = "crate_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Schema(description = "更新日期")
    @Column(name = "update_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
