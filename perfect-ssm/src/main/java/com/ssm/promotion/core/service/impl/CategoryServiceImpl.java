package com.ssm.promotion.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.promotion.core.dao.CategoryDao;
import com.ssm.promotion.core.dto.CategoryManageDTO;
import com.ssm.promotion.core.dto.CategoryParentChildDTO;
import com.ssm.promotion.core.entity.Category;
import com.ssm.promotion.core.entity.Menu;
import com.ssm.promotion.core.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	
	@Resource
	private CategoryDao categoryDao;
	
	
	@Override
	public List<CategoryManageDTO> findCategorys(Map<String, Object> map) {
		List<Category> categories = categoryDao.findCategorys(map);
		List<CategoryManageDTO> dtos = new ArrayList<CategoryManageDTO>();
		Category category = null;
		for (int i = 0; i < categories.size(); i++) {
			category = categories.get(i);
			CategoryManageDTO dto = new CategoryManageDTO();
			if (category.getParentId() !=0 ) {
				Category parentCategory = categoryDao.findCategoryById(category.getParentId());
				if(parentCategory != null && parentCategory.getCategoryName() != null){
					dto.setPcName(parentCategory.getCategoryName());
				}
			}
			dto.setId(category.getId());
			dto.setCategoryName(category.getCategoryName());
			dto.setDescription(category.getDescription());
			dto.setParentId(category.getParentId());
			dto.setHasChild(category.getHasChild());
			dto.setTag(category.getTag());
			dto.setLevel(category.getLevel());
			dto.setIsDel(category.getIsDel());
			dto.setCreateTime(category.getCreateTime());
			dto.setCreateBy(category.getCreateBy());
			dto.setUpdateTime(category.getUpdateTime());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public Long getTotalCategory(Map<String, Object> map) {
		return categoryDao.getTotalCategory(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateCategory(Category category) {
		category.setUpdateTime(new Date());
//		if (category.getParentId() != 0) {
//			//将其父类的has_child设为1
//			categoryDao.updatePCategoryHasChildToOne(category.getParentId());
//		}
		int level = 1;//判断级别
		int pid = category.getParentId();
		while (pid !=0 ) {
			Category pCategory = categoryDao.findCategoryById(pid);
			pid = pCategory.getParentId();
			level++;
		}
		category.setLevel(level);
		return  categoryDao.updateCategory(category);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addCategory(Category category) {	
		int level = 1;
		int pid = category.getParentId();
		while (pid !=0 ) {
			Category pCategory = categoryDao.findCategoryById(pid);
			pid = pCategory.getParentId();
			level++;
		}
		category.setCreateTime(new Date());
		category.setUpdateTime(new Date());
		category.setLevel(level);
		int addc = categoryDao.addCategory(category);
		//添加的为子类目时，其父类目对应的has_child置为1，表示有子目录`++
//		if (addc >0 && category.getParentId()!=0) {
//			int updatechild = categoryDao.updatePCategoryHasChildToOne(category.getParentId());
//			if (updatechild<0) {
//				return -1;
//			}
//		}
		return addc;
	}

	@Override
	public int deleteCategory(Integer id) {
		return categoryDao.deleteCategory(id);
	}

	@Override
	public List<CategoryParentChildDTO> findParentCategory() {
		List<Category> parentCategorys = categoryDao.findPatentCategory();
		List<CategoryParentChildDTO> dtos = new ArrayList<CategoryParentChildDTO>();
		if(parentCategorys != null && parentCategorys.size() >0){
			Category category = null;
			if(parentCategorys instanceof RandomAccess){
				//优化遍历效率
				//(随机访问列表)如 ArrayList 要实现此接口，Sequence Access List(顺序访问列表)如 LinkedList 不要实现
				for (int i = 0; i < parentCategorys.size(); i++) {
					category = parentCategorys.get(i);
					CategoryParentChildDTO dto = new CategoryParentChildDTO();
					dto.setId(category.getId());
					dto.setCategoryName(category.getCategoryName());
					dtos.add(dto);
				}
			}else{
				Iterator<Category> cit = parentCategorys.iterator();
				while (cit.hasNext()) {
					CategoryParentChildDTO dto = new CategoryParentChildDTO();
					category = cit.next();
					dto.setId(category.getId());
					dto.setCategoryName(category.getCategoryName());
					dtos.add(dto);
				}
			}
		}
		return dtos;
	}

}
