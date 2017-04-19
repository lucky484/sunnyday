package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.AndroidDeviceUsers;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.model.ExcelInsertUserModel;
import com.softtek.mdm.model.IosDevicePolicy;
import com.softtek.mdm.model.IosPolicyUser;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.RoleModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserExportModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.model.UserVirtualGroupModel;
import com.softtek.mdm.model.VirtualCollectionModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.queue.MqttQueue;
import com.softtek.mdm.service.DeviceAppInfoService;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceLegalListService;
import com.softtek.mdm.service.DeviceLegalRecordService;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.DeviceNetworkStatusService;
import com.softtek.mdm.service.DeviceSaftyService;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.ParamSettingService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.RoleService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.service.UserVirtualGroupService;
import com.softtek.mdm.service.VirtualCollectionService;
import com.softtek.mdm.service.VirtualGroupService;
import com.softtek.mdm.status.AuthStatus;
import com.softtek.mdm.status.DeviceTypeStatus;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.thread.MqttPushThread;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.ExportData;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;

import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户管理
 * 
 * @author color.wu
 *
 */
@Controller
@RequestMapping(value = "/institution/user")
public class UserController extends BaseController {

	@Autowired
	private StructureService structureService;
	@Autowired
	private TreeManager treeManager;
	@Autowired
	private UserService userService;
	@Autowired
	private VirtualCollectionService virtualCollectionService;
	@Autowired
	private VirtualGroupService virtualGroupService;
	@Autowired
	private UserVirtualGroupService userVirtualGroupService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private DeviceAppInfoService deviceAppInfoService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceNetworkStatusService deviceNetworkStatusService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private DeviceLegalListService deviceLegalListService;
	@Autowired
	private DeviceSaftyService deviceSaftyService;
	@Autowired
	private DeviceLegalRecordService deviceLegalRecordService;
	@Autowired
	private SysmanageDeviceLogService sysmanageDeviceLogService;
	@Autowired
	private DeviceManagerService deviceManagerService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private ParamSettingService paramSettingService;
	@Autowired
	@Qualifier("iosDeviceServiceNotifyService")
	private AbstractIosPush abstractIosPush;
	
    /**
     * logger日志
     */
	private Logger logger = Logger.getLogger(UserController.class);

	/**
     * 用户管理首页
     * 
     * @param request
     * @param response
     * @param session
     * @param model
     * @return
     * @throws IOException
     */
	@Link(family = "institution", label = "web.institution.usercontroller.index.link.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.usercontroller.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public String index(RedirectAttributes attrs, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		@SuppressWarnings("unchecked")
		List<StructureModel> list = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		
		NodeModel nodes = treeManager.bulidTreeContainUser(list, organization.getId(), manager.getUser());
		String jsonStr = "[" + JSONObject.fromObject(nodes).toString() + "]";
		jsonStr = StringUtil.replace(jsonStr, "\"nodes\":[],", "");
		model.addAttribute("tree", jsonStr);
		
		/*List<StructureModel> treeNodes=structureService.getChildren(null, organization.getId());
		model.addAttribute("treeNodes", treeNodes);*/
		
		List<VirtualCollectionModel> vtlcols = (List<VirtualCollectionModel>) virtualCollectionService
				.findAllByOrgID(organization.getId());
		if (vtlcols != null) {
			Integer[] ids = new Integer[vtlcols.size()];
			for (int i = 0; i < vtlcols.size(); i++) {
				ids[i] = vtlcols.get(i).getId();
			}
			List<VirtualGroupModel> vtls = (List<VirtualGroupModel>) virtualGroupService.findByCids(ids);
			model.addAttribute("vtlcols", vtlcols);
			model.addAttribute("vtls", vtls);
		} else {
			model.addAttribute("vtlcols", "");
			model.addAttribute("vtls", "");
		}

		String treeId = "";
		if (list.size() > 0) {
			for (StructureModel ste : list) {
				if (ste.getParent() == null) {
					treeId = ste.getId().toString();
					break;
				}
			}
		}
		model.addAttribute("treeId", treeId);
		return "institution/user/index";
	}

