package com.smartframe.basics.exception;

/**
 * <p><b>Title:</b><i>业务异常类</i></p>
 * <p>Desc: </p>
 * <p>source folder:{@docRoot}</p>
 * @version Version 0.1
 *
 */
public class BusinessException extends RuntimeException {


	private static final long serialVersionUID = -8912507817799695689L;

	public BusinessException() {
		super();
	}

	/**
	 * 构造方法。
	 * @param message 异常信息
	 */
	public BusinessException(String message) {
		super(message);
	}
	
	/**
	 * 构造方法。
	 * @param cause 异常原因
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造方法。
	 * @param message 异常信息
	 * @param cause  异常原因
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
	public BusinessException(int code) {
	}
	
	public BusinessException(int code,String message) {
		super(message);
	}
	
	public BusinessException(Exception e) {
		super(e.getMessage(), e);
	}
	
}
