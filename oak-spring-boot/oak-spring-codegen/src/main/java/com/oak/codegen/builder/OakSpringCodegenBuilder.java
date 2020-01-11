package com.oak.codegen.builder;

import com.levin.commons.service.domain.ApiService;

import com.wuxp.codegen.AbstractDragonCodegenBuilder;
import com.wuxp.codegen.core.CodeGenerator;
import com.wuxp.codegen.core.parser.LanguageParser;
import com.wuxp.codegen.core.strategy.TemplateStrategy;
import com.wuxp.codegen.dragon.DragonSimpleTemplateStrategy;
import com.wuxp.codegen.enums.CodeRuntimePlatform;
import com.wuxp.codegen.model.CommonCodeGenClassMeta;
import com.wuxp.codegen.model.LanguageDescription;
import com.wuxp.codegen.model.languages.java.JavaClassMeta;
import com.wuxp.codegen.templates.TemplateLoader;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class OakSpringCodegenBuilder extends AbstractDragonCodegenBuilder {


    @Override
    public CodeGenerator buildCodeGenerator() {

        if (this.codeRuntimePlatform == null) {
            this.codeRuntimePlatform = CodeRuntimePlatform.JAVA_SERVER;
        }


        return null;

    }


}
