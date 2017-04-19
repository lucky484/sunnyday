package com.f2b.security.action.dashboard;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b.config.ConstantKeySession;
import com.f2b.security.business.biz.AdminUserBiz;
import com.f2b.security.domain.AdminUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: 陈梨春  Date: 14-11-5
 */
@Controller
@RequestMapping("/auth")
public class LoginAction {

	private final static Logger logger = LoggerFactory.getLogger(LoginAction.class);

	@Autowired
	private AdminUserBiz adminUserBiz;

	/**
	 * 登录
	 *
	 * @param agentName 登录名
	 * @param password  密码
	 * @param request   请求实体
	 * @return ModelAndView 视图对象
	 */
	@RequestMapping("/login")
	@ResponseBody
	public JSONObject Login(@RequestParam(value = "agentName", required = false) String agentName,
	                        @RequestParam(value = "password", required = false) String password,
	                        HttpServletRequest request) {

		logger.info("登陆请求提交的【用户名】为[" + agentName + "]");
		logger.info("登陆请求提交的【密  码】为[" + password + "]");

		JSONObject result = new JSONObject();

		//调用【用户名】、【密码】比对方法进行登陆判断
		AdminUser loginUser = adminUserBiz.login(agentName, password);
		if (null == loginUser) {
			logger.error("用户名或密码错误");
			result.put("success", Boolean.FALSE);
			result.put("msg", "用户名或密码错误");
			return result;
		}
		logger.debug("登录成功，登录信息是：" + loginUser);

		//登陆成功，保存用户【Session】
		request.getSession().setAttribute(ConstantKeySession.WEB_USER_INFO_KEY, loginUser);

		result.put("success", Boolean.TRUE);
		result.put("msg", "登录成功");
		return result;
	}

	/**
	 * 退出登录
	 *
	 * @param request HttpServletRequest Object.
	 * @return ViewName
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("loginAgent");
		return "security/login";
	}
}
