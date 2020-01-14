package ${packageName};

import com.oak.api.model.ApiBaseReq;
import com.wuxp.security.example.enums.Week;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
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
@Schema(description = "查找${desc}")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class ${className} extends ApiBaseReq {

    @Schema(description = "${pkField.desc}")
    @NotNull
    @Eq(require = true)
    private ${pkField.type} ${pkField.name};

}
