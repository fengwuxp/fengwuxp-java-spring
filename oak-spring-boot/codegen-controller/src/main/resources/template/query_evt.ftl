package ${packageName};

import lombok.*;
import lombok.experimental.*;
import com.levin.commons.dao.annotation.*;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.levin.commons.service.domain.*;
import com.oaknt.common.service.support.model.ServiceQueryEvt;
import com.levin.commons.dao.annotation.order.OrderBy;
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
 *  查询${desc}
 *  ${.now}
 */
@Desc(value = "查询${desc}")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class ${className} extends ServiceQueryEvt {

<#list fields as field>
    <#if field.type=='Date'>
    @Desc(value = "最小${field.desc}")
    @Gte("${field.name}")
    private ${field.type} min${field.name?cap_first};

    @Desc(value = "最大${field.desc}")
    @Lte("${field.name}")
    private ${field.type} max${field.name?cap_first};

    <#elseif !field.complex>
    @Desc(value = "${field.desc}")
    private ${field.type} ${field.name};

    <#if field.like>
    @Desc(value = "${field.desc}")
    @Like("${field.name}")
    private ${field.type} ${field.name}Like;

    </#if>
    <#elseif field.lazy!>
    @Desc("加载${field.desc}")
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
