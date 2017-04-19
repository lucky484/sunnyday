package com.hd.pfirs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd.pfirs.constant.LogType_Operate;
import com.hd.pfirs.model.MenuDictModel;
import com.hd.pfirs.model.OperationLogInfo;
import com.hd.pfirs.model.User;
import com.hd.pfirs.service.RoleService;
import com.hd.pfirs.service.UserService;
import com.hd.pfirs.util.logs.service.LogService;

/**
 * 
 * @author brave.chen
 *
 */
@Controller
@RequestMapping("/LoginController")
public class LoginController
{

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private LogService logService;
	
	private  DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 详情页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userLogin")
	public String userLogin(HttpServletRequest request)
	{
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.getUserByUserName(userName);
		String pw = null;

		if (null != user)
		{
			pw = user.getPassword();
		}

		if (password.equals(pw))
		{
			HttpSession session = request.getSession();

			Map<String, String[]> paramterMap = request.getParameterMap();
			if (MapUtils.isNotEmpty(paramterMap))
			{
				session.setAttribute("username", userName);
			}
			user.setLastloginDate(new Date());
			user.setUpdateName(userName);
			userService.updateUser(user);
			return "index";
		}

		else
		{
			return "login";
		}

	}

	/**
	 * 详情页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "checkUserName")
	@ResponseBody
	public Map<String, Object> checkUserName(String userName)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.getUserByUserName(userName);

		if (null != user)
		{
			map.put("isExist", true);
		}

		return map;
	}
	
	
	/**
	 * 推出登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "logOut")
	@ResponseBody
	public Map<String, Object> logOut(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		session.removeAttribute("username");
		map.put("status", "success");
		return map;
	}

	/**
	 * 详情页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "isNameNotMatchPw")
	@ResponseBody
	public Map<String, Object> isNameNotMatchPw(String userName, String password) throws Exception
	{
		OperationLogInfo operationLogInfo = getOperationLogInfo(userName, LogType_Operate.type_query, null, null);
		operationLogInfo.setOperateName("用户登录");
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			User user = userService.getUserByUserName(userName);

			if (null != user)
			{
				if ("0".equals(user.getStatus()))
				{
					map.put("checkResult", 0);
				}
				else if (user.getPassword().equals(password))
				{
					map.put("checkResult", 1);
				}
				else
				{
					map.put("checkResult", 3);
				}
				logService.recordOperateLog(operationLogInfo);
			}
			else
			{
				map.put("checkResult", 2);
				operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_USER);
				operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
				logService.recordOperateLog(operationLogInfo);
			}

			return map;
		}

		catch (Exception e)
		{
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}

	}

	/**
	 * 详情页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAuthMenus")
	@ResponseBody
	public Map<String, Object> getAuthMenus(HttpServletRequest request)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("username");

		User user = userService.getUserByUserName(userName);
		Long roleId = user.getRoleId();
		List<MenuDictModel> menuDictModels = roleService.getAuthMenusByRoleId(roleId);
		List<MenuDictModel> authModels = new ArrayList<MenuDictModel>();
		List<MenuDictModel> fatherModels = new ArrayList<MenuDictModel>();
		List<MenuDictModel> subModels = new ArrayList<MenuDictModel>();
		Iterator<MenuDictModel> iterator = menuDictModels.iterator();

		while (iterator.hasNext())
		{
			MenuDictModel model = iterator.next();
			if (null == model.getFatherMenuID())
			{
				fatherModels.add(model);
			}
			else
			{
				subModels.add(model);
			}
		}

		if (CollectionUtils.isNotEmpty(fatherModels))
		{
			for (MenuDictModel menuDictModel : fatherModels)
			{
				int i = 0;
				if (menuDictModel.getMenuID() == 10010000000l)
				{
					authModels.add(menuDictModel);
				}
				for (MenuDictModel subMenuDictModel : subModels)
				{
					if (menuDictModel.getMenuID().equals(subMenuDictModel.getFatherMenuID()))
					{
						authModels.add(subMenuDictModel);
						i++;
					}
				}

				if (i > 0)
				{
					authModels.add(menuDictModel);
				}
			}
		}
		map.put("menuRoles", authModels);
		return map;
	}

	private OperationLogInfo getOperationLogInfo(String userName, int operateType, String optCondition, String optDesc)
	{
		OperationLogInfo info = new OperationLogInfo();
		info.setUserName(userName);
		info.setOperateType(operateType);
		info.setOperateDesc(optDesc);
		info.setOperateCondition(optCondition);
		info.setOperateTime(format.format(new Date()));
		info.setOperateResult(LogType_Operate.SUCCESS_TYPE);
		return info;
	}
}
