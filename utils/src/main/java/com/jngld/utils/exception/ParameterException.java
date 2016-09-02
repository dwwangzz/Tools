package com.jngld.utils.exception;

public class ParameterException extends RuntimeException  {

	private static final long serialVersionUID = 1L;
	
	public static final String message = "该方法的参数不能为null，且长度不能为0";
	
	public ParameterException() {
		super();
	}
	
	public ParameterException(String message) {
		super(message);
	}
	
	public ParameterException(Throwable cause) {
		super(cause);
	}
	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}

}
