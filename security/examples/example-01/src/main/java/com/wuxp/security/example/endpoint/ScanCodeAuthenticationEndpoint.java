package com.wuxp.security.example.endpoint;

import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.captcha.qrcode.QrCodeCaptcha;
import com.wuxp.security.captcha.qrcode.QrCodeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 扫码登录端点
 */
@Slf4j
@RestController
@RequestMapping("/scan_code")
public class ScanCodeAuthenticationEndpoint {


    @Autowired
    private QrCodeCaptcha qrCodeCaptcha;


    /**
     * 轮询登录状态
     *
     * @param qrCodeKey
     */
    @RequestMapping("/state")
    public ApiResp pollingQrCodeState(String qrCodeKey) {

        //判断是否登录
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLogin = authentication != null &&
                authentication.isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(authentication instanceof AnonymousAuthenticationToken);
        if (isLogin) {
            // 已登录
            return RestfulApiRespFactory.ok(authentication);
        }

        boolean effective = qrCodeCaptcha.isEffective(qrCodeKey);
        if (!effective) {
            //无效的key
            return RestfulApiRespFactory.badRequest("无效的二维码");
        }

        QrCodeState qrCodeSate = qrCodeCaptcha.getQrCodeState(qrCodeKey);
        return RestfulApiRespFactory.unAuthorized("", qrCodeSate);
    }


    /**
     * 标记二维码扫码状态
     */
    @RequestMapping("/mark_state")
    public void markQrCodeState(String qrCodeKey, QrCodeState targetState) {
        qrCodeCaptcha.updateQrCodeState(qrCodeKey, targetState);
    }

}
