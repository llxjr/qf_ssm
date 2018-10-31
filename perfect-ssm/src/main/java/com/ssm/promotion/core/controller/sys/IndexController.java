package com.ssm.promotion.core.controller.sys;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.promotion.core.common.Constants;
import com.ssm.promotion.core.common.annotation.NeedLogin;
import com.ssm.promotion.core.dto.MenuDTO;
import com.ssm.promotion.core.entity.Role;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.service.MenuService;
import com.ssm.promotion.core.service.RoleService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("")
	public String home(HttpServletRequest request
			,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		if (user != null) {
			return "redirect:/main";
		}
		return "login";
	}
	
	@RequestMapping("login")
	public String index() {
		return "login";
	}
	
	@RequestMapping("main")
	@NeedLogin
	public String main(HttpServletRequest request,HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		if (user == null) {
			return "login";
		} 
		Role role = roleService.getRoleById(user.getRoleId());
		if (role != null && 1 == role.getIsUsed()) {
			//构建左部导航栏
			List<MenuDTO> menus = menuService.findRoleMenus(role.getId());
			request.setAttribute("menus", menus);
		}
		request.setAttribute("userName", user.getUserName());
		request.setAttribute("roleName", user.getRoleName());
		return "home/main";
	}
}
