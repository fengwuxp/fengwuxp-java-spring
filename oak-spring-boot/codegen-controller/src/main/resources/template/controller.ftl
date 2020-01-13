package ${controllerPackageName};

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.levin.commons.service.domain.Secured;
import com.oaknt.common.annotation.OperatorLog;
import com.oaknt.common.service.support.enums.QueryType;
import com.oaknt.common.service.support.model.PageInfo;
import com.oaknt.common.service.support.model.ServiceQueryResp;
import com.oaknt.common.service.support.model.ServiceResp;
import com.oaknt.common.service.support.ServiceException;
import com.oaknt.udf.admin.enums.ResultAction;
import com.oaknt.udf.admin.mvc.BaseController;

import ${serviceFullName};
import ${packageName}.evt.*;
import ${packageName}.info.${entityName}Info;


import lombok.*;
import lombok.experimental.*;
import lombok.extern.slf4j.Slf4j;

<#list fields as field>
    <#if (field.lzay)??>
import ${field.classType.package.name}.${field.classType.simpleName};
    </#if>
</#list>

import com.wxp.excel.export.ExportExcelDataGrabber;
import com.wxp.excel.export.ExportExcelHandler;
import com.wxp.excel.model.ExportExcelDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Secured(desc = "${desc}", menuName = "${desc}管理")
@Slf4j
public class ${entityName}Controller extends BaseController{

    //final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ${serviceName} ${serviceName?uncap_first};


    /**
     * 到列表视图
     *
     * @param evt   Query${entityName}Evt
     * @param model Model
     */
    @RequestMapping
    @Secured(desc = "${desc}查询", menuName = "${desc}管理")
    public void list( Query${entityName}Evt evt, Model model) {
        model.addAttribute("evt", evt);
    }

    /**
     * 到带回查找视图
     *
     * @param evt   Query${entityName}Evt
     * @param model Model
     */
    @RequestMapping
    @Secured(expr = "list")
    public void lookup(Query${entityName}Evt evt, Model model) {
        model.addAttribute("evtJson", evt.toJson());
    }

    /**
     * 分页数据
     *
     * @param page PageInfo<${entityName}Info>
     * @param evt  Query${entityName}Evt
     * @return PageInfo<${entityName}Info>
     */
    @RequestMapping
    @ResponseBody
    @Secured(expr = "list")
    public PageInfo<${entityName}Info> page(PageInfo<${entityName}Info> page, Query${entityName}Evt evt) {

        //首次查询总数
        if (page.getTotal() == null) {
            evt.setQueryType(QueryType.QUERY_NUM);
            ServiceQueryResp<${entityName}Info> countResp = ${serviceName?uncap_first}.query${entityName}(evt);
            if (countResp.isSuccess()) {
                page.setTotal(countResp.getData().getTotal());
            }
        }

        //获取分页数据
        evt.setQueryType(QueryType.QUERY_RESET);
        ServiceQueryResp<${entityName}Info> resultResp = ${serviceName?uncap_first}.query${entityName}(evt);
        if (resultResp.isSuccess()) {
            page.setRecords(resultResp.getData().getRecords());
        }

        return page;
    }


    /**
     * 到新增视图
     */
    @RequestMapping
    @Secured(expr = "create")
    public void input(Model model) {

    }


    /**
     * 新增保存
     *
     * @param evt   Create${entityName}Evt
     * @return ServiceResp
     */
    @RequestMapping
    @ResponseBody
    @Secured(desc = "新增${desc}")
    @OperatorLog(expression = "'新增${desc}记录['+#evt.toJson()+']'")
    public ServiceResp create(Create${entityName}Evt evt) {

        ServiceResp resp = ${serviceName?uncap_first}.create${entityName}(evt);
        if (!resp.isSuccess()) {
            return resp.error("新增数据失败（" + resp.getMessage() + "）");
        }
        return setAction(resp, ResultAction.BACK);
    }


