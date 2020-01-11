package com.oak.api.initator;

import com.oak.api.enums.SettingValueType;
import com.oak.api.services.system.SystemService;
import com.oak.api.services.system.info.SettingGroupInfo;
import com.oak.api.services.system.req.CreateSettingGroupReq;
import com.oak.api.services.system.req.GetConfigReq;
import com.oak.api.services.system.req.QuerySettingGroupReq;
import com.oak.api.services.system.req.SaveSettingReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 系统设置初始化器
 */
@Slf4j
public class SettingInitiator extends AbstractBaseInitiator<SettingModel> {

    @Autowired
    private SystemService systemService;

    @Override
    public void init() {

        List<SettingModel> list = getInitData();

        if (list == null || list.isEmpty()) {

            return;
        }

        int i = 1;
        for (SettingModel model : list) {

            Pagination<SettingGroupInfo> pagination
                    = systemService.querySettingGroup(new QuerySettingGroupReq(model.getGroup()));
            if (pagination.getFirst() == null) {
                //创建分组
                CreateSettingGroupReq groupEvt = new CreateSettingGroupReq();
                groupEvt.setShow(model.getShow());
                groupEvt.setName(model.getGroup());
                groupEvt.setOrderIndex(i);
                String settingGroup = systemService.createSettingGroup(groupEvt);
                log.info("创建设置分组【" + model.getGroup() + "】->" + settingGroup);
            }

            int j = 1;
            for (SaveSettingReq evt : model.getSettings()) {
                evt.setGroupName(model.getGroup());
                if (evt.getShow() == null) {
                    evt.setShow(true);
                }
                if (evt.getType() == null) {
                    evt.setType(SettingValueType.TEXT);
                }
                if (evt.getOrderIndex() == null) {
                    evt.setOrderIndex(j);
                }

                String config = systemService.getConfig(new GetConfigReq(evt.getName(), false));
                if (config != null) {
                    ApiResp<Void> resp = systemService.saveSetting(evt);
                    log.info("创建设置【" + evt + "】->" + resp);
                }
                j++;
            }

            i++;
        }
    }


}
