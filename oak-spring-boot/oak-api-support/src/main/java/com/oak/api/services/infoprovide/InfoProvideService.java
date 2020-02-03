package com.oak.api.services.infoprovide;


import com.oak.api.services.infoprovide.info.AreaInfo;
import com.oak.api.services.infoprovide.info.ClientChannelInfo;
import com.oak.api.services.infoprovide.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

/**
 * 数据维护服务
 */
public interface InfoProvideService {

    String AREA_CACHE_NAME = "AREA_CACHE";


    String CLIENT_CHANNEL_CACHE_NAME = "CLIENT_CHANNEL_CACHE";

    /**
     * 地区查询
     *
     * @param req
     * @return
     */
    Pagination<AreaInfo> queryArea(QueryAreaReq req);

    AreaInfo findAreaById(FindAreaReq req);

    /**
     * 编辑地区
     *
     * @param req
     * @return
     */
    ApiResp<Void> editArea(EditAreaReq req);


    Pagination<ClientChannelInfo> queryClientChannel(QueryClientChannelReq req);

    ClientChannelInfo findClientChannelById(FindClientChannelReq req);

    ApiResp<String> createClientChannel(CreateClientChannelReq req);

    ApiResp<Void> editClientChannel(EditClientChannelReq req);

}
