package com.wuxp.security.example.exception;

import com.wuxp.api.exception.RestfulExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class UnifiedExceptionHandler extends RestfulExceptionHandler {

//
//    /**
//     * Validator 参数校验异常处理
//     *
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler(value = BindException.class)
//    @ResponseBody
//    public RestfulApiResp handleMethodVoArgumentNotValidException(BindException ex) {
//        FieldError err = ex.getFieldError();
//        if (err == null) {
//            return RestfulApiRespFactory.badRequest(ex.getMessage());
//        }
//        // err.getField() 读取参数字段
//        // err.getDefaultMessage() 读取验证注解中的message值
//        String message = "参数{".concat(err.getField()).concat("}").concat(err.getDefaultMessage());
//        log.info("{} -> {}", err.getObjectName(), message);
//        return RestfulApiRespFactory.badRequest(message);
//    }
//
//    /**
//     * Validator 参数校验异常处理
//     *
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler(value = ConstraintViolationException.class)
//    @ResponseBody
//    public RestfulApiResp handleMethodArgumentNotValidException(ConstraintViolationException ex) {
//        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
//        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
//            PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
//            // 读取参数字段，constraintViolation.getMessage() 读取验证注解中的message值
//            NodeImpl leafNode = pathImpl.getLeafNode();
//            String paramName = leafNode.getName();
////            String message = "参数{".concat(paramName).concat("}").concat(constraintViolation.getMessage());
//            String message = constraintViolation.getMessage();
//            log.info("{} -> {} -> {}", constraintViolation.getRootBeanClass().getName(), pathImpl.toString(), message);
//            return RestfulApiRespFactory.badRequest(message);
//        }
//        return RestfulApiRespFactory.badRequest(ex.getMessage());
//    }
}
