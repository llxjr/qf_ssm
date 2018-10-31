package com.ssm.promotion.core.dto;

import com.ssm.promotion.core.entity.Category;
import com.sun.tools.javac.util.List;

/**
 * @description 类目管理-父子目录数据封装
 * @author liu66
 *
 */

public class CategoryParentChildDTO {
	private int id;
	private String categoryName;
	private List<Category> categoryChildList;
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
	public List<Category> getCategoryChildList() {
		return categoryChildList;
	}
	public void setCategoryChildList(List<Category> categoryChildList) {
		this.categoryChildList = categoryChildList;
	}
	
}
