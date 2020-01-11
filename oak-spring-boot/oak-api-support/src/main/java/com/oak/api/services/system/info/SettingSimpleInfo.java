package com.oak.api.services.system.info;

import com.levin.commons.service.domain.Desc;
import lombok.*;


@Desc("系统设置信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString()
public class SettingSimpleInfo implements java.io.Serializable {

    private static final long serialVersionUID = -1643615679272599295L;

    @Desc("配置名称")
    private String name;

    @Desc("配置值")
    private Object value;

}
