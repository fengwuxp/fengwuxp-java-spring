package ${packageName};

import lombok.*;
import lombok.experimental.*;
import com.levin.commons.service.domain.*;
<#list fields as field>
   <#if !field.baseType && field.enums>
import ${field.classType.name};
   </#if>
    <#if (field.infoClassName)??>
import ${field.infoClassName};
    </#if>
    <#list field.imports as imp>
import ${imp};
    </#list>
</#list>


import java.io.Serializable;
import java.util.Date;



/**
 * ${desc}
 * ${.now}
 */
@Desc(value = "${desc}")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"${pkField.name}"})
@ToString(exclude = {<#list fields as field><#if (field.lazy)??>"${field.name}${field.excessSuffix}"<#if field?has_next>,</#if></#if></#list>})
public class ${className} implements Serializable {

<#list fields as field>
    <#if field.complex>
    @Desc(value = "${field.desc}"<#if (field.lazy)??>,code = "${field.name}"</#if>)
    private ${field.excessReturnType} ${field.name}${field.excessSuffix};

    <#else>
    @Desc(value = "${field.desc}")
    private ${field.type} ${field.name};

    </#if>
</#list>

<#list fields as field>
    <#if !field.complex && field.excessSuffix??>
    public ${field.excessReturnType} get${field.name?cap_first}${field.excessSuffix}() {
            ${field.excessReturn}
    }

    </#if>
</#list>
}
