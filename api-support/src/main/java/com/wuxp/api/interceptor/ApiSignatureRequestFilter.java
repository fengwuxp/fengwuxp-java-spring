package com.wuxp.api.interceptor;

import com.wuxp.api.signature.ApiSignatureException;
import com.wuxp.api.signature.ApiSignatureStrategy;
import com.wuxp.api.signature.InternalApiSignatureRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 用于验证api 签名的过滤器
 * 仅用于拦截spring security 登录相关处理的路径，控制的处理{@link ApiInterceptor#checkApiSignature}
 *
 * @author wxup
 */
@Slf4j
@Setter
public class ApiSignatureRequestFilter extends OncePerRequestFilter implements BeanFactoryAware {

    private BeanFactory beanFactory;

    private ApiSignatureStrategy apiSignatureStrategy;

    private RequestMatcher requiresCheckSignRequestMatcher;

    public ApiSignatureRequestFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApiSignatureStrategy apiSignatureStrategy = this.apiSignatureStrategy;
        if (apiSignatureStrategy == null) {
            return;
        }

        // 默认所有的参数都需要参与签名
        InternalApiSignatureRequest signatureRequest = new InternalApiSignatureRequest(request);
        final Map<String, Object> apiSignatureValues = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            if (values != null && values.length > 0 && StringUtils.hasText(values[0])) {
                apiSignatureValues.put(key, values[0]);
            }
        });
        signatureRequest.setApiSignatureValues(apiSignatureValues);
        try {
            apiSignatureStrategy.check(signatureRequest);
        } catch (ApiSignatureException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }


    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        if (this.apiSignatureStrategy == null) {
            try {
                this.setApiSignatureStrategy(this.beanFactory.getBean(ApiSignatureStrategy.class));
            } catch (BeansException e) {
                log.warn("not found ApiSignatureStrategy Bean");
            }
        }
        if (this.requiresCheckSignRequestMatcher == null) {
            // 匹配所有的登录接口
            this.requiresCheckSignRequestMatcher = new AntPathRequestMatcher("/**/login");
        }
    }
}
