package com.ssm.promotion.core.entity;

import java.util.Date;

import com.ssm.promotion.core.entity.base.BaseEntity;

/**
 * @description 类目管理
 * @date 2018/10/11
 * @author liu66
 *
 */
public class Category extends BaseEntity{
	private int id;
	private String categoryName;
	private String description;
	private int parentId;
	private int hasChild;
	private String tag;
	private int level;
	private int isDel;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getHasChild() {
		return hasChild;
	}
	public void setHasChild(int hasChild) {
		this.hasChild = hasChild;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", description=" + description + ", parentId="
				+ parentId + ", hasChild=" + hasChild + ", tag=" + tag + ", level=" + level + ", isDel=" + isDel + "]";
	}
	
	
}
