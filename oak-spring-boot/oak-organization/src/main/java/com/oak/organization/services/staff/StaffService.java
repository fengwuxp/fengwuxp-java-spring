package com.oak.organization.services.staff;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import com.oak.organization.services.staff.req.*;
import com.oak.organization.services.staff.info.StaffInfo;



/**
 *  员工服务
 *  2020-1-19 14:23:00
 */
public interface StaffService {


    ApiResp<Long> create(CreateStaffReq req);


    ApiResp<Void> edit(EditStaffReq req);


    ApiResp<Void> delete(DeleteStaffReq req);


    StaffInfo findById(Long id);


    Pagination<StaffInfo> query(QueryStaffReq req);

}
