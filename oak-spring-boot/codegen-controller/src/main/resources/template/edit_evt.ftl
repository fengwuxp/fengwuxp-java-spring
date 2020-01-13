package ${packageName};

import lombok.*;
import lombok.experimental.*;
import com.levin.commons.dao.annotation.update.UpdateColumn;
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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.*;
import java.util.Date;
import com.levin.commons.dao.annotation.*;


/**
 *  编辑${desc}
 *  ${.now}
 */
@Desc(value = "编辑${desc}")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ${className} extends ServiceEvt {

    @Desc(value = "${pkField.desc}")
    @NotNull
    @Eq(require = true)
    private ${pkField.type} ${pkField.name};

<#list fields as field>
    <#if !field.notUpdate>
    @Desc(value = "${field.desc}")
    <#list field.annotations as annotation>
    <#if !(annotation?string)?contains("@NotNull")>
    ${annotation}
    </#if>
    </#list>
    @UpdateColumn
    private ${field.type} ${field.name};

    </#if>
</#list>
    public ${className}() {
    }

    public ${className}(${pkField.type} ${pkField.name}) {
        this.${pkField.name} = ${pkField.name};
    }
}
