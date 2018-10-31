package com.ssm.promotion.core.service;

import java.util.List;
import java.util.Map;

import com.ssm.promotion.core.dto.CategoryManageDTO;
import com.ssm.promotion.core.dto.CategoryParentChildDTO;
import com.ssm.promotion.core.entity.Category;

/**
 * 
 * @author liu66
 *
 */
public interface CategoryService {

    /**
     * @param map
     * @return
     */
    public List<CategoryManageDTO> findCategorys(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    public Long getTotalCategory(Map<String, Object> map);

    /**
     * @param category
     * @return
     */
    public int updateCategory(Category category);

    /** 添加类目
     * @param category
     * @return
     */
    public int addCategory(Category category);

    /** 
     * 删除类目
     * @param id
     * @return
     */
    public int deleteCategory(Integer id);

    /**
     * 父目录列表
     * @return
     */
	public List<CategoryParentChildDTO> findParentCategory();
    

   
}
