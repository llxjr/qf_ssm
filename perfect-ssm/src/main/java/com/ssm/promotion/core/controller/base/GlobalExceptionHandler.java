package com.ssm.promotion.core.controller.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.excption.UnauthorizedException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public Result handlerUnauthorizedRequest() {
		return ResultGenerator.genFailResult("帐号或密码错误！");
	}
}
