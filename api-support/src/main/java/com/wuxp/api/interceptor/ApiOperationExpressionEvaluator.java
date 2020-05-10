/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wuxp.api.interceptor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Utility class handling the SpEL expression parsing.
 * Meant to be used as a reusable, thread-safe component.
 * @author wxup
 */
public final class ApiOperationExpressionEvaluator extends CachedExpressionEvaluator {


    private final Map<ExpressionKey, Expression> valueCache = new ConcurrentReferenceHashMap<>(64);

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentReferenceHashMap<>(64);

    private final Map<ExpressionKey, Expression> logCache = new ConcurrentReferenceHashMap<>(64);


    /**
     * Create an {@link ApiEvaluationContext}.
     *
     * @param method      the method
     * @param args        the method arguments
     * @param target      the target object
     * @param targetClass the target class
     * @return the evaluation context
     */
    public EvaluationContext createEvaluationContext(Method method,
                                                     Object[] args,
                                                     Object target,
                                                     Class<?> targetClass,
                                                     Method targetMethod,
                                                     @Nullable BeanFactory beanFactory) {

        ApiExpressionRootObject rootObject = new ApiExpressionRootObject(method, args, target, targetClass);
        ApiEvaluationContext evaluationContext = new ApiEvaluationContext(
                rootObject, targetMethod, args, getParameterNameDiscoverer());
        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    @Nullable
    public Object value(String injectExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return getExpression(this.valueCache, methodKey, injectExpression).getValue(evalContext);
    }

    public boolean condition(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return (Boolean.TRUE.equals(getExpression(this.conditionCache, methodKey, conditionExpression).getValue(evalContext, Boolean.class)));
    }

    public String log(String logExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return getExpression(this.logCache, methodKey, logExpression).getValue(evalContext, String.class);
    }

    /**
     * Clear all caches.
     */
    void clear() {
        this.valueCache.clear();
        this.conditionCache.clear();
        this.logCache.clear();
    }

}
