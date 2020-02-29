package com.oak.organization.management.user;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SettingValueHelper;
import com.oak.organization.constant.OrganizationConstant;
import com.oak.organization.entities.E_Staff;
import com.oak.organization.entities.Organization;
import com.oak.organization.entities.Staff;
import com.oak.organization.enums.ApprovalStatus;
import com.oak.organization.management.user.info.LoginAdminUserInfo;
import com.oak.organization.management.user.req.LoginAdminUserReq;
import com.oak.rbac.entities.E_OakAdminUser;
import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;


/**
 * @author: zhuox
 * @create: 2020-02-04
 * @description: 管理员用户服务
 **/
@Service
@Slf4j
public class AdminUserManagementServiceImpl implements AdminUserManagementService {

    @Autowired
    private JpaDao jpaDao;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SettingValueHelper settingValueHelper;
    @Autowired
    private OakAdminUserService oakAdminUserService;


    @Transactional(rollbackFor = Exception.class)
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
        Staff staff = jpaDao.selectFrom(Staff.class).eq(E_Staff.adminId, adminUser.getId()).findOne();
        Organization organization = staff.getOrganization();
        if (organization.getStatus().equals(ApprovalStatus.DISABLED)) {
            RestfulApiRespFactory.error("账号被禁用");
        }
        if (!organization.getStatus().equals(ApprovalStatus.AGREE)) {
            RestfulApiRespFactory.error("账号未审核通过");
        }
        LoginAdminUserInfo loginAdminUserInfo = new LoginAdminUserInfo();
        BeanUtils.copyProperties(adminUser, loginAdminUserInfo);
        //生成TOKEN
        String token = jwtTokenProvider.generateAccessToken(adminUser.getId() + adminUser.getUserName()
                + System.currentTimeMillis()).getToken();
        Integer tokenHour = Integer.valueOf(settingValueHelper.getSettingValue(OrganizationConstant.LOGIN_TOKEN_VALID_HOUR, 24));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, tokenHour);
        EditOakAdminUserReq editOakAdminUserReq = new EditOakAdminUserReq(adminUser.getId());
        editOakAdminUserReq.setToken(token)
                .setTokenExpired(calendar.getTime());
        oakAdminUserService.edit(editOakAdminUserReq);
        loginAdminUserInfo.setToken(token)
                .setTokenExpired(calendar.getTime());
        return RestfulApiRespFactory.ok(loginAdminUserInfo);
    }

}
