package com.oak.api.services.system.info;

import com.levin.commons.service.domain.Desc;
import com.oak.api.enums.SettingValueType;

import java.util.Date;

/**
 * Created by Administrator on 2016-03-24.
 */
@Desc("系统设置信息")
public class SettingInfo implements java.io.Serializable {

    private static final long serialVersionUID = -2648878896725546195L;

    @Desc("配置名称")
    private String name;

    @Desc("配置值")
    private String value;

    @Desc("配置标题名称")
    private String label;

    @Desc(value = "配置名称后缀")
    private String labelSuffix;

    @Desc("配置描述")
    private String description;

    @Desc("配置类型")
    private SettingValueType type;

    @Desc("是否显示")
    private Boolean show = true;

    @Desc("允许客户端获取")
    private Boolean open = true;

    @Desc("分组名称")
    private String groupName;

    @Desc("可选值（多个以#分隔）")
    private String items;

    @Desc("值正则式")
    private String regex;

    @Desc("更新时间")
    private Date updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SettingValueType getType() {
        return type;
    }

    public void setType(SettingValueType type) {
        this.type = type;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String[] getItemArray() {
        return items != null ? items.split("#") : new String[0];
    }

    @Override
    public String toString() {
        return "SettingInfo{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", show=" + show +
                ", open=" + open +
                ", groupName='" + groupName + '\'' +
                ", items='" + items + '\'' +
                ", regex='" + regex + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
