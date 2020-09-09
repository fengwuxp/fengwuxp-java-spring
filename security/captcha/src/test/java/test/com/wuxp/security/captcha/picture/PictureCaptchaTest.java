package test.com.wuxp.security.captcha.picture;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.SimpleCaptchaUseType;
import com.wuxp.security.captcha.configuration.PictureCaptchaProperties;
import com.wuxp.security.captcha.configuration.WuxpCaptchaProperties;
import com.wuxp.security.captcha.constant.MessageKeyConstant;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptchaGenerateResult;
import com.wuxp.security.captcha.mobile.MobileCaptchaValue;
import com.wuxp.security.captcha.picture.PictureCaptcha;
import com.wuxp.security.captcha.picture.PictureCaptchaGenerateResult;
import com.wuxp.security.captcha.picture.PictureCaptchaType;
import com.wuxp.security.captcha.picture.PictureCaptchaValue;
import com.wuxp.security.captcha.qrcode.QrCodeCaptcha;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaGenerateResult;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaValue;
import com.wuxp.security.captcha.qrcode.QrCodeState;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaptchaApplicationTest.class)
@Slf4j
public class PictureCaptchaTest {

    @Autowired
    private WuxpCaptchaProperties wuxpCaptchaProperties;

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    private PictureCaptcha pictureCaptcha;

    @Autowired
    private MobileCaptcha mobileCaptcha;

    @Autowired
    private QrCodeCaptcha qrCodeCaptcha;

    @Test
    public void testCaptchaProperties() {

        String message = messageSource.getMessage(MessageKeyConstant.PICTURE_CAPTCHA_EMPTY, null, "1", null);
        log.debug("获取消息提示：{}", message);
        Map<String, PictureCaptchaProperties> picture = wuxpCaptchaProperties.getPicture();
        log.debug("获取配置值：{}", picture.keySet());
    }

    @Test
    public void testPictureCaptcha() {

        PictureCaptchaGenerateResult captchaGenerateResult = pictureCaptcha.generate(PictureCaptchaType.ARITHMETIC.name(), "login");
        Assert.assertTrue(pictureCaptcha.isEffective(captchaGenerateResult.getKey()));
        PictureCaptchaValue value = captchaGenerateResult.getValue();
        Captcha.CaptchaVerifyResult verify = pictureCaptcha.verify(captchaGenerateResult.getKey(), new PictureCaptchaValue(value.getValue(), value.getUseType(), -1));
        Assert.assertTrue(verify.getErrorMessage(), verify.isSuccess());
    }

    @Test
    public void testMobileCaptcha() {

        MobileCaptchaGenerateResult mobileCaptchaGenerateResult = mobileCaptcha.generate("login", "18900010002");
        Assert.assertTrue(mobileCaptcha.isEffective(mobileCaptchaGenerateResult.getKey()));
        MobileCaptchaValue value = mobileCaptchaGenerateResult.getValue();
        Captcha.CaptchaVerifyResult verify = mobileCaptcha.verify(mobileCaptchaGenerateResult.getKey(), new MobileCaptchaValue(value.getValue(), value.getUseType(), -1));
        Assert.assertTrue(verify.getErrorMessage(), verify.isSuccess());
    }

    @Test
    public void testQrCodeCaptcha() {
        QrCodeCaptchaGenerateResult qrCodeCaptchaGenerateResult = qrCodeCaptcha.generate(SimpleCaptchaUseType.LOGIN);
        String key = qrCodeCaptchaGenerateResult.getKey();
        Assert.assertTrue(qrCodeCaptcha.isEffective(key));
        QrCodeState qrCodeState = qrCodeCaptcha.getQrCodeState(key);
        Assert.assertEquals(QrCodeState.WAIT, qrCodeState);
        qrCodeCaptcha.updateQrCodeState(key, QrCodeState.SCANNED);
        qrCodeState = qrCodeCaptcha.getQrCodeState(key);
        Assert.assertEquals(QrCodeState.SCANNED, qrCodeState);

        QrCodeCaptchaValue value = qrCodeCaptchaGenerateResult.getValue();
        Captcha.CaptchaVerifyResult verify = qrCodeCaptcha.verify(key, new QrCodeCaptchaValue(value.getValue(), value.getUseType(), -1));
        Assert.assertTrue(verify.getErrorMessage(), verify.isSuccess());
    }
}
