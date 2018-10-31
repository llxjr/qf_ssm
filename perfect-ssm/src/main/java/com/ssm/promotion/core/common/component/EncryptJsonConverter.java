package com.ssm.promotion.core.common.component;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

/** 
 * @description 
 * @author  chj 
 * @date 创建时间：2018-6-4 上午9:48:03 
 * @version 1.0 
 * @since  
 */
public class EncryptJsonConverter extends AbstractHttpMessageConverter<Object>{
	
	public EncryptJsonConverter() {
		super(new MediaType("application", "xx-wisely", Charset.forName("UTF-8")));
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return Object.class.isAssignableFrom(clazz);
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		String temp = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
		
		return null;
	}

	@Override
	protected void writeInternal(Object target, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		String outStr = target.toString();
		outputMessage.getBody().write(outStr.getBytes());
	}

}
