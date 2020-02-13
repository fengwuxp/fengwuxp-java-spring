package com.oak.cms.services.adv;

import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.oak.cms.services.adv.req.QueryAdvReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 广告信息服务
 * 2020-2-10 18:55:01
 *
 * @author chenPC
 */
public interface AdvService {


    /**
     * 添加广告
     *
     * @param req
     * @return
     */
    ApiResp<Long> create(CreateAdvReq req);


    /**
     * 编辑广告
     *
     * @param req
     * @return
     */
    ApiResp<Void> edit(EditAdvReq req);


    /**
     * 删除广告
     *
     * @param req
     * @return
     */
    ApiResp<Void> delete(DeleteAdvReq req);


    /**
     * 查找广告
     *
     * @param id
     * @return
     */
    AdvInfo findById(Long id);


    /**
     * 分页查询
     *
     * @param req
     * @return
     */
    Pagination<AdvInfo> query(QueryAdvReq req);

}
