package com.oak.api.services.system;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.entities.system.E_Setting;
import com.oak.api.entities.system.E_SettingGroup;
import com.oak.api.entities.system.Setting;
import com.oak.api.entities.system.SettingGroup;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.services.system.info.SettingGroupInfo;
import com.oak.api.services.system.info.SettingInfo;
import com.oak.api.services.system.req.*;
import com.oak.api.utils.RegexUtil;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private JpaDao jpaDao;

    @Override
    public String getSetting(GetSettingReq req) {

        SettingInfo settingInfo = this.findSettingByName(req.getName());
        if (settingInfo != null) {
            return settingInfo.getValue();
        }
        return null;
    }


    @Override
    public String[] getSettingList(GetSettingListReq req) {

        return Arrays.stream(req.getNames())
                .map(this::findSettingByName)
                .filter(Objects::nonNull)
                .map(SettingInfo::getValue)
                .toArray(String[]::new);

    }

//    @Override
//    public List<SettingSimpleInfo> getAllSetting() {
//        QuerySettingReq req = new QuerySettingReq();
//        req.setQuerySize(-1);
//
//        Pagination<SettingInfo> settingInfoPagination = this.querySetting(req);
//        return settingInfoPagination.getRecords()
//                .stream()
//                .map(settingInfo -> new SettingSimpleInfo(settingInfo.getName(), settingInfo.getValue()))
//                .collect(Collectors.toList());
//    }

    @Caching(evict = {
            @CacheEvict(value = SETTING_CACHE_NAME, key = "#req.getName()"),
            @CacheEvict(value = CONFIG_CACHE_NAME, key = "#req.getName()")
    })
    @Override
    public ApiResp<Void> saveSetting(SaveSettingReq req) {

        //类型验证
        String message = null;
        if (req.getValue() != null) {
            switch (req.getType()) {
                case INT:
                    if (!RegexUtil.checkDigit(req.getValue())) {
                        message = "请填写整数";
                    }
                    break;
                case DECIMAL:
                    if (!RegexUtil.checkDecimals(req.getValue())) {
                        message = "请填写数值";
                    }
                    break;
                case URL:
                    if (!RegexUtil.checkURL(req.getValue())) {
                        message = "url格式有误";
                    }
                    break;
                case DATE:

                    break;
                case BOOLEAN:
                    String lowerCase = req.getValue().toLowerCase();
                    if (!(Boolean.TRUE.toString().toLowerCase().equals(lowerCase) || Boolean.FALSE.toString().toLowerCase().equals(lowerCase))) {
                        message = "请填写正确的布尔值";
                    }
                    break;
                case ARRAY:
                    break;
            }
        }


        if (message != null) {
            return RestfulApiRespFactory.error(message);
        }


        Setting setting = jpaDao.find(Setting.class, req.getName());
        if (setting != null) {
            int c = jpaDao.updateTo(Setting.class)
                    .appendColumnByDTO(req, E_Setting.name)
                    .appendColumn(E_Setting.updateTime, new Date())
                    .eq(E_Setting.name, req.getName())
                    .update();
            if (c < 1) {
                return RestfulApiRespFactory.error("设置失败");
            }
        } else {
            setting = new Setting();
            BeanUtils.copyProperties(req, setting);
            setting.setUpdateTime(new Date());
            jpaDao.create(setting);
        }

        return RestfulApiRespFactory.ok();

    }

    @Cacheable(value = SETTING_CACHE_NAME,
            key = "#req.getName()",
            condition = "#req.getName()!=null",
            unless = "#result.total==0")
    @Override
    public Pagination<SettingInfo> querySetting(QuerySettingReq req) {
        return SimpleCommonDaoHelper.queryObject(this.jpaDao, Setting.class, SettingInfo.class, req);
    }

    @Cacheable(value = SETTING_CACHE_NAME,
            key = "#name",
            condition = "#name!=null",
            unless = "#result==null")
    @Override
    public SettingInfo findSettingByName(@NotNull String name) {
        return jpaDao.selectFrom(Setting.class)
                .eq(E_Setting.name, name)
                .findOne(SettingInfo.class);

    }

    @Caching(evict = {
            @CacheEvict(value = SETTING_CACHE_NAME, key = "#req.getName()"),
            @CacheEvict(value = CONFIG_CACHE_NAME, key = "#req.getName()")
    })
    @Override
    public void delSetting(DelSettingReq req) {
        jpaDao.deleteById(Setting.class, req.getName());
    }

    @Caching(evict = {
            @CacheEvict(value = SETTING_CACHE_NAME, key = "#req.getName()"),
            @CacheEvict(value = CONFIG_CACHE_NAME, key = "#req.getName()")
    })
    @Override
    public boolean editSetting(EditConfigReq req) {
        return jpaDao.updateTo(Setting.class).appendByQueryObj(req).update() > 0;
    }

    @Override
    public String createSettingGroup(CreateSettingGroupReq req) {

        SettingGroup entity = new SettingGroup();
        BeanUtils.copyProperties(req, entity);
        entity.setUpdateTime(new Date());
        jpaDao.create(entity);
        return entity.getName();
    }

    @Override
    public ApiResp<Void> editSettingGroup(EditSettingGroupReq req) {
        SettingGroup entity = jpaDao.find(SettingGroup.class, req.getName());
        if (entity == null) {
            return RestfulApiRespFactory.error("设置分组数据不存在");
        }

        UpdateDao<SettingGroup> updateDao = jpaDao.updateTo(SettingGroup.class).appendByQueryObj(req);

        updateDao.appendColumn(E_SettingGroup.updateTime, new Date());

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新设置分组失败");
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public void delSettingGroup(DelSettingGroupReq req) {
        jpaDao.deleteFrom(SettingGroup.class).appendByQueryObj(req).delete();
    }

    @Override
    public SettingGroupInfo findSettingGroup(FindSettingGroupReq req) {
        return this.querySettingGroup(new QuerySettingGroupReq(req.getName())).getFirst();
    }

    @Override
    public Pagination<SettingGroupInfo> querySettingGroup(QuerySettingGroupReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao, SettingGroup.class, SettingGroupInfo.class, req);
    }
}