	/**
     * 新增用户的个人信息
     * 
     * @param uid
     * @return
     * @throws IOException
     */
	@Log(operateType = "web.institution.usercontroller.newly.log.operatetype", operateContent = "web.institution.usercontroller.newly.log.operatecontent", args = {
			"u_name" })
	@RequestMapping(value = "/newly", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> newly(Integer uid, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		Map<String, String> userInfo = getUserInfoMap(uid, session);
		request.setAttribute("u_name", userInfo.get("realname"));
		return userInfo;
	}

	/**
     * 查看用户的个人信息
     * 
     * @param uid
     * @param request
     * @param response
     * @param session
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> userinfo(Integer did, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		DeviceBasicInfoModel basicInfo = deviceBasicInfoService.findOne(did);
		return getUserInfoMap(basicInfo.getUser().getId(), session);
	}

	/**
     * 获取用户基本信息的map
     * 
     * @param uid
     * @param session
     * @return
     * @throws IOException
     */
	private Map<String, String> getUserInfoMap(Integer uid, HttpSession session) throws IOException {
		UserModel user = null;
		try {
			user = userService.findOne(uid);
		} catch (Exception e) {
			logger.error("Method findOne invoked by userSerive cause error and the reason is: " + e.getMessage());
			throw e;
		}
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("username", user.getUsername());
		maps.put("realname", user.getRealname());
		StructureModel temp = null;
		try {
			temp = structureService.getParents(user.getStructure().getId());
		} catch (Exception e) {
			logger.error(
					"Method getParents invoked by structureService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		if (temp != null) {
			String belongStr = temp.getName();
			while (temp.getParent() != null) {
				belongStr = StringUtil.insert(belongStr, temp.getParent().getName() + "/");
				temp = temp.getParent();
			}
			maps.put("department", belongStr);
		}
		maps.put("phone", user.getPhone());
		maps.put("email", user.getEmail());
		DateTime dt = new DateTime(user.getCreateTime().getTime());
		maps.put("createtime", dt.toString("yyyy/MM/dd HH:mm:ss"));
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		List<VirtualGroupModel> vtllist = (List<VirtualGroupModel>) userVirtualGroupService
				.findVtlGroupsByUser(organization.getId(), user.getId());
		String belongVtls = "";
		if (vtllist != null) {
			for (VirtualGroupModel vtl : vtllist) {
				belongVtls = StringUtil.insert(belongVtls, vtl.getName() + "/");
			}
			maps.put("vtl", StringUtil.truncate(belongVtls, belongVtls.length() - 1));
		} else {
			maps.put("vtl", "");
		}
		maps.put("mark", user.getMark());
		maps.put("weight", user.getWeight() == null ? "0" : user.getWeight().toString());
		maps.put("gender", user.getGender() == null ? "0" : user.getGender().toString());
		maps.put("sign", user.getSign());
		maps.put("address", user.getAddress());
		maps.put("office", user.getOffice_phone());
		maps.put("position", user.getPosition());
		return maps;
	}

	/**
     * 显示修改个人信息
     * 
     * @param uid
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> modify(Integer uid, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		UserModel user = null;
		try {
			user = userService.findOne(uid);
		} catch (Exception e) {
			logger.error(
					"Method getParents invoked by structureService cause error and the reason is: " + e.getMessage());
			throw e;
		}

		Map<String, String> maps = new HashMap<String, String>();
		maps.put("id", user.getId().toString());
		maps.put("username", user.getUsername());
		maps.put("realname", user.getRealname());
		maps.put("gender", user.getGender().toString());

		maps.put("departmentid", user.getStructure().getId().toString());
		maps.put("phone", user.getPhone());
		maps.put("email", user.getEmail());
		maps.put("mark", user.getMark());
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		List<VirtualCollectionModel> vtlcols = null;
		try {
			vtlcols = (List<VirtualCollectionModel>) virtualCollectionService.findAllByOrgID(organization.getId());
		} catch (Exception e) {
			logger.error("Method findAllByOrgID invoked by virtualCollectionService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}

		Integer[] ids = new Integer[vtlcols.size()];
		for (int i = 0; i < vtlcols.size(); i++) {
			ids[i] = vtlcols.get(i).getId();
		}
		List<VirtualGroupModel> vtls = null;
		try {
			vtls = (List<VirtualGroupModel>) virtualGroupService.findByCids(ids);
		} catch (Exception e) {
			logger.error("Method findByCids invoked by virtualGroupService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
		List<VirtualGroupModel> vtllist = null;
		try {
			vtllist = (List<VirtualGroupModel>) userVirtualGroupService.findVtlGroupsByUser(organization.getId(),
					user.getId());
		} catch (Exception e) {
			logger.error("Method findVtlGroupsByUser invoked by userVirtualGroupService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}

		if (vtllist != null) {
			for (int i = 0; i < vtls.size(); i++) {
				for (int j = 0; j < vtllist.size(); j++) {
					if (vtls.get(i).getId().equals(vtllist.get(j).getId())) {
						vtls.get(i).setWeight(-1);
					}
				}
			}
		}

		maps.put("vtlcols", JSONArray.fromObject(vtlcols).toString());
		maps.put("vtls", JSONArray.fromObject(vtls).toString());
		maps.put("weight", user.getWeight() == null ? "0" : user.getWeight().toString());
		maps.put("gender", user.getGender() == null ? "0" : user.getGender().toString());
		maps.put("sign", user.getSign());
		maps.put("address", user.getAddress());
		maps.put("office", user.getOffice_phone());
		maps.put("position", user.getPosition());
		return maps;
	}

	/**
     * 新增用户
     * 
     * @param user
     * @param model
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@Log(operateType = "web.institution.usercontroller.create.log.operatetype", operateContent = "web.institution.usercontroller.create.log.operatecontent", args = {
			"username" })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody JsonResult create(UserModel user, PolicyModel policy, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		UserModel checkUser = null;
		try {
			checkUser = userService.findUser(user.getUsername(), organization.getId());
		} catch (Exception e) {
			logger.error("Method findUser invoked by userService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		if (checkUser != null) {
			return createJsonResult(MessageType.danger, "web.institution.usercontroller.create.account.exists", null,
					null);
		}

		user.setType(Integer.parseInt(AuthStatus.SOFTTEK_CUSTOMER.toString()));
		user.setOrganization(organization);
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		user.setPassword(md5PasswordEncoder.encodePassword(user.getPassword(), null));
		StructureModel structure = structureService.findOne(user.getStructure().getId());
		if (structure != null) {
			if (structure.getPolicy() != null)
				policy.setId(structure.getPolicy().getId());
		}

		user.setPolicy(policy);
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		user.setCreateBy(manager.getId());
		user.setUpdateBy(manager.getId());
		map.put("user", user);
		map.put("vtls", StringUtils.trimToEmpty(request.getParameter("vtls")));
		try {
			userService.create(map);
		} catch (Exception e) {
			logger.error("Method create invoked by userService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		JsonResult jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.create.success",
				null, null);
		return jsonResult;
	}

	/**
     * 修改用户信息
     * 
     * @param user
     * @param userVirtualGroup
     * @param model
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@Log(operateType = "web.institution.usercontroller.update.log.operatetype", operateContent = "web.institution.usercontroller.update.log.operatecontent", args = {
			"u_name" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody JsonResult update(UserModel user, VirtualGroupModel virtualGroup,
			UserVirtualGroupModel userVirtualGroup, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		Map<String, Object> maps = new HashMap<String, Object>();
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		maps.put("organization", organization);
		UserModel usr = null;
		try {
			usr = userService.findOne(user.getId());
		} catch (Exception e) {
			logger.error("Method findOne invoked by userService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		if (usr != null) {
			if (!StringUtil.isBlank(user.getPassword())) {
				usr.setPassword(user.getPassword());
			} else {
				usr.setPassword(null);
			}
			if (!StringUtil.isBlank(user.getRealname().trim())) {
				usr.setRealname(user.getRealname().trim());
			}
			if (user.getStructure() != null && !StringUtil.isBlank(user.getStructure().getId().toString())) {
				usr.setStructure(user.getStructure());
			}
			if (!StringUtil.isBlank(user.getPhone().trim())) {
				usr.setPhone(user.getPhone().trim());
			}
			if (!StringUtil.isBlank(user.getEmail().trim())) {
				usr.setEmail(user.getEmail().trim());
			}
			if (!StringUtil.isBlank(user.getMark().trim())) {
				usr.setMark(user.getMark().trim());
			}
			StructureModel structure = null;
			try {
				structure = structureService.findOne(user.getStructure().getId());
			} catch (Exception e) {
				logger.error(
						"Method findOne invoked by structureService cause error and the reason is: " + e.getMessage());
				throw e;
			}
			if (structure != null) {
				if (structure.getPolicy() != null)
					usr.setPolicy(structure.getPolicy());
			}
			if (!StringUtil.isBlank(user.getGender().toString())) {
				usr.setGender(user.getGender());
			}
			if (!StringUtil.isBlank(user.getSign().trim())) {
				usr.setSign(user.getSign().trim());
			}
			if (!StringUtil.isBlank(user.getAddress().trim())) {
				usr.setAddress(user.getAddress().trim());
			}
			if (!StringUtil.isBlank(user.getOffice_phone().trim())) {
				usr.setOffice_phone(user.getOffice_phone().trim());
			}
			if (!StringUtil.isBlank(user.getPosition().trim())) {
				usr.setPosition(user.getPosition().trim());
			}
			ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			usr.setUpdateBy(manager.getId());

			maps.put("user", usr);
			maps.put("vtls", StringUtils.trimToEmpty(request.getParameter("vtlids")));
			try {
				userService.update(maps);
			} catch (Exception e) {
				logger.error("Method update invoked by userService cause error and the reason is: " + e.getMessage());
				throw e;
			}
			request.setAttribute("u_name", usr.getRealname());

			if (usr.getPolicy() != null) {
				PolicyModel policy = policyService.findOne(usr.getPolicy().getId());
				Map<String, String> map = new HashMap<String, String>();
				map.put("policy", JSONObject.fromObject(policy).toString());
                // topic采用用户id
				MqProducerThread mqProducerThread = new MqProducerThread(usr.getId().toString(), null, null, 2, map);
				taskExecutor.execute(mqProducerThread);
			}
		}
		JsonResult jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.update.success",
				null, null);
		return jsonResult;
	}

	/**
     * 批量激活用户
     * 
     * @param uid
     * @param request
     * @param response
     * @return
     */
	@Log(operateType = "web.institution.usercontroller.activebatch.log.operatetype", operateContent = "web.institution.usercontroller.activebatch.log.operatecontent", args = {
			"userStr" })
	@RequestMapping(value = "/activebatch", method = RequestMethod.POST)
	public @ResponseBody JsonResult activeBatch(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String uids = StringUtils.trimToEmpty(request.getParameter("param[uids]"));
		JsonResult jsonResult = null;
		String isall = StringUtils.trimToEmpty(request.getParameter("param[isall]"));
		if ("2".equals(isall)) {
			String searchUserName = StringUtils.trimToEmpty(request.getParameter("param[searchusername]"));
			String searchAccountName = StringUtils.trimToEmpty(request.getParameter("param[searchaccountname]"));
			String searchActiveStatus = StringUtils.trimToEmpty(request.getParameter("param[searchactivestatus]"));
			String structureId = StringUtils.trimToEmpty(request.getParameter("param[treeid]"));
			OrganizationModel organization = (OrganizationModel) session
					.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("searchUserName", searchUserName);
			paramMap.put("searchAccountName", searchAccountName);
			paramMap.put("searchActiveStatus", searchActiveStatus);
			paramMap.put("orgId", organization == null ? "" : organization.getId());
			paramMap.put("structureId", Integer.valueOf(structureId));
			@SuppressWarnings("unchecked")
			List<StructureModel> list = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			int issuccess = 0;
			try {
				issuccess = userService.activeAllByMap(list, paramMap);
			} catch (Exception e) {
				logger.error("Method activeAllByMap invoked by userService cause error and the reason is: "
						+ e.getMessage());
				throw e;
			}
			if (issuccess > 0) {
				jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.activebatch.success",
						null, null);
			} else {
				if (issuccess == -1) {
					jsonResult = createJsonResult(MessageType.danger,
							"web.institution.usercontroller.active.license.invalide", null, null);
				} else {
					jsonResult = createJsonResult(MessageType.success,
							"web.institution.usercontroller.activebatch.failed", null, null);
				}
			}
			return jsonResult;
		}

		String[] idsStr = StringUtil.split(uids, ",");
		Integer[] ids = new Integer[idsStr.length];
		StringBuffer userStr = new StringBuffer();
		int activeCount = 0;
		for (int i = 0; i < ids.length; i++) {
			Integer tempId = Integer.parseInt(idsStr[i]);
			ids[i] = tempId;
			UserModel u = userService.findOne(tempId);
			if (u.getIs_active() == 0) {
				activeCount++;
				userStr.append(u.getRealname() + " ");
			}
		}

		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		if (licenseService.isActiveNumBeyondOrgLimit(activeCount, organization.getId())) {
			return createJsonResult(MessageType.danger, "web.institution.usercontroller.active.license.invalide", null,
					null);
		}
		int flag = 0;
		try {
			flag = userService.updateActiveBatch(ids);
		} catch (Exception e) {
			logger.error(
					"Method updateActiveBatch invoked by userService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		if (flag > 0) {
			request.setAttribute("userStr", userStr);
			jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.activebatch.success",
					null, null);
		} else {
			jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.activebatch.failed",
					null, null);
		}
		return jsonResult;

	}

