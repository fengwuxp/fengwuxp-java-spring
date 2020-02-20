package com.wuxp.security.example.exception;

import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.RestfulExceptionHandler;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一错误异常处理
 */
@ControllerAdvice
@Slf4j
public class UnifiedExceptionHandler extends RestfulExceptionHandler {

    /**
     * 处理权限判断异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public ApiResp<Void> handleAccessDeniedException(AccessDeniedException exception) {
        return RestfulApiRespFactory.error(exception.getMessage());
    }
}
