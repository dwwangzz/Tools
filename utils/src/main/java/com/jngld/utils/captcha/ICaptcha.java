package com.jngld.utils.captcha;

import java.io.IOException;

import com.jngld.utils.entity.Captcha;

public interface ICaptcha {
    
    /**
     * 
     * @Description 获取验证码  默认4位
     * @author youps-a
     * @date 2015年12月4日 下午3:50:24
     * @return          Captcha 包含 base64的图片，和验证码key，key用于验证用户输入验证码是否正确
     * @throws IOException
     */
    public Captcha getCaptcha() throws IOException;
    
    /**
     * 
     * @Description 获取验证码  指定位数
     * @author youps-a
     * @date 2015年12月4日 下午3:48:55
     * @param codeSize  验证码位数
     * @return          Captcha 包含base64的图片，和验证码key，key用于验证用户输入验证码是否正确
     * @throws IOException
     */
    public Captcha getCaptcha(int codeSize) throws IOException;
    
    /**
     * 
     * @Description 检验验证码方法,自动忽略大小写
     * @author youps-a
     * @date 2015年12月4日 下午3:47:56
     * @param captchaKey    验证码对应的key
     * @param captcha       验证码字符串
     * @return              验证成功返回true ，失败返回false
     */
    public boolean chechCaptcha(String captchaKey,String captcha);
    
}