	/**
     * 激活用户或者取消激活
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@Log(operateType = "web.institution.usercontroller.active.log.operatetype", operateContent = "web.institution.usercontroller.active.log.operatecontent", args = {
			"strMsg" })
	@RequestMapping(value = "/activetoggle", method = RequestMethod.POST)
	public @ResponseBody JsonResult active(UserModel user, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JsonResult jsonResult = null;
		if (!StringUtil.isBlank(user.getId().toString())) {
			try {
				user = userService.findOne(user.getId());
			} catch (Exception e) {
				logger.error("Method findOne invoked by userService cause error and the reason is: " + e.getMessage());
				throw e;
			}
			if (user.getIs_active() == 0) {
                // 激活用户的时候需要判断当前机构下的license数目
				List<Integer> orgIds = new ArrayList<Integer>();
				orgIds.add(user.getOrganization().getId());
				try {
					if (licenseService.checkLicense(orgIds) == 0) {
						user.setIs_active(1);
					} else {
						return createJsonResult(MessageType.danger,
								"web.institution.usercontroller.active.license.invalide", null, null);
					}
				} catch (Exception e) {
					logger.error("Method checkLicense invoked by licenseService cause error and the reason is: "
							+ e.getMessage());
					throw e;
				}
			} else {
				user.setIs_active(0);
			}
			int flag = 0;
			try {
				flag = userService.update(user);
			} catch (Exception e) {
				logger.error("Method update invoked by userService cause error and the reason is: " + e.getMessage());
				throw e;
			}
			if (flag > 0) {
				if (user.getIs_active() == 1) {
					Object[] objects = { user.getRealname() };
					String strMsg = messageSource.getMessage("web.institution.usercontroller.active.log.success",
							objects, LocaleContextHolder.getLocale());
					request.setAttribute("strMsg", strMsg);
					jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.active.success",
							null, null);
				} else {
					Map<String, String> map = new HashMap<String, String>();
					map.put("userId", user.getId().toString());
                    // 推送给用户，未激活
					MqProducerThread mqProducerThread = new MqProducerThread(user.getId().toString(), null, null, 2,
							map);
					taskExecutor.execute(mqProducerThread);

					Object[] objects = { user.getRealname() };
					String strMsg = messageSource.getMessage("web.institution.usercontroller.active.log.failed",
							objects, LocaleContextHolder.getLocale());
					request.setAttribute("strMsg", strMsg);
					jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.active.failed",
							null, null);
				}
			} else {
				jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.active.failed", null,
						null);
			}
			return jsonResult;
		} else {
			jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.active.failed", null,
					null);
			return jsonResult;
		}
	}

	/**
     * 删除用户
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@Log(operateType = "web.institution.usercontroller.delete.log.operatetype", operateContent = "web.institution.usercontroller.delete.log.operatecontent", args = {
			"u_name" })
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody JsonResult delete(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		JsonResult jsonResult = null;
		String pkStr = request.getParameter("uid");
		UserModel user = null;
		try {
			user = userService.findOne(Integer.parseInt(pkStr));
		} catch (Exception e) {
			logger.error("Method findOne invoked by userService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		request.setAttribute("u_name", user.getRealname());
		if (!StringUtil.isBlank(pkStr)) {
			OrganizationModel organization = (OrganizationModel) session
					.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			int flag = 0;
			try {
				flag = userService.deleteUserRelationInfo(Integer.parseInt(pkStr.trim()), organization.getId());
			} catch (Exception e) {
				logger.error("Method deleteUserRelationInfo invoked by userService cause error and the reason is: "
						+ e.getMessage());
				throw e;
			}
			if (flag > 0) {
				jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.delete.success",
						null, null);
				Map<String, String> map = new HashMap<String, String>();
				map.put("userClear", "1");
                // 推送清除用户
				MqProducerThread mqProducerThread = new MqProducerThread(pkStr.trim(), null, null, 2, map);
				taskExecutor.execute(mqProducerThread);
			} else {
				jsonResult = createJsonResult(MessageType.danger, "web.institution.usercontroller.delete.failed", null,
						null);
			}
			return jsonResult;
		} else {
			jsonResult = createJsonResult(MessageType.danger, "web.institution.usercontroller.delete.failed", null,
					null);
			return jsonResult;
		}

	}

	/**
     * 批量删除用户
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@Log(operateType = "web.institution.usercontroller.deleteusers.log.operatetype", operateContent = "web.institution.usercontroller.deleteusers.log.operatecontent", args = {
			"u_names" })
	@RequestMapping(value = "/deletebatch", method = RequestMethod.POST)
	public @ResponseBody JsonResult deleteUsers(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		JsonResult jsonResult = null;
		String pkStrs = StringUtils.trimToEmpty(request.getParameter("param[uids]"));
		StringBuffer unames = new StringBuffer();
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		if (!StringUtil.isBlank(pkStrs)) {
			String isall = request.getParameter("param[isall]");
			if ("2".equals(isall)) {
				Map<String, Object> paramMap = new HashMap<String, Object>();

				String searchUserName = StringUtils.trimToEmpty(request.getParameter("param[searchusername]"));
				String searchAccountName = StringUtils.trimToEmpty(request.getParameter("param[searchaccountname]"));
				String searchActiveStatus = StringUtils.trimToEmpty(request.getParameter("param[searchactivestatus]"));
				String structureId = StringUtils.trimToEmpty(request.getParameter("param[treeid]"));
				paramMap.put("searchUserName", searchUserName);
				paramMap.put("searchAccountName", searchAccountName);
				paramMap.put("searchActiveStatus", searchActiveStatus);
				paramMap.put("orgId", organization == null ? "" : organization.getId());
				paramMap.put("structureId", Integer.valueOf(structureId));
				@SuppressWarnings("unchecked")
				List<StructureModel> list = (List<StructureModel>) session
						.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
				int issuccess = 0;
				try {
					issuccess = userService.deleteAllByMap(list, paramMap);
				} catch (Exception e) {
					logger.error("Method deleteAllByMap invoked by userService cause error and the reason is: "
							+ e.getMessage());
					throw e;
				}
				if (issuccess > 0) {
                    // 向被删除的用户推送注销设备操作
                    // 按照部门查询下面的用户集合
					List<Integer> ids = userService.findIdsBy(list, Integer.valueOf(structureId));
					if (CollectionUtils.isNotEmpty(ids)) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("userClear", "1");
						List<KeyValue> data=MqttQueue.generateDatas(ids, map);
						MqttPushThread mqttPush=new MqttPushThread(data);
						taskExecutor.execute(mqttPush);
					}
					jsonResult = createJsonResult(MessageType.success,
							"web.institution.usercontroller.deleteusers.success", null, null);
				} else {
					jsonResult = createJsonResult(MessageType.danger,
							"web.institution.usercontroller.deleteusers.failed", null, null);
				}
				return jsonResult;
			}
			String[] pk = StringUtil.split(pkStrs, ",");
			List<Integer> ids = new ArrayList<Integer>();
			for (String str : pk) {
				ids.add(Integer.parseInt(str));
				UserModel temp = userService.findOne(Integer.parseInt(str));
				if (StringUtils.isNotBlank(temp.getRealname())) {
					unames.append(temp.getRealname() + ",");
				}
			}
			int flag = userService.deleteUserBatch(ids, organization.getId());

			if (flag > 0) {
				request.setAttribute("u_names", unames);
				if (CollectionUtils.isNotEmpty(ids)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("userClear", "1");
					List<KeyValue> data=MqttQueue.generateDatas(ids, map);
					MqttPushThread mqttPush=new MqttPushThread(data);
					taskExecutor.execute(mqttPush);
					
				}
				jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.deleteusers.success",
						null, null);
			} else {
				jsonResult = createJsonResult(MessageType.danger, "web.institution.usercontroller.deleteusers.failed",
						null, null);
			}
			return jsonResult;
		} else {
			jsonResult = createJsonResult(MessageType.danger, "web.institution.usercontroller.deleteusers.failed", null,
					null);
			return jsonResult;
		}
	}

	/**
     * 锁定或解锁
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@Log(operateType = "web.institution.usercontroller.lock.log.operatetype", operateContent = "web.institution.usercontroller.lock.log.operatecontent", args = {
			"strMsg" })
	@RequestMapping(value = "/locktoggle", method = RequestMethod.POST)
	public @ResponseBody JsonResult lock(UserModel user, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JsonResult jsonResult = null;

		if (!StringUtil.isBlank(user.getId().toString())) {
			try {
				user = userService.findOne(user.getId());
			} catch (Exception e) {
				logger.error("Method findOne invoked by userService cause error and the reason is: " + e.getMessage());
				throw e;
			}
			if (user.getIs_lock() == 0) {
				user.setIs_lock(1);
			} else {
				user.setIs_lock(0);
			}
			int flag = 0;
			try {
				flag = userService.update(user);
			} catch (Exception e) {
				logger.error("Method update invoked by userService cause error and the reason is: " + e.getMessage());
				throw e;
			}
			if (flag > 0) {
				if (user.getIs_lock() != 0) {
					Object[] objects = { user.getRealname() };
					String strMsg = messageSource.getMessage("web.institution.usercontroller.lock.log.success", objects,
							LocaleContextHolder.getLocale());
					request.setAttribute("strMsg", strMsg);
					Map<String, String> map = new HashMap<String, String>();
					map.put("userId", user.getId().toString());
                    // 推送给用户 通知锁定
                    // 批量删除用户，发送推送信息
					MqProducerThread mqProducerThread = new MqProducerThread(user.getId().toString(), null, null, 2,
							map);
					taskExecutor.execute(mqProducerThread);
					jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.lock.success",
							null, null);
				} else {
					Object[] objects = { user.getRealname() };
					String strMsg = messageSource.getMessage("web.institution.usercontroller.lock.log.failed", objects,
							LocaleContextHolder.getLocale());
					request.setAttribute("strMsg", strMsg);
					jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.lock.failed",
							null, null);
				}
			} else {
				jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.lock.failed", null,
						null);
			}
			return jsonResult;
		} else {
			jsonResult = createJsonResult(MessageType.danger, "web.institution.usercontroller.lock.error", null, null);
			return jsonResult;
		}
	}

	/**
     * ajax查询分页
     * 
     * @param request
     * @param response
     * @param start
     * @param length
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	public @ResponseBody Page userInfos(String id, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Integer start, Integer length) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		@SuppressWarnings("unchecked")
		List<StructureModel> list = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());

		/** modify by brave start */
		Map<String, Object> paramMap = new HashMap<String, Object>();

