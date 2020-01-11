package com.wuxp.spring.codegen.annotations;

import com.wuxp.api.context.InjectField;
import com.wuxp.codegen.annotation.processor.AbstractAnnotationProcessor;
import com.wuxp.codegen.annotation.processor.AnnotationMate;


/**
 * @see InjectField
 */
public class InjectFiledProcessor extends AbstractAnnotationProcessor<InjectField, InjectFiledProcessor.InjectFieldMate> {


    @Override
    public InjectFieldMate process(InjectField annotation) {

        return super.newProxyMate(annotation, InjectFieldMate.class);
    }


    public abstract static class InjectFieldMate implements AnnotationMate<InjectField>, InjectField {

        public InjectFieldMate() {
        }


    }


}
