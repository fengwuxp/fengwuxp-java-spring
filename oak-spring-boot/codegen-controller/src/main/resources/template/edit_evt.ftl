package ${packageName};

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

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
 *  编辑${desc}
 *  ${.now}
 */
@Schema(description = "编辑${desc}")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ${className} extends ApiBaseReq {

    @Schema(description = "${pkField.desc}")
    @NotNull
    @Eq(require = true)
    private ${pkField.type} ${pkField.name};

<#list fields as field>
    <#if !field.notUpdate>
    <#list field.annotations as annotation>
    <#if !(annotation?string)?contains("@NotNull")>
    ${annotation}
    </#if>
    </#list>
    @Schema(description = "${field.desc}")
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
