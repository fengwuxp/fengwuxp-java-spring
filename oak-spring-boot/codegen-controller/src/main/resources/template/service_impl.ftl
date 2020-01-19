package ${packageName};

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import org.springframework.beans.BeanUtils;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wuxp.api.restful.RestfulApiRespFactory;
import ${entityClassName};
import ${packageName}.req.*;
import ${packageName}.info.${entityName}Info;
import java.util.Date;
<#list fields as field>
    <#if (field.lzay)??>
import ${field.classType.package.name}.${field.classType.simpleName};
    </#if>
    <#if (field.infoClassName)??>
import ${field.infoClassName};
    </#if>
</#list>


/**
 *  ${desc}服务
 *  ${.now}
 */
@Service
@Slf4j
public class ${className} implements ${serviceName} {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<${pkField.type}> create(Create${entityName}Req req) {


    <#list fields as field>
        <#if !field.notUpdate && field.uk>
        long ${field.name}C = jpaDao.selectFrom(${entityName}.class)
                .eq("${field.name}", req.get${field.name?cap_first}())
                .count();
        if (${field.name}C > 0) {
            return RestfulApiRespFactory.error("${field.desc}已被使用");
        }

        </#if>
    </#list>
        ${entityName} entity = new ${entityName}();
        BeanUtils.copyProperties(req, entity);

    <#list fields as field>
        <#if field.name == 'sn' && field.type == 'String'>
            String sn = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
            entity.setSn(sn);
        </#if>
        <#if field.name == 'addTime'>
            entity.setAddTime(new Date());
        </#if>
        <#if field.name == 'createTime'>
            entity.setCreateTime(new Date());
        </#if>
        <#if field.name == 'updateTime'>
            entity.setUpdateTime(new Date());
        </#if>
    </#list>

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.get${pkField.name?cap_first}());
    }

    @Override
    public ApiResp<Void> edit(Edit${entityName}Req req) {


<#list fields as field>
    <#if !field.notUpdate && field.uk>
        if (!StringUtils.isEmpty(req.get${field.name?cap_first}())) {
            long ${field.name}C = jpaDao.selectFrom(${entityName}.class)
                    .eq("${field.name}", req.get${field.name?cap_first}())
                    .appendWhere("${pkField.name} != ?", req.get${pkField.name?cap_first}())
                    .count();
            if (${field.name}C > 0) {
                return  RestfulApiRespFactory.error("${field.desc}已被使用");
            }
        }

    </#if>
</#list>
        ${entityName} entity = jpaDao.find(${entityName}.class, req.get${pkField.name?cap_first}());
        if (entity == null) {
            return  RestfulApiRespFactory.error("${desc}数据不存在");
        }

        UpdateDao<${entityName}> updateDao = jpaDao.updateTo(${entityName}.class).appendByQueryObj(req);

<#list fields as field>
    <#if field.name == 'updateTime'>
        updateDao.appendColumn("updateTime", new Date());
    </#if>
</#list>
        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新${desc}失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(Delete${entityName}Req req) {


        if (req.get${pkField.name?cap_first}() == null
                && (req.get${pkField.name?cap_first}s() == null || req.get${pkField.name?cap_first}s().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(${entityName}.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(${entityName}.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除${desc}");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ${entityName}Info findById(${pkField.type} ${pkField.name}) {

        Query${entityName}Req queryReq = new Query${entityName}Req();
        queryReq.set${pkField.name?cap_first}(${pkField.name});
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<${entityName}Info> query(Query${entityName}Req req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,${entityName}.class,${entityName}Info.class,req);

    }
}
