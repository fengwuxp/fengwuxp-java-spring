package com.oak.cms.management.advposition;

import com.oak.cms.management.advposition.req.AddAdvPositionReq;
import com.oak.cms.management.advposition.req.DelAdvPositionReq;
import com.oak.cms.management.advposition.req.QueryAdvPositionsReq;
import com.oak.cms.management.advposition.req.UpdateAdvPositionReq;
import com.oak.cms.services.advposition.AdvPositionService;
import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.oak.cms.services.advposition.req.CreateAdvPositionReq;
import com.oak.cms.services.advposition.req.DeleteAdvPositionReq;
import com.oak.cms.services.advposition.req.EditAdvPositionReq;
import com.oak.cms.services.advposition.req.QueryAdvPositionReq;
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
public class AdvPositionManagementServiceImpl implements AdvPositionManagementService {

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
