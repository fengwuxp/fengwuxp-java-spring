package com.oak.codegen;

import com.wuxp.codegen.core.parser.LanguageParser;
import com.wuxp.codegen.core.strategy.TemplateStrategy;
import com.wuxp.codegen.dragon.AbstractCodeGenerator;
import com.wuxp.codegen.model.CommonCodeGenClassMeta;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class OakSpringCodeGenerator extends AbstractCodeGenerator {


    public OakSpringCodeGenerator(String[] packagePaths, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean enableFieldUnderlineStyle) {
        super(packagePaths, languageParser, templateStrategy, enableFieldUnderlineStyle);
    }

    public OakSpringCodeGenerator(String[] packagePaths, Set<String> ignorePackages, Class<?>[] includeClasses, Class<?>[] ignoreClasses, LanguageParser<CommonCodeGenClassMeta> languageParser, TemplateStrategy<CommonCodeGenClassMeta> templateStrategy, boolean enableFieldUnderlineStyle) {
        super(packagePaths, ignorePackages, includeClasses, ignoreClasses, languageParser, templateStrategy, enableFieldUnderlineStyle);
    }
}
