package com.wuxp.wehcat.interceptor;

import com.wuxp.basic.utils.IpAddressUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.DigestUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * 微信拦截器，用于获取微信用户信息，支持微信公众号的授权登录 以及微信开放平台的网页扫码登录
 * 微信开放平台扫码登录文档：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
 */
@Slf4j
@Setter
@Getter
public abstract class AbstractWeChatInterceptor implements HandlerInterceptor, WeChatAuthInterceptor, InitializingBean, BeanFactoryAware {

    public enum WxAuthScope {

        snsapi_base,

        snsapi_userinfo,

        snsapi_login
    }


    public static final String WX_OPEN_ID = AbstractWeChatInterceptor.class.getName() + ".WX_OPEN_ID";
    public static final String WX_AUTH_REQ_URL = AbstractWeChatInterceptor.class.getName() + ".WX_AUTH_REQ_URL";
    public static final String WX_APP_INSTALLED = AbstractWeChatInterceptor.class.getName() + ".WX_APP_INSTALLED";
    public static final String WX_USER_INFO = AbstractWeChatInterceptor.class.getName() + ".WX_USER_INFO";

    public static final int WX_STATE_VALID_TIME = 5 * 60 * 1000;

    protected static final String WX_AUTH_RETRIES = AbstractWeChatInterceptor.class.getName() + ".WX_AUTH_RETRIES";

    //
    protected String wxAuthCallbackStateValuePrefix;

    //应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
    protected WxAuthScope wxAuthScope = WxAuthScope.snsapi_userinfo;

    //忽略拦截的地址
    protected String[] ignoreUrlPatterns;


    //是否保存到cookie
    protected boolean save2Cookie;

    //cookie超时时间
    protected long cookieTimeout = 60 * 60 * 1000;

    //认证失败后的重定向地址
    protected String authFailUrl;

    //认证重试次数
    protected int maxAuthRetries = 3;

    /**
     * 是否为模拟环境
     */
    protected boolean isMock;

    protected BeanFactory beanFactory;

    protected WxMpService wxService;

    protected UserAction userAction;


    /**
     * 重定向的 base path   contains a protocol, server name, port number, and server path,
     */
    protected String redirectBasePath;

    //拦截器获取openId后执行的动作


//    public AbstractWeixinInterceptor(WxMpService wxService, UserAction userAction) {
//        this.isMock = "1".equalsIgnoreCase(System.getProperty("mock_weixin"));
//        this.wxService = wxService;
//        this.userAction = userAction;
//    }

    public AbstractWeChatInterceptor() {
        this.isMock = "1".equals(System.getProperty("mock_weixin"));
        this.wxAuthCallbackStateValuePrefix = this.getClass().getSimpleName();
    }

    @Override
    public void afterPropertiesSet() {
        if (this.wxAuthScope == null) {
            this.wxAuthScope = WxAuthScope.snsapi_userinfo;
        }

        if (!StringUtils.hasText(this.wxAuthCallbackStateValuePrefix)) {
            this.wxAuthCallbackStateValuePrefix = AbstractWeChatInterceptor.class.getSimpleName();
        }

        BeanFactory beanFactory = this.beanFactory;
        if (this.userAction == null) {
            this.userAction = beanFactory.getBean(UserAction.class);
        }
        if (this.wxService == null) {
            this.wxService = beanFactory.getBean(WxMpService.class);
        }
    }


