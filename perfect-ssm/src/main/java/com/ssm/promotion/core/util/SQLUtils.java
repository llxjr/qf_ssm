package com.ssm.promotion.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ssm.promotion.core.entity.PageBean;

/** 
 * @description 
 * @author  liu66 
 */
public class SQLUtils {
	
	/**
	 * 构造 分页模糊查询 SQL
	 * @param condition
	 * @param pageBean
	 * @return
	 */
	public static Map bulidConditionMap(String condition,String sortField, String order, PageBean pageBean) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		if (null != condition) {
			conditionMap.put("condition", StringUtil.formatLike(condition));
		}
		if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(order)) {
			conditionMap.put("orderByStmt", sortField + " " + order.toUpperCase());
		}
		if (null != pageBean) {
			int page = pageBean.getStart();
			int pageSize = pageBean.getPageSize();
			conditionMap.put("pageNum",page >= 0 ? page : 0);
			conditionMap.put("pageSize", pageSize >= 10 ? pageSize : 10);
		}
		return conditionMap;
	}
	
	/**
	 * 构造 in sql语句
	 * 
	 * @param list
	 * @return
	 */
//	public static String buildInSqlStmt(List<Integer> list, String columnName) {
//		StringBuilder sb = new StringBuilder();
//		if (null != list && list.size() > 0) {
//			sb.append("AND "+ columnName + " IN(");
//			for (int i = 0, size = list.size(); i < size; ++i) {
//				Integer region = list.get(i);
//				sb.append(region);
//				if (i != size - 1) {
//					sb.append(", ");
//				}
//			}
//			sb.append(")");
//		}
//		return sb.toString();
//	}
}
