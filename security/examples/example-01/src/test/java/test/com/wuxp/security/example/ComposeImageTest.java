package test.com.wuxp.security.example;

import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.oak.codegen.ServiceModelUtil;
import com.wuxp.security.captcha.qrcode.DefaultQrCodeCaptchaGenerator;
import com.wuxp.security.example.entities.ExampleEntity;
import org.apache.commons.lang3.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class ComposeImageTest {

    /**
     * 图片合成
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String basePackageName = "test.com.wuxp.security.example.codebuild";
        ServiceModelUtil.entity2ServiceModel(ExampleEntity.class,new HashMap<>(),basePackageName,"d:\\temp");
    }


}
