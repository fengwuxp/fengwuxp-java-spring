package com.wuxp.spring.codegen.antd;

import com.wuxp.codegen.core.parser.LanguageParser;
import com.wuxp.codegen.core.strategy.TemplateStrategy;
import com.wuxp.codegen.model.CommonCodeGenClassMeta;
import com.wuxp.codegen.swagger3.Swagger3CodeGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class AntdUICodeGenerator extends Swagger3CodeGenerator {


    public AntdUICodeGenerator(String[] packagePaths, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean enableFieldUnderlineStyle) {
        super(packagePaths, languageParser, templateStrategy, enableFieldUnderlineStyle);
    }

    public AntdUICodeGenerator(String[] packagePaths, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean looseMode, boolean enableFieldUnderlineStyle) {
        super(packagePaths, languageParser, templateStrategy, looseMode, enableFieldUnderlineStyle);
    }

    public AntdUICodeGenerator(String[] packagePaths, Set<String> ignorePackages, Class<?>[] includeClasses, Class<?>[] ignoreClasses, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean looseMode, boolean enableFieldUnderlineStyle) {
        super(packagePaths, ignorePackages, includeClasses, ignoreClasses, languageParser, templateStrategy, looseMode, enableFieldUnderlineStyle);
    }

    @Override
    public void generate() {
        List<CommonCodeGenClassMeta> commonCodeGenClassMetas = this.scanPackages().stream()
                .map(this.languageParser::parse)
                .filter(Objects::nonNull)
                .filter(commonCodeGenClassMeta -> {
                    //过滤掉无效的数据
                    boolean notMethod = commonCodeGenClassMeta.getMethodMetas() == null || commonCodeGenClassMeta.getMethodMetas().length == 0;
                    boolean notFiled = commonCodeGenClassMeta.getFiledMetas() == null || commonCodeGenClassMeta.getFiledMetas().length == 0;
                    return !(notFiled && notMethod);
                }).collect(Collectors.toList());

        // TODO 控制器的方法转换为UI模型

    }
}
