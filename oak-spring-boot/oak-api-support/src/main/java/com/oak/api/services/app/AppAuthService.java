package com.oak.api.services.app;

import com.oak.api.services.app.info.AppAuthAccountInfo;
import com.oak.api.services.app.req.CreateAppAuthAccountReq;
import com.oak.api.services.app.req.EditAppAuthAccountReq;
import com.oak.api.services.app.req.FindAuthReq;
import com.oak.api.services.app.req.QueryAppAuthAccountReq;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.signature.AppInfoStore;

import javax.validation.constraints.NotNull;

/**
 * app授权相关服务
 */
public interface AppAuthService extends AppInfoStore {

    /**
     * 创建授权账号
     *
     * @param req
     * @return
     */
    Long createAppAuthAccount(CreateAppAuthAccountReq req);

    /**
     * 修改app授权信息
     *
     * @param req
     */
    boolean editAppAuthAccount(EditAppAuthAccountReq req);

    /**
     * 查询授权信息
     *
     * @param req
     * @return
     */

    Pagination<AppAuthAccountInfo> queryAppAuthAccount(QueryAppAuthAccountReq req);

    /**
     * 查找授权信息
     *
     * @param req
     * @return
     */
    AppAuthAccountInfo findAppAuthAccount(FindAuthReq req);


    @Override
    AppAuthAccountInfo getAppInfo(@NotNull String appId);
}
