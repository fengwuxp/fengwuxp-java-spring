package com.wuxp.api.restful;

import com.wuxp.api.ApiResp;
import com.wuxp.api.configuration.WuxpApiSupportProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;


/**
 * 统一处理 restful api 风格的响应
 * 如果返回的是{@link ApiResp}类型的数据，并期望启用restful api风格，使用该类
 *
 * @author wuxp
 */
@Slf4j
@ConditionalOnProperty(prefix = WuxpApiSupportProperties.PREFIX, value = "enabled-restful", havingValue = "true")
@RestControllerAdvice()
public class RestfulResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return Objects.requireNonNull(returnType.getMethod()).getReturnType().isAssignableFrom(ApiResp.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof ApiResp) {
            ApiResp<?> resp = (ApiResp<?>) body;
            response.setStatusCode(resp.getHttpStatus());
            if (resp.isSuccess()) {
                return resp.getData();
            }
        }
        return body;
    }
}
