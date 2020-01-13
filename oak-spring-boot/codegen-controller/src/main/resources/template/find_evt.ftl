package ${packageName};

import lombok.*;
import lombok.experimental.*;
import com.levin.commons.service.domain.*;
import com.levin.commons.dao.annotation.*;
import com.oaknt.common.service.support.model.ServiceEvt;

import javax.validation.constraints.NotNull;

<#list fields as field>
    <#if !field.baseType && field.enums>
import ${field.classType.name};
        <#list field.imports as imp>
import ${imp};
        </#list>
    </#if>
</#list>


/**
 *  查找${desc}
 *  ${.now}
 */
@Desc(value = "查找${desc}")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class ${className} extends ServiceEvt {

    @Desc(value = "${pkField.desc}")
    @NotNull
    @Eq(require = true)
    private ${pkField.type} ${pkField.name};

}
