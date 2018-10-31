package com.ssm.promotion.core.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 菜单管理datagrid
 * @author liu66
 */
public class MenuManageDTO implements Serializable {
	private int id;
	private String title;
	private int type;
	private String menuName;
	private String roleName;
	private String pmName;
	private String path;
	private String icon;
	private String description;
	private Date createTime;
	private String createBy;
	private Integer tOrder;

	public Integer gettOrder() {
		return tOrder;
	}

	public void settOrder(Integer tOrder) {
		this.tOrder = tOrder;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
