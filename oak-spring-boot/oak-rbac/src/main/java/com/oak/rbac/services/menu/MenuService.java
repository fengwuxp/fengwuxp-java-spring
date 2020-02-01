package com.oak.rbac.services.menu;

import com.oak.rbac.services.menu.info.MenuInfo;
import com.oak.rbac.services.menu.req.CreateMenuReq;
import com.oak.rbac.services.menu.req.DeleteMenuReq;
import com.oak.rbac.services.menu.req.EditMenuReq;
import com.oak.rbac.services.menu.req.QueryMenuReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 菜单服务
 * 2020-1-14 16:32:16
 */
public interface MenuService {

    String MENU_CACHE_NAME = "MENU_CACHE";

    ApiResp<Long> create(CreateMenuReq req);


    ApiResp<Void> edit(EditMenuReq req);


    ApiResp<Void> delete(DeleteMenuReq req);


    MenuInfo findById(Long id);


    Pagination<MenuInfo> query(QueryMenuReq req);

}