    public void setMaxAuthRetries(int maxAuthRetries) {
        if (maxAuthRetries > 0) {
            this.maxAuthRetries = maxAuthRetries;
        }
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (userAction == null) {
            if (log.isWarnEnabled()) {
                log.warn("userAction is null");
            }
            return true;
        }

        //用户已经登录了,或者是忽略验证的url 直接过
        if (userAction.isLogin(request) || isIgnore(request)) {
            return true;
        }

        //如果微信用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
        //若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数redirect_uri?state=STATE
        String wxCallbackStateValue = request.getParameter("state");
        boolean hasState = StringUtils.hasText(wxCallbackStateValue);
        boolean isValidWxCallback = hasState && isStateValid(wxCallbackStateValue);

        if (log.isDebugEnabled()) {
            log.debug("是否为来自微信的回调：{}，请求url：{}，查询参数：{}", isValidWxCallback, request.getRequestURL(), request.getQueryString());
        }

        HttpSession session = request.getSession();

        //微信浏览器
        if (this.isWeiXinBrowser(request)) {
            //不是mock 环境存在state 且state被更改了
            boolean isNext = !this.isMock && hasState && !isValidWxCallback;
            if (isNext) {
                return true;
            }
            //如果已经有openId
            String cachedOpenId = getCachedOpenId(request);
            if (log.isDebugEnabled()) {
                log.debug("微信浏览器：openId=" + cachedOpenId);
            }
            if (StringUtils.hasText(cachedOpenId)) {
                return userAction.login(cachedOpenId, getCachedWxMpUser(request), request, response);
            }
        } else {
            if (log.isDebugEnabled()) {
                //非微信环境没有sate 将state设置到request中
                log.debug("普通浏览器");
            }
            if (!isValidWxCallback) {
                request.setAttribute("wxAuthState", this.getCurrentStateValue());
                //直接放过
                return true;
            }
            //普通浏览器中，如果state参数验证通过，则要去微信获取用户信息，先把会话中的微信用户信息移除
            clearCache(request);
            session.removeAttribute(AbstractWeChatInterceptor.WX_APP_INSTALLED);
        }

        //进行获取微信信息的处理
        if (log.isTraceEnabled()) {
            log.trace(getAddrInfo(request) + " AbstractWeixinInterceptor url:" + request.getRequestURL());
        }

        //拿到微信服务器传过来的code
        String wxCallbackCode = request.getParameter("code");
        boolean hasCallbackCode = isValidWxCallback && StringUtils.hasText(wxCallbackCode);

        //如果是微信的认证回调
        //@// TODO: 2016/7/18   检查请求是否来自微信服务器，防止伪请求
        if (hasCallbackCode) {

            if (log.isDebugEnabled()) {
                log.debug(getAddrInfo(request) + " from weixin callback request:" + getUrl(request));
            }

            String appInstalled = request.getParameter("isappinstalled");

            //如果已经关注服务号
            if ("1".equals(appInstalled)) {
                session.setAttribute(WX_APP_INSTALLED, Boolean.TRUE);
            }

            // 2 第二步：通过code换取网页授权access_token
            int n = maxAuthRetries;
            //尝试重复获取
            WxOAuth2AccessToken accessToken = null;
            while (n-- > 0 && accessToken == null) {
                accessToken = requestToken(request, session, wxCallbackCode);
            }

            //如果重复都无法获取，则放弃
            if (accessToken == null) {
                //@// TODO: 2016/7/5
                authFail(request, response);
                return false;
            }


            //清除原请求页面
            session.removeAttribute(WX_AUTH_REQ_URL);
            session.removeAttribute(WX_AUTH_RETRIES);


            if (userAction == null) {
                return true;
            }

            String openId = getCachedOpenId(request);

            if (StringUtils.hasText(openId)) {
                return userAction.login(openId, getCachedWxMpUser(request), request, response);
            }

        }

//        1 第一步：用户同意授权，获取code
//        2 第二步：通过code换取网页授权access_token
//        3 第三步：刷新access_token（如果需要）
//        4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
//        5 附：检验授权凭证（access_token）是否有效

        //保存原始请求地址

        tryRedirect2WxAuth(request, response, session);

        return false;
    }


    /**
     * 是否为微信浏览器
     *
     * @param request
     * @return
     */
    protected boolean isWeiXinBrowser(HttpServletRequest request) {
        if (this.isMock) {
            return true;
        }
        String userAgent = request.getHeader("user-agent");
        userAgent = userAgent == null ? "" : userAgent.toLowerCase();
        return userAgent.contains("micromessenger");
    }

