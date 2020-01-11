//package com.wuxp.security.captcha.endpoint;
//
//import com.wuxp.security.captcha.picture.PictureCaptcha;
//import com.wuxp.security.captcha.picture.PictureCaptchaType;
//import com.wuxp.security.captcha.picture.PictureCaptchaValue;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.provider.endpoint.AbstractEndpoint;
//import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * captcha endpoint
// */
//@FrameworkEndpoint
//public class CaptchaEndpoint extends AbstractEndpoint {
//
//
//    @Autowired
//    private PictureCaptcha pictureCaptcha;
//
//
//    /**
//     * 获取图片验证码
//     *
//     * @param type {@link PictureCaptchaType}
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/captcha/{type}")
//    @ResponseBody
//    public Map<String, String> pictureCaptcha(@PathVariable String type) throws Exception {
//        PictureCaptchaValue captchaValue = pictureCaptcha.generate(type);
//
//        Map<String, String> map = new HashMap<>(2);
//        map.put("key", captchaValue.getKey());
//
//        //convert base64
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write((BufferedImage) captchaValue.getPicture(), "png", outputStream);
//        String value = Base64.getEncoder().encodeToString(outputStream.toByteArray());
//        map.put("value", value);
//        return map;
//    }
//}
