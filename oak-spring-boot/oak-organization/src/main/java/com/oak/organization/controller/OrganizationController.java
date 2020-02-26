package com.oak.organization.controller;

import com.oak.organization.enums.ApprovalStatus;
import com.oak.organization.management.organization.OrganizationManagementService;
import com.oak.organization.management.organization.dto.ResetPasswordDTO;
import com.oak.organization.management.organization.req.AddOrganizationReq;
import com.oak.organization.management.organization.req.UpdateOrganizationExtendedInfoReq;
import com.oak.organization.management.organization.req.UpdateOrganizationReq;
import com.oak.organization.management.user.AdminUserManagementService;
import com.oak.organization.management.user.info.LoginAdminUserInfo;
import com.oak.organization.management.user.req.LoginAdminUserReq;
import com.oak.organization.services.organization.OrganizationService;
import com.oak.organization.services.organization.info.OrganizationInfo;
import com.oak.organization.services.organization.req.EditOrganizationReq;
import com.oak.organization.services.organization.req.QueryOrganizationReq;
import com.oak.organization.services.staff.StaffService;
import com.oak.organization.services.staff.info.StaffInfo;
import com.oak.organization.services.staff.req.QueryStaffReq;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author zhuox
 * @description 组织相关服务
 */
@RestController("OrganizationController")
@RequestMapping("/v1/organization")
@Slf4j
@Tag(name = "机构相关", description = "组织相关服务")
public class OrganizationController {


    @Autowired
    private OrganizationManagementService organizationManagementService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OakAdminUserService oakAdminUserService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private AdminUserManagementService adminUserManagementService;


    /**
     * 查询服务商/代理商列表
     *
     * @param req
     * @return
     */
    @GetMapping("")
    @Operation(summary = "查询列表", description = "查询列表")
    public ApiResp<Pagination<OrganizationInfo>> query(QueryOrganizationReq req) {
        return RestfulApiRespFactory.ok(organizationService.query(req));
    }

    /**
     * 新增服务商/代理商
     *
     * @param req
     * @return
     */
    @PostMapping("")
    @Operation(summary = "新增", description = "新增")
    public ApiResp<Long> add(@RequestBody @Valid AddOrganizationReq req) {
        return organizationManagementService.addOrganization(req);
    }

    /**
     * 编辑服务商/代理商
     *
     * @param req
     * @return
     */
    @PutMapping("")
    @Operation(summary = "修改", description = "修改")
    public ApiResp<Void> edit(@RequestBody @Valid UpdateOrganizationReq req) {
        return organizationManagementService.updateOrganization(req);
    }

    /**
     * 编辑超级后台/服务商信息
     *
     * @param req
     * @return
     */
    @PutMapping("/edit_extended_info")
    @Operation(summary = "编辑超级后台/服务商信息", description = "编辑超级后台/服务商信息")
    public ApiResp<Void> editExtendedInfo(@RequestBody @Valid UpdateOrganizationExtendedInfoReq req) {
        return organizationManagementService.updateOrganizationExtendedInfo(req);
    }

    /**
     * 注销
     *
     * @return
     */
    @PutMapping("/status")
    @Operation(summary = "注销/取消注销（启用/禁用）", description = "注销/取消注销（启用/禁用）")
    public ApiResp<Void> logoff(@RequestBody Long id) {
        OrganizationInfo info = organizationService.findById(id);
        EditOrganizationReq editOrganizationReq = new EditOrganizationReq(id);
        editOrganizationReq.setStatus(ApprovalStatus.AGREE.equals(info.getStatus()) ? ApprovalStatus.DISABLED : ApprovalStatus.AGREE);
        return organizationService.edit(editOrganizationReq);
    }

    /**
     * 提交审核
     *
     * @param id
     * @return
     */
    @PutMapping("/submit_audit")
    @Operation(summary = "提交审核", description = "提交审核")
    public ApiResp<Void> submitAudit(@RequestBody Long id) {
        OrganizationInfo info = organizationService.findById(id);
        AssertThrow.assertFalse("账号已审核", ApprovalStatus.WAIT.equals(info.getStatus()));
        EditOrganizationReq editOrganizationReq = new EditOrganizationReq(id);
        editOrganizationReq.setStatus(ApprovalStatus.AUDIT);
        return organizationService.edit(editOrganizationReq);
    }

    /**
     * 重置密码
     *
     * @param dto
     * @return
     */
    @PutMapping("/reset_password")
    @Operation(summary = "重置密码", description = "重置密码")
    public ApiResp<Void> resetPassword(@RequestBody @Valid ResetPasswordDTO dto) {
        QueryStaffReq queryStaffReq = new QueryStaffReq();
        queryStaffReq.setOrganizationId(dto.getId());
        StaffInfo staffInfo = staffService.query(queryStaffReq).getFirst();
        EditOakAdminUserReq req = new EditOakAdminUserReq(staffInfo.getAdminId());
        return oakAdminUserService.edit(req);
    }

    /**
     * 登录
     * @param req
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "登录", description = "登录")
    public ApiResp<LoginAdminUserInfo> login(@RequestBody LoginAdminUserReq req) {
        return adminUserManagementService.login(req);
    }

}
