package com.jngld.exception;

/**
 * 
 * @Description SpringUtil 自定义异常
 * @author (作者) youps-a
 * @date (开发日期) 2015年11月30日 下午2:53:59
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class SpringUtilException extends RuntimeException {
	
	/**  变量意义 */
    private static final long serialVersionUID = 1916754608618984841L;
    
    public SpringUtilException(){
		super();
	}
	
	public SpringUtilException(String message) {
		super(message);
	}
	
	public SpringUtilException(Throwable cause) {
		super(cause);
	}
	public SpringUtilException(String message, Throwable cause) {
		super(message, cause);
	}

}
