package com.wuxp.spring.codegen.annotations;

import com.wuxp.api.context.InjectField;
import com.wuxp.codegen.annotation.processors.AbstractAnnotationProcessor;
import com.wuxp.codegen.annotation.processors.AnnotationMate;


/**
 * @author wuxp
 * @see InjectField
 */
public class InjectFiledProcessor extends AbstractAnnotationProcessor<InjectField, InjectFiledProcessor.InjectFieldMate> {


    @Override
    public InjectFieldMate process(InjectField annotation) {

        return super.newProxyMate(annotation, InjectFieldMate.class);
    }


    public abstract static class InjectFieldMate implements AnnotationMate, InjectField {



    }


}
