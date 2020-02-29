package com.oak.member.interceptor.impl;

import com.oak.member.cache.MemberSessionCacheHelper;
import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.enums.OpenType;
import com.oak.member.management.member.MemberManagementService;
import com.oak.member.management.member.req.QueryMemberByOpenIdReq;
import com.oak.member.management.member.req.RegisterMemberFromWxReq;
import com.oak.member.services.member.info.MemberInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.security.jwt.JwtTokenProvider;
import com.wuxp.wehcat.interceptor.AbstractWeChatInterceptor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author laiy
 * create at 2020-02-21 14:53
 * @Description
 */
@Component
@Slf4j
public class UserActionImpl implements AbstractWeChatInterceptor.UserAction {

    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private MemberSessionCacheHelper memberSessionCacheHelper;

    @Autowired(required = true)
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean login(String openId, WxMpUser wxMpUser, HttpServletRequest request, HttpServletResponse response) {
        log.info("查询用户 openId--> " + openId);
        MemberInfo memberInfo = null;
        //查询用户是否存在
        QueryMemberByOpenIdReq req = new QueryMemberByOpenIdReq();
        req.setOpenId(openId)
                .setOpenType(OpenType.WEIXIN);
        ApiResp<MemberInfo> resp = memberManagementService.queryMemberByOpenId(req);

        if (!resp.isSuccess()) {
            //用户不存在，注册
            RegisterMemberFromWxReq registerReq = new RegisterMemberFromWxReq();
            BeanUtils.copyProperties(wxMpUser, registerReq);
            ApiResp<MemberInfo> registerResp = memberManagementService.registerFromWx(registerReq);
            AssertThrow.assertResp(registerResp);
            memberInfo = registerResp.getData();
        } else {
            memberInfo = resp.getData();
        }

        // 用户存在 则登陆

        if(!MemberVerifyStatus.APPROVED.equals(memberInfo.getVerify())){
            //登录审核判断
            //StringBuffer urlBuffer=new StringBuffer();
            //try {
            //    urlBuffer.append(request.getContextPath()).append("/common/message.htm?")
            //            .append(URLEncoder.encode("您的账号还未审核通过，暂不能登录","UTF-8"));
            //    response.sendRedirect(urlBuffer.toString());
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}
            return false;
        }

        //TODO
        String cookieValue = jwtTokenProvider.generateAccessToken(memberInfo.getId() + memberInfo.getNo() +System.currentTimeMillis()).getToken();
        memberSessionCacheHelper.save(cookieValue, memberInfo);
        Cookie cookie = new Cookie(MemberSessionCacheHelper.OAK_MEMBER_KEY, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 3600 * 100);
        response.addCookie(cookie);
        request.setAttribute(MemberSessionCacheHelper.OAK_SESSION_MEMBER_KEY, cookieValue);
        return true;
    }

    @Override
    public boolean isLogin(HttpServletRequest request) {
        String requestURI = request.getRequestURI().toString();
        String sessionKey = (String)request.getAttribute(MemberSessionCacheHelper.OAK_SESSION_MEMBER_KEY);
        //用户已经登录
        if (memberSessionCacheHelper.get(sessionKey) != null) {
            return true;
        }

        return false;
    }
}
