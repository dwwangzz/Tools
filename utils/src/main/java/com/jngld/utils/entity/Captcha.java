package com.jngld.utils.entity;

import java.io.Serializable;

/**
 * 
 * @Description 验证码实体封装
 * @author (作者) youps-a
 * @date (开发日期) 2015年12月4日 下午3:43:39
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class Captcha implements Serializable {

    /** 变量意义 */
    private static final long serialVersionUID = -3665652771828062312L;

    // 验证码对应key，用于请求校验验证码使用
    private String captchaKey;

    // 验证码图片 base64字符串
    private String captchaImage;

    // 验证码字符串
    private String captchaString;

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

    public String getCaptchaImage() {
        return captchaImage;
    }

    public void setCaptchaImage(String captchaImage) {
        this.captchaImage = captchaImage;
    }

    public String getCaptchaString() {
        return captchaString;
    }

    public void setCaptchaString(String captchaString) {
        this.captchaString = captchaString;
    }

}
