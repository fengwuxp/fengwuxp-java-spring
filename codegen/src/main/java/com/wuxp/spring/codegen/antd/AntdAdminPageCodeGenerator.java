package com.wuxp.spring.codegen.antd;

import com.wuxp.codegen.core.CodeGenerator;
import com.wuxp.codegen.core.parser.JavaClassParser;
import com.wuxp.codegen.core.parser.LanguageParser;
import com.wuxp.codegen.core.strategy.TemplateStrategy;
import com.wuxp.codegen.dragon.AbstractCodeGenerator;
import com.wuxp.codegen.model.CommonCodeGenClassMeta;
import com.wuxp.codegen.model.CommonCodeGenMethodMeta;
import com.wuxp.codegen.model.languages.java.JavaClassMeta;
import com.wuxp.codegen.model.languages.java.codegen.JavaCodeGenClassMeta;
import com.wuxp.codegen.swagger3.Swagger3CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class AntdAdminPageCodeGenerator extends AbstractCodeGenerator {


    private static final Map<String, String> TEMPLATE_METHOD_NAMES = new HashMap<>(8);

    private JavaClassParser javaClassParser = new JavaClassParser(true);

    public AntdAdminPageCodeGenerator(String[] packagePaths, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean enableFieldUnderlineStyle) {
        super(packagePaths, languageParser, templateStrategy, enableFieldUnderlineStyle);
    }

    public AntdAdminPageCodeGenerator(String[] packagePaths, Set<String> ignorePackages, Class<?>[] includeClasses, Class<?>[] ignoreClasses, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean enableFieldUnderlineStyle) {
        super(packagePaths, ignorePackages, includeClasses, ignoreClasses, languageParser, templateStrategy, enableFieldUnderlineStyle);
    }

    @Override
    public void generate() {
        List<JavaClassMeta> classMetaList = this.scanPackages().stream()
                .map(this.javaClassParser::parse)
                .filter(Objects::nonNull)
                .filter(JavaClassMeta::isApiServiceClass)
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


}