		String searchUserName = StringUtils.trimToEmpty(request.getParameter("searchusername"));
		String searchAccountName = StringUtils.trimToEmpty(request.getParameter("searchaccountname"));
		String searchActiveStatus = StringUtils.trimToEmpty(request.getParameter("searchactivestatus"));

		paramMap.put("searchUserName", searchUserName);
		paramMap.put("searchAccountName", searchAccountName);
		paramMap.put("searchActiveStatus", searchActiveStatus);
		paramMap.put("orgId", organization == null ? "" : organization.getId());
		paramMap.put("structureId", Integer.valueOf(id));
		paramMap.put("pageNum", start == null ? 0 : start);
		paramMap.put("pageSize", length == null ? 10 : length);
		paramMap.put("orgId", organization.getId());
		paramMap.put("user", manager.getUser());
		Page page = null;
		try {
			page = userService.findUserBysExpression(list, paramMap);
		} catch (Exception e) {
			logger.error("Method findUserBysExpression invoked by userService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
		return page;
	}

	/**
     * 用户策略
     * 
     * @param id
     * @param session
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value = "/strategy", method = RequestMethod.GET)
	public @ResponseBody List<PolicyModel> getPolicy(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<PolicyModel> list = new ArrayList<>();
		if (manager.getUser() != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mid", manager.getId());
			map.put("orgId", organization.getId());
			try {
				list = policyService.findAllByMap(map);
			} catch (Exception e) {
				logger.error("Method findAllByMap invoked by policyService cause error and the reason is: "
						+ e.getMessage());
				throw e;
			}
		} else {
			try {
				list = policyService.findAllByOrgId(organization.getId());
			} catch (Exception e) {
				logger.error("Method findAllByOrgId invoked by policyService cause error and the reason is: "
						+ e.getMessage());
				throw e;
			}
		}
		return list;
	}

	/**
     * 更改用户策略
     * 
     * @param pid
     * @param session
     * @param request
     * @param response
     */
	@Log(operateType = "web.institution.usercontroller.updatepolicy.log.operatetype", operateContent = "web.institution.usercontroller.updatepolicy.log.operatecontent", args = {
			"u_name", "u_policy" })
	@RequestMapping(value = "/updateStrategy", method = RequestMethod.POST)
	public @ResponseBody JsonResult updatePolicy(Integer uid, Integer pid, HttpSession session,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		UserModel user = userService.findOne(uid);
		user.getPolicy().setId(pid);
		try {
			userService.update(user);
		} catch (Exception e) {
			logger.error("Method update invoked by userService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		PolicyModel policy = policyService.findOne(pid);

		List<DeviceManagerModel> list = deviceManagerService.queryAllDeviceById(uid);
		List<String> deviceToken = new ArrayList<String>();
		request.setAttribute("u_name", user.getRealname());
		request.setAttribute("u_policy", policy.getName());
		Map<String, String> map = new HashMap<String, String>();
		map.put("policy", JSONObject.fromObject(policy).toString());
        // 将用户策略推送给用户
        // 批量删除用户，发送推送信息
		if (CollectionUtils.isNotEmpty(list)) {
			List<KeyValue> kvs=new ArrayList<KeyValue>();
			for (DeviceManagerModel deviceManager : list) {
				if (deviceManager.getDevice_type() != null) {
					if ("android".equals(deviceManager.getDevice_type())) {
						kvs.add(MqttQueue.generateKeyValue(String.valueOf(uid), map));
					} else if (deviceManager.getDevice_type().equals("ios")) {
						deviceToken.add(deviceManager.getDeviceToken().replaceAll(" ", ""));
					}
				}
			}
			MqttPushThread mqttPush=new MqttPushThread(kvs);
			taskExecutor.execute(mqttPush);
		}
		if (CollectionUtils.isNotEmpty(deviceToken)) {
            IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map); // 多个ios设备放到队列里面推送
		}
		JsonResult jsonResult = createJsonResult(MessageType.success,
				"web.institution.usercontroller.updatepolicy.success", null, null);
		return jsonResult;
	}

	/**
     * 提升部门管理员
     * 
     * @param pid
     * @param session
     * @param request
     * @param response
     */
	@Log(operateType = "web.institution.usercontroller.promotion.log.operatetype", operateContent = "web.institution.usercontroller.promotion.log.operatecontent", args = {
			"u_name" })
	@RequestMapping(value = "/promotion", method = RequestMethod.POST)
	public @ResponseBody JsonResult promotion(UserRoleDepartmentModel userRoleDepartment, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uid = StringUtils.trimToEmpty(request.getParameter("uid"));
		JsonResult jsonResult = null;
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		UserModel user = null;
		try {
			user = userService
					.findOne(StringUtil.isBlank(uid) ? userRoleDepartment.getUser().getId() : Integer.parseInt(uid));
		} catch (Exception e) {
			logger.error("Method findOne invoked by userService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		if (user != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			request.setAttribute("u_name", user.getRealname());
			if (StringUtil.isBlank(uid)) {
				userRoleDepartment.setOrganization(organization);
				map.put("userRoleDepartment", userRoleDepartment);

				String departmentIds = request.getParameter("departmentIds");
				String[] ids = StringUtil.split(departmentIds, ",");
				List<UserDepartmentModel> userDepartmentList = new ArrayList<UserDepartmentModel>();
				for (String str : ids) {
					UserDepartmentModel userDepartment = new UserDepartmentModel();
					userDepartment.setOrganization(organization);
					StructureModel structure = new StructureModel();
					structure.setId(Integer.parseInt(str));
					userDepartment.setStructure(structure);
					userDepartmentList.add(userDepartment);
				}
				map.put("userDepartmentList", userDepartmentList);

				ManagerModel manager = new ManagerModel();
				manager.setUser(user);
				manager.setUser_type(Integer.parseInt(AuthStatus.SOFTTEK_INSTITUTION.toString()));
				manager.setUsername(user.getUsername());
				manager.setPassword(user.getPassword());
				manager.setOrganization(organization);
				manager.setEmail(user.getEmail());
				manager.setName(user.getRealname());
				manager.setPhone(user.getPhone());
				manager.setLogin_count(0);
				manager.setAuth(StringUtils.trimToNull(request.getParameter("auth")) == null ? 1
						: Integer.parseInt(request.getParameter("auth")));
				ManagerModel sessionManager = (ManagerModel) session
						.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
				manager.setCreateBy(sessionManager.getId());
				manager.setUpdateBy(sessionManager.getId());
				map.put("manager", manager);
				try {
					userService.promotion(map);
				} catch (Exception e) {
					logger.error(
							"Method promotion invoked by userService cause error and the reason is: " + e.getMessage());
					throw e;
				}
				jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.promotion.success",
						null, null);
			} else {
				ManagerModel manager = managerService.findOneByName(user.getUsername());
				userService.truncateDepartToCustomer(manager.getId(), Integer.parseInt(uid));
				jsonResult = createJsonResult(MessageType.success, "web.institution.usercontroller.promotion.failed",
						null, null);
			}
		} else {
			if (StringUtil.isBlank(uid)) {
				jsonResult = createJsonResult(MessageType.danger,
						"web.institution.usercontroller.promotion.success.error", null, null);
			} else {
				jsonResult = createJsonResult(MessageType.danger,
						"web.institution.usercontroller.promotion.failed.error", null, null);
			}
		}
		return jsonResult;
	}

	/**
     * 设备基本信息
     * 
     * @param session
     * @param request
     * @param response
     */
	@RequestMapping(value = "/device", method = RequestMethod.GET)
	public @ResponseBody List<DeviceBasicInfoModel> device(Integer uid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<DeviceBasicInfoModel> dvBInfoList = null;
		try {
			dvBInfoList = deviceBasicInfoService.findByUserId(uid);
		} catch (Exception e) {
			logger.error("Method findByUserId invoked by deviceBasicInfoService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}

		if (dvBInfoList != null && dvBInfoList.size() > 0) {
			try {
				DeviceSaftyModel deviceSafty = deviceSaftyService.findOneWithDeviceId(dvBInfoList.get(0).getId());
				dvBInfoList.get(0).setUser(new UserModel());
				dvBInfoList.get(0).getUser().setWeight(deviceSafty == null ? 0 : deviceSafty.getViolate());
				SystemParamSetModel param = paramSettingService.querySysParamSetting();
                // address 中临时存放托管时间
				dvBInfoList.get(0).getUser().setAddress(param.getOutManagerTime().toString());
			} catch (Exception e) {
				logger.error("Method findOneWithDeviceId invoked by deviceSaftyService cause error and the reason is: "
						+ e.getMessage());
				throw e;
			}
		}
		return dvBInfoList;
	}

	/**
	 * appinfo
	 * 
	 * @param session
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/apps", method = RequestMethod.GET)
	public @ResponseBody Page appInfo(Integer did, Integer start, Integer length, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		try {
			Page page = deviceAppInfoService.findByPage(did, start, length);
			return page;
		} catch (Exception e) {
			logger.error("Method findByPage invoked by deviceAppInfoService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
     * 违规信息
     * 
     * @param did
     * @param start
     * @param length
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/illegal", method = RequestMethod.GET)
	public @ResponseBody Page illegalInfo(Integer did, Integer start, Integer length, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		try {
			Page page = deviceLegalListService.findIllegalListWithDeviceId(did, start, length);
			return page;
		} catch (Exception e) {
			logger.error(
					"Method findIllegalListWithDeviceId invoked by deviceLegalListService cause error and the reason is: "
							+ e.getMessage());
			throw e;
		}

	}

	/**
     * 违规记录
     * 
     * @param did
     * @param start
     * @param length
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public @ResponseBody Page illegalRecordInfo(Integer did, Integer start, Integer length, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		try {
			Page page = deviceLegalRecordService.findLegalRecordByDeviceId(did, start, length);
			return page;
		} catch (Exception e) {
			logger.error(
					"Method findLegalRecordByDeviceId invoked by deviceLegalRecordService cause error and the reason is: "
							+ e.getMessage());
			throw e;
		}
	}

	/**
     * 日志记录
     * 
     * @param did
     * @param start
     * @param length
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/dlogs", method = RequestMethod.GET)
	public @ResponseBody Page deviceLogInfo(Integer did, Integer start, Integer length, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		try {
			Page page = sysmanageDeviceLogService.findRecordWithDeviceId(did, start, length);
			return page;
		} catch (Exception e) {
			logger.error(
					"Method findRecordWithDeviceId invoked by sysmanageDeviceLogService cause error and the reason is: "
							+ e.getMessage());
			throw e;
		}

	}

	/**
     * 设备安全
     * 
     * @param did
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/safety", method = RequestMethod.GET)
	@ResponseBody
	public DeviceSaftyModel deviceSaftyInfo(Integer did, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			DeviceSaftyModel deviceSafty = deviceSaftyService.findOneWithDeviceId(did);
			DeviceBasicInfoModel deviceBI=deviceBasicInfoService.findOne(did);
			if (deviceSafty != null) {
				deviceSafty.setMdm(Integer.valueOf(StringUtils.isNotEmpty(deviceBI.getDevice_status())?deviceBI.getDevice_status():"0"));
				return deviceSafty;
			} else {
				return new DeviceSaftyModel();
			}
		} catch (Exception e) {
			logger.error("Method findOneWithDeviceId invoked by deviceSaftyService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
     * 设备基本信息
     * 
     * @param session
     * @param request
     * @param response
     */
	@RequestMapping(value = "/device-base", method = RequestMethod.GET)
	public @ResponseBody DeviceBasicInfoModel oneDevice(Integer did, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			DeviceBasicInfoModel basicInfo = deviceBasicInfoService.findOne(did);
			try {
				if (basicInfo != null) {
					DeviceSaftyModel deviceSafty = deviceSaftyService.findOneWithDeviceId(basicInfo.getId());
					basicInfo.getUser().setWeight(deviceSafty == null ? 0 : deviceSafty.getViolate());
					SystemParamSetModel param = paramSettingService.querySysParamSetting();
                    // address 中临时存放托管时间
					basicInfo.getUser().setAddress(param.getOutManagerTime().toString());
				}
				return basicInfo;
			} catch (Exception e) {
				logger.error("Method findOneWithDeviceId by deviceSaftyService cause error and the reason is: "
						+ e.getMessage());
				throw e;
			}
		} catch (Exception e) {
			logger.error("Method findOneWithDeviceId by deviceBasicInfoService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
     * 角色
     * 
     * @param session
     * @param request
     * @param response
     */
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> allRoles(Integer uid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		List<RoleModel> roleList = null;
		try {
			roleList = (List<RoleModel>) roleService.findAllByOrgId(organization.getId());
		} catch (Exception e) {
			logger.error("Method findAllByOrgId by roleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		map.put("roles", roleList);

		Map<String, Object> maps = new HashMap<String, Object>();
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		maps.put("orgId", organization.getId());
		maps.put("mid", manager.getId());
		try {
			List<StructureModel> structureList = structureService.findAllByMap(maps);
			NodeModel nodes = treeManager.buildTree(structureList);
			String jsonStr = "[" + JSONObject.fromObject(nodes).toString() + "]";
			jsonStr = StringUtil.replace(jsonStr, "\"nodes\":[],", "");
			map.put("treeData", jsonStr);
			return map;
		} catch (Exception e) {
			logger.error("Method findAllByMap by structureService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
     * 检查名称是否存在
     * 
     * @param session
     * @param request
     * @param response
     */
	@RequestMapping(value = "/checkname", method = RequestMethod.GET)
	public @ResponseBody boolean isNameExists(String username, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		UserModel user = userService.findUser(username, organization.getId());
		try {
			ManagerModel manager = managerService.findOneByOrgAndName(organization.getId(), username);
			ManagerModel institution = managerService.findOneInstitution(username);
			if (user == null && manager == null && institution == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.error("Method findOneByName by managerService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
     * 设备策略
     * 
     * @param uid
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/devicestrategy", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getDeviceStrategy(Integer uid, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<AndroidDevicePolicy> androidDevicePolicies = new ArrayList<AndroidDevicePolicy>();
		List<IosDevicePolicy> iosDevicePolicies = new ArrayList<IosDevicePolicy>();
		if (manager.getUser() != null) {
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("orgId", organization.getId());
			maps.put("mid", manager.getId());
			try {
				androidDevicePolicies = deviceService.findAllAndroidDevicePolicyByMap(maps);
				iosDevicePolicies = deviceService.findAllIosDevicePolicyByMap(maps);
			} catch (Exception e) {
				logger.error("Method by deviceService cause error and the reason is: " + e.getMessage());
				throw e;
			}
		} else {
			try {
				androidDevicePolicies = deviceService.findAll(organization.getId());
				iosDevicePolicies = deviceService.findIosAll(organization.getId());
			} catch (Exception e) {
				logger.error("Method findAll by deviceService cause error and the reason is: " + e.getMessage());
				throw e;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DeviceTypeStatus.SOFTTEK_ANDROID.toString(), androidDevicePolicies);
		map.put(DeviceTypeStatus.SOFTTEK_IOS.toString(), iosDevicePolicies);
		try {
			Map<String, Object> resultMap = deviceService.findUserDevicePolicy(uid, organization.getId());
			map.put("current_android", resultMap.get(DeviceTypeStatus.SOFTTEK_ANDROID.toString()));
			map.put("current_ios", resultMap.get(DeviceTypeStatus.SOFTTEK_IOS.toString()));
			return map;
		} catch (Exception e) {
			logger.error(
					"Method findUserDevicePolicy by deviceService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
     * 更新数据
     * 
     * @param session
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
	@Log(operateType = "web.institution.usercontroller.updatedevicestrategy.log.operatetype", operateContent = "web.institution.usercontroller.updatedevicestrategy.log.operatecontent", args = {
			"u_name", "a_policy" })
	@RequestMapping(value = "/updateds", method = RequestMethod.POST)
	public @ResponseBody JsonResult updateDeviceStrategy(Integer uid, Integer deviceStrategy, Integer iosDeviceStrategy,
			AndroidDeviceUsers androidDeviceUsers, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException {
		JsonResult jsonResult = null;
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		UserModel temp = null;
		try {
			temp = userService.findOne(uid);
		} catch (Exception e) {
			logger.error("Method findOne by userService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		request.setAttribute("u_name", temp.getRealname());

		androidDeviceUsers.setAndroidDevicePolicyId(deviceStrategy);
		androidDeviceUsers.setUsersId(uid);
		AndroidDeviceUsers result = null;
		try {
			result = deviceService.findOneByEntity(androidDeviceUsers);
		} catch (Exception e) {
			logger.error("Method findOneByEntity by deviceService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		//
		IosPolicyUser iosPolicyUser = new IosPolicyUser();
		iosPolicyUser.setIosDevicePolicyId(iosDeviceStrategy);
		iosPolicyUser.setUserId(uid);
		IosPolicyUser iosResult = null;
		try {
			iosResult = deviceService.findOneByIosEntity(iosPolicyUser);
		} catch (Exception e) {
			logger.error("Method findOneByIosEntity by deviceService cause error and the reason is: " + e.getMessage());
			throw e;
		}
        // 更新时间信息
		if (deviceStrategy != null && !Constant.EMPTY_STR.equals(deviceStrategy)) {
			if (result != null) {
				result.setUpdateDate(DateTime.now().toDate());
				result.setCreateUser(manager.getId());
				result.setUpdateUser(manager.getId());
				int flag = 0;
				try {
					flag = deviceService.updateAndroidDeviceUser(result);
				} catch (Exception e) {
					logger.error(
							"Method findOneByEntity by deviceService cause error and the reason is: " + e.getMessage());
					throw e;
				}
				if (flag > 0) {
					Map<String, String> map = new HashMap<String, String>();
					Map<String, Object> resultMap = deviceService.findUserDevicePolicy(uid, organization.getId());
					Integer adpid = Integer
							.parseInt(resultMap.get(DeviceTypeStatus.SOFTTEK_ANDROID.toString()).toString());
					AndroidDevicePolicy androidDevicePolicy = deviceService.findOneAndroidDevicePolicy(adpid);
					request.setAttribute("a_policy", androidDevicePolicy.getName());
					map.put("androidDevicePolicy", JSONObject.fromObject(androidDevicePolicy).toString());

					MqProducerThread mqProducerThread = new MqProducerThread(uid.toString(), null, null, 2, map);
					taskExecutor.execute(mqProducerThread);

					jsonResult = createJsonResult(MessageType.success,
							"web.institution.usercontroller.updatedevicestrategy.success", null, null);
				} else {
					jsonResult = createJsonResult(MessageType.danger,
							"web.institution.usercontroller.updatedevicestrategy.failed", null, null);
				}
			} else {
				androidDeviceUsers.setCreateDate(DateTime.now().toDate());
				androidDeviceUsers.setUpdateDate(DateTime.now().toDate());
				androidDeviceUsers.setCreateUser(manager.getId());
				androidDeviceUsers.setUpdateUser(manager.getId());
				int flag = 0;
				try {
					flag = deviceService.insertAndroidDeviceUser(androidDeviceUsers);
				} catch (Exception e) {
					logger.error("Method insertAndroidDeviceUser by deviceService cause error and the reason is: "
							+ e.getMessage());
					throw e;
				}
				if (flag > 0) {
					Map<String, String> map = new HashMap<String, String>();
					Map<String, Object> resultMap = deviceService.findUserDevicePolicy(uid, organization.getId());
					Integer adpid = Integer
							.parseInt(resultMap.get(DeviceTypeStatus.SOFTTEK_ANDROID.toString()).toString());
					map.put("androidDevicePolicy", adpid.toString());

					MqProducerThread mqProducerThread = new MqProducerThread(uid.toString(), null, null, 2, map);
					taskExecutor.execute(mqProducerThread);

					jsonResult = createJsonResult(MessageType.success,
							"web.institution.usercontroller.device.update.success.label", null, null);
				} else {
					jsonResult = createJsonResult(MessageType.danger,
							"web.institution.usercontroller.device.update.failed.label", null, null);
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Set<Integer> userSet = new LinkedHashSet<Integer>();
		int iosFlag = 0;
		if (iosDeviceStrategy != null && !Constant.EMPTY_STR.equals(iosDeviceStrategy)) {
            // 更新ios设备策略
			if (iosResult != null) {
				iosResult.setUpdateDate(DateTime.now().toDate());
				iosResult.setCreateUser(manager.getId());
				iosResult.setUpdateUser(manager.getId());
				try {
					iosFlag = deviceService.updateIosDeviceUser(iosResult);
				} catch (Exception e) {
					logger.error("Method updateAndroidDeviceUser by deviceService cause error and the reason is: "
							+ e.getMessage());
					throw e;
				}
				if (iosFlag > 0) {
					Map<String, Object> resultMap = deviceService.findUserDevicePolicy(uid, organization.getId());
					Integer adpid = Integer.parseInt(resultMap.get(DeviceTypeStatus.SOFTTEK_IOS.toString()).toString());
					IosDevicePolicy iosDevicePolicy = deviceService.findOneIosDevicePolicy(adpid);
					map.put("iosDevicePolicy", iosDevicePolicy);
					userSet.add(uid);
					map.put("userSet", userSet);
					String status = abstractIosPush.nofity(map);
					if (BaseDTO.SUCCESS.equals(status)) {
						jsonResult = createJsonResult(MessageType.success,
								"web.institution.usercontroller.device.update.success.label", null, null);
					} else {
						jsonResult = createJsonResult(MessageType.danger,
								"web.institution.usercontroller.device.update.failed.label", null, null);
					}
				} else {
					jsonResult = createJsonResult(MessageType.danger,
							"web.institution.usercontroller.updatedevicestrategy.failed", null, null);
				}
			} else {
				iosPolicyUser.setCreateDate(new Date());
				iosPolicyUser.setUpdateDate(new Date());
				iosPolicyUser.setCreateUser(manager.getId());
				iosPolicyUser.setUpdateUser(manager.getId());
				try {
					iosFlag = deviceService.insertIosDeviceUser(iosPolicyUser);
				} catch (Exception e) {
					logger.error("Method insertIosDeviceUser by deviceService cause error and the reason is: "
							+ e.getMessage());
					throw e;
				}
				if (iosFlag > 0) {
					Map<String, Object> resultMap = deviceService.findUserDevicePolicy(uid, organization.getId());
					Integer adpid = Integer.parseInt(resultMap.get(DeviceTypeStatus.SOFTTEK_IOS.toString()).toString());
					IosDevicePolicy iosDevicePolicy = deviceService.findOneIosDevicePolicy(adpid);
					map.put("iosDevicePolicy", iosDevicePolicy);
					userSet.add(uid);
					map.put("userSet", userSet);
					String status = abstractIosPush.nofity(map);
					if (BaseDTO.SUCCESS.equals(status)) {
						jsonResult = createJsonResult(MessageType.success,
								"web.institution.usercontroller.device.update.success.label", null, null);
					} else {
						jsonResult = createJsonResult(MessageType.danger,
								"web.institution.usercontroller.device.update.failed.label", null, null);
					}
				} else {
					jsonResult = createJsonResult(MessageType.danger,
							"web.institution.usercontroller.device.update.failed.label", null, null);
				}
			}
		}
		return jsonResult;
	}

	/**
     * 网络信息
     * 
     * @param did
     * @param deviceStrategy
     * @param androidDeviceUsers
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/netinfo", method = RequestMethod.GET)
	public @ResponseBody DeviceNetworkStatusModel netInfo(Integer did, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			DeviceNetworkStatusModel deviceNetworkStatus = deviceNetworkStatusService.findOne(did);
			if (deviceNetworkStatus == null) {
				deviceNetworkStatus = new DeviceNetworkStatusModel();
			}
			return deviceNetworkStatus;
		} catch (Exception e) {
			logger.error(
					"Method findOne by deviceNetworkStatusService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
     * 获取设备定位
     * 
     * @param did
     * @param deviceStrategy
     * @param androidDeviceUsers
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/devicegps", method = RequestMethod.POST)
	public void devicegps(String did, String uid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		/*
		 * ExecutorService pool = Executors.newFixedThreadPool(5); try { String
		 * sn = deviceManagerService.findUdidById(Integer.parseInt(did));
		 * Map<String, String> map = new HashMap<String, String>();
		 * map.put("uploadxy", "1"); JpushUdidWithTags jpushUdidWithTags = new
		 * JpushUdidWithTags(map, sn); pool.execute(jpushUdidWithTags);
		 * pool.shutdown(); } catch (Exception e) { logger.error(
		 * "Method findUdidById by deviceManagerService cause error and the reason is: "
		 * + e.getMessage()); throw e; }
		 */
		try {
			if (StringUtils.isNotEmpty(String.valueOf(uid))) {
				Map<String, String> map = new HashMap<String, String>();
				String sn = deviceManagerService.findUdidById(Integer.parseInt(did));
				map.put("uploadxy", "1");
				map.put("sn", sn);
				MqProducerThread mqProducerThread = new MqProducerThread(uid.toString(), null, null, 2, map);
				taskExecutor.execute(mqProducerThread);
			}
		} catch (NumberFormatException e) {
			logger.error(
					"Method findUdidById by deviceManagerService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
     * 客户端返回坐标存储
     * 
     * @param did
     * @param deviceStrategy
     * @param androidDeviceUsers
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/getdeviceposition", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getdeviceposition(String did, HttpServletRequest request, HttpServletResponse response) {
		// deviceBasicInfoService.updateLongiAndLati(longitude,latitude,udid);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < 5; i++) {
			try {
				DeviceBasicInfoModel deviceBasicInfo = deviceBasicInfoService.queryDeviceLocation(Integer.valueOf(did));
				Date a = deviceBasicInfo.getUpdate_time();
				Date newdate = new Date();
				long s = (newdate.getTime() - a.getTime()) / 1000;
				if (s <= 60) {
					deviceBasicInfo.setId(1);
					map.put("deviceBasicInfo", deviceBasicInfo);
					break;
				}
			} catch (Exception e) {
				logger.error("Method queryDeviceLocation by deviceBasicInfoService cause error and the reason is: "
						+ e.getMessage());
				throw e;
			}
		}
		return map;
	}

	/**
     * 下载用户导入模板
     * 
     * @param request
     * @param response
     * @throws Exception
     */
	@RequestMapping(value = "/getuserexcelmodel", method = RequestMethod.GET)
	@ResponseBody
	public void getuserexcelmodel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExportData exportData = new ExportData();
		String headers[][] = {
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label1", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label2", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label3", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label4", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label5", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label6", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label7", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label8", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label9", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label10", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.excel.label11", null,
						LocaleContextHolder.getLocale()), "String" } };
		SXSSFWorkbook workbook = exportData.getwb(headers, "sheet1");
		// XSSFWorkbook workbook = new XSSFWorkbook();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
		OutputStream os = null;
		String fileName = messageSource.getMessage("web.institution.usercontroller.export.excel.model", null,
				LocaleContextHolder.getLocale());
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		os = response.getOutputStream();
		workbook.write(os);
		os.flush();
		os.close();
	}

	/**
     * 导出用户
     * 
     * @param request
     * @param response
     * @throws Exception
     */
	@RequestMapping(value = "/exportuser", method = RequestMethod.GET)
	@ResponseBody
	public void exportuser(String groupid, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		@SuppressWarnings("unchecked")
		List<StructureModel> list = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		Integer id = Integer.parseInt(groupid);
		String name = structureService.queryNameById(id);
		request.setAttribute("name", name);
		List<Integer> idList = new ArrayList<Integer>();
		structureService.queryAllChildrenId(id, list, idList);
		idList.add(id);
        // 根据部门ID获得所有部门下相关人员的部分信息
		List<UserExportModel> userExportModel = userService.exportUsersById(idList);
        // 添加部门名称拼接
		for (int i = 0; i < idList.size(); i++) {
			StructureModel temp = structureService.getParents(idList.get(i));
			String belongStr = temp.getName();
			if (temp != null) {
				for (int j = 0; j < userExportModel.size(); j++) {
					if (userExportModel.get(j).getGroup_id().equals(idList.get(i))) {
						// String belongStr = temp.getName();
						while (temp.getParent() != null) {
							belongStr = StringUtil.insert(belongStr, temp.getParent().getName() + "/");
							temp = temp.getParent();
						}
						userExportModel.get(j).setGroup_name(belongStr);
					}
				}
			}
		}

		ExportData exportData = new ExportData();
		String headers[][] = {
				{ messageSource.getMessage("web.institution.usercontroller.export.users.label1", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.users.label2", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.users.label3", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.users.label4", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.users.label5", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.users.label6", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.usercontroller.export.users.label7", null,
						LocaleContextHolder.getLocale()), "String" } };
		SXSSFWorkbook workbook = exportData.getwb(headers, "sheet1");
		int currentRow = 1;
		Sheet sheet = workbook.getSheetAt(0);
		CellStyle cellStyle = workbook.createCellStyle();
		// cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		for (int i = 0; i < userExportModel.size(); i++) {
			Row row = sheet.createRow(currentRow);
			for (int j = 0; j < 7; j++) {
				Cell cell = row.createCell(j);
				if (j == 0) {
					cell.setCellValue(userExportModel.get(i).getGroup_name());
					cell.setCellStyle(cellStyle);
				}
				if (j == 1) {
					cell.setCellValue(userExportModel.get(i).getUser_name());
					cell.setCellStyle(cellStyle);
				}
				if (j == 2) {
					cell.setCellValue(userExportModel.get(i).getReal_name());
					cell.setCellStyle(cellStyle);
				}
				if (j == 3) {
					cell.setCellValue(userExportModel.get(i).getPhone());
					cell.setCellStyle(cellStyle);
				}
				if (j == 4) {
					cell.setCellValue(userExportModel.get(i).getEmail());
					cell.setCellStyle(cellStyle);
				}
				if (j == 5) {
					cell.setCellValue(userExportModel.get(i).getMark());
					cell.setCellStyle(cellStyle);
				}
				if (j == 6) {
					String sex = messageSource.getMessage("web.institution.usercontroller.sex.woman", null,
							LocaleContextHolder.getLocale());

					if (userExportModel.get(i).getGender().equals("1")) {
						sex = messageSource.getMessage("web.institution.usercontroller.sex.man", null,
								LocaleContextHolder.getLocale());
					}

					cell.setCellValue(sex);
					cell.setCellStyle(cellStyle);
				}
			}
			currentRow++;
		}
		// XSSFWorkbook workbook = new XSSFWorkbook();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
		OutputStream os = null;
		String fileName = messageSource.getMessage("web.institution.usercontroller.export.users.model", null,
				LocaleContextHolder.getLocale());
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		os = response.getOutputStream();
		workbook.write(os);
		os.flush();
		os.close();
	}

	/**
     * 导入用户
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@Log(operateType = "logs.usercontroller.member.type.import", operateContent = "logs.usercontroller.content.member.import")
	@RequestMapping(value = "/importusers", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importvirmember(MultipartFile files, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		Map<String, Object> messages = new HashMap<String, Object>();
        // 判断是xls还是xlsx
		Integer filetype = 0;
		if (files.getOriginalFilename().endsWith("xls")) {
			filetype = 03;
		}
		if (files.getOriginalFilename().endsWith("xlsx")) {
			filetype = 07;
		}
        // 获取机构id
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer orgid = organization.getId();
        // 获取操作人信息
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
        // 获得createById
		Integer managerId = managerModel.getId();
		@SuppressWarnings("unchecked")
		List<StructureModel> list = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		NodeModel nodes = treeManager.bulidTreeContainUser(list, organization.getId(), managerModel.getUser());
        // 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
		InputStream ins = files.getInputStream();
		Workbook wb = null;
		wb = WorkbookFactory.create(ins);
		ins.close();
		Sheet sheet = null;
        // 3.得到Excel工作表对象
		if (filetype == 03) {
			sheet = (HSSFSheet) wb.getSheetAt(0);
		}
		if (filetype == 07) {
			sheet = (XSSFSheet) wb.getSheetAt(0);
		}
        // 总行数
		int trLength = sheet.getLastRowNum();

		Row rowtest = sheet.getRow(0);
		Cell celltest1 = rowtest.getCell(0);
		Cell celltest2 = rowtest.getCell(1);
		Cell celltest3 = rowtest.getCell(2);
		if (celltest1 != null && celltest2 != null && celltest3 != null) {
			celltest1.setCellType(Cell.CELL_TYPE_STRING);
			celltest2.setCellType(Cell.CELL_TYPE_STRING);
			celltest3.setCellType(Cell.CELL_TYPE_STRING);
			if (trLength > 0) {

				if (celltest1.getStringCellValue()
						.equals(messageSource.getMessage("web.institution.usercontroller.export.excel.label1", null,
								LocaleContextHolder.getLocale()))
						&& celltest2.getStringCellValue()
								.equals(messageSource.getMessage("web.institution.usercontroller.export.excel.label2",
										null, LocaleContextHolder.getLocale()))
						&& celltest3.getStringCellValue()
								.equals(messageSource.getMessage("web.institution.usercontroller.export.excel.label3",
										null, LocaleContextHolder.getLocale()))) {
					List<ExcelInsertUserModel> excelList = new ArrayList<ExcelInsertUserModel>();
					List<String> usertlist = (List<String>) userService.findAllMember(orgid);
					String erromessages = "";

					int rownumber = 0;
					for (int i = 1; i <= trLength; i++) {
                        // 得到Excel工作表的行
						Row row = sheet.getRow(i);
						ExcelInsertUserModel excelInsertUserModel = new ExcelInsertUserModel();
						for (int j = 0; j <= 10; j++) {
                            // 得到Excel工作表指定行的单元格
							if (row != null) {
								Cell newcell = row.getCell(j);
								String cell = "";
								if (newcell != null) {
									newcell.setCellType(Cell.CELL_TYPE_STRING);
									if (StringUtils.isNotBlank(StringUtils.trim(newcell.getStringCellValue()))) {
										// cell =
										// StringUtils.trim(newcell.getStringCellValue());
										cell = newcell.getStringCellValue().replaceAll(" ", "");
									}
								}

								if (j == 0) {
                                    // 如果部门名称不为空
									if (StringUtils.isNotBlank(cell)) {
										String groupname[] = cell.split("/");
										if (null != groupname) {
                                            /* 深度 */
											int deep = 0;
											List<NodeModel> nodeList = null;
											StructureModel structure = null;
											NodeModel temp = nodes;
											do {
												if (temp.getTags().getParent() == null) {
													nodeList = temp.getNodes();
													for (NodeModel n : nodeList) {
														if (n.getTags().getName().equals(groupname[deep])) {
															deep++;
															temp = n;
															break;
														}
													}
													if (deep == 0) {
														break;
													}
												} else {
													int deep1 = deep;
													for (NodeModel n : nodeList) {
														if (n.getTags().getName().equals(groupname[deep])) {
															deep++;
															temp = n;
															break;
														}
													}
													if (deep1 == deep) {
														break;
													}
												}
												if (deep == groupname.length) {
													structure = temp.getTags();
													break;
												}
											} while ((nodeList = temp.getNodes()) != null);
											if (structure == null) {
												rownumber = i + 1;
												Object[] args = { rownumber };
												erromessages = erromessages + messageSource.getMessage(
														"web.institution.usercontroller.excel.erro.nullgroup", args,
														LocaleContextHolder.getLocale());
											} else {
												if (idlist != null) {
													int idlistsize1 = idlist.size();
													idlist.remove(structure.getId());
													int idlistsize2 = idlist.size();
													if (idlistsize1 != idlistsize2) {
														excelInsertUserModel.setGroup_id(structure.getId());
													} else {
														rownumber = i + 1;
														Object[] args = { rownumber };
														erromessages = erromessages + messageSource.getMessage(
																"web.institution.usercontroller.excel.erro.group2",
																args, LocaleContextHolder.getLocale());
													}
													idlist.add(structure.getId());
												} else {
													excelInsertUserModel.setGroup_id(structure.getId());
													// excelList.add(excelInsertUserModel);
												}
											}
										}
									} else {
										rownumber = i + 1;
										Object[] args = { rownumber };
										erromessages = erromessages + messageSource.getMessage(
												"web.institution.usercontroller.excel.erro.group3", args,
												LocaleContextHolder.getLocale());
									}
								}
                                // 用户帐号
								if (j == 1) {

									if (StringUtils.isNotBlank(cell)) {
										if (cell.length() < 20) {
											boolean reuser = usertlist.remove(cell);
											if (reuser == true) {
												excelInsertUserModel.setUser_name(cell);
												rownumber = i + 1;
												Object[] args = { rownumber };
												erromessages = erromessages + messageSource.getMessage(
														"web.institution.usercontroller.excel.erro.username", args,
														LocaleContextHolder.getLocale());
												usertlist.add(cell);
											} else {
												excelInsertUserModel.setUser_name(cell);
											}
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.username1", args,
													LocaleContextHolder.getLocale());
										}

									} else {
										rownumber = i + 1;
										Object[] args = { rownumber };
										erromessages = erromessages + messageSource.getMessage(
												"web.institution.usercontroller.excel.erro.username2", args,
												LocaleContextHolder.getLocale());
									}
								}
                                // 真实姓名
								if (j == 2) {
									if (StringUtils.isNotBlank(cell)) {
										if (cell.length() < 20) {
											excelInsertUserModel.setReal_name(cell);
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.realname", args,
													LocaleContextHolder.getLocale());
										}

									} else {
										rownumber = i + 1;
										Object[] args = { rownumber };
										erromessages = erromessages + messageSource.getMessage(
												"web.institution.usercontroller.excel.erro.realname1", args,
												LocaleContextHolder.getLocale());
									}
								}
                                // 邮箱
								if (j == 3) {
									if (StringUtils.isNotBlank(cell)) {
										String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
										if (cell.matches(regex) == true) {
											excelInsertUserModel.setEmail(cell);
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.email", args,
													LocaleContextHolder.getLocale());
										}
									}
								}
                                // 备注
								if (j == 4) {
									if (StringUtils.isNotBlank(cell)) {
										if (cell.length() < 30) {
											excelInsertUserModel.setMark(cell);
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.mark", args,
													LocaleContextHolder.getLocale());
										}

									}
								}
                                // 手机号码
								if (j == 5) {
									if (StringUtils.isNotBlank(cell)) {
										if (cell.length() < 30) {
											excelInsertUserModel.setPhone((cell));
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.phone", args,
													LocaleContextHolder.getLocale());
										}

									}
								}
                                // 性别
								if (j == 6) {
									if (StringUtils.isNotBlank(cell)) {
										if (cell.equals(
												messageSource.getMessage("web.institution.usercontroller.sex.woman",
														null, LocaleContextHolder.getLocale()))) {
											excelInsertUserModel.setGender(0);
										} else {
											excelInsertUserModel.setGender(1);
										}
									} else {
										excelInsertUserModel.setGender(1);
									}
								}
                                // 签名
								if (j == 7) {
									if (StringUtils.isNotBlank(cell)) {
										if (cell.length() < 30) {
											excelInsertUserModel.setSign(cell);
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.sign", args,
													LocaleContextHolder.getLocale());
										}
									}
								}
                                // 联系地址
								if (j == 8) {
									if (StringUtils.isNotBlank(cell)) {
										if (cell.length() < 30) {
											excelInsertUserModel.setAddress(cell);
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.address", args,
													LocaleContextHolder.getLocale());
										}
									}
								}
                                // 办公电话
								if (j == 9) {
									if (StringUtils.isNotBlank(cell)) {
										if (cell.length() < 30) {
											excelInsertUserModel.setOffice_phone(cell);
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.office_phone", args,
													LocaleContextHolder.getLocale());
										}
									}
								}
                                // 职位
								if (j == 10) {
									if (StringUtils.isNotBlank(cell)) {
										if (cell.length() < 30) {
											excelInsertUserModel.setPosition(cell);
										} else {
											rownumber = i + 1;
											Object[] args = { rownumber };
											erromessages = erromessages + messageSource.getMessage(
													"web.institution.usercontroller.excel.erro.position", args,
													LocaleContextHolder.getLocale());
										}
									}
								}
							}
						}
						excelList.add(excelInsertUserModel);
					}

                    // 如果有重复 直接返回重复用户帐号
					List<String> yqs = new ArrayList<String>();
					Set<String> testreusername = new HashSet<String>(yqs);
					for (int k = 0; k < excelList.size(); k++) {
						if (excelList.get(k).getUser_name() != null) {
							testreusername.add(excelList.get(k).getUser_name());
							yqs.add(excelList.get(k).getUser_name());
						}
					}
					if (testreusername.size() < yqs.size()) {
						erromessages = messageSource.getMessage(
								"web.institution.usercontroller.excel.erro.erromessages", null,
								LocaleContextHolder.getLocale());
						messages.put("messages", erromessages);
					} else {
                        // 如果错误信息长度有
						if (erromessages.length() > 0) {
							erromessages.substring(0, erromessages.length() - 1);
							messages.put("messages", erromessages);
						} else {
							Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
							String password = md5PasswordEncoder.encodePassword("123456", null);
							if (excelList.size() > 0) {
								for (int q = 0; q < excelList.size(); q++) {
									Integer policyId = structureService
											.queryPolicyIdById(excelList.get(q).getGroup_id());
									excelList.get(q).setPolicy_id(policyId);
									excelList.get(q).setOrgid(orgid);
									excelList.get(q).setPassword(password);
									excelList.get(q).setCreate_by(managerId);
								}
								userService.importUsers(excelList);
								messages.put("success", "success");
							}
						}
					}
				} else {
					String nullmessages = messageSource.getMessage(
							"web.institution.usercontroller.excel.erromodel.erromessages", null,
							LocaleContextHolder.getLocale());
					messages.put("messages", nullmessages);
				}
			} else {
				String nullmessages = messageSource.getMessage("web.institution.usercontroller.excel.erro.nullmessages",
						null, LocaleContextHolder.getLocale());
				messages.put("messages", nullmessages);
			}
		} else {
			String nullmessages = messageSource.getMessage(
					"web.institution.usercontroller.excel.erromodel.erromessages", null,
					LocaleContextHolder.getLocale());
			messages.put("messages", nullmessages);
		}
		return messages;
	}
}