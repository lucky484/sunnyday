package com.f2b2c.eco.controller.platform.f2b;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.common.ProvinceModel;
import com.f2b2c.eco.model.platform.FRoleModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.model.platform.FUserToRoleModel;
import com.f2b2c.eco.service.common.AreaService;
import com.f2b2c.eco.service.platform.FRoleService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.service.platform.FUserToRoleService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.AreaUtil;
import com.f2b2c.eco.util.ConstantUtil.RoleType;
import com.f2b2c.eco.util.Pbkdf2Encryption;

@Controller("/farmUserController")
@RequestMapping("/farm/user")
public class FUserController extends BaseController {

	/**
	 * 日志工具类
	 */
	private static final Logger logger = LoggerFactory.getLogger(FUserController.class);

	@Autowired
	private FUserService fUserService;
	@Autowired
	private FRoleService fRoleService;
	@Autowired
	private FUserToRoleService fUserToRoleService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * 登录验证
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 查询结果对象
	 */
	@RequestMapping(value = "/check-isexist", method = RequestMethod.POST)
	@ResponseBody
	public final String checkIsExist(HttpServletRequest request, HttpServletResponse response, FUserModel fUserModel) {
		String result = "0";
		String accountName = fUserModel.getAccountName();
		if (StringUtils.isEmpty(accountName)) {
			result = "2";
			logger.error("AccountName is empty or null!");
		} else {
			if (fUserService.checkIsExist(accountName)) {
				result = "1";
			}
		}
		return result;
	}

	/**
	 * 账户唯一性校验
	 * 
	 * @param request
	 * @param accountName
	 * @return
	 */
	@RequestMapping(value = "/check-accountname", method = RequestMethod.POST)
	@ResponseBody
	public final String checkAccountName(HttpServletRequest request, String accountName) {
		String result = "0";
		if (StringUtils.isEmpty(accountName)) {
			result = "2";
			logger.error("AccountName is empty or null!");
		} else {
			if (fUserService.checkIsExist(accountName)) {
				result = "1";
			}
		}
		return result;
	}

	/**
	 * 合伙人唯一性校验
	 * 
	 * @param request
	 * @param accountName
	 * @return
	 */
	@RequestMapping(value = "/check-partner", method = RequestMethod.POST)
	@ResponseBody
	public final Integer checkPartner(HttpServletRequest request, Integer provinceid, Integer cityid, Integer areaid,Integer roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("provinceid", provinceid);
		map.put("cityid", cityid);
		map.put("areaid", areaid);
		map.put("roleId", roleId);
		Integer result = fUserService.checkPartner(map);
		return result;
	}

	/**
	 * 登录验证
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 查询结果对象
	 */
	@RequestMapping(value = "/deluser", method = RequestMethod.GET)
	@ResponseBody
	public final boolean delUser(HttpServletRequest request, HttpServletResponse response, Integer userId) {
		boolean isSuccess = true;
		try {
			fUserService.delete(userId);
		} catch (Exception e) {
			isSuccess = false;
			logger.error("Excepiton happened when delete user, excepiton message is" + e.getMessage());
		}

		return isSuccess;
	}

	/**
	 * 登录验证
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 查询结果对象
	 */
	@RequestMapping(value = "/queryuserpages", method = RequestMethod.GET)
	public final String queryUserPages(HttpServletRequest request, HttpServletResponse response, Integer userId) {
		return "platform/user";
	}

