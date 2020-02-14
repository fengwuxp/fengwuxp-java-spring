package com.oak.rbac.management.menu;

import com.oak.rbac.management.menu.req.AddMenuInfoReq;
import com.oak.rbac.management.menu.req.DelMenuInfoReq;
import com.oak.rbac.management.menu.req.QueryMenuInfoReq;
import com.oak.rbac.management.menu.req.UpdateMenuInfoReq;
import com.oak.rbac.services.menu.MenuService;
import com.oak.rbac.services.menu.info.MenuInfo;
import com.oak.rbac.services.menu.req.CreateMenuReq;
import com.oak.rbac.services.menu.req.DeleteMenuReq;
import com.oak.rbac.services.menu.req.EditMenuReq;
import com.oak.rbac.services.menu.req.QueryMenuReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理业务服务实现
 *
 * @author chenPC
 */
@Service
@Slf4j
public class MenuManagementServiceImpl implements MenuManagementService {
    @Autowired
    private MenuService menuService;

    @Override
    public Pagination<MenuInfo> getMenuInfo(QueryMenuInfoReq queryMenuInfoReq) {
        QueryMenuReq queryMenuReq = new QueryMenuReq();
        BeanUtils.copyProperties(queryMenuInfoReq, queryMenuReq);
        return menuService.query(queryMenuReq);
    }

    @Override
    public ApiResp<Long> addMenuInfo(AddMenuInfoReq addMenuInfoReq) {
        CreateMenuReq createMenuReq = new CreateMenuReq();
        BeanUtils.copyProperties(addMenuInfoReq, createMenuReq);
        ApiResp<Long> addMenu = menuService.create(createMenuReq);
        if (!addMenu.isSuccess()) {
            return RestfulApiRespFactory.error(addMenu.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> updateMenuInfo(UpdateMenuInfoReq updateMenuInfoReq) {
        EditMenuReq editMenuReq = new EditMenuReq();
        MenuInfo menuInfo = menuService.findById(updateMenuInfoReq.getId());
        if (!menuInfo.getEditable()) {
            return RestfulApiRespFactory.error("该菜单信息不可编辑");
        }
        BeanUtils.copyProperties(updateMenuInfoReq, editMenuReq);
        ApiResp<Void> updateMenu = menuService.edit(editMenuReq);

        if (!updateMenu.isSuccess()) {
            return RestfulApiRespFactory.error(updateMenu.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delMenuInfo(DelMenuInfoReq delMenuInfoReq) {
        DeleteMenuReq deleteMenuReq = new DeleteMenuReq();
        MenuInfo menuInfo = menuService.findById(delMenuInfoReq.getId());
        if (menuInfo.getDeleted()) {
            return RestfulApiRespFactory.error("很抱歉，该菜单已经被删除了");
        }
        BeanUtils.copyProperties(delMenuInfoReq, deleteMenuReq);
        ApiResp<Void> delMenuInfo = menuService.delete(deleteMenuReq);
        if (!delMenuInfo.isSuccess()) {
            return RestfulApiRespFactory.error(delMenuInfo.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }


}
