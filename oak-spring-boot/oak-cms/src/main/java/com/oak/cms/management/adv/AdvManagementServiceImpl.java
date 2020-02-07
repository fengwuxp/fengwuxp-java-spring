package com.oak.cms.management.adv;

import com.oak.cms.management.adv.req.AddAdvPositionReq;
import com.oak.cms.management.adv.req.DelAdvPositionReq;
import com.oak.cms.management.adv.req.QueryAdvPositionsReq;
import com.oak.cms.management.adv.req.UpdateAdvPositionReq;
import com.oak.cms.services.adv.AdvPositionService;
import com.oak.cms.services.adv.info.AdvPositionInfo;
import com.oak.cms.services.adv.req.CreateAdvPositionReq;
import com.oak.cms.services.adv.req.DeleteAdvPositionReq;
import com.oak.cms.services.adv.req.EditAdvPositionReq;
import com.oak.cms.services.adv.req.QueryAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 广告位服务实现
 *
 * @author chenPC
 */
@Slf4j
@Service
public class AdvManagementServiceImpl implements AdvManagementService {

    @Autowired
    private AdvPositionService advPositionService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Long> addAdvPosition(AddAdvPositionReq req) {
        CreateAdvPositionReq createAdvPositionReq = new CreateAdvPositionReq();
        BeanUtils.copyProperties(req, createAdvPositionReq);
        ApiResp<Long> createAdvPosition = advPositionService.create(createAdvPositionReq);
        if (!createAdvPosition.isSuccess()) {
            return RestfulApiRespFactory.error(createAdvPosition.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Void> editAdvPosition(UpdateAdvPositionReq req) {
        EditAdvPositionReq editAdvPositionReq = new EditAdvPositionReq();
        BeanUtils.copyProperties(req, editAdvPositionReq);
        ApiResp<Void> updateAdvPositionReq = advPositionService.edit(editAdvPositionReq);
        if (!updateAdvPositionReq.isSuccess()) {
            return RestfulApiRespFactory.error(updateAdvPositionReq.getMessage());
        }
        return RestfulApiRespFactory.ok();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Void> deleteAdvPosition(DelAdvPositionReq req) {
        DeleteAdvPositionReq deleteAdvPositionReq = new DeleteAdvPositionReq();
        BeanUtils.copyProperties(req, deleteAdvPositionReq);
        ApiResp<Void> deleteAdvPosition = advPositionService.delete(deleteAdvPositionReq);
        if (!deleteAdvPosition.isSuccess()) {
            return RestfulApiRespFactory.error(deleteAdvPosition.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public AdvPositionInfo findById(Long id) {

        return advPositionService.findById(id);
    }

    @Override
    public Pagination<AdvPositionInfo> query(QueryAdvPositionsReq req) {
        QueryAdvPositionReq queryAdvPositionReq = new QueryAdvPositionReq();
        BeanUtils.copyProperties(req, queryAdvPositionReq);
        return advPositionService.query(queryAdvPositionReq);
    }
}