    /**
     * 到编辑视图
     * @param ${pkField.name} ${pkField.type}
     * @param model Model
     */
    @RequestMapping
    @Secured(expr = "edit")
    public void load(${pkField.type} ${pkField.name}, Model model) {
        if (${pkField.name} != null) {
            ServiceResp<${entityName}Info> resp = ${serviceName?uncap_first}.find${entityName}(new Find${entityName}Evt(${pkField.name}));

            if(resp == null || !resp.isSuccess() || resp.getData()==null){
                throw new ServiceException("数据不存在");
            }

            model.addAttribute("${entityName?uncap_first}Info",resp.getData());
        }
    }

    /**
    * 加载
    *
    * @param ${pkField.name} ${pkField.type}
    */
    @RequestMapping
    @Secured(expr = "edit")
    @ResponseBody
    public ${entityName}Info find(${pkField.type} ${pkField.name}) {
        ServiceResp<${entityName}Info> resp = ${serviceName?uncap_first}.find${entityName}(new Find${entityName}Evt(${pkField.name}));
        if (resp == null || !resp.isSuccess() || resp.getData() == null) {
            throw new ServiceException("数据不存在");
        }

        return resp.getData();
     }


    /**
     * 修改保存
     */
    @RequestMapping
    @ResponseBody
    @Secured(desc = "编辑${desc}")
    @OperatorLog(expression = "'编辑${desc}['+#evt.toJson()+']'")
    public ServiceResp edit(Edit${entityName}Evt evt) {

        ServiceResp resp = ${serviceName?uncap_first}.edit${entityName}(evt);
        if (!resp.isSuccess()) {
            return resp.error("编辑保存失败（" + resp.getMessage() + "）");
        }

        return setAction(resp, ResultAction.BACK);
    }


    /**
     * 删除
     */
    @RequestMapping
    @ResponseBody
    @Secured(desc = "删除${desc}")
    @OperatorLog(expression = "'删除${desc}['+#evt.toJson()+']'")
    public ServiceResp del(Del${entityName}Evt evt) {

        ServiceResp resp = ${serviceName?uncap_first}.del${entityName}(evt);
        if (!resp.isSuccess()) {
             return resp.error("删除失败（" + resp.getMessage() + "）");
        }
        return setAction(resp, ResultAction.RELOAD);
    }


    /**
     * 到查看详情视图
     * @param id Long
     * @param model Model
     */
    @RequestMapping
    @Secured(expr = "list")
    public void show(Long id, Model model) {

        ServiceResp<${entityName}Info> resp = ${serviceName?uncap_first}.find${entityName}(new Find${entityName}Evt(id));

        if(resp == null || !resp.isSuccess() || resp.getData()==null){
             throw new ServiceException("数据不存在");
        }

        model.addAttribute("${entityName?uncap_first}Info",resp.getData());
    }

    @RequestMapping
    @Secured(desc = "导出${desc}")
    public void export(HttpServletResponse response, final Query${entityName}Evt evt, String exportDescJSON, Boolean async, String fileName) {

        List<ExportExcelDesc> list = JSON.parseObject(exportDescJSON, new TypeReference<List<ExportExcelDesc>>() {
        });
        ExportExcelHandler handler = new ExportExcelHandler(list, ${entityName}Info.class, new ExportExcelDataGrabber<${entityName}Info>() {
            @Override
            public List<${entityName}Info> fetchData(int page, int fetchSize) {
                evt.setQueryType(QueryType.QUERY_RESET);
                evt.setQuerySize(fetchSize);
                evt.setQueryPage(page);
                ServiceQueryResp<${entityName}Info> resp = ${serviceName?uncap_first}.query${entityName}(evt);
                return resp.getData().getRecords();
            }

            @Override
            public long getTotalNumber() {
                evt.setQueryType(QueryType.QUERY_NUM);
                ServiceQueryResp<${entityName}Info> resp = ${serviceName?uncap_first}.query${entityName}(evt);
                return resp.getData().getTotal().longValue();
            }
        });

        handler.setFetchSize(200);
        handler.start(async, response, fileName);
    }

}
