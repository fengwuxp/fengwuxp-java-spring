package com.wuxp.api.exception;

import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.BusinessServiceException;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.api.signature.ApiSignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Set;


/**
 * 基于restful的统一异常处理
 */
@Slf4j
public class RestfulExceptionHandler {
    /**
     * 参数校验异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ApiResp<Void> handleValidationException(ConstraintViolationException exception) {

        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String message = constraintViolation.getMessage();
            return RestfulApiRespFactory.badRequest(message);
        }
        return RestfulApiRespFactory.badRequest(exception.getMessage());
    }

    /**
     * controller bind data 的异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ApiResp<Void> handleMethodBindException(BindException exception) {
        FieldError fieldError = exception.getFieldError();
        if (fieldError == null) {
            return RestfulApiRespFactory.badRequest(exception.getMessage());
        }
        String message = fieldError.getDefaultMessage();
        return RestfulApiRespFactory.badRequest(message);
    }


    /**
     * 比如404的异常就会被这个方法捕获
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResp<Void> handle404Error(Exception exception) throws Exception {
        return RestfulApiRespFactory.notFound(exception.getMessage());
    }


    /**
     * 处理api签名验证失败异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({ApiSignatureException.class})
    @ResponseBody
    public ApiResp<Void> handleApiSignatureException(ApiSignatureException exception) {
        return RestfulApiRespFactory.badRequest(exception.getMessage());
    }

    /**
     * 处理业务异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({BusinessServiceException.class})
    @ResponseBody
    public ApiResp<Integer> handleBusinessServiceException(BusinessServiceException exception) {
        return RestfulApiRespFactory.error(exception.getMessage(), exception.getErrorCode());
    }

//    /**
//     * 处理权限判断异常
//     *
//     * @param exception
//     * @return
//     */
//    @ExceptionHandler({AccessDeniedException.class})
//    @ResponseBody
//    public ApiResp<Void> handleAccessDeniedException(AccessDeniedException exception) {
//        return RestfulApiRespFactory.error(exception.getMessage());
//    }

    /**
     * 处理所有异常
     *
     * @param request
     * @param response
     * @param exception
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiResp<Void> handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        Throwable throwable = exception;
        log.error("统一异常拦截", exception);
        if (throwable instanceof UndeclaredThrowableException) {
            // 获取真正的异常
            InvocationTargetException invocationTargetException = (InvocationTargetException) ((UndeclaredThrowableException) throwable).getUndeclaredThrowable();
            throwable = invocationTargetException.getTargetException();
        }

        return RestfulApiRespFactory.error(throwable.getMessage());
    }
}
