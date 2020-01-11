package com.oak.codegen.spring;

import com.wuxp.codegen.core.strategy.TemplateStrategy;
import com.wuxp.codegen.model.languages.java.codegen.JavaCodeGenClassMeta;
import com.wuxp.codegen.templates.TemplateLoader;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理生成spring 控制器 服务的处理策略
 */
@Slf4j
public class OakSpringTemplateStrategy implements TemplateStrategy<JavaCodeGenClassMeta[]> {

    //在LAST_MODIFIED_MINUTE内生成的文件不在生成
    public static final float LAST_MODIFIED_MINUTE = 0.1f;

    /**
     * 模板加载器
     */
    protected TemplateLoader<Template> templateLoader;


    /**
     * 支持将多个实体生成到一个基础服务中
     *
     * @param list
     */
    @Override
    public void build(JavaCodeGenClassMeta[] list) {

        //对于一个{@code list}需要生成至少生成一个控制器，一个服务,以及对于增删改查的DTO对象

    }

    /**
     * 获取生成控制器的模板
     *
     * @return
     */
    private Template getControllerTemplate() {

        return null;
    }

    /**
     * 获取生成服务的模板
     *
     * @return
     */
    private Template getServiceTemplate() {

        return null;
    }


    /**
     * 获取生成 dto的模板
     *
     * @return
     */
    private Template getDTOTemplate() {
        return null;
    }
}
