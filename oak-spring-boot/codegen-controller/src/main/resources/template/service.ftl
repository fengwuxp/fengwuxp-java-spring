package ${packageName};

import com.levin.commons.service.domain.ApiService;
import com.levin.commons.service.domain.*;
import com.oaknt.common.service.support.model.ServiceQueryResp;
import com.oaknt.common.service.support.model.ServiceResp;
import ${packageName}.evt.*;
import ${packageName}.info.${entityName}Info;



/**
 *  ${desc}服务
 *  ${.now}
 */
@ApiService
@Desc(value = "${desc}服务")
public interface ${className} {

    @Desc("创建${desc}")
    ServiceResp<${pkField.type}> create${entityName}(Create${entityName}Evt evt);

    @Desc("编辑${desc}")
    ServiceResp edit${entityName}(Edit${entityName}Evt evt);

    @Desc("删除${desc}")
    ServiceResp del${entityName}(Del${entityName}Evt evt);

    @Desc("查找${desc}")
    ServiceResp<${entityName}Info> find${entityName}(Find${entityName}Evt evt);

    @Desc("查询${desc}")
    ServiceQueryResp<${entityName}Info> query${entityName}(Query${entityName}Evt evt);

}