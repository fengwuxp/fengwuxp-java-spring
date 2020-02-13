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
 *
 * @author chenPC
 */
public interface MenuService {

    String MENU_CACHE_NAME = "MENU_CACHE";

    /**
     * 添加
     *
     * @param req
     * @return
     */
    ApiResp<Long> create(CreateMenuReq req);


    /**
     * 编辑
     *
     * @param req
     * @return
     */
    ApiResp<Void> edit(EditMenuReq req);


    /**
     * 删除
     *
     * @param req
     * @return
     */
    ApiResp<Void> delete(DeleteMenuReq req);


    /**
     * id查找
     *
     * @param id
     * @return
     */
    MenuInfo findById(Long id);


    /**
     * 分页查询
     *
     * @param req
     * @return
     */
    Pagination<MenuInfo> query(QueryMenuReq req);

}
