package com.oak.api.entities.system;

import com.levin.commons.service.domain.Desc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Desc(value = "地区信息")
@Table(name = "t_sys_area")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"parent", "children"})
public class Area implements java.io.Serializable {


    private static final long serialVersionUID = -5425888458843820538L;

    @Schema(description = "地区编码")
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 50)
    private String id;

    @Schema(description = "地区父ID")
    @Column(name = "parent_id", length = 50)
    private String parentId;

    @Schema(description = "地区名称")
    @Column(name = "`name`", nullable = false, length = 200)
    private String name;

    @Schema(description = "地区简称")
    @Column(name = "short_name", nullable = false, length = 200)
    private String shortName;

    @Schema(description = "地区全称")
    @Column(name = "full_name", nullable = false, length = 500)
    private String fullName;

    @Schema(description = "经度")
    @Column(name = "`longitude`", nullable = false, precision = 12, scale = 0)
    private Float longitude;

    @Schema(description = "纬度")
    @Column(name = "`latitude`", nullable = false, precision = 12, scale = 0)
    private Float latitude;

    @Schema(description = "地区深度，从1开始")
    @Column(name = "`level`", nullable = false)
    private Integer level;

    @Schema(description = "层级排序")
    @Column(name = "level_path", length = 20)
    private String levelPath;

    @Schema(description = "排序")
    @Column(name = "`sort`", nullable = false)
    private Short sort;

    @Schema(description = "地区启用状态")
    @Column(name = "`status`", nullable = false)
    private Boolean status;

    @Schema(description = "省直区县")
    @Column(name = "`directly`", nullable = false)
    private Boolean directly = false;

    @Schema(description = "第三方地区编码")
    @Column(name = "third_code")
    private String thirdCode;

    @Schema(description = "是否为市区")
    @Column(name = "urban")
    private Boolean urban;

    @Desc(value = "", code = "parent")
    @Schema(description = "上级地区")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Area parent;

    @Schema(description = "下级地区列表")
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Area> children;


}
