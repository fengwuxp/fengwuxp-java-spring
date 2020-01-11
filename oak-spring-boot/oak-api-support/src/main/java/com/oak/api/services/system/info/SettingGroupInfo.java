package com.oak.api.services.system.info;

import com.levin.commons.service.domain.Desc;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 设置分组
 * 2018-3-30 20:39:48
 */
@Desc(value = "设置分组")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString(exclude = {})
public class SettingGroupInfo implements Serializable {

    @Desc(value = "分组名称")
    private String name;

    @Desc(value = "是否显示")
    private Boolean show;

    @Desc(value = "排序")
    private Integer orderIndex;


}
