package test.com.wuxp.security.example;

import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.wuxp.security.captcha.qrcode.DefaultQrCodeCaptchaGenerator;
import org.apache.commons.lang3.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ComposeImageTest {

    /**
     * 图片合成
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        DefaultQrCodeCaptchaGenerator defaultQrCodeCaptchaGenerator = new DefaultQrCodeCaptchaGenerator();


        Image image = defaultQrCodeCaptchaGenerator.generate(
                RandomStringUtils.randomAlphabetic(32),
                200,
                1,
                "D://logo.png",
                40,
                1,
                "#ff0000",
                new MatrixToImageConfig());
        OutputStream fileOutputStream = new FileOutputStream("D://4.png");
        ImageIO.write((BufferedImage) image, "png", fileOutputStream);
        fileOutputStream.close();
//        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);//创建图片
//        BufferedImage bg = ImageIO.read(new File("D://2.png"));
//        BufferedImage logoImage = ImageIO.read(new File("D://logo.png"));
//        Graphics2D g = image.createGraphics();
//        // logo起始位置，此目的是为logo居中显示
//        int width = 200;
//        int logoWidth = 40;
//        int x = (width - logoWidth) / 2;
//        int y = (width - logoWidth) / 2;
//
//
//        g.drawImage(bg, 0, 0, null);
//        g.drawImage(logoImage.getScaledInstance(logoWidth,logoWidth,Image.SCALE_DEFAULT), x, y, logoWidth, logoWidth, null);
//        g.drawRoundRect(x, y, logoWidth, logoWidth, 10, 10);
//        // 给logo画边框 构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
//        g.setStroke(new BasicStroke(1));
////        g.setColor(new Color(255, 255, 255));
//        g.drawRect(x, y, width, width);
//        g.dispose();
//        OutputStream outImage = new FileOutputStream("D://3.png");
//
//        ImageIO.write(image, "png", outImage);
//
//        outImage.close();


    }


}
