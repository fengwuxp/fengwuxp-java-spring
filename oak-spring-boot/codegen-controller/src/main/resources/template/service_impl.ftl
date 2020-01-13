package ${packageName};

import com.levin.commons.dao.Converter;
import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.levin.commons.dao.UpdateDao;
import com.oaknt.common.service.support.enums.QueryType;
import com.oaknt.common.service.support.model.PageInfo;
import com.oaknt.common.service.support.model.ServiceQueryResp;
import com.oaknt.common.service.support.model.ServiceResp;
import com.oaknt.yunxin.uitls.ServiceHelper;

import ${entityClassName};
import ${packageName}.evt.*;
import ${packageName}.info.${entityName}Info;

<#list fields as field>
    <#if (field.lzay)??>
import ${field.classType.package.name}.${field.classType.simpleName};
    </#if>
    <#if (field.infoClassName)??>
import ${field.infoClassName};
    </#if>
</#list>


import lombok.*;
import lombok.experimental.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  ${desc}服务
 *  ${.now}
 */
@Service
@Slf4j
public class ${className} implements ${serviceName} {

   // private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JpaDao jpaDao;

    @Override
    public ServiceResp<${pkField.type}> create${entityName}(Create${entityName}Evt evt) {
        ServiceResp<${pkField.type}> resp = new ServiceResp<>();

<#list fields as field>
        <#if !field.notUpdate && field.uk>
        long ${field.name}C = jpaDao.selectFrom(${entityName}.class)
                .appendWhereEquals("${field.name}", evt.get${field.name?cap_first}())
                .count();
        if (${field.name}C > 0) {
            return resp.error("${field.desc}已被使用");
        }

        </#if>
</#list>
        ${entityName} entity = new ${entityName}();
        BeanUtils.copyProperties(evt, entity);

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
        resp.setData(entity.get${pkField.name?cap_first}());
        return resp;
    }

    @Override
    public ServiceResp edit${entityName}(Edit${entityName}Evt evt) {
        ServiceResp resp = new ServiceResp();

<#list fields as field>
    <#if !field.notUpdate && field.uk>
        if (!StringUtils.isEmpty(evt.get${field.name?cap_first}())) {
            long ${field.name}C = jpaDao.selectFrom(${entityName}.class)
                    .appendWhereEquals("${field.name}", evt.get${field.name?cap_first}())
                    .appendWhere("${pkField.name} != ?", evt.get${pkField.name?cap_first}())
                    .count();
            if (${field.name}C > 0) {
                return resp.error("${field.desc}已被使用");
            }
        }

    </#if>
</#list>
        ${entityName} entity = jpaDao.find(${entityName}.class, evt.get${pkField.name?cap_first}());
        if (entity == null) {
            return resp.error("${desc}数据不存在");
        }

        UpdateDao<${entityName}> updateDao = jpaDao.updateTo(${entityName}.class).appendByQueryObj(evt);

<#list fields as field>
    <#if field.name == 'updateTime'>
        updateDao.appendColumn("updateTime", new Date());
    </#if>
</#list>
        int update = updateDao.update();
        if (update < 1) {
            return resp.error("更新${desc}失败");
        }

        return resp;
    }

    @Override
    public ServiceResp del${entityName}(Del${entityName}Evt evt) {
        ServiceResp resp = new ServiceResp();

        if (evt.get${pkField.name?cap_first}() == null
                && (evt.get${pkField.name?cap_first}s() == null || evt.get${pkField.name?cap_first}s().length == 0)) {
            return resp.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(${entityName}.class).appendByQueryObj(evt).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(${entityName}.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(evt)
                    .update() > 0;
            //return resp.error("无法删除${desc}");
        }

        if (!r) {
            return resp.error("删除失败");
        }

        return resp;
    }

    @Override
    public ServiceResp<${entityName}Info> find${entityName}(Find${entityName}Evt evt) {
        ServiceResp<${entityName}Info> resp = new ServiceResp<>();

        if (evt.get${pkField.name?cap_first}() == null) {
            return resp.error("查询ID不能为空");
        }

        Query${entityName}Evt queryEvt = new Query${entityName}Evt();
        queryEvt.set${pkField.name?cap_first}(evt.get${pkField.name?cap_first}());
        ${entityName}Info info = query${entityName}(queryEvt).getOne();
        if (info == null) {
            return resp.error("未查询到数据");
        }

        resp.setData(info);
        return resp;
    }

    @Override
    public ServiceQueryResp<${entityName}Info> query${entityName}(Query${entityName}Evt evt) {

        return ServiceHelper.buildQueryService(evt,${entityName}.class,${entityName}Info.class,jpaDao);

    }
}
