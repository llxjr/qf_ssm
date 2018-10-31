package com.ssm.promotion.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.promotion.core.entity.RoleMenu;

/** 
 * @description 
 * @author  chj 
 * @date 创建时间：2018-6-21 下午2:53:22 
 * @version 1.0 
 * @since  
 */
@Repository
public interface RoleMenuDao {
	
	List<RoleMenu> findMenuByRoleId(Integer roleId);
	
	int insertBatch(List list);
	
	int deleteByRoleId(Integer roleId);
}
