package com.wuxp.security.authenticate.scancode;

import com.wuxp.security.captcha.qrcode.QrCodeCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

@Slf4j
public class ScanCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


//    private final static String LOGIN_PATH = "/login";
//
//    private final static String POLLING_PATH = "/polling";
//
//    //标记二维码已经被扫描
//    private final static String UPDATE_STATE = "mark";

    private String qrCodeCaptchaKeyParameter = "qrCodeCaptchaKey";

    private String qrCodeParameter = "qrCode";

    private String tokenHeaderName = "Access-Token";

    private boolean postOnly = true;

    private QrCodeCaptcha qrCodeCaptcha;

    public ScanCodeAuthenticationFilter(String patternUrl) {
        super(new AntPathRequestMatcher(patternUrl, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(MessageFormat.format("Authentication method not supported: {0}", request.getMethod()));
        }
//        String requestURI = request.getRequestURI();
//        if (requestURI.endsWith(POLLING_PATH)) {
//            //轮询处理
//            String qrCodeCaptchaKey = obtainQrCodeCaptchaKey(request);
//            if (!qrCodeCaptcha.isEffective(qrCodeCaptchaKey)) {
//                // 返回无效的提示
//                Map<String, Object> map = new HashMap<>();
//                map.put("message", "无效的二维码");
//                map.put("code", -1);
//                response.setStatus(HttpStatus.BAD_REQUEST.value());
//                response.getWriter().write(JSON.toJSONString(map));
//                return null;
//            }
//
//            //判断是否登录
//            boolean isLogin = SecurityContextHolder.getContext().getAuthentication() != null &&
//                    SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
//                    //when Anonymous Authentication is enabled
//                    !(SecurityContextHolder.getContext().getAuthentication()
//                            instanceof AnonymousAuthenticationToken);
//            if (isLogin) {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                Map<String, Object> map = new HashMap<>();
//                map.put("code", -1);
//                response.getWriter().write(JSON.toJSONString(map));
//
//            }
//            return null;
//
//        }
//
//        if (!requestURI.endsWith(LOGIN_PATH)) {
//            // 404
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            return null;
//        }


        String qrCode = obtainQrCode(request);
        String accessToken = obtainAccessToken(request);
        if (qrCode == null) {
            qrCode = "";
        }
        if (accessToken == null) {
            accessToken = "";
        }

        qrCode = qrCode.trim();
        accessToken = accessToken.trim();

        ScanCodeAuthenticationToken authRequest = new ScanCodeAuthenticationToken(accessToken, qrCode);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, ScanCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainQrCodeCaptchaKey(HttpServletRequest request) {
        return request.getParameter(qrCodeCaptchaKeyParameter);
    }

    private String obtainQrCode(HttpServletRequest request) {
        return request.getParameter(qrCodeParameter);
    }

    private String obtainAccessToken(HttpServletRequest request) {
        return request.getHeader(tokenHeaderName);
    }

    public String getScanCodeParameter() {
        return qrCodeParameter;
    }

    public void setQrCodeParameter(String qrCodeParameter) {
        Assert.hasText(qrCodeParameter, "QrCode parameter must not be empty or null");
        this.qrCodeParameter = qrCodeParameter;
    }

    public String getQrCodeCaptchaKeyParameter() {
        return qrCodeCaptchaKeyParameter;
    }

    public void setQrCodeCaptchaKeyParameter(String qrCodeCaptchaKeyParameter) {
        Assert.hasText(qrCodeCaptchaKeyParameter, "QrCode captcha key parameter must not be empty or null");
        this.qrCodeCaptchaKeyParameter = qrCodeCaptchaKeyParameter;
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public void setTokenHeaderName(String tokenHeaderName) {
        Assert.hasText(tokenHeaderName, "TokenHeaderName must not be empty or null");
        this.tokenHeaderName = tokenHeaderName;
    }

    public QrCodeCaptcha getQrCodeCaptcha() {
        return qrCodeCaptcha;
    }

    public void setQrCodeCaptcha(QrCodeCaptcha qrCodeCaptcha) {
        this.qrCodeCaptcha = qrCodeCaptcha;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
