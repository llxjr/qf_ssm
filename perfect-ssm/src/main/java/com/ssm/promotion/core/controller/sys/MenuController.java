package com.ssm.promotion.core.controller.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.promotion.core.common.Constants;
import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.dto.MenuDTO;
import com.ssm.promotion.core.dto.MenuManageDTO;
import com.ssm.promotion.core.entity.Menu;
import com.ssm.promotion.core.entity.PageBean;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.service.MenuService;
import com.ssm.promotion.core.util.DataGrid;
import com.ssm.promotion.core.util.SQLUtils;

/**
 * @description 菜单管理
 * @author liu66
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController {

	private static final Logger log = Logger.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	/**
	 * 跳转菜单页面
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String index() {
		return "menu/menuManage";
	}

	/**
	 * 数据表格
	 * 
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public DataGrid datagrid(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			@RequestParam(value = "sort", required = false) String sortName,
			@RequestParam(value = "order", required = false) String order,
			Menu searchMenu) {
		Map searchMap = SQLUtils.bulidConditionMap(searchMenu.getTitle(), sortName,
				order, new PageBean(page, rows));
		List<MenuManageDTO> list = null;
		Long total = null;
		try {
			list = menuService.datagrid(searchMap);
			total = menuService.getTotalMenu(searchMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return new DataGrid(total, list);
	}
	
	/**
	 * 获取父菜单
	 * @return
	 */
	@RequestMapping("/findParentMenus")
	@ResponseBody
	public Result findParentMenus() {
		List<MenuDTO> dtos = null;
		try {
			dtos = menuService.findParentMenus();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return ResultGenerator.genFailResult("获取父菜单失败,服务器异常");
		}
		return ResultGenerator.genSuccessResult(dtos);
	}

	/**
	 * 增添菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "add", method = { RequestMethod.POST })
	@ResponseBody
	public Result addMenu(@RequestBody Menu menu, HttpServletRequest request,
			HttpServletResponse response) {
		User currentUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER);
		try {
			if (currentUser != null) {
				menu.setCreateBy(currentUser.getUserName());
			}
			menuService.insert(menu);
		} catch (Exception e) {
			log.error("add: " + e);
			e.printStackTrace();
			return ResultGenerator.genFailResult("添加失败,服务器异常!");
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 编辑菜单
	 * 
	 * @param menu
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "edit", method = { RequestMethod.POST })
	@ResponseBody
	public Result editMenu(@RequestBody Menu menu, HttpServletRequest request,
			HttpServletResponse response) {
		User currentUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER);
		try {
			if (null != currentUser) {
				menu.setUpdateBy(currentUser.getUserName());
			}
			menuService.update(menu);
		} catch (Exception e) {
			log.error("update: " + e);
			e.printStackTrace();
			return ResultGenerator.genFailResult("编辑失败,服务器异常!");
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 删除菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result delMenu(@PathVariable Integer id) {
		try {
			if (id != null) {
				menuService.delete(id);
			} else {
				return ResultGenerator.genFailResult("数据主键为空");
			}
		} catch (Exception e) {
			log.error("delete: " + e);
			e.printStackTrace();
			return ResultGenerator.genFailResult("删除失败,服务器异常!");
		}
		return ResultGenerator.genSuccessResult();
	}
}
