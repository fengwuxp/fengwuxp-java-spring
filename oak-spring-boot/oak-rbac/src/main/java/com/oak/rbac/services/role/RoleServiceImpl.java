package com.oak.rbac.services.role;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.rbac.entities.E_OakPermission;
import com.oak.rbac.entities.E_OakRole;
import com.oak.rbac.entities.OakPermission;
import com.oak.rbac.entities.OakRole;
import com.oak.rbac.services.permission.PermissionService;
import com.oak.rbac.services.role.info.RoleInfo;
import com.oak.rbac.services.role.req.CreateRoleReq;
import com.oak.rbac.services.role.req.DeleteRoleReq;
import com.oak.rbac.services.role.req.EditRoleReq;
import com.oak.rbac.services.role.req.QueryRoleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.oak.rbac.authority.OakRequestUrlResourceProvider.URL_ACCESS_ROLES_CACHE_NAME;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private PermissionService permissionService;


    @Override
    @Transactional
    public ApiResp<Long> createRole(CreateRoleReq req) {

        RoleInfo roleInfo = this.findRoleByName(req.getName());
        if (roleInfo != null) {
            return RestfulApiRespFactory.error("该角色已存在");
        }

        OakRole oakRole = new OakRole();
        oakRole.setName(req.getName());
        Date createTime = new Date();
        oakRole.setCreateTime(createTime);
        oakRole.setLastUpdateTime(createTime);
        oakRole.setEditable(true);
        oakRole.setEnable(true);


        List<OakPermission> permissions = jpaDao.selectFrom(OakPermission.class)
                .in(E_OakPermission.id, req.getPermissionIds())
                .find();
        oakRole.setPermissions(new HashSet<>(permissions));
        jpaDao.save(oakRole);

        // 创建角色权限关联列表
        Long roleId = oakRole.getId();
        if (roleId == null) {
            return RestfulApiRespFactory.error("角色创建失败");
        }


        return RestfulApiRespFactory.created(roleId);
    }

    @Caching(
            evict = {
                    @CacheEvict(
                            key = ROLE_CACHE_NAME,
                            value = "#req.id",
                            condition = "#result.success"
                    ),
                    @CacheEvict(
                            key = ROLE_CACHE_NAME,
                            value = "#req.name",
                            condition = "#result.success"
                    ),
                    @CacheEvict(
                            value = URL_ACCESS_ROLES_CACHE_NAME,
                            allEntries = true
                    )
            }
    )
    @Override
    @Transactional
    public ApiResp<Void> editRole(EditRoleReq req) {

        OakRole role = jpaDao.selectFrom(OakRole.class)
                .eq(E_OakRole.id, req.getId())
                .join(E_OakRole.permissions)
                .findOne();

        Set<OakPermission> permissions = new HashSet<>(role.getPermissions());

        List<Long> permissionIdList = Arrays.asList(req.getPermissionIds());

        // 删除
        permissions.forEach(oakPermission -> {
            boolean match = permissionIdList.stream().anyMatch(id -> id.equals(oakPermission.getId()));
            if (!match) {
                // 删除
                role.getPermissions().remove(oakPermission);
                if (log.isDebugEnabled()) {
                    log.debug("给角色{}删除权限：{}", role.getName(), oakPermission.getName());
                }
            }
        });

        // 添加
        List<Long> needAddList = permissions.isEmpty() ? permissionIdList :
                permissionIdList.stream().filter(id -> permissions.stream()
                        .anyMatch(oakPermission -> !id.equals(oakPermission.getId())))
                        .collect(Collectors.toList());

        if (!needAddList.isEmpty()) {
            List<OakPermission> oakPermissions = jpaDao.selectFrom(OakPermission.class)
                    .in(E_OakPermission.id, needAddList.toArray(new Long[0]))
                    .find();
            role.getPermissions().addAll(oakPermissions);
            if (log.isDebugEnabled()) {
                log.debug("给角色{}添加{}条权限", role.getName(), oakPermissions.size());
            }
        }
        jpaDao.save(role);


        return RestfulApiRespFactory.ok();
    }


    @Caching(
            evict = {
                    @CacheEvict(
                            value = ROLE_CACHE_NAME,
                            key = "#req.id",
                            condition = "#result.success"
                    ),
                    @CacheEvict(
                            value = ROLE_CACHE_NAME,
                            key = "#req.name",
                            condition = "#result.success"
                    ),
                    @CacheEvict(
                            value = URL_ACCESS_ROLES_CACHE_NAME,
                            allEntries = true
                    )
            }
    )
    @Transactional
    @Override
    public ApiResp<Void> deleteRole(DeleteRoleReq req) {


        // 删除角色，同时级联删除角色和权限的关联
        jpaDao.deleteById(OakRole.class, req.getId());

        return RestfulApiRespFactory.ok();
    }

    @Override
    public RoleInfo findRoleById(Long id) {

        return this.queryRole(new QueryRoleReq(id)).getFirst();
    }

    @Override
    public RoleInfo findRoleByName(String name) {
        return this.queryRole(new QueryRoleReq(name)).getFirst();
    }

    @Override
    @Caching(
            cacheable = {
                    @Cacheable(
                            value = ROLE_CACHE_NAME,
                            key = "#req.id",
                            condition = "#req.fetchPermission",
                            unless = "#result.empty"),
                    @Cacheable(
                            value = ROLE_CACHE_NAME,
                            key = "#req.name",
                            condition = "#req.fetchPermission",
                            unless = "#result.empty")
            }
    )
    public Pagination<RoleInfo> queryRole(QueryRoleReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao, OakRole.class, RoleInfo.class, req);
    }



}
