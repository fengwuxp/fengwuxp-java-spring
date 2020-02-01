package com.oak.rbac.services.menu;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.rbac.entities.E_Menu;
import com.oak.rbac.entities.Menu;
import com.oak.rbac.enums.MenuShowType;
import com.oak.rbac.enums.MenuType;
import com.oak.rbac.services.menu.info.MenuInfo;
import com.oak.rbac.services.menu.req.CreateMenuReq;
import com.oak.rbac.services.menu.req.DeleteMenuReq;
import com.oak.rbac.services.menu.req.EditMenuReq;
import com.oak.rbac.services.menu.req.QueryMenuReq;
import org.springframework.beans.BeanUtils;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.wuxp.api.restful.RestfulApiRespFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 菜单服务
 * 2020-1-14 16:32:16
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    private static final String PATH_SPLITTER = "#";

    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMenuReq req) {
        if (req.getOrderCode() == null) {
            // 查找排序
            long sort = jpaDao.selectFrom(Menu.class)
                    .appendWhere(req.getParentId() != null, "parentId = ?", req.getParentId())
                    .appendWhere(req.getParentId() == null, "parentId is null")
                    .count() + 1;
            req.setOrderCode((int) sort);
        } else {
            // 排序排重
            long c = jpaDao.selectFrom(Menu.class)
                    .appendWhere(req.getParentId() != null, "parentId = ?", req.getParentId())
                    .appendWhere(req.getParentId() == null, "parentId is null")
                    .eq(E_Menu.orderCode, req.getOrderCode())
                    .count();
            if (c > 0) {
                return RestfulApiRespFactory.error("排序已存在");
            }
        }

        Menu parent = null;
        if (req.getParentId() != null) {
            parent = jpaDao.find(Menu.class, req.getParentId());
            if (parent == null) {
                return RestfulApiRespFactory.error("上级菜单不存在");
            }
        }

        Menu entity = new Menu();
        BeanUtils.copyProperties(req, entity);

        boolean hasParent = parent != null;
        if (hasParent) {
            entity.setLevel(parent.getLevel() + 1);
        } else {
            entity.setLevel(0);
        }

        Date time = new Date();
        entity.setCreateTime(time);
        entity.setLastUpdateTime(time);
        entity.setDeleted(false);
        if (entity.getType() == null) {
            entity.setType(MenuType.DEFAULT);
        }
        if (entity.getShowType() == null) {
            entity.setShowType(MenuShowType.DEFAULT);
        }
        entity.setIdPath(PATH_SPLITTER);
        jpaDao.create(entity);
        if (hasParent) {
            // 更新id path
            entity.setIdPath(parent.getIdPath() + entity.getId() + PATH_SPLITTER);
        } else {
            entity.setIdPath(PATH_SPLITTER + entity.getId() + PATH_SPLITTER);
        }
        jpaDao.save(entity);
        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Caching(
            evict = {
                    @CacheEvict(value = MENU_CACHE_NAME, key = "#req.id", condition = "#req.id!=null"),
                    @CacheEvict(value = MENU_CACHE_NAME, key = "#req.parentId", condition = "#req.parentId!=null")
            }
    )
    @Override
    public ApiResp<Void> edit(EditMenuReq req) {


        Menu entity = jpaDao.find(Menu.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("菜单数据不存在");
        }

        if (req.getParentId() != null && req.getParentId().equals(entity.getId())) {
            return RestfulApiRespFactory.error("不能设置自已为上级菜单");
        }

        if (entity.getParentId() == null && req.getParentId() != null) {
            return RestfulApiRespFactory.error("根菜单不能调整");
        }

        boolean updateParent = req.getParentId() != null && !req.getParentId().equals(entity.getParentId());
        ;
        if (updateParent) {
            // 更改上级菜单
            Menu oldParent = jpaDao.find(Menu.class, entity.getParentId());
            Menu newParent = jpaDao.find(Menu.class, req.getParentId());

            // 层级差值
            int l = newParent.getLevel() - oldParent.getLevel();
            String replacePath = entity.getIdPath();
            String path = newParent.getIdPath() + entity.getId() + PATH_SPLITTER;

            List<Menu> updateList = jpaDao.selectFrom(Menu.class)
                    .contains(E_Menu.idPath, replacePath)
                    .find();
            for (Menu channel : updateList) {
                // 更改层级
                channel.setLevel(channel.getLevel() + l);
                // 更改相关目录递归路径
                channel.setIdPath(channel.getIdPath().replace(replacePath, path));
                jpaDao.save(channel);
            }
        }


        UpdateDao<Menu> updateDao = jpaDao.updateTo(Menu.class)
                .appendColumn(E_Menu.lastUpdateTime, new Date())
                .appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新菜单失败");
        }

        boolean updateEnabledFalse = Boolean.FALSE.equals(req.getEnable()) && !entity.getEnable().equals(req.getEnable());
        if (updateEnabledFalse) {
            // 更新所有子级菜单为禁用
            jpaDao.updateTo(Menu.class)
                    .startsWith(E_Menu.idPath, entity.getIdPath())
                    .appendColumn(E_Menu.enable, false)
                    .update();
        }

        return RestfulApiRespFactory.ok();
    }

    @CacheEvict(
            value = MENU_CACHE_NAME, key = "#req.id", condition = "#req.id!=null"
    )
    @Override
    public ApiResp<Void> delete(DeleteMenuReq req) {


        boolean deleteParamIsEmpty = req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0);
        if (deleteParamIsEmpty) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Menu.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Menu.class)
                    .appendColumn(E_Menu.deleted, true)
                    .appendByQueryObj(req)
                    .update() > 0;
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MenuInfo findById(Long id) {
        QueryMenuReq queryReq = new QueryMenuReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Caching(
            cacheable = {
                    @Cacheable(value = MENU_CACHE_NAME, key = "#req.id", condition = "#req.id!=null", unless = "#result.empty"),
                    @Cacheable(value = MENU_CACHE_NAME, key = "#req.parentId", condition = "#req.parentId!=null", unless = "#result.empty"),
                    @Cacheable(value = MENU_CACHE_NAME, key = "#req.startsWithIdPath", condition = "#req.startsWithIdPath!=null", unless = "#result.empty")
            }
    )
    @Override
    public Pagination<MenuInfo> query(QueryMenuReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Menu.class, MenuInfo.class, req);

    }
}
