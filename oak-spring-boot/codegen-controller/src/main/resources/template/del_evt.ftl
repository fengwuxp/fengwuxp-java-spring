package ${packageName};

import lombok.*;
import lombok.experimental.*;
import com.levin.commons.dao.annotation.In;
import com.levin.commons.service.domain.*;
import com.oaknt.common.service.support.model.ServiceEvt;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

<#list fields as field>
    <#if !field.baseType && field.enums>
import ${field.classType.name};
        <#list field.imports as imp>
import ${imp};
        </#list>
    </#if>
</#list>

/**
 *  删除${desc}
 *  ${.now}
 */
@Desc(value = "删除${desc}")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ${className} extends ServiceEvt {

    @Desc(value = "${pkField.desc}")
    private ${pkField.type} ${pkField.name};

    @Desc(value = "${pkField.desc}集合")
    @In("${pkField.name}")
    private ${pkField.type}[] ${pkField.name}s;

    public ${className}() {
    }

    public ${className}(${pkField.type} ${pkField.name}) {
        this.${pkField.name} = ${pkField.name};
    }

}
