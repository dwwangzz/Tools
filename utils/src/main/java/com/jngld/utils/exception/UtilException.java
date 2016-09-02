package com.jngld.utils.exception;

/**
 * 工具包自定义异常
 * @author wangzz-a
 * @version $Id: UtilException.java, v 0.1 2015年11月26日 上午9:20:51 wangzz-a Exp $
 */
public class UtilException extends Exception {
	
	private static final long serialVersionUID = 7357244769859699567L;
	
	public UtilException(){
		super();
	}
	
	public UtilException(String message) {
		super(message);
	}
	
	public UtilException(Throwable cause) {
		super(cause);
	}
	public UtilException(String message, Throwable cause) {
		super(message, cause);
	}

}
