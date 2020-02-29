package com.oak.api.services.system;

import com.oak.api.services.system.info.SettingGroupInfo;
import com.oak.api.services.system.info.SettingInfo;
import com.oak.api.services.system.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

import javax.validation.constraints.NotNull;

/**
 * 系统服务
 */
public interface SystemService {

    String SETTING_CACHE_NAME = "SYSTEM_SETTING";

    String CONFIG_CACHE_NAME = "SYSTEM_CONFIG";


    /**
     * 获取配置
     *
     * @param req
     * @return
     */
    String getSetting(GetSettingReq req);

    /**
     * 获取配置列表
     *
     * @param req
     * @return
     */
    String[] getSettingList(GetSettingListReq req);


    ApiResp<Void> saveSetting(SaveSettingReq req);


    Pagination<SettingInfo> querySetting(QuerySettingReq req);


    SettingInfo findSettingByName(@NotNull String name);


    void delSetting(DelSettingReq req);


    boolean editSetting(EditConfigReq req);


    String createSettingGroup(CreateSettingGroupReq req);


    ApiResp<Void> editSettingGroup(EditSettingGroupReq req);


    void delSettingGroup(DelSettingGroupReq req);


    SettingGroupInfo findSettingGroup(FindSettingGroupReq req);


    Pagination<SettingGroupInfo> querySettingGroup(QuerySettingGroupReq req);


}
