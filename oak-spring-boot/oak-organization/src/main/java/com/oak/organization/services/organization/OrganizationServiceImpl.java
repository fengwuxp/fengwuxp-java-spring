package com.oak.organization.services.organization;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.organization.entities.E_Organization;
import com.oak.organization.entities.Organization;
import com.oak.organization.services.organization.info.OrganizationInfo;
import com.oak.organization.services.organization.req.CreateOrganizationReq;
import com.oak.organization.services.organization.req.DeleteOrganizationReq;
import com.oak.organization.services.organization.req.EditOrganizationReq;
import com.oak.organization.services.organization.req.QueryOrganizationReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.basic.utils.ChinesePinyinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * 组织服务
 * 2020-1-19 13:18:21
 */
@Service
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Organization> create(CreateOrganizationReq req) {

        synchronized (this) {
            // 判断机构名称是否重复
            long count = jpaDao.selectFrom(Organization.class)
                    .select(E_Organization.id)
                    .eq(E_Organization.name, req.getName())
                    .count();
            if (count > 0) {
                return RestfulApiRespFactory.error("该机构名称已被使用");
            }
        }


        Organization entity = new Organization();
        BeanUtils.copyProperties(req, entity);

        entity.setType(req.getOrganizationType().name());
        Organization parent = null;
        // 存在上级组织
        if (entity.getParentId() != null) {
            parent = jpaDao.find(Organization.class, entity.getParentId());
            entity.setLevel(parent.getLevel() + 1);
            entity.setLevelPath(String.format("%s%s#", parent.getLevelPath(), entity.getLevel()));
        } else {
            entity.setLevel(0);
            entity.setLevelPath(String.format("#%s#", entity.getLevel()));
        }
        if (entity.getOrderCode() == null) {
            entity.setOrderCode(0);
        }
        if (StringUtils.hasText(entity.getPinyinInitials())) {
            entity.setPinyinInitials(ChinesePinyinUtil.getPinYinHeadChar(entity.getName()));
        }

        // 生成code
        long codeC = 0L;
        synchronized (this) {
            codeC = jpaDao.selectFrom(Organization.class)
                    .eq(E_Organization.level, entity.getLevel())
                    .count();
        }

        // code的编码规则，定长10位，前2位标识层级，3-10标识在该层的创建排序
        StringBuilder code = new StringBuilder();
        code.append(0).append(entity.getLevel());
        int len = Long.toString(codeC).length();
        for (int i = 8 - len; i >= 0; i--) {
            code.append(0);
        }
        code.append(codeC);
        entity.setCode(code.toString());
        Date createTime = new Date();
        entity.setCreateTime(createTime);
        entity.setLastUpdateTime(createTime);
        entity.setDeleted(false);
        entity.setEnable(true);
        jpaDao.create(entity);

        // 更新组织路径
        if (parent != null) {
            entity.setIdPath(String.format("%s%s#", parent.getIdPath(), entity.getId()));
        } else {
            entity.setIdPath(String.format("#%s#", entity.getId()));
        }
        jpaDao.save(entity);

        return RestfulApiRespFactory.ok(entity);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = ORGANIZATION_CACHE_SINGLE_CACHE,
                            key = "#req.id",
                            condition = "#req.id !=null")
            }
    )
    @Override
    public ApiResp<Void> edit(EditOrganizationReq req) {

        Organization entity = jpaDao.find(Organization.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("组织数据不存在");
        }

        UpdateDao<Organization> updateDao = jpaDao.updateTo(Organization.class)
                .appendColumn(E_Organization.lastUpdateTime, new Date())
                .appendByQueryObj(req);
        if (StringUtils.hasText(req.getName()) && !StringUtils.hasText(req.getPinyinInitials())) {
            //更新企业首字母拼音
            updateDao.appendColumn(E_Organization.pinyinInitials,
                    ChinesePinyinUtil.getPinYinHeadChar(req.getName()).toUpperCase());
        }

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新组织失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = ORGANIZATION_CACHE_SINGLE_CACHE,
                            key = "#req.id",
                            condition = "#req.id !=null")
            }
    )
    @Override
    public ApiResp<Void> delete(DeleteOrganizationReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Organization.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Organization.class)
                    .appendColumn(E_Organization.deleted, true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除组织");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public OrganizationInfo findById(Long id) {

        QueryOrganizationReq queryReq = new QueryOrganizationReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Caching(
            cacheable = {
                    @Cacheable(value = ORGANIZATION_CACHE_SINGLE_CACHE,
                            key = "#req.id",
                            condition = "#req.id != null and #req.fromCache",
                            unless = "#result.empty"),
            }
    )
    @Override
    public Pagination<OrganizationInfo> query(QueryOrganizationReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Organization.class, OrganizationInfo.class, req);

    }
}
