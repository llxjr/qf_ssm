package com.ssm.promotion.core.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.promotion.core.common.Constants;
import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.service.UserService;
import com.ssm.promotion.core.util.MD5Util;

/**
 * @description
 * @author liu66
 */
@Controller
@RequestMapping("/security/user")
public class UserSecurityController {

	private static final Logger log = Logger
			.getLogger(UserSecurityController.class);
	@Autowired
	private UserService userService;

	@RequestMapping("/resetPwdPage")
	public String rpPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		if (null != user) {
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("oldPwd", user.getPassword());
			request.setAttribute("userId", user.getUuid());
		}
		return "user/resetPwd";
	}

	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST, headers = { "oper=PassWordModify" })
	@ResponseBody
	public Result modifyPassword(@RequestParam("userId") String userId,
			@RequestParam("oldPwd") String oldPassword,
			@RequestParam("newPwd") String newPassword,
			HttpServletRequest request) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(oldPassword)
				|| StringUtils.isBlank(newPassword)) {
			return ResultGenerator.genFailResult("数据缺失！请校对后重试！");
		}
		User user = userService.getUserByUUID(userId);
		if (null != user && Constants.USER_STATUS_NORMAL == user.getStatus()) {
			final String oldPwd = user.getPassword();
			if (oldPassword.equals(oldPwd)) {
				try {
					synchronized (user) {
						user.setPassword(MD5Util
								.MD5Encode(newPassword, "utf-8"));
						int result = userService.updateUser(user);
						if (1 == result) {
							request.getSession().removeAttribute(
									Constants.SESSION_USER);
							return ResultGenerator.genSuccessResult();
						} else {
							return ResultGenerator.genFailResult("更新失败，未知错误！");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
					ResultGenerator.genFailResult("更新失败，未知错误！");
				}
			} else {
				return ResultGenerator.genFailResult("旧密码输入错误，请重新输入！");
			}
		} else {
			return ResultGenerator.genFailResult("用户不存在！");
		}
		return null;
	}
}
