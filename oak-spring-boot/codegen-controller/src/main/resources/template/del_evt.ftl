package ${packageName};

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;
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
@Schema(description = "删除${desc}")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ${className} extends ApiBaseReq {

    @Schema(description = "${pkField.desc}")
    private ${pkField.type} ${pkField.name};

    @Schema(description = "${pkField.desc}集合")
    @In("${pkField.name}")
    private ${pkField.type}[] ${pkField.name}s;

    public ${className}() {
    }

    public ${className}(${pkField.type} ${pkField.name}) {
        this.${pkField.name} = ${pkField.name};
    }

}
