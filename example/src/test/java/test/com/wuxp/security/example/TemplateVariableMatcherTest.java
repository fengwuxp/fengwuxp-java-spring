package test.com.wuxp.security.example;

import org.apache.commons.text.StringSubstitutor;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.*;

public class TemplateVariableMatcherTest {
    ExpressionParser expressionParser = new SpelExpressionParser();

    @Test
    public void testMatcher() {

        String template = "我是谁，name = { name }，年龄：{age}";
        List<String> expressionVariable = getExpressionVariable(template);
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("name", "张三");
        evaluationContext.setVariable("age", "11");
        Map<String, Object> templateVariableValue = getTemplateVariableValue(expressionVariable, evaluationContext);
        String s = replaceTemplateContent(template, templateVariableValue);
        System.out.println(s);
    }

    private List<String> getExpressionVariable(String template) {
        if (!StringUtils.hasText(template)) {
            return Collections.emptyList();
        }
        char[] chars = template.toCharArray();
        List<String> list = new ArrayList<>(4);
        List<String> expression = new ArrayList<>(8);
        boolean isExpression = false;
        for (char c : chars) {
            if (c == '}' && !expression.isEmpty()) {
                isExpression = false;
                list.add(String.join("", expression));
                expression.clear();
            }
            if (isExpression) {
                expression.add(c + "");
            }
            if (c == '{') {
                isExpression = true;
            }
        }

        return list;
    }

    private Map<String, Object> getTemplateVariableValue(List<String> expressionVariable, EvaluationContext evaluationContext) {
        Map<String, Object> namedParams = new HashMap<>(expressionVariable.size());

        for (String expr : expressionVariable) {
            Expression expression = expressionParser.parseExpression("#" + expr.trim());
            Object value = expression.getValue(evaluationContext);
            namedParams.put(expr, value);
        }

        return namedParams;
    }

    /**
     * 替换模板变量
     *
     * @param template    模板内容
     * @param namedParams 命名参数
     * @return
     */
    protected String replaceTemplateContent(String template, Map<String, Object> namedParams) {
        boolean noneReplace = template == null || namedParams == null;
        if (noneReplace) {
            return template;
        }

        StringSubstitutor sub = new StringSubstitutor(namedParams, "{", "}", '{');
        template = sub.replace(template);

        return template;
    }
}
