package com.oak.api.entities.system;

import com.oak.api.enums.SettingValueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Schema(description = "设置信息")
@Entity
@Table(
        name = "t_system_setting",
        indexes = {
                @Index(columnList = "name")
        })
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class Setting implements java.io.Serializable {

    private static final long serialVersionUID = 3510512829690007470L;
    @Schema(description = "配置名称")
    @Id
    @Column(name = "`name`", length = 50)
    private String name;

    @Schema(description = "配置值")
    @Column(name = "`value`")
    @Lob
    private String value;

    @Schema(description = "配置标题名称")
    @Column(name = "`label`")
    private String label;

    @Schema(description = "配置名称后缀")
    @Column(name = "label_suffix")
    private String labelSuffix;

    @Schema(description = "配置描述")
    @Column(name = "`description`")
    private String description;

    @Schema(description = "配置类型")
    @Column(name = "`type`")
    @Enumerated(EnumType.STRING)
    private SettingValueType type;

    @Schema(description = "是否显示")
    @Column(name = "`show`")
    private Boolean show = true;

    @Schema(description = "客户端是否可获取")
    @Column(name = "open")
    private Boolean open = true;

    @Schema(description = "排序")
    @Column(name = "order_index")
    private Integer orderIndex = 1;

    @Schema(description = "分组名称")
    @Column(name = "group_name", length = 100)
    private String groupName;

    @Schema(description = "分组信息")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_name", insertable = false, updatable = false)
    private SettingGroup settingGroup;

    @Schema(description = "可选值，多个使用json格式")
    @Column(name = "`items`", length = 500)
    private String items;

    @Schema(description = "值正则式")
    @Column(name = "`regex`")
    private String regex;

    @Schema(description = "更新时间")
    @Column(name = "update_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Setting(String value, String description) {
        this.value = value;
        this.description = description;
    }

}
