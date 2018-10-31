package com.ssm.promotion.core.service;

import java.util.List;
import java.util.Map;

import com.ssm.promotion.core.dto.RoleManageDTO;
import com.ssm.promotion.core.entity.Role;

/** 
 * @description 
 * @author  chj 
 * @date 创建时间：2018-5-4 上午10:36:15 
 * @version 1.0 
 * @since  
 */
public interface RoleService {
	
	/**
	 * 根据id获取角色
	 * @param id
	 * @return
	 */
	Role getRoleById(Integer id);
	
	/**
	 * 获取所有角色
	 * @return
	 */
	List<Role> findAllRoles();
	
	/**
	 * 插入
	 * @param role
	 * @return
	 */
	int insert(Role role);
	
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	int update(Role role);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(Integer id);
	
	/**
	 * datagrid
	 * @param map
	 * @return
	 */
	List<RoleManageDTO> datagrid(Map map);
	
	/**
	 * 获取总数
	 * @return
	 */
	Long getTotalRole(Map map);
	
}
