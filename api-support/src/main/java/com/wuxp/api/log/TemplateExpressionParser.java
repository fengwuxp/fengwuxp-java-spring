package com.wuxp.api.log;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

/**
 * 模板表达式解析者
 * <>
 * 1：提取模板中的模板变量，暂时只支持命令变量，example: "这是一个日志记录,类型：{type},时间:{date}" ==> 变量为 type,date
 * 2：通过 {@link  org.springframework.expression.spel.standard.SpelExpressionParser} 解析对应的变量
 * 3：替换模板变量
 * </>
 *
 * @author wuxp
 */
public interface TemplateExpressionParser {


    /**
     * 用于解析模板字符串，获取模板内容
     *
     * @param templateContent   要解析模板字符串内容
     * @param methodKey         用于获取缓存的表达式  {@link org.springframework.context.expression.CachedExpressionEvaluator#getExpression}
     * @param evaluationContext 上下文
     * @return 期望的模板内容
     */
    String parse(String templateContent, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);
}
