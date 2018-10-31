package com.ssm.promotion.core.excption;

/** 
 * @description 未授權
 * @author  chj 
 * @date 创建时间：2018-4-9 下午2:47:35 
 * @version 1.0 
 * @since  
 */
public class UnauthorizedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	private boolean propertiesKey = true;
	
	public UnauthorizedException() {
		super();
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}
	
	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnauthorizedException(String message, String errorCode, boolean propertiesKey) {
		super(message);
		this.setErrorCode(errorCode);
		this.setPropertiesKey(propertiesKey);
	}
	
	public UnauthorizedException(String message, Throwable cause, String errorCode, boolean propertiesKey) {
		super(message, cause);
		this.setErrorCode(errorCode);
		this.setPropertiesKey(propertiesKey);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isPropertiesKey() {
		return propertiesKey;
	}

	public void setPropertiesKey(boolean propertiesKey) {
		this.propertiesKey = propertiesKey;
	}

}
