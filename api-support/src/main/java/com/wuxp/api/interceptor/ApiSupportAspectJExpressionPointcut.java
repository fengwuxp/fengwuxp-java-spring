package com.wuxp.api.interceptor;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wxup
 */
public class ApiSupportAspectJExpressionPointcut extends AspectJExpressionPointcut {

    private static final Set<Class<? extends Annotation>> CACHE_CONTROLLER_ANNOTATIONS = new LinkedHashSet<>(2);

    private static final Set<Class<? extends Annotation>> CACHE_MAPPING_ANNOTATIONS = new LinkedHashSet<>(8);

    private static final List<String> IGNORE_PACKAGES = Arrays.asList(
            "org.springframework.",
            "org.springdoc."
    );


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

    @Override
    public boolean matches(Class<?> targetClass) {
        if (IGNORE_PACKAGES.stream().anyMatch((packageName -> targetClass.getName().startsWith(packageName)))) {
            return false;
        }
        if (CACHE_CONTROLLER_ANNOTATIONS.stream().noneMatch(targetClass::isAnnotationPresent)) {
            return false;
        }


        return super.matches(targetClass);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (!this.matches(targetClass)) {
            return false;
        }
        return super.matches(method, targetClass);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, boolean hasIntroductions) {
        if (!this.matches(targetClass)) {
            return false;
        }
        return super.matches(method, targetClass, hasIntroductions);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        if (!this.matches(targetClass)) {
            return false;
        }
        return super.matches(method, targetClass, args);
    }
}
