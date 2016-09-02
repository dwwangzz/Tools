package com.jngld.cache.util.exception;

/**
 * CacheUtil自定义异常类
 * @author wangzz-a
 * @version $Id: CacheUtilException.java, v 0.1 2015年12月8日 下午3:27:13 wangzz-a Exp $
 */
public class CacheUtilException extends Exception {

	private static final long serialVersionUID = 1L;

	public CacheUtilException() {
		super();
	}

	public CacheUtilException(String message) {
		super(message);
	}

	public CacheUtilException(Throwable cause) {
		super(cause);
	}

	public CacheUtilException(String message, Throwable cause) {
		super(message, cause);
	}

}
