package com.ssm.promotion.core.service;

import java.util.List;
import java.util.Map;

import com.ssm.promotion.core.dto.MenuDTO;
import com.ssm.promotion.core.dto.MenuManageDTO;
import com.ssm.promotion.core.dto.RoleAuthParentMenuDTO;
import com.ssm.promotion.core.entity.Menu;

/**
 * @description
 * @author liu66
 * @date 创建时间：2018-4-25 下午2:27:57
 */
public interface MenuService {
	
	/**
	 * 构建左侧导航菜单
	 * 
	 * @return
	 */
	public List<MenuDTO> findRoleMenus(Integer roleId);

	/**
	 * 菜单管理表格
	 * 
	 * @return
	 */
	public List<MenuManageDTO> datagrid(Map map);

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	public int insert(Menu menu);

	/**
	 * 编辑菜单
	 * 
	 * @param menu
	 * @return
	 */
	public int update(Menu menu);

	/**
	 * 删除菜单
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id);

	/**
	 * 获取所有父菜单
	 * 
	 * @return
	 */
	public List<MenuDTO> findParentMenus();
	
	/**
	 * 获取菜单总数
	 * @return
	 */
	public Long getTotalMenu(Map map);
}
