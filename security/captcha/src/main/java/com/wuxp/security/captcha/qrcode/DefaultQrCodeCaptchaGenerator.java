package com.wuxp.security.captcha.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class DefaultQrCodeCaptchaGenerator implements QrCodeCaptchaGenerator {

    @Override
    public Image generate(String content,
                          int width,
                          int margin,
                          String logo,
                          int logoWidth,
                          int logoBorderWith,
                          String logoBorderColor,
                          MatrixToImageConfig config) {

        BitMatrix qrCodeMatrix = createQrCodeMatrix(content, width, margin);
        if (qrCodeMatrix == null) {
            return null;
        }
        if (logo == null) {
            return MatrixToImageWriter.toBufferedImage(qrCodeMatrix, config);
        }
        try {
            return insertLogo(MatrixToImageWriter.toBufferedImage(qrCodeMatrix, config),
                    width,
                    logo,
                    logoWidth,
                    logoBorderWith,
                    logoBorderColor);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("给二维码添加logo失败", e);
            return null;
        }
    }


    /**
     * 根据内容生成二维码数据
     *
     * @param content 二维码文字内容[为了信息安全性，一般都要先进行数据加密]
     * @param length  二维码图片宽度和高度
     * @param margin  边距
     */
    private BitMatrix createQrCodeMatrix(String content, int length, int margin) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 设置字符编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, margin);
        try {
            return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, length, length, hints);
        } catch (Exception e) {
            log.warn("内容为：【{}】的二维码生成失败！", content, e);
            return null;
        }
    }

    /**
     * 将logo添加到二维码中间
     *
     * @param qrCodeImage 生成的二维码图片对象
     * @param logo        logo文件对象
     * @param logo        logo 图片路径
     * @param logoWidth   logo 图片的绘制大小
     */
    private BufferedImage insertLogo(BufferedImage qrCodeImage,
                                     int width,
                                     String logo,
                                     int logoWidth,
                                     int logoBorderWith,
                                     String logoBorderColor) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);

        boolean isRemote = logo.startsWith("http://") || logo.startsWith("https://");
        BufferedImage logoImage = isRemote ? ImageIO.read(new URL(logo)) : ImageIO.read(new File(logo));
        Graphics2D qrCodeImageGraphics = bufferedImage.createGraphics();

        // logo起始位置，此目的是为logo居中显示
        int x = (width - logoWidth) / 2;
        int y = (width - logoWidth) / 2;
        // 绘制图
        qrCodeImageGraphics.drawImage(qrCodeImage, 0, 0, width, width, null);
//        qrCodeImageGraphics.drawImage(logoImage.getScaledInstance(logoWidth, logoWidth, Image.SCALE_DEFAULT), x, y, logoWidth, logoWidth, null);
        qrCodeImageGraphics.drawImage(logoImage, x, y, logoWidth, logoWidth, null);
        qrCodeImageGraphics.drawRoundRect(x, y, logoWidth, logoWidth, logoWidth / 10, logoWidth / 10);
        // 给logo画边框 构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
        qrCodeImageGraphics.setStroke(new BasicStroke(logoBorderWith));
        qrCodeImageGraphics.setColor(new Color(Integer.parseInt(logoBorderColor.replaceFirst("#", ""), 16)));
        qrCodeImageGraphics.drawRect(x, y, logoWidth, logoWidth);
        qrCodeImageGraphics.dispose();
        return bufferedImage;

    }

}
