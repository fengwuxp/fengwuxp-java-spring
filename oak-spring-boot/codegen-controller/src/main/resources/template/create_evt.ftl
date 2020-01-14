package ${packageName};

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

<#list fields as field>
    <#if !field.baseType && field.enums>
import ${field.classType.name};
        <#list field.imports as imp>
import ${imp};
        </#list>
    </#if>
</#list>


/**
 *  创建${entityName}
 *  ${.now}
 */
@Schema(description = "创建${className}的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ${className} extends ApiBaseReq {

<#list fields as field>
    <#if (!field.notUpdate && !field.hasDefValue && !field.complex) || (field.identity?? && !field.identity)>
    @Schema(description = "${field.desc}")
    <#list field.annotations as annotation>
    ${annotation}
    </#list>
    private ${field.type} ${field.name};

    </#if>
</#list>
}
