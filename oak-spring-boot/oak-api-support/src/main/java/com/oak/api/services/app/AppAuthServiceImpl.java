package com.oak.api.services.app;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.entities.AppAuthAccount;
import com.oak.api.entities.E_AppAuthAccount;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.services.app.info.AppAuthAccountInfo;
import com.oak.api.services.app.req.CreateAppAuthAccountReq;
import com.oak.api.services.app.req.EditAppAuthAccountReq;
import com.oak.api.services.app.req.FindAuthReq;
import com.oak.api.services.app.req.QueryAppAuthAccountReq;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Slf4j
@Service
public class AppAuthServiceImpl implements AppAuthService {

    @Autowired
    private JpaDao jpaDao;

    @Override
    public Long createAppAuthAccount(CreateAppAuthAccountReq req) {

        req.setEnabled(Boolean.TRUE.equals(req.getEnabled()));
        AppAuthAccount authAccount = new AppAuthAccount();
        BeanUtils.copyProperties(req, authAccount);
        authAccount.setAppId(req.getAppId());
        authAccount.setAppSecret(req.getAppSecret());
        authAccount.setAddTime(new Date());
        authAccount.setUpdateTime(authAccount.getAddTime());
        authAccount.setDeleted(false);
        jpaDao.create(authAccount);

        return authAccount.getId();
    }

    @Override
    public boolean editAppAuthAccount(EditAppAuthAccountReq req) {

        UpdateDao<AppAuthAccount> updateDao = jpaDao.updateTo(AppAuthAccount.class)
                .appendByQueryObj(req);
        if (!StringUtils.isEmpty(req.getAppId())) {
            updateDao.appendColumn(E_AppAuthAccount.appSecret, req.getAppSecret());
        }
        int c = updateDao
                .appendWhereEquals("id", req.getId())
                .update();

        return c >= 1;

    }

    @Override
    public Pagination<AppAuthAccountInfo> queryAppAuthAccount(QueryAppAuthAccountReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, AppAuthAccount.class, AppAuthAccountInfo.class, req);
    }

    @Override
    public AppAuthAccountInfo findAppAuthAccount(FindAuthReq req) {
        QueryAppAuthAccountReq queryAppAuthAccountReq = new QueryAppAuthAccountReq();
        queryAppAuthAccountReq.setAppId(req.getAppId());
        Pagination<AppAuthAccountInfo> pageInfo = this.queryAppAuthAccount(queryAppAuthAccountReq);
        return pageInfo.getFirst();
    }

    @Override
    public AppAuthAccountInfo getAppInfo(@NotNull String appId) {
        return this.findAppAuthAccount(new FindAuthReq(appId));
    }
}
