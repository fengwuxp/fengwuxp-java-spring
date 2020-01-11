package com.oak.api.initator;

import com.oak.api.services.system.req.SaveSettingReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "设置model")
@Data
public class SettingModel implements java.io.Serializable {

    @Schema(description = "组名")
    private String group;

    @Schema(description = "是否显示")
    private Boolean show;

    @Schema(description = "排序")
    private Integer index;

    @Schema(description = "设置数据列表")
    private List<SaveSettingReq> settings;

    public SettingModel() {
    }

    public SettingModel(String group, Boolean show, Integer index) {
        this.group = group;
        this.show = show;
        this.index = index;
    }

}
