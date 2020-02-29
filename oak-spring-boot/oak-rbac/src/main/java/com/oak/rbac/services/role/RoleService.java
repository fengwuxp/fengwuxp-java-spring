package com.oak.rbac.services.role;

import com.oak.rbac.services.role.info.RoleInfo;
import com.oak.rbac.services.role.req.CreateRoleReq;
import com.oak.rbac.services.role.req.DeleteRoleReq;
import com.oak.rbac.services.role.req.EditRoleReq;
import com.oak.rbac.services.role.req.QueryRoleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 角色服务
 */
public interface RoleService {


    String ROLE_CACHE_NAME = "RBAC_ROLE_CACHE";



    /**
     * 创建角色
     *
     * @param req
     * @return
     */
    ApiResp<Long> createRole(CreateRoleReq req);

    /**
     * 编辑角色
     *
     * @param req
     * @return
     */
    ApiResp<Void> editRole(EditRoleReq req);


    /**
     * 删除角色
     *
     * @param req
     */
    ApiResp<Void> deleteRole(DeleteRoleReq req);

    /**
     * 查找角色
     *
     * @param id
     * @return
     */
    RoleInfo findRoleById(Long id);

    /**
     * 查找角色
     *
     * @param name
     * @return
     */
    RoleInfo findRoleByName(String name);


    /**
     * 查询角色
     *
     * @param req
     * @return
     */
    Pagination<RoleInfo> queryRole(QueryRoleReq req);


}
