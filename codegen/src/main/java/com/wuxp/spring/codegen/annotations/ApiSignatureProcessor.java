package com.wuxp.spring.codegen.annotations;


import com.alibaba.fastjson.JSON;
import com.wuxp.api.signature.ApiSignature;
import com.wuxp.codegen.annotation.processors.AbstractAnnotationProcessor;
import com.wuxp.codegen.annotation.processors.AnnotationMate;
import com.wuxp.codegen.model.CommonCodeGenAnnotation;

import java.util.*;


/**
 * @author wuxp
 * @see ApiSignature
 */
public class ApiSignatureProcessor extends AbstractAnnotationProcessor<ApiSignature, ApiSignatureProcessor.ApiSignatureMate> {

    public static final String ANNOTATION_NAME = "Signature";

    @Override
    public ApiSignatureMate process(ApiSignature annotation) {

        return super.newProxyMate(annotation, ApiSignatureMate.class);
    }


    public abstract static class ApiSignatureMate implements AnnotationMate, ApiSignature {

        public ApiSignatureMate() {
        }


    }

    public static CommonCodeGenAnnotation genAnnotation(Set<String> needSignFields) {

        CommonCodeGenAnnotation genAnnotation = new CommonCodeGenAnnotation();
        genAnnotation.setName(ANNOTATION_NAME);
        Map<String, String> namedArguments = new LinkedHashMap<>();
        List<String> positionArguments = new ArrayList<>(needSignFields);
        namedArguments.put("fields", JSON.toJSONString(positionArguments));
        genAnnotation.setNamedArguments(namedArguments);
        genAnnotation.setPositionArguments(positionArguments);

        return genAnnotation;
    }
}
