package com.wuxp.miniprogram.services.organizationminiprogramconfig;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.organization.miniprogram.services.organizationminiprogramconfig.info.OrganizationMiniProgramConfigInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.miniprogram.entitys.OrganizationMiniProgramConfig;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.CreateOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.DeleteOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.EditOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.QueryOrganizationMiniProgramConfigReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 *  组织小程序发布配置服务
 *  2020-3-2 17:28:21
 */
@Service
@Slf4j
public class OrganizationMiniProgramConfigServiceImpl implements OrganizationMiniProgramConfigService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateOrganizationMiniProgramConfigReq req) {


        long organizationCodeC = jpaDao.selectFrom(OrganizationMiniProgramConfig.class)
                .eq("organizationCode", req.getOrganizationCode())
                .count();
        if (organizationCodeC > 0) {
            return RestfulApiRespFactory.error("组织代码已被使用");
        }

        OrganizationMiniProgramConfig entity = new OrganizationMiniProgramConfig();
        BeanUtils.copyProperties(req, entity);

            entity.setCreateTime(new Date());
        //设置一个预先默认值
        if(  StringUtils.isBlank( req.getMiniProgramItemList() ) ){
            entity.setMiniProgramItemList("[{\"pagePath\":\"pages/index/index\",\"first_class\":\"工具\",\"second_class\":\"效率\",\"first_id\":287,\"second_id\":616,\"title\":\"首页\"}]");
        }

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditOrganizationMiniProgramConfigReq req) {


        if (!StringUtils.isEmpty(req.getOrganizationCode())) {
            long organizationCodeC = jpaDao.selectFrom(OrganizationMiniProgramConfig.class)
                    .eq("organizationCode", req.getOrganizationCode())
                    .appendWhere("id != :?", req.getId())
                    .count();
            if (organizationCodeC > 0) {
                return  RestfulApiRespFactory.error("组织代码已被使用");
            }
        }

        OrganizationMiniProgramConfig entity = jpaDao.find(OrganizationMiniProgramConfig.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("组织小程序发布配置数据不存在");
        }

        UpdateDao<OrganizationMiniProgramConfig> updateDao = jpaDao.updateTo(OrganizationMiniProgramConfig.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新组织小程序发布配置失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteOrganizationMiniProgramConfigReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(OrganizationMiniProgramConfig.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(OrganizationMiniProgramConfig.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除组织小程序发布配置");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public OrganizationMiniProgramConfigInfo findById(Long id) {

        QueryOrganizationMiniProgramConfigReq queryReq = new QueryOrganizationMiniProgramConfigReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<OrganizationMiniProgramConfigInfo> query(QueryOrganizationMiniProgramConfigReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,OrganizationMiniProgramConfig.class,OrganizationMiniProgramConfigInfo.class,req);

    }
}
