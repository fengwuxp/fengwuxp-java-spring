package com.fengwuxp.wechat.multiple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于http请求时提供微信appId的提供者
 */
@Slf4j
public class HttpRequestWeChatServiceAppIdProvider implements WeChatAppIdProvider {

    public static final String WE_CHAT_APP_ID_HEADER_KEY = "We-Chat-Mp-AppId";

    protected String weChatAppIdName = WE_CHAT_APP_ID_HEADER_KEY;

    public HttpRequestWeChatServiceAppIdProvider() {
    }

    public HttpRequestWeChatServiceAppIdProvider(String weChatAppIdName) {
        this.weChatAppIdName = weChatAppIdName;
    }

    @Override
    public String getTargetAppId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            throw new RuntimeException("requestAttributes is null");
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        return request.getHeader(this.weChatAppIdName);
    }


}
