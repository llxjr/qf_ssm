package com.ssm.promotion.core.controller.sys;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.controller.base.BasicController;
import com.ssm.promotion.core.dto.RoleAuthParentMenuDTO;
import com.ssm.promotion.core.dto.RoleManageDTO;
import com.ssm.promotion.core.entity.PageBean;
import com.ssm.promotion.core.entity.Role;
import com.ssm.promotion.core.service.RoleAuthService;
import com.ssm.promotion.core.service.RoleService;
import com.ssm.promotion.core.util.DataGrid;
import com.ssm.promotion.core.util.SQLUtils;

/**
 * @description 权限管理
 * @author liu66
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BasicController {

	private static final Logger log = Logger.getLogger(RoleController.class);

	private static final String OPER_STARTUP = "0x01";
	private static final String OPER_FORBID = "0x00";

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleAuthService roleAuthService;
	
	@RequestMapping("/list")
	public String index() {
		return "role/roleManage";
	}

	/**
	 * datagrid
	 * 
	 * @param page
	 * @param rows
	 * @param sortName
	 * @param order
	 * @param searchRole
	 * @return
	 */
	@RequestMapping("datagrid")
	@ResponseBody
	public DataGrid dataGrid(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			@RequestParam(value = "sort", required = false) String sortName,
			@RequestParam(value = "order", required = false) String order,
			Role searchRole) {
		Map searchMap = SQLUtils.bulidConditionMap(searchRole.getRoleName(),
				sortName, order, new PageBean(page, rows));
		List<RoleManageDTO> list = null;
		Long total = null;
		try {
			list = roleService.datagrid(searchMap);
			total = roleService.getTotalRole(searchMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return new DataGrid(total, list);
	}

	/**
	 * 添加
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result insertRole(@RequestBody Role role) {
		try {
			int result = roleService.insert(role);
			if (result == 1) {
				return ResultGenerator.genSuccessResult();
			}
		} catch (Exception e) {
			log.error("权限添加错误:" + e);
			return ResultGenerator.genFailResult("系统异常，添加失败！");
		}
		return ResultGenerator.genFailResult("插入失败，未知异常！");
	}

	/**
	 * 更新
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result updateRole(@RequestBody Role role) {
		try {
			int result = roleService.update(role);
			if (result == 1) {
				return ResultGenerator.genSuccessResult();
			}
		} catch (Exception e) {
			log.error("权限更新错误：" + e);
			return ResultGenerator.genFailResult("系统异常，更新失败！");
		}
		return ResultGenerator.genFailResult("更新失败，未知异常！");
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result delRole(@PathVariable Integer id) {
		try {
			int result = roleService.delete(id);
			if (result == 1) {
				return ResultGenerator.genSuccessResult();
			}
		} catch (Exception e) {
			log.error("权限插入错误：" + e);
			return ResultGenerator.genFailResult("系统异常, 更新失败！");
		}
		return ResultGenerator.genFailResult("删除失败, 未知异常！");
	}

	/**
	 * 超级管理员 更改角色权限
	 * 
	 * @param roleId
	 * @param operType
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/chStatus/{id}/{oper}", method = RequestMethod.GET, headers = { "oper=RoleOper" })
	@ResponseBody
	public Result chRoleStatus(@PathVariable("id") Integer roleId,
			@PathVariable("oper") String operType, HttpServletRequest request,
			HttpServletResponse response) {
		if (null != roleId) {
			// validate role
			if (validSuperUser(request)) {
				if (StringUtils.isNotBlank(operType)) {
					try {
						Role role = roleService.getRoleById(roleId);
						if (null != role) {
							if (OPER_STARTUP.equals(operType)) {// 启用
								role.setIsUsed(1);
							} else if (OPER_FORBID.equals(operType)) {// 禁用
								role.setIsUsed(0);
							}
							int result = roleService.update(role);
							if (1 == result) {
								return ResultGenerator.genSuccessResult();
							}
						}
					} catch (Exception e) {
						log.error("更改角色权限错误：" + e.getMessage());
						return ResultGenerator.genFailResult("系统异常，操作失败！");
					}
				}
			} else {
				ResultGenerator.genFailResult("您没有权限！");
			}
		}
		return ResultGenerator.genFailResult("操作失败，未知异常！");
	}

	/**
	 * 获取授权菜单
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getAuthMenus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<RoleAuthParentMenuDTO> findAuthMenu(
			@PathVariable("id") Integer roleId) {
		List<RoleAuthParentMenuDTO> result = null;
		if (null != roleId) {
			Role role = roleService.getRoleById(roleId);
			if (null != roleId && 1 == role.getIsUsed()) {
				result = roleAuthService.findAuthMenus(roleId);
			}
		}
		return result;
	}
	
	/**
	 * 设置授权菜单
	 * @param data
	 * @param roleId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "auth", method = RequestMethod.POST, headers = { "oper=RoleAuth" })
	@ResponseBody
	public Result auth(@RequestParam("dataStr") String data,
			@RequestParam Integer roleId, HttpServletRequest request,
			HttpServletResponse response) {
		if (validSuperUser(request) && null != roleId) {
			try {
				String[] arr = data.split(",");
				boolean result = roleAuthService.setAuthMenus(roleId, Arrays.asList(arr));
				if (result) {
					return ResultGenerator.genSuccessResult();
				}
			} catch (Exception e) {
				log.error("授权菜单异常：" + e.getMessage());
				return ResultGenerator.genFailResult("系统异常，操作失败！");
			}
		} else {
			ResultGenerator.genFailResult("您没有操作权限！");
		}
		return ResultGenerator.genFailResult("操作失败，未知异常！");
	}
	
	/**
	 * 获取所有权限信息
	 * @return
	 */
	@RequestMapping(value = "/findAllroles", method = RequestMethod.GET)
	@ResponseBody
	public List<Role> findAllRoles(){
		List<Role> result = null;
		try {
			result = roleService.findAllRoles();
		} catch (Exception e) {
			log.error("获取所有权限信息失败：" + e.getMessage());
		}
		return result;
	}
}
