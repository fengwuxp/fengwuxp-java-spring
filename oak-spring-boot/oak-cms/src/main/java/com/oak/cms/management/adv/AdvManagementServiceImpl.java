package com.oak.cms.management.adv;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SettingValueHelper;
import com.oak.cms.constant.AdvCacheKeyConstant;
import com.oak.cms.entities.AdvPosition;
import com.oak.cms.management.adv.req.*;
import com.oak.cms.services.adv.AdvService;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.oak.cms.services.adv.req.QueryAdvReq;
import com.oak.cms.services.advposition.AdvPositionService;
import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.oak.cms.services.advposition.req.CreateAdvPositionReq;
import com.oak.cms.services.advposition.req.DeleteAdvPositionReq;
import com.oak.cms.services.advposition.req.EditAdvPositionReq;
import com.oak.cms.services.advposition.req.QueryAdvPositionReq;
import com.oaknt.ncms.enums.AdvCheckState;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author chenPC
 */
@Slf4j
@Service
public class AdvManagementServiceImpl implements AdvManagementService {
    @Autowired
    private AdvService advService;

    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private AdvPositionService advPositionService;

    @Autowired
    private SettingValueHelper settingValueHelper;

    /**
     * 分页查询
     *
     * @param req
     * @return
     */
    @Override
    public Pagination<AdvInfo> queryAdv(QueryAdvInfoReq req) {
        QueryAdvReq queryAdvReq = new QueryAdvReq();
        queryAdvReq.setTitle(req.getTitle()).setEnabled(req.getEnabled());
        return advService.query(queryAdvReq);
    }

    /**
     * 添加广告
     *
     * @param req
     * @return
     */
    @Override
    public ApiResp<Long> createAdv(AddAdvInfoReq req) {
        if (!StringUtils.hasText(req.getUrl())) {
            req.setUrl(null);
        }

        CreateAdvReq adv = new CreateAdvReq();
        BeanUtils.copyProperties(req, adv);
        if (!StringUtils.hasText(adv.getBuyStyle())) {
            adv.setBuyStyle("SYS");
        }

        adv.setClickNum(0);
        if (adv.getGoldpay() == null) {
            adv.setGoldpay(0);
        }

        String isAdAuto = settingValueHelper.getSettingValue(AdvCacheKeyConstant.AD_AUTO_APPROVE, "APPROVED");
        adv.setState(isAdAuto.equals(adv.getState()) ? AdvCheckState.APPROVED : AdvCheckState.UNAUDITED);
        adv.setEnabled(Boolean.TRUE);

        ApiResp<Long> createAdv = advService.create(adv);
        if (!createAdv.isSuccess()) {
            return RestfulApiRespFactory.error(createAdv.getMessage());
        }


        boolean incrementNum = jpaDao.updateTo(AdvPosition.class)
                .appendColumns("num=num+1")
                .appendWhereEquals("id", req.getApId())
                .update() == 1;

        if (!incrementNum) {
            throw new RuntimeException("广告位更新失败");
        }
        return RestfulApiRespFactory.ok();
    }

    /**
     * 启用/禁用
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Void> editAdv(UpdateAdvReq req) {
        if (!StringUtils.hasText(req.getUrl())) {
            req.setUrl(null);
        }

        AdvInfo checkAdv = advService.findById(req.getId());
        if (checkAdv.getEnabled() != null) {
            EditAdvReq editAdvEnableReq = new EditAdvReq();
            editAdvEnableReq.setId(req.getId()).setEnabled(req.getEnabled());
            ApiResp<Void> editEnabled = advService.edit(editAdvEnableReq);
            if (!editEnabled.isSuccess()) {
                return RestfulApiRespFactory.error(editEnabled.getMessage());
            }
        }
        if (AdvCheckState.APPROVED.equals(checkAdv.getState())) {

            return RestfulApiRespFactory.error("该广告已经被审核通过，不允许编辑");

        }
        EditAdvReq editAdvReq = new EditAdvReq();
        BeanUtils.copyProperties(req, editAdvReq);
        ApiResp<Void> editAdvInfo = advService.edit(editAdvReq);
        if (!editAdvInfo.isSuccess()) {
            return RestfulApiRespFactory.error(editAdvInfo.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }


    /**
     * 根据ID删除广告
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Void> deleteAdvById(DelAdvReq req) {
        DeleteAdvReq deleteAdvReq = new DeleteAdvReq();
        deleteAdvReq.setId(req.getId());
        ApiResp<Void> delAdvReq = advService.delete(deleteAdvReq);
        if (!delAdvReq.isSuccess()) {
            return RestfulApiRespFactory.error(delAdvReq.getMessage());
        }

        //更新广告位拥有的数量
        boolean decrementNum = jpaDao.updateTo(AdvPosition.class)
                .appendColumns("num=num-1")
                .appendWhereEquals("id", req.getApId())
                .update() == 1;
        if (!decrementNum) {

            throw new RuntimeException("广告位更新失败");

        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Long> createAdvPosition(AddAdvPositionReq req) {

        CreateAdvPositionReq createAdvPositionReq = new CreateAdvPositionReq();
        BeanUtils.copyProperties(req, createAdvPositionReq);
        createAdvPositionReq.setClickNum(0);
        createAdvPositionReq.setNum(0);
        if (createAdvPositionReq.getHeight() == null) {
            createAdvPositionReq.setHeight(-1);
        }
        if (createAdvPositionReq.getWidth() == null) {
            createAdvPositionReq.setWidth(-1);
        }
        if (createAdvPositionReq.getPrice() == null) {
            createAdvPositionReq.setPrice(0);
        }

        if (!StringUtils.hasText(createAdvPositionReq.getAreaId())) {
            createAdvPositionReq.setAreaId(null);
        }

        ApiResp<Long> createAdvPositionInfo = advPositionService.create(createAdvPositionReq);
        if (!createAdvPositionInfo.isSuccess()) {
            return RestfulApiRespFactory.error(createAdvPositionInfo.getMessage());
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResp<Void> deleteAdvPosition(DelAdvPositionReq req) {
        DeleteAdvPositionReq deleteAdvPositionReq = new DeleteAdvPositionReq();
        BeanUtils.copyProperties(req, deleteAdvPositionReq);
        ApiResp<Void> delAdvPosition = advPositionService.delete(deleteAdvPositionReq);
        if (!delAdvPosition.isSuccess()) {
            return RestfulApiRespFactory.error(delAdvPosition.getMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> editAdvPosition(UpdateAdvPositionReq req) {
        if (!StringUtils.hasText(req.getAreaId())) {
            req.setAreaId(null);
        }
        EditAdvPositionReq editAdvPositionReq = new EditAdvPositionReq();
        BeanUtils.copyProperties(req, editAdvPositionReq);
        ApiResp<Void> updateAdvPosition = advPositionService.edit(editAdvPositionReq);
        if (!updateAdvPosition.isSuccess()) {
            return RestfulApiRespFactory.error(updateAdvPosition.getMessage());
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public Pagination<AdvPositionInfo> queryPositions(QueryAdvPositionInfoReq req) {
        QueryAdvPositionReq queryAdvPositionReq = new QueryAdvPositionReq();
        BeanUtils.copyProperties(req, queryAdvPositionReq);
        return advPositionService.query(queryAdvPositionReq);
    }


}
