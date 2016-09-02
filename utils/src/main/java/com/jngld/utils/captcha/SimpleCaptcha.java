package com.jngld.utils.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * 
 * @Description 较易识别验证码生成方法
 * @author (作者) youps-a
 * @date (开发日期) 2015年12月7日 上午11:08:55
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class SimpleCaptcha extends AbstractCaptcha{
    
    private static final String CAPTCHA_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    @Override
    protected String getSources() {
        return CAPTCHA_CODES;
    }

    @Override
    protected void setGraphics2D(BufferedImage image, String code, int width,
            int height) {
        int lineCount = 20;                     //干扰线数量
        int codeCount = code.length();          //验证码字符串数量
        int codeX = width / (codeCount +1);     //每个字符的宽度   
        int fontSize = height*72/96;            //字高
        int codeY = height/2 + fontSize/2 - 5;  //验证码Y轴距
        
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for(int i = 0; i < colors.length; i++){
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);
        
        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, width, height);
        
        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, width, height-4);
        
        //绘制干扰线
        g2.setColor(getRandColor(160, 255));// 设置线条的颜色
        for (int i = 0; i < lineCount; i++) {
            int x = rand.nextInt(width - 1);
            int y = rand.nextInt(height - 1);
            int xl = rand.nextInt(6) + 1;
            int yl = rand.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }
        
        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
        
        // 使图片扭曲
        shear(g2, width, height, c);

        g2.setColor(getRandColor(160));
        // Arial serif Verdana Dialog
        Font font = new Font("serif", Font.PLAIN, fontSize);
        
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for(int i = 0; i < codeCount; i++){
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (width / codeCount) * i + fontSize/2, height/2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, (i) * codeX + 20, codeY);
        }
        
        g2.dispose();
    }

}
