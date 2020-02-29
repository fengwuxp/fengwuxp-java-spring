package com.oak.organization.services.staff;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.organization.entities.E_Staff;
import com.oak.organization.entities.Staff;
import com.oak.organization.services.staff.info.StaffInfo;
import com.oak.organization.services.staff.req.CreateStaffReq;
import com.oak.organization.services.staff.req.DeleteStaffReq;
import com.oak.organization.services.staff.req.EditStaffReq;
import com.oak.organization.services.staff.req.QueryStaffReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * 员工服务
 * 2020-1-19 14:23:00
 */
@Service
@Slf4j
public class StaffServiceImpl implements StaffService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateStaffReq req) {


        long userNameC = jpaDao.selectFrom(Staff.class)
                .eq(E_Staff.userName, req.getUserName())
                .count();
        if (userNameC > 0) {
            return RestfulApiRespFactory.error("用户名称已被使用");
        }

        Staff entity = new Staff();
        BeanUtils.copyProperties(req, entity);

        entity.setCreateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditStaffReq req) {


        if (!StringUtils.isEmpty(req.getUserName())) {
            long userNameC = jpaDao.selectFrom(Staff.class)
                    .eq(E_Staff.userName, req.getUserName())
                    .appendWhere("id != ?", req.getId())
                    .count();
            if (userNameC > 0) {
                return RestfulApiRespFactory.error("用户名称已被使用");
            }
        }

        Staff entity = jpaDao.find(Staff.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("员工数据不存在");
        }

        UpdateDao<Staff> updateDao = jpaDao.updateTo(Staff.class).appendByQueryObj(req)
                .appendColumn(E_Staff.lastUpdateTime,new Date());

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新员工失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteStaffReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Staff.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Staff.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除员工");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public StaffInfo findById(Long id) {

        QueryStaffReq queryReq = new QueryStaffReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<StaffInfo> query(QueryStaffReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Staff.class, StaffInfo.class, req);

    }
}
