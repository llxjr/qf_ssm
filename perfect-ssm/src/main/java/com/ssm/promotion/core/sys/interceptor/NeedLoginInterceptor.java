package com.ssm.promotion.core.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssm.promotion.core.common.Constants;
import com.ssm.promotion.core.common.annotation.NeedLogin;
import com.ssm.promotion.core.entity.Role;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.service.RoleService;
import com.ssm.promotion.core.service.UserService;

/**
 * @description
 * @author chj
 * @date 创建时间：2018-4-25 上午10:13:57
 * @version 1.0
 * @since
 */
public class NeedLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("servletPath:"+request.getServletPath() + "  requestURI:" +request.getRequestURI()
//					+ "  realPath:" + request.getServletContext().getRealPath("") + "  contextPath:" + request.getContextPath());
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			NeedLogin anno = ((HandlerMethod)handler).getMethodAnnotation(NeedLogin.class);
			if (anno != null && anno.required() == Boolean.TRUE) {
				User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
				if (user != null) {
					User dbUser = userService.getUserByUUID(user.getUuid());
					if (null != dbUser) {
						Role role = roleService.getRoleById(dbUser.getRoleId() != null
								? dbUser.getRoleId() : 0);
						if (null != role && 1 == role.getIsUsed()) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					response.sendRedirect(request.getContextPath() + "/login");
				}
			}
		}
		return true;
	}

}
