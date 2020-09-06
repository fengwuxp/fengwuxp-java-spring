package com.wuxp.security.example.endpoint;

import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.captcha.CaptchaGenerateResult;
import com.wuxp.security.captcha.CombinationCaptcha;
import com.wuxp.security.captcha.SimpleCaptchaUseType;
import com.wuxp.security.captcha.configuration.PictureCaptchaProperties;
import com.wuxp.security.captcha.configuration.QrCodeCaptchaProperties;
import com.wuxp.security.captcha.configuration.WuxpCaptchaProperties;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptchaValue;
import com.wuxp.security.captcha.picture.PictureCaptcha;
import com.wuxp.security.captcha.picture.PictureCaptchaGenerateResult;
import com.wuxp.security.captcha.picture.PictureCaptchaGenerator;
import com.wuxp.security.captcha.picture.PictureCaptchaType;
import com.wuxp.security.captcha.qrcode.QrCodeCaptcha;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaGenerateResult;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaGenerator;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * captcha endpoint
 */
@Validated
@RestController
@RequestMapping("/captcha")
@Slf4j
public class CaptchaEndpoint {

    @Autowired
    private PictureCaptcha pictureCaptcha;

    @Autowired
    private MobileCaptcha mobileCaptcha;

    @Autowired
    private QrCodeCaptcha qrCodeCaptcha;

    @Autowired
    private CombinationCaptcha combinationCaptcha;

    @Autowired
    private PictureCaptchaGenerator pictureCaptchaGenerator;

    @Autowired
    private QrCodeCaptchaGenerator qrCodeCaptchaGenerator;

    @Autowired
    protected WuxpCaptchaProperties wuxpCaptchaProperties;

    @RequestMapping("/combination/{useType}")
    @ResponseBody
    public CaptchaGenerateResult captcha(@PathVariable String useType, @NotNull(message = "key不能为空") String key) {
        return combinationCaptcha.generate(useType, key);
    }


    /**
     * 获取图片验证码
     *
     * @param useType use type
     * @param type    picture captcha type {@link PictureCaptchaType} example
     * @return
     * @throws Exception
     */
    @RequestMapping("/picture/{useType}")
    @ResponseBody
    public ApiResp<Map<String, String>> pictureCaptcha(@PathVariable PictureCaptchaType useType, String type) throws Exception {
        PictureCaptchaGenerateResult captchaGenerateResult = pictureCaptcha.generate(useType.name(), type);
        if (!captchaGenerateResult.isSuccess()) {
            return RestfulApiRespFactory.error(captchaGenerateResult.getErrorMessage());
        }
        PictureCaptchaProperties pictureProperties = wuxpCaptchaProperties.getPictureProperties(type);
        //convert base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write((BufferedImage) pictureCaptchaGenerator.generate(
                captchaGenerateResult.getCaptchaContent(),
                pictureProperties.getWidth(),
                pictureProperties.getHeight(),
                pictureProperties.isGif()
        ), "png", outputStream);
        String value = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        Map<String, String> map = new HashMap<>(2);
        map.put("key", captchaGenerateResult.getKey());
        map.put("value", value);

        return RestfulApiRespFactory.ok(map);
    }

    /**
     * 获取手机验证码
     *
     * @param useType {@Link SimpleCaptchaUseType}
     * @param mobile
     * @see SimpleCaptchaUseType
     */
    @RequestMapping("/mobile/{useType}")
    @ResponseBody
    public ApiResp<Map<String, String>> mobileCaptcha(@PathVariable SimpleCaptchaUseType useType, String mobile) {
        assert mobile != null;

        CaptchaGenerateResult<MobileCaptchaValue> mobileCaptchaGenerateResult = mobileCaptcha.generate(useType, mobile);
        log.info("获取手机验证码{} {}", useType, mobile);
        if (!mobileCaptchaGenerateResult.isSuccess()) {
            return RestfulApiRespFactory.error(mobileCaptchaGenerateResult.getErrorMessage());
        }
        Map<String, String> map = new HashMap<>(2);
        return RestfulApiRespFactory.ok(map);

    }

    /**
     * 获取二维码
     *
     * @param useType {@Link SimpleCaptchaUseType}
     */
    @RequestMapping("/qr_code/{useType}")
    @ResponseBody
    public ApiResp<Map<String, Object>> qrCodeCaptcha(@PathVariable SimpleCaptchaUseType useType) throws Exception {
        QrCodeCaptchaGenerateResult result = qrCodeCaptcha.generate(useType, UUID.randomUUID().toString());
//        if (true) {
//            throw new RuntimeException("测试异常");
//        }
        log.info("获取二维码{}", useType);
        if (!result.isSuccess()) {
            return RestfulApiRespFactory.error(result.getErrorMessage());
        }
        //convert base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QrCodeCaptchaValue qrCodeCaptchaValue = result.getValue();
        QrCodeCaptchaProperties qrCodeCaptchaProperties = wuxpCaptchaProperties.getQrCodeCaptchaProperties(useType.name());
        String path = CaptchaEndpoint.class.getResource("/logo.png").getPath();
        ImageIO.write((BufferedImage) qrCodeCaptchaGenerator.generate(
                qrCodeCaptchaValue.getValue(),
                qrCodeCaptchaProperties.getWidth(),
                qrCodeCaptchaProperties.getMargin(),
//                qrCodeCaptchaProperties.getLogo(),
                path,
                qrCodeCaptchaProperties.getLogoWidth(),
                qrCodeCaptchaProperties.getLogoBorderWith(),
                qrCodeCaptchaProperties.getLogoBorderColor()
        ), "png", outputStream);
        String value = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        Map<String, Object> map = new HashMap<>(2);
        map.put("key", result.getKey());
        map.put("value", value);
        map.put("expireTime", result.getValue().getExpireTime());
        return RestfulApiRespFactory.ok(map);

    }
}
