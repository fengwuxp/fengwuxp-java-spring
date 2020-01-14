package ${packageName};

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import com.levin.commons.dao.annotation.misc.Fetch;
<#list fields as field>
    <#if !field.baseType && field.enums>
import ${field.classType.name};
        <#list field.imports as imp>
import ${imp};
        </#list>
    </#if>
</#list>
import java.util.Date;
/**
 *  查询${desc}
 *  ${.now}
 */
@Schema(description = "查询${desc}")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class ${className} extends ApiBaseQueryReq {

<#list fields as field>
    <#if field.type=='Date'>
    @Schema(description = "最小${field.desc}")
    @Gte("${field.name}")
    private ${field.type} min${field.name?cap_first};

    @Schema(description = "最大${field.desc}")
    @Lte("${field.name}")
    private ${field.type} max${field.name?cap_first};

    <#elseif !field.complex>
    @Schema(description = "${field.desc}")
    private ${field.type} ${field.name};

    <#if field.like>
    @Schema(description = "${field.desc}")
    @Like("${field.name}")
    private ${field.type} ${field.name}Like;

    </#if>
    <#elseif field.lazy!>
    @Schema(description = "加载${field.desc}")
    @Fetch(value = "${field.name}", condition = "#_val==true")
    private Boolean load${field.name?cap_first};

    </#if>
</#list>
    public ${className}() {
    }

    public ${className}(${pkField.type} ${pkField.name}) {
        this.${pkField.name} = ${pkField.name};
    }
}
