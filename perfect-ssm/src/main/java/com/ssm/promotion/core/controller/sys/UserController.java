package com.ssm.promotion.core.controller.sys;

import java.util.HashMap;
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
import com.ssm.promotion.core.entity.PageBean;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.redis.RedisUtil;
import com.ssm.promotion.core.service.UserService;
import com.ssm.promotion.core.util.MD5Util;
import com.ssm.promotion.core.util.ResponseUtil;
import com.ssm.promotion.core.util.StringUtil;

/**
 * @author liu66
 * @project_name perfect-ssm
 */
@Controller
@RequestMapping("/users")
public class UserController extends BasicController {

	@Resource
	private UserService userService;
	@Autowired
	private RedisUtil redisUtil;

	private static final Logger log = Logger.getLogger(UserController.class);// 日志文件
	private static final Lock lock = new ReentrantLock();
	private static final String CACHE_KEY_PREFIX = "err_count_";
	private static final int LOGIN_ERR_COUNT = 5;// 默认登录失败次数为5次

	/**
	 * 登录
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Result login(User user, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session;
		User resultUser = userService.getUserByName(user.getUserName().trim());
		if (null != resultUser) {
			final String pwd = StringUtils.isNotBlank(user.getPassword()) ? user
					.getPassword().trim() : null;
			if (null == pwd || !resultUser.getPassword().equals(pwd)) {
				final String key = CACHE_KEY_PREFIX + resultUser.getUuid();
				try {
					Integer errorCount = (Integer) redisUtil.get(key,
							Integer.class);
					if (null == errorCount) {
						redisUtil.put(key, 1);
					} else {
						try {
							lock.lock();
							final int restCount = LOGIN_ERR_COUNT - errorCount;
							if (restCount <= 0) {
								resultUser.setStatus(1);
								userService.updateUser(resultUser);
								return ResultGenerator
										.genFailResult("登录失败您的登录次数已用尽，请联系管理员！");
							} else {
								redisUtil.put(key, errorCount + 1);
								return ResultGenerator
										.genFailResult("登录失败，24小时内还有 " + restCount
												+ " 次登录机会！");
							}
						} catch (Exception e) {
							log.error("Login lock trowable:" + e);
						} finally {
							lock.unlock();
						}
					}
				} catch (Exception e) {
					log.error("Redis:连接异常！-->" + e.getMessage());
					return ResultGenerator.genFailResult("连接异常！");
				} 
			} else {
				Map data = new HashMap();
				session = request.getSession();
				resultUser.setPassword("PASSWORD");
				session.setAttribute(Constants.SESSION_USER, resultUser);
				data.put("currentUser", resultUser);
				return ResultGenerator.genSuccessResult(data);
			}
		}
		return ResultGenerator.genFailResult("账号状态异常，请联系管理员。");
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Result logOut(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.SESSION_USER);
		return ResultGenerator.genSuccessResult("成功登出！");
	}

	/**
	 * 跳转tab
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String index() {
		return "user/userManage";
	}

	/**
	 * @param page
	 * @param rows
	 * @param s_user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	public String list(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			User s_user, HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", StringUtil.formatLike(s_user.getUserName()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<User> userList = userService.findUser(map);
		Long total = userService.getTotalUser(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(userList);
		result.put("rows", jsonArray);
		result.put("total", total);
		log.info("request: user/list , map: " + map.toString());
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 添加或修改管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@RequestBody User user) throws Exception {
		int resultTotal = 0;
		resultTotal = userService.addUser(user);
		if (resultTotal > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("FAIL");
		}
	}

	/**
	 * 修改
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	public Result update(@RequestBody User user) throws Exception {
		String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
		user.setPassword(MD5pwd);
		int resultTotal = userService.updateUser(user);
		log.info("request: user/update , user: " + user.toString());
		if (resultTotal > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("FAIL");
		}
	}

	/**
	 * 删除管理员
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result delete(@PathVariable(value = "ids") String ids)
			throws Exception {
		if (ids.length() > 20) {
			return ResultGenerator.genFailResult("ERROR");
		}
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			userService.deleteUser(Integer.valueOf(idsStr[i]));
		}
		log.info("request: article/delete , ids: " + ids);
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 封停、解封
	 * 
	 * @param id
	 * @param oper
	 * @return
	 */
	@RequestMapping(value = "/chmod/{id}/{oper}")
	@ResponseBody
	public Result chMod(@PathVariable Integer id, @PathVariable String oper) {
		if (StringUtils.isBlank(oper)) {
			return ResultGenerator.genFailResult("");
		}
		User user = userService.getUserById(id);
		if (null != user) {
			if ("forbid".equals(oper)) {// 封停
				user.setStatus(1);
			} else if ("unforbid".equals(oper)) {// 解封
				user.setStatus(0);
				final String key = CACHE_KEY_PREFIX + user.getUuid();
				try {
					redisUtil.del(key);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			userService.updateUser(user);
		}
		return ResultGenerator.genSuccessResult("操作成功!");
	}

	/**
	 * 密码重置
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/resetPwd/{id}")
	@ResponseBody
	public Result resetPassword(@PathVariable Integer id) {
		final String rspwd = Constants.USER_DEFAULT_PWD;
		if (null == id) {
			return ResultGenerator.genFailResult("id为空");
		}
		User user = userService.getUserById(id);
		if (null != user) {
			user.setPassword(MD5Util.MD5Encode(rspwd, "utf-8"));
			try {
				int result = userService.updateUser(user);
				if (result > 0) {
					return ResultGenerator.genSuccessResult();
				} else {
					return ResultGenerator.genFailResult("修改失败，数据异常！");
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResultGenerator.genFailResult(e.getMessage());
			}
		}
		return ResultGenerator.genFailResult("修改失败，未知异常！");
	}

}
