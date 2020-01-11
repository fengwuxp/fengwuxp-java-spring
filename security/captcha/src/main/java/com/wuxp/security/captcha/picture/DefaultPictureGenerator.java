package com.wuxp.security.captcha.picture;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wuxp.security.captcha.random.SecureRandomUtils.randomNextInt;

@Slf4j
public class DefaultPictureGenerator implements PictureCaptchaGenerator {


    //常用颜色
    protected static final int[][] COLOR = {
            {0, 135, 255},
            {51, 153, 51},
            {255, 102, 102},
            {255, 153, 0},
            {153, 102, 0},
            {153, 102, 153},
            {51, 153, 153},
            {102, 102, 255},
            {0, 102, 204},
            {204, 51, 51},
            {0, 153, 204},
            {0, 51, 102}
    };

    protected static Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 32);

    protected static Font DEFAULT_CHINESE_FONT = new Font("楷体", Font.PLAIN, 28);

    @Override
    public Image generate(String content, int width, int height, boolean useGif) {
        char[] chars = content.toCharArray();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) bi.getGraphics();
        // 填充背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 画干扰圆
        drawOval(8, g2d, width, height);
        // 画干扰线
        g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        drawBesselLine(1, g2d, width, height);
        // 画字符串
        g2d.setFont(captchaTextFont(content));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        // 每一个字符所占的宽度
        int fW = width / chars.length;
        // 字符的左右边距
        int fSp = (fW - (int) fontMetrics.getStringBounds("W", g2d).getWidth()) / 2;
        for (int i = 0; i < chars.length; i++) {
            g2d.setColor(captchaTextColor());
            // 文字的纵坐标
            int fY = height - ((height - (int) fontMetrics.getStringBounds(String.valueOf(chars[i]), g2d).getHeight()) >> 1);
            g2d.drawString(String.valueOf(chars[i]), i * fW + fSp + 3, fY - 3);
        }
        g2d.dispose();
        return bi;
    }


    @Override
    public Color backgroundColor() {
        int[] color = COLOR[randomNextInt(COLOR.length)];
        return new Color(color[0], color[1], color[2]);
    }

    @Override
    public Color captchaTextColor() {
        return backgroundColor();
    }

    @Override
    public Color interferenceLineColor() {
        return backgroundColor();
    }

    @Override
    public Color bezierCurveColor() {
        return backgroundColor();
    }

    @Override
    public Font captchaTextFont(String text) {
        if (this.isChinese(text)) {
            return DEFAULT_CHINESE_FONT;
        }
        return DEFAULT_FONT;
    }

    /**
     * 随机画干扰圆
     *
     * @param num    数量
     * @param g      Graphics2D
     * @param width  图片宽度
     * @param height 图片高度
     */
    private void drawOval(int num, Graphics2D g, int width, int height) {
        for (int i = 0; i < num; i++) {
            g.setColor(interferenceLineColor());
            int w = 5 + randomNextInt(num);
            g.drawOval(
                    randomNextInt(width - 25),
                    randomNextInt(height - 15),
                    w,
                    w);
        }
    }

    /**
     * 随机画贝塞尔曲线
     *
     * @param num    数量
     * @param g      Graphics2D
     * @param width
     * @param height
     */
    private void drawBesselLine(int num, Graphics2D g, int width, int height) {
        for (int i = 0; i < num; i++) {
            g.setColor(bezierCurveColor());
            int x1 = 5, y1 = randomNextInt(5, height / 2);
            int x2 = width - 5, y2 = randomNextInt(height / 2, height - 5);
            int ctrlx = randomNextInt(width / 4, width / 4 * 3), ctrly = randomNextInt(5, height - 5);
            if (randomNextInt(2) == 0) {
                int ty = y1;
                y1 = y2;
                y2 = ty;
            }
            if (randomNextInt(2) == 0) {
                // 二阶贝塞尔曲线
                QuadCurve2D shape = new QuadCurve2D.Double();
                shape.setCurve(x1, y1, ctrlx, ctrly, x2, y2);
                g.draw(shape);
            } else {  // 三阶贝塞尔曲线
                int ctrlx1 = randomNextInt(width / 4, width / 4 * 3), ctrly1 = randomNextInt(5, height - 5);
                CubicCurve2D shape = new CubicCurve2D.Double(x1, y1, ctrlx, ctrly, ctrlx1, ctrly1, x2, y2);
                g.draw(shape);
            }
        }
    }


    /**
     * 是否为中文
     *
     * @param text
     * @return
     */
    private boolean isChinese(String text) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = pattern.matcher(text);
        return m.find();
    }

}

