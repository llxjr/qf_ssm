package com.ssm.promotion.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.promotion.core.dao.MenuDao;
import com.ssm.promotion.core.dao.RoleMenuDao;
import com.ssm.promotion.core.dto.RoleAuthChildMenuDTO;
import com.ssm.promotion.core.dto.RoleAuthParentMenuDTO;
import com.ssm.promotion.core.entity.Menu;
import com.ssm.promotion.core.entity.RoleMenu;
import com.ssm.promotion.core.service.RoleAuthService;

/**
 * @description
 * @author chj
 * @date 创建时间：2018-6-22 上午10:47:25
 * @version 1.0
 * @since
 */
@Service
public class RoleAuthServiceImpl implements RoleAuthService {

	private static ThreadLocal<List<RoleMenu>> menuHolder = new ThreadLocal<List<RoleMenu>>();

	@Autowired
	private RoleMenuDao roleMenuDao;
	@Autowired
	private MenuDao menuDao;

	@Override
	public List<RoleAuthParentMenuDTO> findAuthMenus(Integer roleId) {
		List<RoleAuthParentMenuDTO> result = new ArrayList<RoleAuthParentMenuDTO>();
		List<Menu> parentMenus = menuDao.findParentMenus();// 获取所有父菜单
		List<RoleMenu> authMenus = roleMenuDao.findMenuByRoleId(roleId);// 授权菜单
		try {
			menuHolder.set(authMenus);
			Menu menu = null;
			for (int i = 0, size = parentMenus.size(); i < size; ++i) {
				menu = parentMenus.get(i);
				RoleAuthParentMenuDTO parentDto = new RoleAuthParentMenuDTO();
				List<Menu> childMenus = menuDao.findMenusByParentId(menu
						.getId());
				List<RoleAuthChildMenuDTO> childDtos = new ArrayList<RoleAuthChildMenuDTO>();
				for (Menu child : childMenus) {
					RoleAuthChildMenuDTO childDto = new RoleAuthChildMenuDTO();
					BeanUtils.copyProperties(childDto, child);
					if (checkAuthMenu(child.getId())) {
						childDto.setSelected(Boolean.TRUE);
					} else {
						childDto.setSelected(Boolean.FALSE);
					}
					childDtos.add(childDto);
				}
				parentDto.setId(menu.getId());
				parentDto.setTitle(menu.getTitle());
				parentDto.setMenuChildList(childDtos);
				if (checkAuthMenu(menu.getId())) {
					parentDto.setSelected(Boolean.TRUE);
				} else {
					parentDto.setSelected(Boolean.FALSE);
				}
				result.add(parentDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			menuHolder.remove();
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean setAuthMenus(Integer roleId, List params) {
		List<RoleMenu> authList = roleMenuDao.findMenuByRoleId(roleId);
		if (!authList.isEmpty()) {// del
			for (RoleMenu item : authList) {
				int isDel = roleMenuDao.deleteByRoleId(roleId);
				if (isDel == 0) {
					System.out.println("ID为：" + item.getId() + "的数据删除失败！");
				}
			}
		}
		// add
		List<RoleMenu> lst =new ArrayList<RoleMenu>();
		for (int i = 0, size = params.size(); i < size; i ++) {
			final Integer menuId = Integer.parseInt(params.get(i).toString());
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			lst.add(roleMenu);
		}
		int isAdd = roleMenuDao.insertBatch(lst);
		if (lst.size() == isAdd) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否在授权内
	 * 
	 * @param menuId
	 * @return
	 */
	private boolean checkAuthMenu(Integer menuId) {
		List<RoleMenu> authMenus = menuHolder.get();
		RoleMenu roleMenu = null;
		for (int i = 0, size = authMenus.size(); i < size; i++) {
			roleMenu = authMenus.get(i);
			if (roleMenu.getMenuId() == menuId) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}
