package com.ssm.promotion.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssm.promotion.core.dto.RoleManageDTO;
import com.ssm.promotion.core.entity.Role;

/** 
 * @description 角色管理DAO
 * @author  chj 
 * @date 创建时间：2018-5-4 上午10:27:22 
 * @version 1.0 
 * @since  
 */
@Repository
public interface RoleDao {
	
	/**
	 * 根据id获取角色信息
	 * @param id
	 * @return
	 */
	Role getRoleById(int id);
	
	/**
	 * 根据权限等级获取权限
	 * @param grade
	 * @return
	 */
	Role getRoleByGrade(int grade);
	
	/**
	 * 获取所有角色信息
	 * @return
	 */
	List<Role> findAllRoles();
	
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	int insert(Role role);
	
	/**
	 * 更改
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
	List<Role> dataGrid(Map map);
	
	/**
	 * 获取总数
	 * @return
	 */
	Long getTotal(Map map);
}
