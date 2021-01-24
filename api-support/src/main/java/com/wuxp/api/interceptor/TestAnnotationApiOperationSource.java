package com.wuxp.api.interceptor;

import com.wuxp.api.ApiRequest;
import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * 用于测试服务层时拦截
 *
 * @author wxup
 */
@Slf4j
public class TestAnnotationApiOperationSource implements ApiOperationSource {


    private static final Set<Class<? extends Annotation>> CACHE_MAPPING_ANNOTATIONS = new LinkedHashSet<>(8);

    static {
        CACHE_MAPPING_ANNOTATIONS.add(DecimalMax.class);
        CACHE_MAPPING_ANNOTATIONS.add(DecimalMin.class);
        CACHE_MAPPING_ANNOTATIONS.add(Digits.class);
        CACHE_MAPPING_ANNOTATIONS.add(Email.class);
        CACHE_MAPPING_ANNOTATIONS.add(Future.class);
        CACHE_MAPPING_ANNOTATIONS.add(FutureOrPresent.class);
        CACHE_MAPPING_ANNOTATIONS.add(Max.class);
        CACHE_MAPPING_ANNOTATIONS.add(Min.class);
        CACHE_MAPPING_ANNOTATIONS.add(Negative.class);
        CACHE_MAPPING_ANNOTATIONS.add(NegativeOrZero.class);
        CACHE_MAPPING_ANNOTATIONS.add(NotBlank.class);
        CACHE_MAPPING_ANNOTATIONS.add(NotEmpty.class);
        CACHE_MAPPING_ANNOTATIONS.add(NotNull.class);
        CACHE_MAPPING_ANNOTATIONS.add(Null.class);
        CACHE_MAPPING_ANNOTATIONS.add(Past.class);
        CACHE_MAPPING_ANNOTATIONS.add(PastOrPresent.class);
        CACHE_MAPPING_ANNOTATIONS.add(Pattern.class);
        CACHE_MAPPING_ANNOTATIONS.add(Positive.class);
        CACHE_MAPPING_ANNOTATIONS.add(PositiveOrZero.class);
        CACHE_MAPPING_ANNOTATIONS.add(Size.class);
    }


    @Override
    public boolean isCandidateClass(Method method, Class<?> targetClass) {
        Class<?> returnType = method.getReturnType();
        if (ApiResp.class.isAssignableFrom(returnType)) {
            return true;
        }

        Parameter[] parameters = method.getParameters();

        return Arrays.stream(parameters).anyMatch(parameter -> {
            Annotation[] annotations = parameter.getAnnotations();
            if (Arrays.stream(annotations).anyMatch(CACHE_MAPPING_ANNOTATIONS::contains)) {
                // 存在验证注解
                return true;
            }
            Type type = parameter.getParameterizedType();
            return ApiRequest.class.isAssignableFrom((Class<?>) type);
        });
    }

    public static void setValidationAnnotationType(Class<? extends Annotation> annotationType) {
        CACHE_MAPPING_ANNOTATIONS.add(annotationType);
    }
}