	/**
	 * 用户管理-用户列表
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 查询结果对象
	 */
	@RequestMapping(value = "/querybypages", method = RequestMethod.GET)
	@ResponseBody
	public final Page<FUserModel> queryByPages(final Pageable pageable, HttpServletRequest request, HttpServletResponse response, Integer userId) {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		List<Integer> areaListAll = new ArrayList<Integer>();
		List<Integer> userList = new ArrayList<Integer>();
		if (!user.getRoleId().equals(RoleType.ADMIN_ROLE_ID)) {
			if (user.getRoleId().equals(RoleType.PROVINCE_ROLE_ID)) { // 省合伙人
//				List<Integer> list = areaService.queryCityIdByProvinceId(user.getProvinceid()); // 获取所有的市id
//				List<Integer> areaList = areaService.queryAreaIdByCityId(list);
				areaListAll.add(user.getProvinceid());
//				areaListAll.addAll(list);
//				areaListAll.addAll(areaList);
			} else if (user.getRoleId().equals(RoleType.CITY_ROLE_ID)) {
//				List<Integer> cityList = new ArrayList<Integer>();
//				cityList.add(user.getCityid());
//				List<Integer> areaList = areaService.queryAreaIdByCityId(cityList);
				areaListAll.add(user.getCityid());
//				areaListAll.addAll(areaList);
			} else if (user.getRoleId().equals(RoleType.AREA_ROLE_ID)) {
				areaListAll.add(user.getAreaId());
			}
			if(areaListAll != null && areaListAll.size() > 0)
			userList = fUserService.queryUserIdByAreaId(areaListAll);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userList", userList);
		Page<FUserModel> pages = fUserService.findWithPagination(pageable, paramMap);
		return pages;
	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/adduser", method = RequestMethod.GET)
	public final String addUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ProvinceModel> provinces = AreaUtil.getProvinceModels();
		model.addAttribute("provinces", provinces);
		List<FRoleModel> roles = fRoleService.queryAll();
		model.addAttribute("roles", roles);
		return "platform/adduser";
	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public final String saveUser(HttpServletRequest request, HttpServletResponse response, FUserModel fUserModel, RedirectAttributes attr) {
		// TODO:更新人和修改人需要重session中获取
		String pwd = StringUtils.trimToNull(request.getParameter("password"));
		String confirm_pwd = StringUtils.trimToNull(request.getParameter("confirm_password"));
		if (null == pwd || null == confirm_pwd || !pwd.equals(confirm_pwd)) {
			attr.addFlashAttribute(StorageStatus.REQ_TIP.name(), "密码输入错误！");
			return "redirect:/farm/user/adduser";
		}

		try {
			FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
			fUserModel.setCreatedTime(new Date());
			fUserModel.setUpdatedTime(new Date());
			fUserModel.setIsActive(0);
			fUserModel.setUpdatedUser(user);
			fUserModel.setCreatedUser(user);
			String encodePassword = Pbkdf2Encryption.encode(confirm_pwd);
			fUserModel.setPassword(encodePassword);

			fUserService.saveFUserModel(fUserModel);
			// 把用户对应角色存储下来
			String roleId = request.getParameter("roleId");
			FRoleModel roleModel = new FRoleModel();
			roleModel.setId(Integer.parseInt(roleId));
			FUserToRoleModel fUserToRoleModel = new FUserToRoleModel();
			fUserToRoleModel.setRole(roleModel);
			fUserToRoleModel.setUser(fUserModel);
			fUserToRoleService.saveObject(fUserToRoleModel);

		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (InvalidKeySpecException e) {
			logger.error(e.getMessage());
		}
		return "platform/user";
	}

	/**
	 * 查看用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewuser", method = RequestMethod.GET)
	public final String viewUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		String userId = request.getParameter("id");
		refreshModel(model, userId);
		// 把该用户对应的角色查出来
		FUserToRoleModel fUserToRoleModel = fUserToRoleService.getUserToRoleById(Integer.parseInt(userId));
		model.addAttribute("roleName", fUserToRoleModel.getRoleName());
		model.addAttribute("roleId", fUserToRoleModel.getRoleId());
		return "platform/viewyuser";
	}

	/**
	 * 修改用户
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 查询结果对象
	 */
	@RequestMapping(value = "/modifyuser", method = RequestMethod.GET)
	public final String modfifyUser(HttpServletRequest request, HttpServletResponse response, FUserModel fUserModel, Model model) {
		String userId = request.getParameter("id");
		refreshModel(model, userId);
		List<FRoleModel> roles = fRoleService.queryAll();
		model.addAttribute("roles", roles);
		FUserToRoleModel fUserToRoleModel = fUserToRoleService.getUserToRoleById(Integer.parseInt(userId));
		model.addAttribute("roleId", fUserToRoleModel.getRoleId());
		return "platform/modifyuser";
	}

	/**
	 * 修改用户
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 查询结果对象
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public final String updateUser(HttpServletRequest request, HttpServletResponse response, FUserModel fUserModel) {
		try {
			if (null != fUserModel) {
				FUserModel temp = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
				fUserModel.setUpdatedUser(temp);
				fUserModel.setUpdatedTime(new Date());
				if (!StringUtils.isEmpty(fUserModel.getPassword())) {
					fUserModel.setPassword(Pbkdf2Encryption.encode(fUserModel.getPassword()));
				}
				fUserService.updateFUserModelInfo(fUserModel);
				// 修改用户对应的角色
				// 把用户对应角色存储下来
				String roleId = request.getParameter("roleId");
				FRoleModel roleModel = new FRoleModel();
				roleModel.setId(Integer.parseInt(roleId));
				FUserToRoleModel fUserToRoleModel = new FUserToRoleModel();
				fUserToRoleModel.setRole(roleModel);
				fUserToRoleModel.setUser(fUserModel);
				fUserToRoleService.updateObject(fUserToRoleModel);
			}
		} catch (Exception e) {
			logger.error("Excepiton happened when update user, excepiton message is" + e.getMessage());
		}

		return "platform/user";
	}

	private void refreshModel(Model model, String userId) {
		Map<String, Object> paramMap = fUserService.queryParamsByUserId(Integer.valueOf(userId));

		if (MapUtils.isNotEmpty(paramMap)) {
			model.addAttribute("provinces", paramMap.get("provinces"));
			model.addAttribute("cityModels", paramMap.get("cityModels"));
			model.addAttribute("fUserModel", paramMap.get("fUserModel"));
			model.addAttribute("areaModels", paramMap.get("areaModels"));
			model.addAttribute("selectedProvinceCode", paramMap.get("selectedProvinceCode"));
			model.addAttribute("selectedProvinceName", paramMap.get("selectedProvinceName"));
			model.addAttribute("selectedCityName", paramMap.get("selectedCityName"));
			model.addAttribute("selectAreadName", paramMap.get("selectAreadName"));

		}
	}
}
