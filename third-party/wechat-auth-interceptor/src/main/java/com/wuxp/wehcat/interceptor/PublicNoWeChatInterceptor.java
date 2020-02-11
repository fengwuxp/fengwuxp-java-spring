package com.wuxp.wehcat.interceptor;


import javax.servlet.http.HttpServletRequest;

/**
 * 微信公众平号拦截器
 *
 * @author wxup
 * @create 2018-07-21 17:44
 **/
public class PublicNoWeChatInterceptor extends AbstractWeChatInterceptor {


    public PublicNoWeChatInterceptor() {
    }

    @Override
    protected boolean isIgnore(HttpServletRequest request) {
        boolean ignore = super.isIgnore(request);
        if (ignore) {
            return ignore;
        }
        return !this.isWeiXinBrowser(request) && !this.isMock;
    }
}
