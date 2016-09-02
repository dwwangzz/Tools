package com.jngld.imgutils.exception;

/**
 * 图片处理工具包自定义异常
 * @author wangzz-a
 * @version $Id: UtilException.java, v 0.1 2015年11月26日 上午9:20:51 wangzz-a Exp $
 */
public class ImgUtilsException extends RuntimeException {

	private static final long serialVersionUID = -8899914887761044197L;
	
	public ImgUtilsException(){
		super();
	}
	
	public ImgUtilsException(String message) {
		super(message);
	}
	
	public ImgUtilsException(Throwable cause) {
		super(cause);
	}
	public ImgUtilsException(String message, Throwable cause) {
		super(message, cause);
	}

}
