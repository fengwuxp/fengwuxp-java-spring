package ${packageName};

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import ${packageName}.req.*;
import ${packageName}.info.${entityName}Info;



/**
 *  ${desc}服务
 *  ${.now}
 */
public interface ${className} {


    ApiResp<${pkField.type}> create(Create${entityName}Req req);


    ApiResp<Void> edit(Edit${entityName}Req req);


    ApiResp<Void> delete(Delete${entityName}Req req);


    ${entityName}Info findById(${pkField.type} ${pkField.name});


    Pagination<${entityName}Info> query(Query${entityName}Req req);

}
