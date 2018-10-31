package com.ssm.promotion.core.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.promotion.core.common.Constants;
import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.controller.base.BasicController;
import com.ssm.promotion.core.dto.CategoryManageDTO;
import com.ssm.promotion.core.dto.CategoryParentChildDTO;
import com.ssm.promotion.core.entity.Category;
import com.ssm.promotion.core.entity.Menu;
import com.ssm.promotion.core.entity.PageBean;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.redis.RedisUtil;
import com.ssm.promotion.core.service.CategoryService;
import com.ssm.promotion.core.util.DataGrid;
import com.ssm.promotion.core.util.ResponseUtil;
import com.ssm.promotion.core.util.SQLUtils;
import com.ssm.promotion.core.util.StringUtil;

/**
 * 类目管理
 * @author liu66
 */
@Controller
@RequestMapping("/qf/category")
public class CategoryController extends BasicController {

	@Resource
	private CategoryService categoryService;
	@Autowired
	private RedisUtil redisUtil;

	private static final Logger log = Logger.getLogger(CategoryController.class);// 日志文件
	private static final Lock lock = new ReentrantLock();

	
	/**
	 * 跳转tab
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String index() {
		return "category/category";
	}

	/**
	 * @param page
	 * @param rows
	 * @param c_category
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public String list(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			Category c_category, HttpServletResponse response) throws Exception {
		Map map = SQLUtils.bulidConditionMap(c_category.getCategoryName(), null,
				null, new PageBean(page, rows));
		List<CategoryManageDTO> list = categoryService.findCategorys(map);
		Long total = categoryService.getTotalCategory(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(list);
		result.put("rows", jsonArray);
		result.put("total", total);
		log.info("request: qf/category/list , map: " + map.toString());
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 父目录列表
	 */
	@RequestMapping(value="findParentCategory")
	@ResponseBody
	public Result findParentCategory(){
		List<CategoryParentChildDTO> dtos = new ArrayList<CategoryParentChildDTO>();
		dtos = categoryService.findParentCategory();
		return ResultGenerator.genSuccessResult(dtos);
	}
	
	
	/**
	 * 添加类目
	 * 
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "addCategory", method = { RequestMethod.POST })
	@ResponseBody
	public Result addCategory(@RequestBody Category category, HttpServletRequest request,
			HttpServletResponse response) {
		User currentUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER);
		try {
			if (currentUser != null) {
				category.setCreateBy(currentUser.getUserName());
			}
			categoryService.addCategory(category);
		} catch (Exception e) {
			log.error("add: " + e);
			e.printStackTrace();
			return ResultGenerator.genFailResult("添加失败,服务器异常!");
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 编辑类目
	 * 
	 * @param category
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editCategory", method = { RequestMethod.POST })
	@ResponseBody
	public Result editCategory(@RequestBody Category category, HttpServletRequest request,
			HttpServletResponse response) {
		User currentUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER);
		try {
			if (null != currentUser) {
				category.setUpdateBy(currentUser.getUserName());
			}
			categoryService.updateCategory(category);
		} catch (Exception e) {
			log.error("update: " + e);
			e.printStackTrace();
			return ResultGenerator.genFailResult("编辑失败,服务器异常!");
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 删除类目
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteCategory/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteCategory(@PathVariable Integer id) {
		try {
			if (id != null) {
				categoryService.deleteCategory(id);
			} else {
				return ResultGenerator.genFailResult("数据主键为空");
			}
		} catch (Exception e) {
			log.error("delete: " + e);
			e.printStackTrace();
			return ResultGenerator.genFailResult("删除失败,服务器异常!");
		}
		return ResultGenerator.genSuccessResult();
	}


}
