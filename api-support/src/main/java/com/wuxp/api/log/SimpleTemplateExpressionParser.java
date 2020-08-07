package com.wuxp.api.log;

import com.wuxp.api.interceptor.ApiOperationExpressionEvaluator;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuxp
 */
public class SimpleTemplateExpressionParser implements TemplateExpressionParser {

    private static final char TEMPLATE_PREFIX = '{';
    private static final char TEMPLATE_SUFFIX = '}';

    private ApiOperationExpressionEvaluator apiOperationExpressionEvaluator;

    public SimpleTemplateExpressionParser() {

    }


    public SimpleTemplateExpressionParser(ApiOperationExpressionEvaluator apiOperationExpressionEvaluator) {
        this.apiOperationExpressionEvaluator = apiOperationExpressionEvaluator;
    }

    @Override
    public String parse(String templateContent, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        if (!StringUtils.hasText(templateContent)) {
            return null;
        }
        List<String> expressionVariables = this.getExpressionVariables(templateContent);
        return this.replaceTemplateContent(templateContent, this.getTemplateVariableValues(expressionVariables, methodKey, evaluationContext));
    }

    /**
     * 获取模板上的变量
     *
     * @param template 模板内容
     * @return 变量列表
     */
    private List<String> getExpressionVariables(String template) {

        char[] chars = template.toCharArray();
        List<String> list = new ArrayList<>(4);
        List<String> expression = new ArrayList<>(8);
        boolean isExpression = false;
        for (char c : chars) {
            if (c == TEMPLATE_SUFFIX && !expression.isEmpty()) {
                isExpression = false;
                list.add(String.join("", expression));
                expression.clear();
            }
            if (isExpression) {
                expression.add(c + "");
            }
            if (c == TEMPLATE_PREFIX) {
                isExpression = true;
            }
        }

        return list;
    }

    /**
     * 获取模板变量内容
     *
     * @param expressionVariable
     * @param methodKey
     * @param evaluationContext
     * @return
     */
    private Map<String, Object> getTemplateVariableValues(List<String> expressionVariable, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        Map<String, Object> namedParams = new HashMap<>(expressionVariable.size());

        for (String expr : expressionVariable) {
            Object value = apiOperationExpressionEvaluator.value("#" + expr.trim(), methodKey, evaluationContext);
            namedParams.put(expr, value);
        }

        return namedParams;
    }

    /**
     * 替换模板变量
     *
     * @param template    模板内容
     * @param namedParams 命名参数
     * @return 最终的模板内容
     */
    protected String replaceTemplateContent(String template, Map<String, Object> namedParams) {
        boolean noneReplace = namedParams == null || namedParams.isEmpty();
        if (noneReplace) {
            return template;
        }
        StringSubstitutor sub = new StringSubstitutor(namedParams, TEMPLATE_PREFIX + "", TEMPLATE_SUFFIX + "", TEMPLATE_PREFIX);
        template = sub.replace(template);

        return template;
    }

    public void setApiOperationExpressionEvaluator(ApiOperationExpressionEvaluator apiOperationExpressionEvaluator) {
        this.apiOperationExpressionEvaluator = apiOperationExpressionEvaluator;
    }
}
