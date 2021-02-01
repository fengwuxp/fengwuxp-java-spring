package com.wuxp.spring.codegen.antd;

import com.wuxp.codegen.core.config.CodegenConfigHolder;
import com.wuxp.codegen.core.event.CodeGenPublisher;
import com.wuxp.codegen.core.parser.JavaClassParser;
import com.wuxp.codegen.core.parser.LanguageParser;
import com.wuxp.codegen.core.strategy.TemplateStrategy;
import com.wuxp.codegen.dragon.AbstractCodeGenerator;
import com.wuxp.codegen.model.CommonCodeGenClassMeta;
import com.wuxp.codegen.model.languages.java.JavaClassMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wxup
 */
@Slf4j
public class AntdAdminPageCodeGenerator extends AbstractCodeGenerator {


    private static final Map<String, String> TEMPLATE_METHOD_NAMES = new HashMap<>(8);

    private JavaClassParser javaClassParser = new JavaClassParser(true);

    public AntdAdminPageCodeGenerator(String[] packagePaths, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean enableFieldUnderlineStyle) {
        super(packagePaths, languageParser, templateStrategy, enableFieldUnderlineStyle);
    }

    public AntdAdminPageCodeGenerator(String[] packagePaths, Set<String> ignorePackages, Class<?>[] includeClasses, Class<?>[] ignoreClasses, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean enableFieldUnderlineStyle) {
        super(packagePaths, ignorePackages, includeClasses, ignoreClasses, languageParser, templateStrategy, enableFieldUnderlineStyle, CodeGenPublisher.NONE);
    }

    @Override
    public void generate() {
        List<JavaClassMeta> classMetaList = this.scanPackages().stream()
                .map(this.javaClassParser::parse)
                .filter(Objects::nonNull)
                .filter(this::isApiServiceClass)
                .filter(commonCodeGenClassMeta -> {
                    //过滤掉无效的数据
                    return commonCodeGenClassMeta.getMethodMetas() == null || commonCodeGenClassMeta.getMethodMetas().length == 0;
                })
                .collect(Collectors.toList());

        // TODO 控制器的方法转换为UI模型

        classMetaList.stream().map(javaClassMeta -> {

            Arrays.stream(javaClassMeta.getMethodMetas()).filter(javaMethodMeta -> {

                return javaClassMeta.existAnnotation(RestController.class) || javaMethodMeta.existAnnotation(ResponseBody.class);
            }).map(javaMethodMeta -> {

                return null;
            });
            return null;
        });

    }

    public boolean isApiServiceClass(JavaClassMeta javaClassMeta) {
        return javaClassMeta.existAnnotation(CodegenConfigHolder.getConfig().getApiMarkedAnnotations().toArray(new Class[0]));
    }


}
