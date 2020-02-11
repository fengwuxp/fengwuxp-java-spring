package com.oak.cms.management.adv;

import com.oak.cms.management.adv.req.AddAdvInfoReq;
import com.oak.cms.management.adv.req.DelAdvReq;
import com.oak.cms.management.adv.req.QueryAdvInfoReq;
import com.oak.cms.management.adv.req.UpdateAdvReq;
import com.oak.cms.services.adv.AdvService;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.oak.cms.services.adv.req.QueryAdvReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenPC
 */
@Slf4j
@Service
public class AdvManagementServiceImpl implements AdvManagementService {
    @Autowired
    private AdvService advService;

    @Override
    public Pagination<AdvInfo> query(QueryAdvInfoReq req) {
        QueryAdvReq queryAdvReq = new QueryAdvReq();
        queryAdvReq.setTitle(req.getTitle()).setEnabled(req.getEnabled());
        return advService.query(queryAdvReq);
    }

    @Override
    public ApiResp<Long> create(AddAdvInfoReq req) {
        CreateAdvReq createAdvReq = new CreateAdvReq();
        BeanUtils.copyProperties(req, createAdvReq);
        ApiResp<Long> createAdv = advService.create(createAdvReq);
        if (!createAdv.isSuccess()) {
            return RestfulApiRespFactory.error(createAdv.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Void> editEnabled(UpdateAdvReq req) {
        EditAdvReq editAdvReq = new EditAdvReq();
        editAdvReq.setId(req.getId()).setEnabled(req.getEnabled());
        ApiResp<Void> editEnabled = advService.edit(editAdvReq);
        if (!editEnabled.isSuccess()) {
            return RestfulApiRespFactory.error(editEnabled.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Void> deleteAdvById(DelAdvReq req) {
        DeleteAdvReq deleteAdvReq = new DeleteAdvReq();
        deleteAdvReq.setId(req.getId());
        ApiResp<Void> delAdvReq = advService.delete(deleteAdvReq);
        if (!delAdvReq.isSuccess()) {

            return RestfulApiRespFactory.error(delAdvReq.getMessage());

        }
        return RestfulApiRespFactory.ok();
    }


}
