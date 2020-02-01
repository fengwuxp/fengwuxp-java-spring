package com.oak.organization.management.organization;

import com.oak.organization.entities.Organization;
import com.oak.organization.enums.StaffAccountType;
import com.oak.organization.management.organization.req.RegisterOrganizationReq;
import com.oak.organization.services.organization.OrganizationService;
import com.oak.organization.services.organization.req.CreateOrganizationReq;
import com.oak.organization.services.staff.StaffService;
import com.oak.organization.services.staff.req.CreateStaffReq;
import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.req.CreateOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrganizationManagementServiceImpl implements OrganizationManagementService {


    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private OakAdminUserService oakAdminUserService;


    @Transactional
    @Override
    public ApiResp<Long> registerOrganization(RegisterOrganizationReq req) {

        // 创建机构
        CreateOrganizationReq createOrganizationReq = new CreateOrganizationReq();
        createOrganizationReq.setName(req.getName())
                .setAreaId(req.getAreaId())
                .setAddress(req.getAddress())
                .setAreaName(req.getAreaName())
                .setContacts(req.getContacts())
                .setContactMobilePhone(req.getMobilePhone())
                .setOrganizationType(req.getOrganizationType())
                .setRemark(req.getRemark());

        ApiResp<Organization> createOrganizationResp = organizationService.create(createOrganizationReq);
        if (!createOrganizationResp.isSuccess()) {
            return RestfulApiRespFactory.error(createOrganizationResp.getMessage());
        }

        // 创建rbac 关联账号
        CreateOakAdminUserReq createOakAdminUserReq = new CreateOakAdminUserReq();
        createOakAdminUserReq.setMobilePhone(req.getMobilePhone())
                .setName(req.getContacts())
                .setUserName(req.getMobilePhone())
                .setMobilePhone(req.getMobilePhone())
                .setRoot(true)
                .setNickName(req.getContacts())
                .setPassword(req.getPassword())
                .setCreatorId(req.getCreatorId());
        ApiResp<OakAdminUser> createOakAdminResp = oakAdminUserService.create(createOakAdminUserReq);
        AssertThrow.assertResp(createOakAdminResp);

        Organization organization = createOrganizationResp.getData();
        // 创建机构超级管理员
        CreateStaffReq createStaffReq = new CreateStaffReq();
        createStaffReq.setAccountType(StaffAccountType.ROOT)
                .setName(req.getName())
                .setMobilePhone(req.getMobilePhone())
                .setUserName(req.getMobilePhone())
                .setOrganizationId(organization.getId())
                .setOrganizationCode(organization.getCode())
                .setAdminId(createOakAdminResp.getData().getId())
                .setCreatorId(req.getCreatorId());
        ApiResp<Long> createStaffResp = staffService.create(createStaffReq);
        AssertThrow.assertResp(createStaffResp);

        return RestfulApiRespFactory.ok(organization.getId());
    }
}
