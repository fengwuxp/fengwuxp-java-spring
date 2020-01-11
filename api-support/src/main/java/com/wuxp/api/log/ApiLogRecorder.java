package com.wuxp.api.log;


import org.springframework.expression.EvaluationContext;

/**
 * 操作日志记录者
 */
public interface ApiLogRecorder {

    /**
     * 记录日志
     *
     * @param apiLogModel
     * @param evaluationContext     经过spel表达式计算过的日志内容
     * @param throwable   执行异常
     */
    void log(ApiLogModel apiLogModel,
             EvaluationContext evaluationContext,
             Throwable throwable);
}
