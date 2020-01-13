package com.oak.rbac.services.resource;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.rbac.entities.OakPermission;
import com.oak.rbac.entities.OakResource;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.resource.info.ResourceInfo;
import com.oak.rbac.services.resource.req.CreateResourceReq;
import com.oak.rbac.services.resource.req.DeleteResourceReq;
import com.oak.rbac.services.resource.req.QueryResourceReq;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private JpaDao jpaDao;

    @Override
    public void createResource(CreateResourceReq req) {

        OakResource oakResource = new OakResource();
        Date time = new Date();
        oakResource.setId(req.getCode())
                .setType(req.getType())
                .setModuleCode(req.getModuleCode())
                .setModuleName(req.getModuleName())
                .setName(req.getName())
                .setEditable(false)
                .setEnable(true)
                .setOrderCode(req.getOrderIndex())
                .setLastUpdateTime(time)
                .setCreateTime(time);
        CreatePermissionReq[] permissions = req.getPermissions();
        if (permissions != null) {
            Set<OakPermission> oakPermissions = Arrays.stream(permissions).map(createPermissionReq -> {
                OakPermission oakPermission = new OakPermission();
                oakPermission.setResourceId(oakResource.getId())
                        .setValue(createPermissionReq.getValue())
                        .setName(createPermissionReq.getName())
                        .setRemark(createPermissionReq.getRemark())
                        .setCreateTime(time)
                        .setLastUpdateTime(time)
                        .setEditable(false)
                        .setEnable(true);
                return oakPermission;
            }).collect(Collectors.toSet());
            oakResource.setPermissions(oakPermissions);
        }

        jpaDao.save(oakResource);
    }

    @Caching(
            evict = {
                    @CacheEvict(
                            value = RESOURCE_CACHE_NAME,
                            key = "#req.id",
                            condition = "#req.id!=null")
            }
    )
    @Override
    public int deleteResource(DeleteResourceReq req) {

        return jpaDao.deleteFrom(OakResource.class).appendByQueryObj(req).delete();
    }

    @Caching(
            cacheable = {
                    @Cacheable(
                            value = RESOURCE_CACHE_NAME,
                            key = "#req.id",
                            condition = "#req.id!=null",
                            unless = "!#result.empty")
            }
    )
    @Override
    public Pagination<ResourceInfo> queryResource(QueryResourceReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao, OakResource.class, ResourceInfo.class, req);
    }
}
