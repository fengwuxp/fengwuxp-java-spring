package com.oak.rbac.services.permission;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.rbac.entities.OakPermission;
import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.permission.req.DeletePermissionReq;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.oak.rbac.services.resource.ResourceService;
import com.oak.rbac.services.resource.info.ResourceInfo;
import com.oak.rbac.services.resource.req.QueryResourceReq;
import com.wuxp.api.model.Pagination;
import com.wuxp.security.authority.url.RequestUrlConfigAttribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private ResourceService resourceService;

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

    @Override
    public Collection<RequestUrlConfigAttribute> getUrlConfigAttributes(String url) {
        String[] values = url.split("/");
        String resourceCode = values[1];
        QueryPermissionReq req = new QueryPermissionReq();
        req.setResourceId(resourceCode);

        return this.queryPermission(req)
                .getRecords()
                .stream()
                .filter(permissionInfo -> permissionInfo.getValue().equals(url))
                .map(permissionInfo -> new RequestUrlConfigAttribute(permissionInfo.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<RequestUrlConfigAttribute> getAllUrlConfigAttributes() {

        QueryResourceReq queryResourceReq = new QueryResourceReq();
        queryResourceReq.setType(ResourceType.API_URL);
        Pagination<ResourceInfo> resourceInfoPagination = resourceService.queryResource(queryResourceReq);

        List<ResourceInfo> records = resourceInfoPagination.getRecords();

        return records.stream().map(ResourceInfo::getPermissions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .stream()
                .map(permissionInfo -> new RequestUrlConfigAttribute(permissionInfo.getValue()))
                .collect(Collectors.toList());

    }
}
