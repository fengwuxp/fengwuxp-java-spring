package com.oak.organization.services.department;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SettingValueHelper;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.organization.constant.OrganizationCacheKeyConstant;
import com.oak.organization.entities.Department;
import com.oak.organization.entities.E_Department;
import com.oak.organization.services.department.info.DepartmentInfo;
import com.oak.organization.services.department.req.CreateDepartmentReq;
import com.oak.organization.services.department.req.DeleteDepartmentReq;
import com.oak.organization.services.department.req.EditDepartmentReq;
import com.oak.organization.services.department.req.QueryDepartmentReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;


/**
 * 部门服务
 * 2020-1-19 14:02:11
 */
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {


    @Autowired
    private SettingValueHelper settingValueHelper;

    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateDepartmentReq req) {

        Department entity = new Department();
        BeanUtils.copyProperties(req, entity);

        if (entity.getEnable() == null) {
            entity.setEditable(true);
        }
        if (entity.getOrderCode() == null) {
            entity.setOrderCode(0);
        }

        //存在上级部门
        Department parent = null;
        if (entity.getParentId() != null) {
            parent = jpaDao.find(Department.class, entity.getParentId());
            entity.setLevel(parent.getLevel() + 1);
            entity.setLevelPath(String.format("%s%s#", parent.getLevelPath(), entity.getLevel()));
        } else {
            entity.setLevel(0);
            entity.setLevelPath(String.format("#%s#", entity.getLevel()));
        }

        if (entity.getLevel() > 0 && entity.getParentId() == null) {
            return RestfulApiRespFactory.error("");
        }

        int maxDepartmentLevel = settingValueHelper.getSettingValue(OrganizationCacheKeyConstant.MAX_DEPARTMENT_LEVEL, 2);

        if (entity.getLevel() > maxDepartmentLevel) {
            return RestfulApiRespFactory.error(MessageFormat.format("部门最多只允许创建{0}级", maxDepartmentLevel + 1));
        }
        Date time = new Date();
        entity.setCreateTime(time);
        entity.setLastUpdateTime(time);
        entity.setDeleted(false);
        jpaDao.create(entity);
        // 更新组织路径
        if (parent != null) {
            entity.setIdPath(String.format("%s%s#", parent.getIdPath(), entity.getId()));
        } else {
            entity.setIdPath(String.format("#%s#", entity.getId()));
        }
        jpaDao.save(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditDepartmentReq req) {


        Department entity = jpaDao.find(Department.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("部门数据不存在");
        }

        UpdateDao<Department> updateDao = jpaDao.updateTo(Department.class)
                .appendByQueryObj(req)
                .appendColumn(E_Department.lastUpdateTime,new Date());

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新部门失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteDepartmentReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Department.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Department.class)
                    .appendColumn(E_Department.deleted, true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除部门");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public DepartmentInfo findById(Long id) {

        QueryDepartmentReq queryReq = new QueryDepartmentReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<DepartmentInfo> query(QueryDepartmentReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Department.class, DepartmentInfo.class, req);

    }
}
