package com.ssm.promotion.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ssm.promotion.core.entity.Menu;

/** 
 * @description 
 * @author  liu66 
 * @date 创建时间：2018-4-25 下午2:06:19 
 * @version 1.0 
 * @since  
 */
@Repository
public interface MenuDao {
	
	/**
	 * 增加菜单
	 * @param menu
	 * @return
	 */
	public int insert(Menu menu);
	
	/**
	 * 更新菜单
	 * @param menu
	 * @return
	 */
	public int update(Menu menu);
	
	/**
	 * 删除菜单
	 * @param menu
	 * @return
	 */
	public int delete(int id);
	
	/**
	 * 根据主键id获取菜单
	 * @param id
	 * @return
	 */
	public Menu getMenuById(int id);
	
	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<Menu> findAllMenus(Map map);
	
	/**
	 * 获取所有父菜单
	 * @return
	 */
	public List<Menu> findParentMenus();
	
	/**
	 * 根据权限获取所有菜单
	 * @return
	 */
	public List<Menu> findMenusByRole(List list);
	
	/**
	 * 根据父菜单id获取子菜单集合
	 * @param parentId
	 * @return
	 */
	public List<Menu> findMenusByParentId(@Param("id")int parentId);
	
	/**
	 * 获取菜单总记录数
	 * @param map
	 * @return
	 */
	public Long getTotalMenu(Map map);
}
