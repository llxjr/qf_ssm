package com.ssm.promotion.core.entity;

/**
 * @description 权限授权菜单
 * @author liu66 
 * @date 创建时间：2018-6-21 下午2:51:59
 * @version 1.0
 * @since
 */
public class RoleMenu {
	private Integer id;
	private Integer roleId;
	private Integer menuId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

}
