package com.oak.rbac.management.menu;

import com.oak.rbac.management.menu.req.AddMenuInfoReq;
import com.oak.rbac.management.menu.req.DelMenuInfoReq;
import com.oak.rbac.management.menu.req.QueryMenuInfoReq;
import com.oak.rbac.management.menu.req.UpdateMenuInfoReq;
import com.oak.rbac.services.menu.info.MenuInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

/**
 * 菜单管理业务服务
 *
 * @author chenPC
 */
public interface MenuManagementService {

    /**
     * 查询菜单分页列表
     *
     * @param queryMenuInfoReq
     * @return
     */
    Pagination<MenuInfo> getMenuInfo(QueryMenuInfoReq queryMenuInfoReq);

    /**
     * 添加菜单
     *
     * @param addMenuInfoReq
     * @return
     */
    ApiResp<Long> addMenuInfo(AddMenuInfoReq addMenuInfoReq);


    /**
     * 编辑菜单
     *
     * @param updateMenuInfoReq
     * @return
     */
    ApiResp<Void> updateMenuInfo(UpdateMenuInfoReq updateMenuInfoReq);


    /**
     * 删除菜单
     *
     * @param delMenuInfoReq
     * @return
     */
    ApiResp<Void> delMenuInfo(DelMenuInfoReq delMenuInfoReq);

}
