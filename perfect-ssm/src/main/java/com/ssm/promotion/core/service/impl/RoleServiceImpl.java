package com.ssm.promotion.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.promotion.core.dao.RoleDao;
import com.ssm.promotion.core.dto.RoleManageDTO;
import com.ssm.promotion.core.entity.Role;
import com.ssm.promotion.core.service.RoleService;

/** 
 * @description 
 * @author  chj 
 * @date 创建时间：2018-5-4 上午10:38:34 
 * @version 1.0 
 * @since  
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public Role getRoleById(Integer id) {
		return roleDao.getRoleById(id);
	}

	@Override
	public List<Role> findAllRoles() {
		return roleDao.findAllRoles();
	}

	@Override
	public int insert(Role role) {
		role.setGrade(9);//预设值
		role.setCreateTime(new Date());
		role.setCreateBy("author");
		return roleDao.insert(role);
	}

	@Override
	public int update(Role role) {
		role.setUpdateTime(new Date());
		return roleDao.update(role);
	}

	@Override
	public int delete(Integer id) {
		return roleDao.delete(id);
	}

	@Override
	public List<RoleManageDTO> datagrid(Map map) {
		List<Role> roleList = roleDao.dataGrid(map);
		List<RoleManageDTO> dtos = new ArrayList<RoleManageDTO>();
		Role role = null;
		for (int i = 0, size = roleList.size(); i < size; ++ i) {
			role = roleList.get(i);
			RoleManageDTO dto = new RoleManageDTO();
			dto.setId(role.getId());
			dto.setRoleName(role.getRoleName());
			dto.setIsUsed(role.getIsUsed());
			dto.setCreateTime(role.getCreateTime());
			dto.setCreateBy(role.getCreateBy());
			dto.setDescription(role.getDescription());
			dto.setGrade(role.getGrade());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public Long getTotalRole(Map map) {
		return roleDao.getTotal(map);
	}

}
