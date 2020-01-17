package com.oak.admin.services.infoprovide;


import com.levin.commons.dao.Converter;
import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.admin.entities.Area;
import com.oak.admin.entities.E_Area;
import com.oak.admin.services.infoprovide.info.AreaInfo;
import com.oak.admin.services.infoprovide.req.EditAreaReq;
import com.oak.admin.services.infoprovide.req.FindAreaReq;
import com.oak.admin.services.infoprovide.req.QueryAreaReq;
import com.oak.api.helper.SettingValueHelper;
import com.oak.api.model.PageInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QuerySortType;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.oak.api.model.ApiBaseQueryReq.TABLE_ALIAS;

@Service
@Slf4j
public class InfoProvideServiceImpl implements InfoProvideService {

    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private SettingValueHelper settingValueHelper;

    @Override
    @Caching(cacheable = {
            @Cacheable(value = AREA_CACHE_NAME,
                    key = "'LIST_ID_'+#req.getId()",
                    condition = "#req.getFromCache() and #req.id != null",
                    unless = "#result.empty"),
            @Cacheable(value = AREA_CACHE_NAME,
                    key = "'LIST_T'+#req.thirdCode",
                    condition = "#req.getFromCache() and #req.thirdCode != null",
                    unless = "#result.empty")
    })
    public Pagination<AreaInfo> queryArea(QueryAreaReq req) {
        SelectDao<Area> selectDao = jpaDao.selectFrom(Area.class, TABLE_ALIAS);

        PageInfo<AreaInfo> pageInfo = PageInfo.newInstance(req);

        if (req.getLevel() == null) {
            Integer maxLevel = settingValueHelper.getSettingValue("area_level", 3);
            selectDao.appendWhere("e.level <= ?", maxLevel);
        }

        if (Boolean.TRUE.equals(req.getSplitIdToIds()) && req.getId() != null) {
            // 分割ids
            char[] chars = req.getId().toCharArray();
            List<String> ids = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                builder.append(chars[i]);
                if (i == 1) {
                    ids.add(builder.toString());
                }
                if (i == 3) {
                    ids.add(builder.toString() + "00");
                }
                if (i == 5) {
                    ids.add(builder.toString());
                }
                if (i == 8) {
                    ids.add(builder.toString());
                }
            }
            req.setId(null);
            req.setIds(ids.toArray(new String[]{}));
            req.setDefOrderBy(E_Area.id, QuerySortType.ASC.name());
        }

        selectDao.appendByQueryObj(req);

        if (req.getQueryType().isQueryNum()) {
            int count = (int) selectDao.count();
            pageInfo.setTotal(count);
        }

        if (req.getQueryType().isQueryResult()) {

            if (!req.isOrderBy()) {
                req.setDefOrderBy(E_Area.levelPath, QuerySortType.ASC.name());
            }

            List<AreaInfo> records = selectDao
                    .page(req.getQueryPage(), req.getQuerySize())
                    .orderBy(req.getOrderByArr(TABLE_ALIAS)).find((Converter<Area, AreaInfo>) data -> {
                        AreaInfo areaInfo = new AreaInfo();
                        BeanUtils.copyProperties(data, areaInfo, E_Area.parent);
                        if (Boolean.TRUE.equals(req.getLoadPrent()) && data.getParent() != null) {
                            AreaInfo parent = new AreaInfo();
                            BeanUtils.copyProperties(data.getParent(), parent, "parent");
                            areaInfo.setParent(parent);
                        }

                        if (Boolean.TRUE.equals(req.getLoadChildren())) {
                            QueryAreaReq querySubReq = new QueryAreaReq();
                            querySubReq.setParentId(data.getId());
                            querySubReq.setQuerySize(-1);
                            Pagination<AreaInfo> areaInfoPagination = queryArea(querySubReq);
                            areaInfo.setChildren(areaInfoPagination.getRecords());
                        }

                        return areaInfo;
                    });

            pageInfo.setRecords(records);
        }

        return pageInfo;
    }

    @Caching(cacheable = {
            @Cacheable(value = AREA_CACHE_NAME,
                    key = "'ID_'+#req.areaCode",
                    condition = "#req.areaCode != null",
                    unless = "#result==null"),
            @Cacheable(value = AREA_CACHE_NAME,
                    key = "'T'+#req.thirdCode",
                    condition = "#req.thirdCode != null",
                    unless = "#result==null")
    })
    @Override
    public AreaInfo findAreaById(FindAreaReq req) {

        if (StringUtils.isEmpty(req.getAreaCode()) && StringUtils.isEmpty(req.getThirdCode())) {
            return null;
        }

        return jpaDao.selectFrom(Area.class)
                .eq(E_Area.id, req.getAreaCode())
                .eq(E_Area.thirdCode, req.getThirdCode())
                .findOne(AreaInfo.class, 4);
    }

    @Caching(evict = {
            @CacheEvict(value = AREA_CACHE_NAME, key = "'ID_'+#req.id"),
            @CacheEvict(value = AREA_CACHE_NAME, key = "'LIST_ID_'+#req.id"),
            @CacheEvict(value = AREA_CACHE_NAME, key = "'LIST_T'+#req.thirdCode", condition = "#req.thirdCode != null"),
            @CacheEvict(value = AREA_CACHE_NAME, key = "'T'+#req.thirdCode", condition = "#req.thirdCode != null")
    })
    @Override
    public ApiResp<Void> editArea(EditAreaReq req) {


        Area entity = jpaDao.find(Area.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("Area数据不存在");
        }

        UpdateDao<Area> updateDao = jpaDao.updateTo(Area.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新Area失败");
        }

        return RestfulApiRespFactory.ok();
    }
}
