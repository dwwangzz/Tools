package com.jngld.utils.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * 
 * @Description 较难识别验证码生成方法
 * @author (作者) youps-a
 * @date (开发日期) 2015年12月7日 上午11:07:19
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class HardCaptcha extends AbstractCaptcha{
    
    private static final String CAPTCHA_CODES = "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    @Override
    protected String getSources() {
        return CAPTCHA_CODES;
    }

    @Override
    protected void setGraphics2D(BufferedImage image, String code, int width,
            int height) {
        
        int lineCount = 130;                    //干扰线数量
        int codeCount = code.length();          //验证码字符串数量
        int x = width / (codeCount +1);         //每个字符的宽度   
        int fontSize = height*72/96;            //字高
        int codeY = height/2 + fontSize/2 - 5;  //验证码Y轴距
        
        Graphics2D g = image.createGraphics();  
        // 将图像填充为白色  
        g.setColor(Color.WHITE);  
        g.fillRect(1, 1, width-2, height-2);  
        // 创建字体  
        g.setFont(new Font("Arial",Font.PLAIN,fontSize));
        
        for (int i = 0; i < lineCount; i++) {  
            int xs = rand.nextInt(width);  
            int ys = rand.nextInt(height);  
            int xe = xs+rand.nextInt(width/6);  
            int ye = ys+rand.nextInt(height/6);  
            g.setColor(getRandColor(255));  
            g.drawLine(xs, ys, xe, ye);  
        }
      
        // 随机产生codeCount个字符的验证码
        char[] codes = code.toCharArray(); 
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codes[i]);  
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。  
            g.setColor(getRandColor(160));
            // 设置旋转
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (width / codeCount) * i + fontSize/4, height/4);
            g.setTransform(affine);
            g.drawString(strRand, (i) * x + 20, codeY);
        }  
        g.dispose();
    }

}
