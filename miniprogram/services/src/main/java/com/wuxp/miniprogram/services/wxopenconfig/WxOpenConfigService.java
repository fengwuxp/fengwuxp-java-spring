package com.wuxp.miniprogram.services.wxopenconfig;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.miniprogram.services.wxopenconfig.info.WxOpenConfigInfo;
import com.wuxp.miniprogram.services.wxopenconfig.req.CreateWxOpenConfigReq;
import com.wuxp.miniprogram.services.wxopenconfig.req.DeleteWxOpenConfigReq;
import com.wuxp.miniprogram.services.wxopenconfig.req.EditWxOpenConfigReq;
import com.wuxp.miniprogram.services.wxopenconfig.req.QueryWxOpenConfigReq;


/**
 *  组织服务
 *  2020-3-2 14:28:21
 */
public interface WxOpenConfigService {


    ApiResp<Long> create(CreateWxOpenConfigReq req);


    ApiResp<Void> edit(EditWxOpenConfigReq req);


    ApiResp<Void> delete(DeleteWxOpenConfigReq req);


    WxOpenConfigInfo findById(Long id);


    Pagination<WxOpenConfigInfo> query(QueryWxOpenConfigReq req);

}
