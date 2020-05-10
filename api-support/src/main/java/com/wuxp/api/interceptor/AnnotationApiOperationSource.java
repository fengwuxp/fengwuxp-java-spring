package com.wuxp.api.interceptor;

import com.wuxp.api.ApiRequest;
import com.wuxp.api.signature.ApiSignatureRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 匹配 控制器相关的注解
 *
 * @author wxup
 */
@Setter
@Slf4j
public class AnnotationApiOperationSource implements ApiOperationSource {

    private static final Set<Class<? extends Annotation>> CACHE_CONTROLLER_ANNOTATIONS = new LinkedHashSet<>(2);
    private static final Set<Class<? extends Annotation>> CACHE_MAPPING_ANNOTATIONS = new LinkedHashSet<>(8);

    static {
        CACHE_CONTROLLER_ANNOTATIONS.add(Controller.class);
        CACHE_CONTROLLER_ANNOTATIONS.add(RestController.class);
        CACHE_MAPPING_ANNOTATIONS.add(RequestMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(GetMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(PostMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(DeleteMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(PutMapping.class);
        CACHE_MAPPING_ANNOTATIONS.add(PatchMapping.class);
    }

    private static final List<String> IGNORE_PACKAGES = Arrays.asList(
            "org.springframework.",
            "org.springdoc."
    );


//    protected Class<?> injectSupperClazz = ApiRequest.class;

//    protected Class<?> checkSignatureSupperClazz = ApiSignatureRequest.class;

    @Override
    public boolean isCandidateClass(Method method, Class<?> targetClass) {
        if (IGNORE_PACKAGES.stream().anyMatch((packageName -> targetClass.getName().startsWith(packageName)))) {
            return false;
        }
        if (CACHE_CONTROLLER_ANNOTATIONS.stream().noneMatch(targetClass::isAnnotationPresent)) {
            return false;
        }

        // 控制器，并且为控制器方法
        boolean match = CACHE_MAPPING_ANNOTATIONS.stream()
                .anyMatch(method::isAnnotationPresent);
        log.info("==>拦截方法 {} {}", method.getName(), match);
        return match;



//        if (method.isAnnotationPresent(ApiLog.class)) {
//            return true;
//        }
//
//        Parameter[] parameters = method.getParameters();
//        for (Parameter parameter : parameters) {
//            InjectField injectField = parameter.getAnnotation(InjectField.class);
//            if (injectField != null) {
//                return true;
//            }
//            ApiSignature apiSignature = parameter.getAnnotation(ApiSignature.class);
//            if (apiSignature != null) {
//                return true;
//            }
//            Type parameterParameterizedType = parameter.getParameterizedType();
//            if (parameterParameterizedType instanceof Class) {
//                Class<?> parameterizedType = (Class<?>) parameterParameterizedType;
//                if (injectSupperClazz.isAssignableFrom(parameterizedType) || checkSignatureSupperClazz.isAssignableFrom(parameterizedType)) {
//                    return true;
//                }
//            }
//
//        }

//        return true;
    }
}
