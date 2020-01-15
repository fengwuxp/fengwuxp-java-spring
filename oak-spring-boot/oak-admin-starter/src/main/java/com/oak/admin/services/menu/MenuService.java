package com.oak.admin.services.menu;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import com.oak.admin.services.menu.req.*;
import com.oak.admin.services.menu.info.MenuInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;


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
