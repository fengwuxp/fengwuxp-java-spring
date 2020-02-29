package com.oak.organization.services.organizationextendedinfo;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.organization.entities.OrganizationExtendedInfo;
import com.oak.organization.services.organizationextendedinfo.info.OrganizationExtendedInfoInfo;
import com.oak.organization.services.organizationextendedinfo.req.CreateOrganizationExtendedInfoReq;
import com.oak.organization.services.organizationextendedinfo.req.DeleteOrganizationExtendedInfoReq;
import com.oak.organization.services.organizationextendedinfo.req.EditOrganizationExtendedInfoReq;
import com.oak.organization.services.organizationextendedinfo.req.QueryOrganizationExtendedInfoReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *  组织扩展信息服务
 *  2020-2-2 15:59:04
 */
@Service
@Slf4j
public class OrganizationExtendedInfoServiceImpl implements OrganizationExtendedInfoService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateOrganizationExtendedInfoReq req) {


        OrganizationExtendedInfo entity = new OrganizationExtendedInfo();
        BeanUtils.copyProperties(req, entity);


        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditOrganizationExtendedInfoReq req) {


        OrganizationExtendedInfo entity = jpaDao.find(OrganizationExtendedInfo.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("组织扩展信息数据不存在");
        }

        UpdateDao<OrganizationExtendedInfo> updateDao = jpaDao.updateTo(OrganizationExtendedInfo.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新组织扩展信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteOrganizationExtendedInfoReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(OrganizationExtendedInfo.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(OrganizationExtendedInfo.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除组织扩展信息");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public OrganizationExtendedInfoInfo findById(Long id) {

        QueryOrganizationExtendedInfoReq queryReq = new QueryOrganizationExtendedInfoReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<OrganizationExtendedInfoInfo> query(QueryOrganizationExtendedInfoReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,OrganizationExtendedInfo.class,OrganizationExtendedInfoInfo.class,req);

    }
}
