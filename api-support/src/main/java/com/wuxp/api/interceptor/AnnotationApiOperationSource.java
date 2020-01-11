package com.wuxp.api.interceptor;

import com.wuxp.api.ApiRequest;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.signature.ApiSignature;
import com.wuxp.api.signature.ApiSignatureRequest;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 匹配
 */
@Setter
public class AnnotationApiOperationSource implements ApiOperationSource {

    private static final Set<Class<? extends Annotation>> CACHE_MAPPING_ANNOTATIONS = new LinkedHashSet<>(8);

    static {
        CACHE_MAPPING_ANNOTATIONS.add(Controller.class);
        CACHE_MAPPING_ANNOTATIONS.add(RestController.class);
        CACHE_MAPPING_ANNOTATIONS.add(RequestMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(GetMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(PostMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(DeleteMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(PutMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(PatchMapping.class);
    }

    protected Class<?> injectSupperClazz = ApiRequest.class;

    protected Class<?> checkSignatureSupperClazz = ApiSignatureRequest.class;

    @Override
    public boolean isCandidateClass(Method method, Class<?> targetClass) {

//        if (AnnotationUtils.isCandidateClass(targetClass, CACHE_MAPPING_ANNOTATIONS)) {
//            return false;
//        }
        Optional<Class<? extends Annotation>> optionalClass = CACHE_MAPPING_ANNOTATIONS.stream()
                .filter(method::isAnnotationPresent)
                .findFirst();

        if (!optionalClass.isPresent()) {
            return false;
        }

        if (method.isAnnotationPresent(ApiLog.class)) {
            return true;
        }

        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            InjectField injectField = parameter.getAnnotation(InjectField.class);
            if (injectField != null) {
                return true;
            }
            ApiSignature apiSignature = parameter.getAnnotation(ApiSignature.class);
            if (apiSignature != null) {
                return true;
            }
            Type parameterParameterizedType = parameter.getParameterizedType();
            if (parameterParameterizedType instanceof Class) {
                Class<?> parameterizedType = (Class<?>) parameterParameterizedType;
                if (injectSupperClazz.isAssignableFrom(parameterizedType) || checkSignatureSupperClazz.isAssignableFrom(parameterizedType)) {
                    return true;
                }
            }

        }

        return false;
    }
}
