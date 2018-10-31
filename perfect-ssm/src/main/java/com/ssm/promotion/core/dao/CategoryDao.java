package com.ssm.promotion.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssm.promotion.core.entity.Category;

/**
 * @author liu66
 * @project_name perfect-ssm
 */
@Repository
public interface CategoryDao {


    /**
     * 查找类目列表
     *
     * @param map
     * @return
     */
    public List<Category> findCategorys(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    public Long getTotalCategory(Map<String, Object> map);

    /**
     * 修改类目
     *
     * @param category
     * @return
     */
    public int updateCategory(Category category);

    /**
     * 添加类目
     *
     * @param category
     * @return
     */
    public int addCategory(Category category);

    /**
     * 删除类目
     *
     * @param id
     * @return
     */
    public int deleteCategory(Integer id);
    
    /**
     * 获取父目录
     * @return
     */
	public List<Category> findPatentCategory();
	
	/**
	 * 按类目id查找
	 * @param id
	 * @return
	 */
	public Category findCategoryById(int id);
	
	/**
	 * 添加子类目时，其父目录对应的has_child置为1
	 * @param parentId
	 * @return
	 */
	public int updatePCategoryHasChildToOne(int parentId);

	/**
	 * 设置父类目的has_child为1
	 * @param parentId
	 */
	public void setParentHaschild(int parentId);
    
}
