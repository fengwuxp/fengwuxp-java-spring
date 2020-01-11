package ${packagePath?replace('.'+name,'')};


import lombok.Data;
import com.oak.springboot.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.validation.constraints.NotNull;

<#if dependencies??>
<#--依赖导入处理-->
    <#list dependencies as key,val >
        import ${customize_method.pathoResolve(packagePath,val.packagePath)};
    </#list>
</#if>

<#if comments??>
    /**
    <#list comments as cmment>
        * ${cmment}
    </#list>
    **/
</#if>

<#if filedMetas??>
    <#list annotations as annotation>
        @${annotation.name}({
        <#list annotation.namedArguments as name,val>
            ${name}=${val}
        </#list>
        })
    </#list>
</#if>

@Data
public class  ${finallyClassName} extends ApiBaseReq {

<#if filedMetas??>
    <#list filedMetas as field>
        /**
        <#list field.comments as cmment>
            *${cmment}
        </#list>
        **/
        ${field.accessPermissionName} ${customize_method.combineType(field.filedTypes)} ${field.name};
    </#list>
</#if>
}
