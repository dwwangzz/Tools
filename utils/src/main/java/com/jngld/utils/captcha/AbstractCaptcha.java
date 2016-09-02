package com.jngld.utils.captcha;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jngld.utils.Base64Util;
import com.jngld.utils.EmptyUtil;
import com.jngld.utils.EncryptUtil;
import com.jngld.utils.entity.Captcha;
import com.jngld.utils.exception.ParameterException;

/**
 * 
 * @Description 验证码抽象类
 * @author (作者) youps-a
 * @date (开发日期) 2015年12月7日 上午11:00:53
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public abstract class AbstractCaptcha implements ICaptcha{
    
    /** 随机数对象*/
    protected static Random rand = new Random();

    @Override
    public Captcha getCaptcha() throws IOException {
        return getCaptcha(4);
    }

    @Override
    public Captcha getCaptcha(int codeSize) throws IOException {
        String captchaCode = getCaptchaCode(codeSize, getSources());
        return getCaptcha(captchaCode);
    }

    @Override
    public boolean chechCaptcha(String captchaKey, String captcha) {
        if (EmptyUtil.isEmpty(captchaKey) || EmptyUtil.isEmpty(captcha)) {
            throw new ParameterException(ParameterException.message);
        }
        captcha = captcha.toUpperCase();
        return captchaKey.equals(EncryptUtil.string2MD5(captcha));
    }
    
    /**
     * 
     * @Description 获取验证码字符串数据源
     * @author youps-a
     * @date 2015年12月7日 上午11:03:19
     * @return
     */
    protected abstract String getSources();
    
    /**
     * 
     * @Description 验证码Graphics2D 样式设置
     * @author youps-a
     * @date 2015年12月7日 上午11:03:43
     * @param image         BufferedImage 图像缓冲
     * @param code          验证码字符串
     * @param width         验证码图片宽度
     * @param height        验证码图片高度
     */
    protected abstract void setGraphics2D(BufferedImage image, String code, int width, int height);

    /**
     * 
     * @Description 获取验证码方法
     * @author youps-a
     * @date 2015年12月7日 上午11:01:30
     * @param captchaCode       验证码字符串
     * @return                  Captcha实体，封装base64图片字符串及校验所需key
     * @throws IOException
     */
    protected Captcha getCaptcha(String captchaCode) throws IOException{
        Captcha captcha = new Captcha();
        // 图片的宽度
        int width = 160;
        // 图片的高度
        int height = 40;
        
        // 图像buffer  
        BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB); 
        
        //Graphics2D效果设置
        setGraphics2D(buffImg, captchaCode, width, height);
        
        // 创建字符流
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        
        // 写入字符流
        ImageIO.setUseCache(false);
        ImageIO.write(buffImg, "jpg", bs);
        
        // 转码成base64字符串,封装返回实体
        captcha.setCaptchaImage(Base64Util.encodeImgBase64(bs.toByteArray(),"jpg"));
        captcha.setCaptchaKey(getCaptchaKeyByCode(captchaCode));
        return captcha;
    }
    
    /**
     * 
     * @Description 获取验证码
     * @author youps-a
     * @date 2015年12月1日 下午1:48:38
     * @param codeSize      验证码长度        
     * @param sources       验证码字符源
     * @return
     */
    private String getCaptchaCode(int codeSize,String sources){
        if (0 >= codeSize) {
            throw new ParameterException("验证码长度必须大于0");
        }
        int codesLen = sources.length();
        StringBuilder captchaCode = new StringBuilder(codeSize);
        for(int i = 0; i < codeSize; i++){
            captchaCode.append(sources.charAt(rand.nextInt(codesLen-1)));
        }
        return captchaCode.toString();
    }
    
    /**
     * 
     * @Description 通过code获取用于校验的加密key
     * @author youps-a
     * @date 2015年12月7日 上午9:17:41
     * @param captchaCode
     * @return
     */
    private String getCaptchaKeyByCode(String captchaCode){
        captchaCode = captchaCode.toUpperCase();
        return EncryptUtil.string2MD5(captchaCode);
    }
    
    /**
     * 
     * @Description 获取随机rgb色color
     * @author youps-a
     * @date 2015年12月7日 上午10:12:40
     * @param size      最大rgb色值  0~255
     * @return          随机色Color 对象
     */
    protected Color getRandColor(int size){
        if (0 > size || 255 < size) {
            throw new ParameterException("参数错误，随机色rgb参数值应为 0~255");
        }
        return getRandColor(0,size);
    }
    
    /**
     * 
     * @Description 获取随机rgb色color
     * @author youps-a
     * @date 2015年12月7日 上午10:11:20
     * @param start     起始rgb值   0~255
     * @param end       截止rgb值   0~255 
     * @return          随机色Color 对象
     */
    protected Color getRandColor(int start,int end){
        if (0 > start || 255 < start || 0 > end || 255 < end) {
            throw new ParameterException("参数错误，随机色rgb参数值应为 0~255");
        }
        if (end < start) {
            throw new ParameterException("参数错误，截止值应大于起始值");
        }
        int r = start + rand.nextInt(end - start);
        int g = start + rand.nextInt(end - start);
        int b = start + rand.nextInt(end - start);
        return new Color(r, g, b);
    }
    
    /**
     * 
     * @Description 获取随机rgb色值
     * @author youps-a
     * @date 2015年12月7日 上午9:41:39
     * @return      rgb色值
     */
    protected int getRandomIntColor() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = rand.nextInt(255);
        }
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }
    
    /**
     * 
     * @Description 扭曲图片
     * @author youps-a
     * @date 2015年12月7日 上午10:20:19
     * @param g         Graphics对象
     * @param w1        高度
     * @param h1        宽度
     * @param color     色值
     */
    protected void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }
    
    /**
     * 
     * @Description X轴扭曲
     * @author youps-a
     * @date 2015年12月7日 上午10:59:12
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shearX(Graphics g, int w1, int h1, Color color) {
        int period = rand.nextInt(2);
        int frames = 1;
        int phase = rand.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            g.setColor(color);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + w1, i, w1, i);
        }
    }

    /**
     * 
     * @Description Y轴扭曲
     * @author youps-a
     * @date 2015年12月7日 上午11:00:07
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shearY(Graphics g, int w1, int h1, Color color) {
        int period = rand.nextInt(40) + 10; // 50;
        int frames = 20;
        int phase = 7;
        
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            g.setColor(color);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + h1, i, h1);
        }
    }
    
}
