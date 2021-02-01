package com.fengwuxp.wechat.multiple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.fengwuxp.wechat.multiple.WeChatMultipleProperties.WE_CHAT_APP_ID_HEADER_KEY;

/**
 * 用于http请求时提供微信appId的提供者
 *
 * @author wuxp
 */
@Slf4j
public class HttpRequestWeChatServiceAppIdProvider implements WeChatAppIdProvider {


    protected String weChatAppIdName;

    public HttpRequestWeChatServiceAppIdProvider() {
        this.weChatAppIdName = WE_CHAT_APP_ID_HEADER_KEY;
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
