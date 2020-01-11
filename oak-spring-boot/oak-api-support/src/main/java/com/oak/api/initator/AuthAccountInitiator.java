package com.oak.api.initator;


import com.oak.api.services.app.AppAuthService;
import com.oak.api.services.app.info.AppAuthAccountInfo;
import com.oak.api.services.app.req.CreateAppAuthAccountReq;
import com.oak.api.services.app.req.QueryAppAuthAccountReq;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 鉴权账号初始化器
 */
@Slf4j
public class AuthAccountInitiator extends AbstractBaseInitiator<CreateAppAuthAccountReq> {


    @Autowired
    private AppAuthService appAuthService;

    @Override
    public void init() {

        List<CreateAppAuthAccountReq> list = getInitData();
        if (list == null || list.isEmpty()) {
            return;
        }
        for (CreateAppAuthAccountReq createAuthAccountEvt : list) {
            QueryAppAuthAccountReq queryAuthAccountEvt = new QueryAppAuthAccountReq(createAuthAccountEvt.getAppId());
            queryAuthAccountEvt.setQueryType(QueryType.QUERY_NUM);
            Pagination<AppAuthAccountInfo> page = appAuthService.queryAppAuthAccount(queryAuthAccountEvt);
            if (page.isEmpty()) {
                Long appAuthAccount = appAuthService.createAppAuthAccount(createAuthAccountEvt);
                log.info("创建接入账号【" + createAuthAccountEvt + "】-> {} ,{}", createAuthAccountEvt, appAuthAccount);
            }
        }
    }
}
