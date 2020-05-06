package com.wuxp.miniprogram.services.wxopenconfig;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.miniprogram.entitys.WxOpenConfig;
import com.wuxp.miniprogram.services.wxopenconfig.info.WxOpenConfigInfo;
import com.wuxp.miniprogram.services.wxopenconfig.req.CreateWxOpenConfigReq;
import com.wuxp.miniprogram.services.wxopenconfig.req.DeleteWxOpenConfigReq;
import com.wuxp.miniprogram.services.wxopenconfig.req.EditWxOpenConfigReq;
import com.wuxp.miniprogram.services.wxopenconfig.req.QueryWxOpenConfigReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 *  组织服务
 *  2020-3-2 14:28:21
 */
@Service
@Slf4j
public class WxOpenConfigServiceImpl implements WxOpenConfigService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateWxOpenConfigReq req) {


        long organizationCodeC = jpaDao.selectFrom(WxOpenConfig.class)
                .eq("organizationCode", req.getOrganizationCode())
                .count();
        if (organizationCodeC > 0) {
            return RestfulApiRespFactory.error("组织代码已被使用");
        }

        WxOpenConfig entity = new WxOpenConfig();
        BeanUtils.copyProperties(req, entity);

            entity.setCreateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditWxOpenConfigReq req) {


        if (!StringUtils.isEmpty(req.getOrganizationCode())) {
            long organizationCodeC = jpaDao.selectFrom(WxOpenConfig.class)
                    .eq("organizationCode", req.getOrganizationCode())
                    .appendWhere("id != :?", req.getId())
                    .count();
            if (organizationCodeC > 0) {
                return  RestfulApiRespFactory.error("组织代码已被使用");
            }
        }

        WxOpenConfig entity = jpaDao.find(WxOpenConfig.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("组织数据不存在");
        }

        UpdateDao<WxOpenConfig> updateDao = jpaDao.updateTo(WxOpenConfig.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新组织失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteWxOpenConfigReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(WxOpenConfig.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(WxOpenConfig.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除组织");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public WxOpenConfigInfo findById(Long id) {

        QueryWxOpenConfigReq queryReq = new QueryWxOpenConfigReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<WxOpenConfigInfo> query(QueryWxOpenConfigReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,WxOpenConfig.class,WxOpenConfigInfo.class,req);

    }
}
