package com.ssm.promotion.core.service;

import java.util.List;

import com.ssm.promotion.core.dto.RoleAuthParentMenuDTO;

/** 
 * @description 
 * @author  chj 
 * @date 创建时间：2018-6-22 上午10:46:54 
 * @version 1.0 
 * @since  
 */
public interface RoleAuthService {
	/**
	 * 获取授权菜单
	 * @return
	 */
	List<RoleAuthParentMenuDTO> findAuthMenus(Integer roleId);
	
	/**
	 * 更新授权菜单
	 */
	public boolean setAuthMenus(Integer roleId, List params);
}
