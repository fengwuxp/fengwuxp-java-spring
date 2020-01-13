package com.oak.rbac.services.permission;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.rbac.entities.OakPermission;
import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.permission.req.DeletePermissionReq;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public Long createPermission(CreatePermissionReq req) {
        OakPermission permission = new OakPermission();

        Date createTime = new Date();
        permission.setResourceId(req.getResourceId())
                .setValue(req.getValue())
                .setName(req.getName())
                .setOrderCode(req.getOrderCode())
                .setEnable(true)
                .setRemark(req.getRemark())
                .setCreateTime(createTime)
                .setLastUpdateTime(createTime);

        jpaDao.create(permission);

        return permission.getId();
    }

    @Override
    public int deletedPermission(DeletePermissionReq req) {
        return jpaDao.deleteFrom(OakPermission.class).appendByQueryObj(req).delete();
    }

    @Override
    public Pagination<PermissionInfo> queryPermission(QueryPermissionReq req) {
        return SimpleCommonDaoHelper.queryObject(this.jpaDao, OakPermission.class, PermissionInfo.class, req);
    }
}
