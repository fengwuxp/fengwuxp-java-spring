package com.oak.codegen.builder;

import com.wuxp.codegen.AbstractDragonCodegenBuilder;
import com.wuxp.codegen.core.CodeGenerator;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class OakSpringCodegenBuilder extends AbstractDragonCodegenBuilder {


    @Override
    public CodeGenerator buildCodeGenerator() {

        if (this.codeRuntimePlatform == null) {
//            this.codeRuntimePlatform = CodeRuntimePlatform.JAVA_SERVER;
        }


        return null;

    }


}
