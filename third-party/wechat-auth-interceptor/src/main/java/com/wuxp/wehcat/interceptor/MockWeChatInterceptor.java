package com.wuxp.wehcat.interceptor;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 本地测试用微信openid模拟
 */
@Slf4j
public class MockWeChatInterceptor extends HandlerInterceptorAdapter implements WeChatAuthInterceptor {


    private Faker faker = new Faker();

    /**
     * 是否为模拟环境
     */
//    @Value("${}")
    private boolean isMock;

    public MockWeChatInterceptor() {
        this.isMock = "1".equals(System.getProperty("mock_weixin"));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!this.isMock) {
            return true;
        }

        String openId = request.getParameter("openid");

        if (!StringUtils.hasText(openId)) {
            //从缓存中来
            openId = AbstractWeChatInterceptor.getCachedOpenId(request);
        }

        if (!StringUtils.hasText(openId)) {
            //自动模拟openId
            // openId = "Mock_wx_openid_" + System.currentTimeMillis() + "_" + request.hashCode();
            openId = System.getProperty("openid");
        }
        log.info("enable mock  open id {}", openId);

        //设置openId
        request.setAttribute(AbstractWeChatInterceptor.WX_OPEN_ID, openId);
        HttpSession session = request.getSession(true);
        session.setAttribute(AbstractWeChatInterceptor.WX_OPEN_ID, openId);

        WxMpUser user = AbstractWeChatInterceptor.getCachedWxMpUser(request);

        if (user == null) {

            user = new WxMpUser();
            user.setOpenId(openId);
            user.setNickname(faker.name().name());
            //user.setHeadImgUrl(request.getContextPath() + "/res/images/head.jpg");
            user.setCity(faker.address().cityName());

            session.setAttribute(AbstractWeChatInterceptor.WX_USER_INFO, user);
        }

        return true;
    }


    @Override
    public boolean isMock() {
        return this.isMock;
    }
}
