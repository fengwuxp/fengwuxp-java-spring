package com.oak.rbac.controller;


import com.levin.commons.dao.JpaDao;
import com.oak.rbac.management.menu.MenuManagementService;
import com.oak.rbac.management.menu.req.AddMenuInfoReq;
import com.oak.rbac.management.menu.req.DelMenuInfoReq;
import com.oak.rbac.management.menu.req.QueryMenuInfoReq;
import com.oak.rbac.management.menu.req.UpdateMenuInfoReq;
import com.oak.rbac.services.menu.info.MenuInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenPC
 */
@RestController
@RequestMapping("/menu")
@Tag(name = "菜单", description = "菜单管理")
@Slf4j
public class MenuController {
    @Autowired
    private MenuManagementService menuManagementService;

    @Autowired
    private JpaDao jpaDao;

    /**
     * 查询菜单分页
     *
     * @param dto
     * @return
     */
    @GetMapping("/query_menu_info")
    @Operation(summary = "查询菜单", description = "查询菜单")
    public ApiResp<Pagination<MenuInfo>> getMenuInfo(QueryMenuInfoReq dto) {
        return RestfulApiRespFactory.ok(menuManagementService.getMenuInfo(dto));

    }

    /**
     * 添加菜单
     *
     * @param dto
     * @return
     */
    @GetMapping("/add_menu_info")
    @Operation(summary = "添加菜单", description = "添加菜单")
    public ApiResp<Long> addMenuInfo(AddMenuInfoReq dto) {
        return menuManagementService.addMenuInfo(dto);
    }


    /**
     * 修改菜单
     *
     * @param dto
     * @return
     */
    @GetMapping("/update_menu_info")
    @Operation(summary = "添加菜单", description = "添加菜单")
    public ApiResp<Void> updateMenuInfo(UpdateMenuInfoReq dto) {
        return menuManagementService.updateMenuInfo(dto);
    }


    @GetMapping("/del_menu_info")
    @Operation(summary = "添加菜单", description = "添加菜单")
    public ApiResp<Void> delMenuInfo(DelMenuInfoReq dto) {
        return menuManagementService.delMenuInfo(dto);

    }


}
