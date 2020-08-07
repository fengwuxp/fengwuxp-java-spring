package com.wuxp.security.example.log;

import com.wuxp.api.log.ApiLogModel;
import com.wuxp.api.log.ApiLogRecorder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;

/**
 * mock 日志记录
 */
@Slf4j
public class MockApiLogRecorder implements ApiLogRecorder {

    @Override
    public void log(ApiLogModel apiLogModel, EvaluationContext evaluationContext, Throwable throwable) {
        log.info("模拟日志记录 {}", apiLogModel);

    }
}
