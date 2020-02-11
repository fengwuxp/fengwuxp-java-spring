package com.wuxp.wehcat.interceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信开放平台拦截器
 *
 * @author wxup
 * @create 2018-07-21 17:44
 **/
public class OpenWeChatInterceptor extends AbstractWeChatInterceptor {


    public OpenWeChatInterceptor() {
    }

    @Override
    protected boolean isIgnore(HttpServletRequest request) {
        boolean ignore = super.isIgnore(request);
        if (ignore) {
            return ignore;
        }
        return this.isWeiXinBrowser(request);
    }
}
