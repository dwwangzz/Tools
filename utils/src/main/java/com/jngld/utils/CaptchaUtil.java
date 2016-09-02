package com.jngld.utils;

import com.jngld.utils.captcha.HardCaptcha;
import com.jngld.utils.captcha.ICaptcha;
import com.jngld.utils.captcha.SimpleCaptcha;
import com.jngld.utils.captcha.SimpleChineseCaptcha;

/**
 * 
 * @Description 验证码工具类
 * @author (作者) youps-a
 * @date (开发日期) 2015年12月7日 下午2:30:42
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class CaptchaUtil {
    
    /** 困难识别验证码*/
    public final static ICaptcha hard = new HardCaptcha();
    
    /** 简单识别验证码*/
    public final static ICaptcha simple = new SimpleCaptcha();
    
    /** 简单识别验证码-常用中文*/
    public final static ICaptcha simpleChinese = new SimpleChineseCaptcha();

    public static void main(String[] args) {

        String key = "123qwe!@#";
        String string = "asdfghj";
        String password;
        try {
            password = EncryptUtil.aesStringEncode(string, key);
            System.out.println(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
