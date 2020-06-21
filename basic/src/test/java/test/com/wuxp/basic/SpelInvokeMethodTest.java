package test.com.wuxp.basic;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * spel 表达式调用方法例子
 */
public class SpelInvokeMethodTest {


    @Test
    public void testStaticMethodInvoke() throws Exception {
        String expression = "T(test.com.wuxp.basic.Test1).test1(#v1,#v2)";

        EvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("v1", 1);
        evaluationContext.setVariable("v2", 3);
        ExpressionParser expParser = new SpelExpressionParser();
        Expression exp = expParser.parseExpression(expression);

        String result = exp.getValue(evaluationContext, String.class);
        System.out.println("result=>: " + result);
    }


    @Test
    public void testMethodInvoke() throws Exception {
        String expression = "#testObject.test1(#v1,#v2)";

        EvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("testObject", new Test1());
        evaluationContext.setVariable("v1", 1);
        evaluationContext.setVariable("v2", 3);
        ExpressionParser expParser = new SpelExpressionParser();
        Expression exp = expParser.parseExpression(expression);

        String result = exp.getValue(evaluationContext, String.class);
        System.out.println("result=>: " + result);
    }
}
