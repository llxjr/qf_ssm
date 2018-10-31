package com.ssm.promotion.core.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author liu66
 * @date 创建时间：2018-6-19 
 */
public class RoleManageDTO implements Serializable {
	/** pkey */
	private Integer id;
	/** 等级 */
	private Integer grade;
	/** 角色名称 */
	private String roleName;
	/** 描述 */
	private String description;
	/** 是否启用 */
	private Integer isUsed;
	/** 创建时间 */
	private Date createTime;
	/** 创建者 */
	private String createBy;
	
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

}
