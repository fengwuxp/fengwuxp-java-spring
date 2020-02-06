package com.oak.rbac.security;

import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.oak.rbac.services.user.req.QueryOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.security.authenticate.LockedUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;


/**
 * 锁定连续登录失败的账号
 */
@Slf4j
public class OakLockedUserDetailsService implements LockedUserDetailsService {

    @Autowired
    private OakAdminUserService oakAdminUserService;

    @Override
    public void lockUserByUsername(String username, Duration limitLoginTimes) throws UsernameNotFoundException {

        QueryOakAdminUserReq req = new QueryOakAdminUserReq();
        req.setUserName(username);
        OakAdminUserInfo adminUserInfo = oakAdminUserService.query(req).getFirst();
        if (adminUserInfo == null) {
            throw new UsernameNotFoundException(username);
        }

        EditOakAdminUserReq oakAdminUserReq = new EditOakAdminUserReq();

        Date lockExpired = Date.from(Instant.now().plusSeconds(limitLoginTimes.getSeconds()));
        oakAdminUserReq.setId(adminUserInfo.getId())
                .setLockExpired(lockExpired);
        ApiResp<Void> resp = oakAdminUserService.edit(oakAdminUserReq);
        if (resp.isSuccess()) {
            if (log.isDebugEnabled()) {
                log.debug("锁定账号 {} 到 {}", username, limitLoginTimes);
            }

            // TODO
        }

    }
}
