package ${packageName};

import lombok.*;
import lombok.experimental.*;
import com.levin.commons.service.domain.*;
import com.oaknt.common.service.support.model.ServiceEvt;

<#list fields as field>
    <#if !field.baseType && field.enums>
import ${field.classType.name};
        <#list field.imports as imp>
import ${imp};
        </#list>
    </#if>
</#list>

import javax.validation.constraints.*;
import java.util.Date;

/**
 *  创建${desc}
 *  ${.now}
 */
@Desc(value = "创建${desc}")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ${className} extends ServiceEvt {

<#list fields as field>
    <#if (!field.notUpdate && !field.hasDefValue && !field.complex) || (field.identity?? && !field.identity)>
    @Desc(value = "${field.desc}")
    <#list field.annotations as annotation>
    ${annotation}
    </#list>
    private ${field.type} ${field.name};

    </#if>
</#list>
}
