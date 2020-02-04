package com.wuxp.security.example.services.user;

import com.levin.commons.dao.JpaDao;
import com.oak.rbac.entities.E_OakAdminUser;
import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.OakAdminUserService;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.example.services.user.info.LoginAdminUserInfo;
import com.wuxp.security.example.services.user.req.LoginAdminUserReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;


/**
* @author: zhuox
* @create: 2020-02-04
* @description: 管理员用户服务
**/
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private JpaDao jpaDao;



    @Override
    public ApiResp<LoginAdminUserInfo> login(LoginAdminUserReq req) {
        OakAdminUser adminUser = jpaDao.selectFrom(OakAdminUser.class).eq(E_OakAdminUser.userName, req.getUserName()).findOne();
        if (adminUser == null) {
            RestfulApiRespFactory.error("用户不存在");
        }
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(adminUser.getCryptoSalt());
        if (!passwordEncoder.matches(adminUser.getPassword(), req.getPassword())) {
            RestfulApiRespFactory.error("密码错误");
        }
        LoginAdminUserInfo loginAdminUserInfo = new LoginAdminUserInfo();
        BeanUtils.copyProperties(adminUser, loginAdminUserInfo);
        //TODO

        return RestfulApiRespFactory.ok(loginAdminUserInfo);
    }

}
