package ${packagePath?replace('.'+name,'')};

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

public interface ${name}{

<#list methodMetas as method>
    /**
    <#list method.comments as cmment>
    * ${cmment_index+1}:${cmment}
    </#list>
    **/
    ${customize_method.combineType(method.returnTypes)}  ${method.name} (${method.params["req"].name} req);
</#list>
}
