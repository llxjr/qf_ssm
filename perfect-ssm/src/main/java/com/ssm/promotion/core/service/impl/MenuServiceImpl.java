package com.ssm.promotion.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.promotion.core.dao.MenuDao;
import com.ssm.promotion.core.dao.RoleMenuDao;
import com.ssm.promotion.core.dto.MenuDTO;
import com.ssm.promotion.core.dto.MenuManageDTO;
import com.ssm.promotion.core.entity.Menu;
import com.ssm.promotion.core.entity.RoleMenu;
import com.ssm.promotion.core.service.MenuService;

/**
 * @description
 * @author liu66
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Override
	public List<MenuDTO> findRoleMenus(Integer roleId) {
		List<MenuDTO> dtoList = new ArrayList<MenuDTO>();
		List<Menu> menuList = null;
		if (roleId == 1) {// 超级管理员
			menuList = menuDao.findAllMenus(new HashMap());
		} else {
			List<RoleMenu> authList = roleMenuDao.findMenuByRoleId(roleId);// 获取所有授权菜单
			if (!authList.isEmpty()) {
				menuList = menuDao.findMenusByRole(authList);
			}
		}
		Menu menu = null;
		if (null != menuList && menuList.size() > 0) {
			for (int i = 0, size = menuList.size(); i < size; ++i) {
				menu = menuList.get(i);
				if (0 == menu.getType()) {// 是父菜单
					MenuDTO dto = new MenuDTO();
					List<Menu> childList = menuDao
							.findMenusByParentId(menu.getId());// 获取所有子菜单
					if (childList.size() > 0) {
						dto.setMenuChildList(childList);
					}
					dto.setIcon(menu.getIcon());
					dto.setId(menu.getId());
					dto.setTitle(menu.getTitle());
					dtoList.add(dto);
				}
			}
		}
		return dtoList;
	}

	@Override
	public List<MenuManageDTO> datagrid(Map map) {
		List<Menu> menus = menuDao.findAllMenus(map);
		List<MenuManageDTO> dtos = new ArrayList<MenuManageDTO>();
		Menu menu = null;
		for (int i = 0; i < menus.size(); ++i) {
			menu = menus.get(i);
			MenuManageDTO dto = new MenuManageDTO();
			if (menu.getType() == 1) {// 如果是子目录
				Menu parent = menuDao.getMenuById(menu.getParentId());// 获取父目录
				if (null != parent && null != parent.getTitle()) {
					dto.setPmName(parent.getTitle());
				}
			}
			dto.setMenuName(menu.getName());
			dto.setId(menu.getId());
			dto.setTitle(menu.getTitle());
			dto.setType(menu.getType());
			dto.setPath(menu.getPath());
			dto.setCreateTime(menu.getCreateTime());
			dto.setCreateBy(menu.getCreateBy());
			dto.setDescription(menu.getDescription());
			dto.setIcon(menu.getIcon());
			dto.settOrder(menu.gettOrder());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insert(Menu menu) {
		// 如果是子菜单 判断父菜单角色
		if (1 == menu.getType()) {
			Menu parent = menuDao.getMenuById(menu.getParentId());
		}
		String path = menu.getPath();
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		menu.setPath(path);
		menu.setName(menu.getTitle());
		menu.setCreateTime(new Date());
		menu.setUpdateTime(new Date());
		return menuDao.insert(menu);
	}

	@Override
	public List<MenuDTO> findParentMenus() {
		List<Menu> parentMenus = menuDao.findParentMenus();
		List<MenuDTO> dtos = new ArrayList<MenuDTO>();
		if (parentMenus != null && parentMenus.size() > 0) {
			Menu menu = null;
			if (parentMenus instanceof RandomAccess) {//提高遍历效率
				for (int i = 0; i < parentMenus.size(); ++i) {
					menu = parentMenus.get(i);
					MenuDTO dto = new MenuDTO();
					dto.setId(menu.getId());
					dto.setTitle(menu.getTitle());
					dtos.add(dto);
				}
			} else {
				Iterator<Menu> mit = parentMenus.iterator();
				while (mit.hasNext()) {
					MenuDTO dto = new MenuDTO();
					menu = mit.next();
					dto.setId(menu.getId());
					dto.setTitle(menu.getTitle());
					dtos.add(dto);
				}
			}
		}
		return dtos;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(Menu menu) {
		if (null == menu.getParentId() || menu.getParentId() == 0) {
			menu.setParentId(null);
		}
		List<Menu> childList = menuDao.findMenusByParentId(menu.getId());
		Menu child = null;
		for (int i = 0, size = childList.size(); i < size; ++i) {
			child = childList.get(i);
			child.setUpdateTime(new Date());
			menuDao.update(child);
		}
		menu.setName(menu.getName());
		menu.setUpdateTime(new Date());
		return menuDao.update(menu);
	}

	@Override
	public int delete(int id) {
		return menuDao.delete(id);
	}

	@Override
	public Long getTotalMenu(Map map) {
		return menuDao.getTotalMenu(map);
	}

}
