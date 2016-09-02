package com.jngld.utils.exception;

/**
 * 
 * @Description 反射方法异常
 * @author (作者) youps-a
 * @date (开发日期) 2015年11月30日 下午2:53:59
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class ReflectException extends Exception {
	
	/**  变量意义 */
    private static final long serialVersionUID = 1916754608618984841L;
    
    public static final String message = "反射方法属性异常";
    
    public ReflectException(){
		super();
	}
	
	public ReflectException(String message) {
		super(message);
	}
	
	public ReflectException(Throwable cause) {
		super(cause);
	}
	public ReflectException(String message, Throwable cause) {
		super(message, cause);
	}

}
