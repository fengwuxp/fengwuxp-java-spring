package com.oak.api.entities.system;

import com.levin.commons.service.domain.Desc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Schema(description = "设置分组")
@Entity
@Table(name = "t_system_setting_group")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class SettingGroup implements java.io.Serializable {

    private static final long serialVersionUID = -1298383658857005713L;
    @Desc("分组名称")
    @Id
    @Column(name = "`name`", length = 80)
    private String name;

    @Desc("是否显示")
    @Column(name = "`show`")
    private Boolean show;

    @Desc("排序")
    @Column(name = "order_index")
    private Integer orderIndex = 1;

    @Desc("更新时间")
    @Column(name = "update_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

}
