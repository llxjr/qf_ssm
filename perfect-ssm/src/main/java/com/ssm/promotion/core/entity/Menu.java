package com.ssm.promotion.core.entity;

import com.ssm.promotion.core.entity.base.BaseEntity;

/**
 * @description 菜单管理
 * @author liu66
 * @date 创建时间：2018-4-25 下午1:48:35
 * @version 1.0
 * @since
 */
public class Menu extends BaseEntity {

	private int type;// 父菜单0 子菜单1
	private Integer parentId;// 父菜单 id
	private String title;// 标题
	private String icon;// 图标
	private String path;// 路径
	private String name;// 名称
	private String description;// 描述
	private Integer tOrder;//排序

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer gettOrder() {
		return tOrder;
	}

	public void settOrder(Integer tOrder) {
		this.tOrder = tOrder;
	}

}
