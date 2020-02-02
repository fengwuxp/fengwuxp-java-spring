package com.wuxp.security.example.exception;

import com.wuxp.api.exception.RestfulExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 统一错误异常处理
 */
@ControllerAdvice
@Slf4j
public class UnifiedExceptionHandler extends RestfulExceptionHandler {

}
