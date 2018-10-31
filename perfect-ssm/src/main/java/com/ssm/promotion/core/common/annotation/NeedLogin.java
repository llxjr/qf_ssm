package com.ssm.promotion.core.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @description 
 * @author  chj 
 * @date 创建时间：2018-4-25 上午10:06:24 
 * @version 1.0 
 * @since  
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {
	
	boolean required() default true;
}