    /**
     * 是否忽略
     *
     * @param request
     * @return
     */
    protected boolean isIgnore(HttpServletRequest request) {

        if (ignoreUrlPatterns == null
                || ignoreUrlPatterns.length < 1) {
            return false;
        }


        String url = getUrl(request);

        String uri = request.getRequestURI();

        PathMatcher matcher = new AntPathMatcher();

        for (String pattern : ignoreUrlPatterns) {
            if (matcher.match(pattern, url)
                    || matcher.match(pattern, uri)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 获取当前的 state 主要用于微信扫码登录
     *
     * @return
     */
    protected String getCurrentStateValue() {

        //5分钟之内有效
        long currentTimeMillis = System.currentTimeMillis();
        long remaining = currentTimeMillis % WX_STATE_VALID_TIME;

        String stateValue = this.hashCode() + "_" + (currentTimeMillis / WX_STATE_VALID_TIME);

        String delim = "_P";
        return wxAuthCallbackStateValuePrefix + delim + currentTimeMillis + delim + DigestUtils.md5DigestAsHex(stateValue.getBytes(StandardCharsets.UTF_8)) + delim + remaining;
    }


    /**
     * @param stateValue
     * @return
     * @see this.getCurrentStateValue()
     */
    protected boolean isStateValid(String stateValue) {
        try {

            String delim = "_P";

            String[] strs = stateValue.split(delim);

            if (strs.length < 4
                    || !wxAuthCallbackStateValuePrefix.equals(strs[0])) {
                return false;
            }

            //5分钟内有效
            long currentTimeMillis = System.currentTimeMillis();

            long currTime = currentTimeMillis / WX_STATE_VALID_TIME;

            long prevTimeMillis = Long.parseLong(strs[1].trim());
            long genTime = prevTimeMillis / WX_STATE_VALID_TIME;

            long timeout = currTime - genTime;

            //保证值没有被人改变
            String md5Value = this.hashCode() + "_" + (currTime - timeout);
            if (!DigestUtils.md5DigestAsHex(md5Value.getBytes(StandardCharsets.UTF_8)).equals(strs[2])) {
                return false;
            }

            if (Math.abs(currentTimeMillis - prevTimeMillis) >= WX_STATE_VALID_TIME) {
                return false;
            }
            return true;


        } catch (Exception e) {
            log.warn("Verify weixin state " + stateValue + " fail", e);
            return false;
        }
    }


    private String getAddrInfo(HttpServletRequest request) {
        String realIp = IpAddressUtils.try2GetUserRealIPAddr(request);
//        String location = IPUtil.getIPLocation(request);
//        return (location != null ? ("[" + location + "]") : "") + (realIp != null ? ("[" + realIp + "]") : "");
        return realIp;
    }


    private void tryRedirect2WxAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {

        Integer retries = (Integer) session.getAttribute(WX_AUTH_RETRIES);

        if (retries == null) {
            retries = 0;
        }

        retries++;

        session.setAttribute(WX_AUTH_RETRIES, retries);

        if (retries > this.maxAuthRetries) {
            authFail(request, response);
        } else {
            redirect2WeixinAuthUrl(request, response, session);
        }
    }

    private void authFail(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (log.isInfoEnabled()) {
            log.info(getAddrInfo(request) + " weixin auth fail " + request.getSession().getAttribute(WX_AUTH_RETRIES) + "," + getUrl(request));
        }

        request.getSession().removeAttribute(WX_AUTH_RETRIES);
        request.getSession().removeAttribute(WX_AUTH_REQ_URL);

        if (StringUtils.hasText(this.authFailUrl)) {
            request.getSession().setAttribute("auth_fail_url", getUrl(request));
            response.sendRedirect(authFailUrl);
        } else {
            log.warn("认证失败，请求url：{}，查询参数：{}", request.getRequestURL(), request.getQueryString());
            //设置为无法认证
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private WxOAuth2AccessToken requestToken(HttpServletRequest request, HttpSession session, String code) {

        WxOAuth2AccessToken accessToken = null;

        try {
            accessToken = wxService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException ex) {
            log.error(getAddrInfo(request) + " oauth2getAccessToken error, code:" + code, ex);
        }

        if (accessToken == null) {
            return null;

        }
        String openId = accessToken.getOpenId();
        setOpenIdToCache(request, openId);
        //如果是用户已经关注服务号，或是授权模式用户信息
        if (Boolean.TRUE.equals(session.getAttribute(WX_APP_INSTALLED))
                || WxAuthScope.snsapi_userinfo.equals(this.getWxAuthScope())) {
            WxOAuth2UserInfo wxOAuth2UserInfo = null;
            try {
                wxOAuth2UserInfo = wxService.getOAuth2Service().getUserInfo(accessToken, "zh_CN");
            } catch (WxErrorException ex) {
                //获取微信用户信息失败则清除缓存中的openId
                clearCache(request);
                log.error(getAddrInfo(request) + "oauth2getUserInfo error, accessToken:" + accessToken, ex);
            }
            if (wxOAuth2UserInfo != null) {
                WxMpUser wxMpUser = new WxMpUser();
                wxMpUser.setProvince(wxOAuth2UserInfo.getProvince());
                wxMpUser.setCity(wxOAuth2UserInfo.getCity());
                wxMpUser.setCountry(wxOAuth2UserInfo.getCountry());
                wxMpUser.setSex(wxOAuth2UserInfo.getSex());
                wxMpUser.setHeadImgUrl(wxOAuth2UserInfo.getHeadImgUrl());
                wxMpUser.setNickname(wxOAuth2UserInfo.getNickname());
                wxMpUser.setOpenId(wxOAuth2UserInfo.getOpenid());
                wxMpUser.setUnionId(wxOAuth2UserInfo.getUnionId());
                wxMpUser.setPrivileges(wxOAuth2UserInfo.getPrivileges());
                setWxMpUserToCache(request, wxMpUser);
                if (log.isDebugEnabled()) {
                    log.debug(getAddrInfo(request) + " oauth2getUserInfo, openid:" + openId + ",user:" + wxMpUser);
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug(getAddrInfo(request) + " openid:" + openId + ",accessToken:" + accessToken);
        }

        return accessToken;
    }

    private void redirect2WeixinAuthUrl(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {

        String srcUrl = (String) session.getAttribute(WX_AUTH_REQ_URL);

        if (!StringUtils.hasText(srcUrl)) {

            srcUrl = getUrl(request);

            boolean isAuth = srcUrl.contains("code") && srcUrl.contains("state");

            if (isAuth) {
                srcUrl = buildUrl(request);
                log.debug("Url fixed " + getUrl(request) + " --> " + srcUrl);
            }

            session.setAttribute(WX_AUTH_REQ_URL, srcUrl);
        }

        //  if (UriUtils.)
        //应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
        String authorizationUrl = wxService.getOAuth2Service().buildAuthorizationUrl(srcUrl, wxAuthScope.toString(), getCurrentStateValue());

        if (log.isDebugEnabled()) {
            log.debug(getAddrInfo(request) + " " + request.getRequestURL() + " send redirect to weixin auth url:" + authorizationUrl);
        }

        //使用301重定向
        response.setStatus(301);

        response.sendRedirect(authorizationUrl);
    }

    private String buildUrl(HttpServletRequest request, String... excludeNames) throws UnsupportedEncodingException {

        String srcUrl;
        srcUrl = request.getRequestURL() + "";

        Map<String, String[]> parameterMap = request.getParameterMap();

        StringBuilder queryStr = new StringBuilder();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            //排除
            if ("state".equalsIgnoreCase(entry.getKey())
                    || "code".equalsIgnoreCase(entry.getKey())) {
                continue;
            }

            String encoding = request.getCharacterEncoding();

            if (!StringUtils.hasText(encoding)) {
                encoding = "utf-8";
            }

            String name = URLEncoder.encode(entry.getKey(), encoding);

            for (String value : entry.getValue()) {
                queryStr.append("&").append(name).append("=").append(URLEncoder.encode(value, encoding));
            }

        }

        if (queryStr.length() > 0) {
            srcUrl = srcUrl + "?" + queryStr;
        }
        return srcUrl;
    }

    private String getUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();

        String requestURL;
        if (StringUtils.hasText(this.redirectBasePath)) {
            //如果设置了重定向的base path
            requestURL = this.redirectBasePath + request.getRequestURI();
        } else {
            requestURL = request.getRequestURL().toString();
        }

        return requestURL + (StringUtils.hasText(queryString) ? "?" + queryString : "");
    }

    private static void setOpenIdToCache(HttpServletRequest request, String openId) {
        request.setAttribute(AbstractWeChatInterceptor.WX_OPEN_ID, openId);
        request.getSession().setAttribute(AbstractWeChatInterceptor.WX_OPEN_ID, openId);
    }

    private static void setWxMpUserToCache(HttpServletRequest request, WxMpUser wxMpUser) {
        request.setAttribute(AbstractWeChatInterceptor.WX_USER_INFO, wxMpUser);
        request.getSession().setAttribute(AbstractWeChatInterceptor.WX_USER_INFO, wxMpUser);
    }


    /**
     * 获取微信openId
     *
     * @param request
     * @return
     */
    public static String getCachedOpenId(HttpServletRequest request) {

        String openid = (String) request.getAttribute(AbstractWeChatInterceptor.WX_OPEN_ID);

        if (openid == null) {
            openid = (String) request.getSession().getAttribute(AbstractWeChatInterceptor.WX_OPEN_ID);
        } else {
            request.getSession().setAttribute(AbstractWeChatInterceptor.WX_OPEN_ID, openid);

        }
        return openid;
    }


    /**
     * 获取微信用户
     *
     * @param request
     * @return
     */
    public static WxMpUser getCachedWxMpUser(HttpServletRequest request) {

        WxMpUser user = (WxMpUser) request.getAttribute(AbstractWeChatInterceptor.WX_USER_INFO);

        if (user == null) {
            user = (WxMpUser) request.getSession().getAttribute(AbstractWeChatInterceptor.WX_USER_INFO);
        } else {
            request.getSession().setAttribute(AbstractWeChatInterceptor.WX_USER_INFO, user);
        }

        return user;
    }


    public static void clearCache(HttpServletRequest request) {
        HttpSession session = request.getSession();

        request.removeAttribute(AbstractWeChatInterceptor.WX_OPEN_ID);
        request.removeAttribute(AbstractWeChatInterceptor.WX_USER_INFO);
        session.removeAttribute(AbstractWeChatInterceptor.WX_OPEN_ID);
        session.removeAttribute(AbstractWeChatInterceptor.WX_USER_INFO);
    }


    /**
     * openId 获取成功后的用户动作
     */
    public interface UserAction {

        /**
         * @param openId
         * @param wxMpUser
         * @param request
         * @param response
         * @return true ：继续执行其他拦截或者到请求 false：重定向
         * 对于本接口仅有登陆成功时返回true 其他情况返回false 并进行重定向
         */
        boolean login(String openId, WxMpUser wxMpUser, HttpServletRequest request, HttpServletResponse response);

        boolean isLogin(HttpServletRequest request);
    }

}
