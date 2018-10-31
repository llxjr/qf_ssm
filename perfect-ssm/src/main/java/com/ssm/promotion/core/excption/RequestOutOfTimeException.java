package com.ssm.promotion.core.excption;

/** 
 * @description 
 * @author  chj 
 * @date 创建时间：2018-6-4 上午10:18:10 
 * @version 1.0 
 * @since  
 */
public class RequestOutOfTimeException extends RuntimeException{
	
	public RequestOutOfTimeException() {
		super("密码输入错误超过次数");
	}
}
