package com.oak.rbac.services.permission;

import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.permission.req.DeletePermissionReq;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.wuxp.api.model.Pagination;

/**
 * 权限管理服务
 */
public interface PermissionService  {

    /**
     * 创建权限
     *
     * @param req
     * @return
     */
    Long createPermission(CreatePermissionReq req);


    /**
     * 删除权限
     *
     * @param req
     * @return
     */
    int deletedPermission(DeletePermissionReq req);

    /**
     * 查询权限
     *
     * @param req
     * @return
     */
    Pagination<PermissionInfo> queryPermission(QueryPermissionReq req);


}
