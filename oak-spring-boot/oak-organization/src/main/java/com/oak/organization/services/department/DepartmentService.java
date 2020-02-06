package com.oak.organization.services.department;

import com.oak.organization.services.department.info.DepartmentInfo;
import com.oak.organization.services.department.req.CreateDepartmentReq;
import com.oak.organization.services.department.req.DeleteDepartmentReq;
import com.oak.organization.services.department.req.EditDepartmentReq;
import com.oak.organization.services.department.req.QueryDepartmentReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 部门服务
 * 2020-1-19 14:02:11
 *
 * @author
 */
public interface DepartmentService {


    /**
     * @param req
     * @return
     */
    ApiResp<Long> create(CreateDepartmentReq req);


    /**
     * @param req
     * @return
     */
    ApiResp<Void> edit(EditDepartmentReq req);


    /**
     * @param req
     * @return
     */
    ApiResp<Void> delete(DeleteDepartmentReq req);


    /**
     * @param id
     * @return
     */
    DepartmentInfo findById(Long id);


    /**
     * @param req
     * @return
     */
    Pagination<DepartmentInfo> query(QueryDepartmentReq req);

}
