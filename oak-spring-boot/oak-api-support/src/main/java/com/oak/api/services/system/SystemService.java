package com.oak.api.services.system;

import com.oak.api.services.system.info.SettingGroupInfo;
import com.oak.api.services.system.info.SettingInfo;
import com.oak.api.services.system.info.SettingSimpleInfo;
import com.oak.api.services.system.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

/**
 * 系统服务
 */
public interface SystemService {

    String CONFIG_CACHE_NAME = "SYSTEM_CONFIG";

    String SETTING_CACHE_NAME = "SYSTEM_SETTING";


    @Cacheable(value = CONFIG_CACHE_NAME, key = "#req.getName()", condition = "#req.getFromCache()")
    String getConfig(GetConfigReq req);


    @Cacheable(value = SETTING_CACHE_NAME, key = "'ALL'")
    List<SettingSimpleInfo> getAllSetting();


    @Caching(evict = {
            @CacheEvict(value = CONFIG_CACHE_NAME, key = "#req.getName()"),
            @CacheEvict(value = SETTING_CACHE_NAME, key = "'ALL'")})
    ApiResp<Void> saveSetting(SaveSettingReq req);


    @Cacheable(value = CONFIG_CACHE_NAME,
            key = "#req.getName()",
            condition = "#req.getName()!=null",
            unless = "#result.total==0")
    Pagination<SettingInfo> querySetting(QuerySettingReq req);


    @CacheEvict(value = CONFIG_CACHE_NAME, key = "#req.getName()")
    void delSetting(DelSettingReq req);


    @CacheEvict(value = CONFIG_CACHE_NAME, key = "#req.getName()")
    boolean editSetting(EditConfigReq req);


    String createSettingGroup(CreateSettingGroupReq req);


    ApiResp<Void> editSettingGroup(EditSettingGroupReq req);


    void delSettingGroup(DelSettingGroupReq req);


    SettingGroupInfo findSettingGroup(FindSettingGroupReq req);


    Pagination<SettingGroupInfo> querySettingGroup(QuerySettingGroupReq req);


}
