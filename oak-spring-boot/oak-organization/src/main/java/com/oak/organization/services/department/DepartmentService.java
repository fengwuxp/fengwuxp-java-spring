package com.oak.organization.services.department;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import com.oak.organization.services.department.req.*;
import com.oak.organization.services.department.info.DepartmentInfo;



/**
 *  部门服务
 *  2020-1-19 14:02:11
 */
public interface DepartmentService {


    ApiResp<Long> create(CreateDepartmentReq req);


    ApiResp<Void> edit(EditDepartmentReq req);


    ApiResp<Void> delete(DeleteDepartmentReq req);


    DepartmentInfo findById(Long id);


    Pagination<DepartmentInfo> query(QueryDepartmentReq req);

}
