package com.ssm.promotion.core.dto;

import java.io.Serializable;

/**
 * @description
 * @author liu66
 * @date 创建时间：2018-6-21
 */
public class RoleAuthChildMenuDTO implements Serializable {
	private Integer id;
	private int type;// 父菜单0 子菜单1
	private Integer parentId;// 父菜单 id
	private String title;// 标题
	private String path;// 路径
	private String name;// 名称
	private Integer tOrder;// 排序
	private boolean selected;// 是否选中

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer gettOrder() {
		return tOrder;
	}

	public void settOrder(Integer tOrder) {
		this.tOrder = tOrder;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
