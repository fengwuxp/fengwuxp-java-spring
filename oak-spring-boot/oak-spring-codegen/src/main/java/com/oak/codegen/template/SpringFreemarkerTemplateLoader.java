package com.oak.codegen.template;

import com.wuxp.codegen.model.LanguageDescription;
import com.wuxp.codegen.templates.FreemarkerTemplateLoader;

import java.util.Map;

public class SpringFreemarkerTemplateLoader extends FreemarkerTemplateLoader {


    public SpringFreemarkerTemplateLoader(LanguageDescription language) {
        super(language);
    }

    public SpringFreemarkerTemplateLoader(LanguageDescription language, Map<String, Object> sharedVariables) {
        super(language, sharedVariables);
    }
}
