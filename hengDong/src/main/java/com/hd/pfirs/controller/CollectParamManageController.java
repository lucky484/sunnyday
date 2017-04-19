package com.hd.pfirs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hd.pfirs.constant.LogType_Operate;
import com.hd.pfirs.model.InstitutionInfoModel;
import com.hd.pfirs.model.MenuDictModel;
import com.hd.pfirs.model.MenuModel;
import com.hd.pfirs.model.OperationLogInfo;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.model.RegionModel;
import com.hd.pfirs.model.RoleAuthModel;
import com.hd.pfirs.model.RoleModel;
import com.hd.pfirs.model.User;
import com.hd.pfirs.service.IPCInfoService;
import com.hd.pfirs.service.InstitutionInfoService;
import com.hd.pfirs.service.ParamSetService;
import com.hd.pfirs.service.RegionService;
import com.hd.pfirs.service.RoleService;
import com.hd.pfirs.service.UserService;
import com.hd.pfirs.util.CommUtil;
import com.hd.pfirs.util.logs.service.LogService;

@Controller
@RequestMapping(value = "/CollectParamManage")
public class CollectParamManageController {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(CollectParamManageController.class);

	public static List<MenuDictModel> allMenus = null;

	public static Map<String, RoleModel> roleMap = null;

	@Autowired
	private ParamSetService service;

	@Autowired
	private RoleService roleService;

	@Autowired
	private InstitutionInfoService institutionInfoService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private IPCInfoService iPCInfoService;

//	private SimpleDateFormat sdf_log = new SimpleDateFormat("YYYYMMDDhhmmss");
	
	private  DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 记录到数据库日志
	 */
	@Autowired
	private LogService pLog;

	/**
	 * 导航栏：指向日志管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "LogManagement")
	public String getLogManagement() {

		return "log";
	}

	/**
	 * 导航栏：指向权限管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "chmod")
	public String getChmod() {

		return "chmod";
	}

	/**
	 * 导航栏：指向用户管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "UserManage")
	public String getUserManage() {

		return "UserManage";
	}

	@Autowired
	private UserService userService;

	@RequestMapping(value = "getUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUser(int page, String diId, String username, String name, String department,HttpServletRequest request) {
		
		
		String optCondition = "where diId =" +diId + "username" + username + "name" + name + "department" +department;
		
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_query,optCondition,null);
		try
		{
			if (diId != null && !diId.equals("")) {
				diId = "%" + diId + "%";
			}
			if (username != null && !username.equals("")) {
				username = "%" + username + "%";
			}
			if (name != null && !name.equals("")) {
				name = "%" + name + "%";
			}
			if (department != null && !department.equals("")) {
				department = "%" + department + "%";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			List<User> user = userService.getUser(page, diId, username, name, department);
			int count = userService.getCount(diId, username, name, department);
			int pages = 0;
			if (count % 10 == 0) {
				pages = count / 10;
			} else {
				pages = count / 10 + 1;
			}
			map.put("user", user);
			map.put("count", count);
			map.put("pages", pages);
			logService.recordOperateLog(operationLogInfo);
			return map;
		}
		catch(Exception e)
		{
			operationLogInfo.setErrorCode("1000");
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			logger.error("something wrong when query user", e);
			throw e;
		}
		
	}

	@RequestMapping("delete")
	@ResponseBody
	public int deleteUser(String diId, HttpServletRequest request) {
		
		String optCondition = "where diId = " + diId;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_delete,optCondition ,null);
		operationLogInfo.setOperateName("用户管理");
		try
		{
			long diid = Long.valueOf(diId);
			int status = userService.deleteUser(diid);
			logService.recordOperateLog(operationLogInfo);
			return status;
		}
		catch(Exception e)
		{
			logger.error("something wrong when delete user!", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
		
	}

	@RequestMapping("change")
	@ResponseBody
	public int changeStatus(String diId, String status,HttpServletRequest request) {
		long diid = Long.valueOf(diId);
		String optCondition = " set status =" +status + " where diId = " + diId;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_update,optCondition ,null);
		operationLogInfo.setOperateName("用户管理");
		try
		{
			int changeStatus = userService.changeStatus(diid, status);
			logService.recordOperateLog(operationLogInfo);
			return changeStatus;
		}
		catch(Exception e)
		{
			logger.error("something wrong when changeStatus user!", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
		
	}

	@RequestMapping("get")
	@ResponseBody
	public User getuser(String userName,HttpServletRequest request) {
		
		String optCondition = "where userName = " + userName;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_query,optCondition ,null);
		operationLogInfo.setOperateName("用户管理");
		try
		{
			User user = userService.getuser(userName);
			logService.recordOperateLog(operationLogInfo);
			return user;
		}
		catch(Exception e)
		{
			logger.error("something wrong when changeStatus user!", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
		
	}

	@RequestMapping("getModifyUser")
	@ResponseBody
	public Map<String, Object> getModifyUser(String diId,HttpServletRequest request) {
		
		String optCondition = "where diId = " + diId;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_query,optCondition ,null);
		operationLogInfo.setOperateName("用户管理");
		
		try
		{
			Map<String, Object> map = getUserSeletedInfo();
			User user = userService.getUserByUserId(diId);
			logService.recordOperateLog(operationLogInfo);
			InstitutionInfoModel institutionInfo = institutionInfoService.getInstitutionInfoById(user.getInstitutionCode());
			map.put("user", user);
			map.put("institutionInfo", institutionInfo);
			return map;
		}
		catch(Exception e)
		{
			logger.error("something wrong when getModify user!", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
		
	}

	@RequestMapping(value = "addUserManage")
	public String addUserManage(HttpServletRequest request) {
		String currentUserName = (String) request.getSession().getAttribute("username");

		String userName = request.getParameter("addUserName");
		String password = request.getParameter("addPassword");

		// 身份证号
		String idCardNm = request.getParameter("addIdCardNm");

		// 警员编号
		String policeNm = request.getParameter("addPoliceNm");

		String name = request.getParameter("addName");
		String sex = request.getParameter("addSex");

		String remarkDesc = request.getParameter("addRemarkDesc");

		// 所属机构
		String institutionCode = request.getParameter("addDept");
		
		
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_insert,null ,null);

		InstitutionInfoModel institutionInfo = institutionInfoService.getInstitutionInfoById(institutionCode);

		String institutionName = institutionInfo == null ? null : institutionInfo.getInstitutionName();
		try
		{
			String roleID = request.getParameter("addUserRole");

			RoleModel roleModel = roleService.getRoleByRoleID(Long.valueOf(roleID));

			String roleName = roleModel == null ? null : roleModel.getRoleName();

			User user = new User();
			user.setDiId(CommUtil.getPrimaryKey());
			user.setInstitutionName(institutionName);
			user.setInstitutionCode(institutionCode);
			user.setUserName(userName);
			user.setPassword(password);
			user.setIdCardNm(idCardNm);
			user.setPoliceNm(policeNm);
			user.setName(name);
			user.setSex(sex);
			user.setRemarkDesc(remarkDesc);
			user.setRoleId(Long.valueOf(roleID));
			user.setRoleName(roleName);
			user.setCreateName(currentUserName);
			user.setUpdateName(currentUserName);
			user.setStatus("0");
			user.setDeleteStatus("0");
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			int reply = userService.addUser(user);
			logService.recordOperateLog(operationLogInfo);
			if (reply == 1) {
				return "UserManage";
			} else {
				return "添加用户失败!";
			}
		}
		catch(Exception e)
		{
			logger.error("something wrong when add User!", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}

		
	}

	@RequestMapping(value = "updateUserManage")
	@ResponseBody
	public int updateUserManage(HttpServletRequest request) {

		String currentUserName = (String) request.getSession().getAttribute("username");

		String userName = request.getParameter("modifyUserName");
		String password = request.getParameter("modifyPassword");

		// 身份证号
		String idCardNm = request.getParameter("modifyIdCardNm");
		// 警员编号
		String policeNm = request.getParameter("modifyPoliceNm");

		String name = request.getParameter("modifyName");
		String sex = request.getParameter("modifySex");

		String remarkDesc = request.getParameter("modifyRemarkDesc");

		// 所属机构
		String institutionCode = request.getParameter("modifyDept");
		
		
		

		InstitutionInfoModel institutionInfo = institutionInfoService.getInstitutionInfoById(institutionCode);

		String institutionName = institutionInfo == null ? null : institutionInfo.getInstitutionName();

		String roleID = request.getParameter("modifyUserRole");
		String userID = request.getParameter("modifyUserId");

		RoleModel roleModel = roleService.getRoleByRoleID(Long.valueOf(roleID));

		String roleName = roleModel == null ? null : roleModel.getRoleName();
		User user = new User();

		user.setDiId(Long.valueOf(userID));

		user.setInstitutionCode(institutionCode);
		user.setInstitutionName(institutionName);
		user.setRoleName(roleName);
		user.setUserName(userName);
		user.setPassword(password);
		user.setIdCardNm(idCardNm);
		user.setPoliceNm(policeNm);
		user.setName(name);
		user.setSex(sex);
		user.setRemarkDesc(remarkDesc);
		user.setRoleId(Long.valueOf(roleID));
		user.setCreateName(currentUserName);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setUpdateName(currentUserName);
		
		String optCondition = "where diId = " + userID;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_update,optCondition ,null);
		operationLogInfo.setOperateName("用户管理");
		
		try
		{
			int changeStatus = userService.updateUser(user);
			return changeStatus;
		}
		catch(Exception e)
		{
			logger.error("something wrong when update User!", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
		
	}

	/**
	 * 导航栏：指向角色权限管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "RoleRightsManagement")
	public String getRoleRightsManagement() {

		return "RoleRightsManagement";
	}

	/**
	 * 导航栏：指向设备管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "DeviceManagement")
	public String getDeviceManagement() {

		return "DeviceManagement";
	}

	/**
	 * 导航栏：指向辖区管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "AreaManagement")
	public String getAreaManagement() {

		return "AreaManagement";
	}

	@Autowired
	private RegionService regionService;

	/**
	 * 
	 * @Title: getRegion
	 * @Description: 
	 * @param page页数
	 * @param regionCode辖区编号
	 * @param regionName辖区名称
	 * @param regionLevel级别
	 * @param preRegionCode上级辖区编号
	 * @return
	 * @return: Map<String,Object>
	 */
	@RequestMapping(value = "getRegion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRegion(int page, String regionCode, String regionName, String regionLevel,
			String preRegionCode, HttpServletRequest request) {
		HttpSession session  = request.getSession();
		String userName = (String) session.getAttribute("username");
		if (regionCode != null && !regionCode.equals("")) {
			regionCode = "%" + regionCode + "%";
		}
		if (regionName != null && !regionName.equals("")) {
			regionName = "%" + regionName + "%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<RegionModel> list = new ArrayList<RegionModel>();
		int count = 0;
		
		String optCondition = "where regionCode = " + regionCode + "regionName =" +regionName;
		OperationLogInfo info = getOperationLogInfo(userName, LogType_Operate.type_query,optCondition,null);
		info.setOperateName("辖区管理");
		try
		{
			list = regionService.getRegion(page, regionCode, regionName, regionLevel, preRegionCode);
			
			if (CollectionUtils.isNotEmpty(list))
			{
				for (RegionModel model : list)
				{
					String reg = model.getRegionCode();
					int deviceCount = iPCInfoService.queryDevicesByRegionCode(reg);
					model.setDeviceCount(deviceCount);
				}
			}
			count = regionService.getRegionCount(regionCode, regionName, regionLevel, preRegionCode);
			info.setOperateResult(LogType_Operate.SUCCESS_TYPE);
			logService.recordOperateLog(info);
		}
		catch(Exception e)
		{
			info.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			info.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(info);
			logger.error("something wrong when query region!", e);
			throw e;
		}
		
		
		info.setUserName(userName);
		int pages = 0;
		if (count % 10 == 0) {
			pages = count / 10;
		} else {
			pages = count / 10 + 1;
		}
		map.put("list", list);
		map.put("count", count);
		map.put("pages", pages);
		return map;
	}

	// 辖区管理的删除操作
	@RequestMapping("deleteRegion")
	@ResponseBody
	public int deleteRegion(String regionId, HttpServletRequest request) {
		String optCondition = "where regionId =" + regionId;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request),
				LogType_Operate.type_delete, optCondition, null);
		operationLogInfo.setOperateName("辖区管理");
		try
		{
			int deleteStatus = regionService.deleteRegion(Long.valueOf(regionId));
			logService.recordOperateLog(operationLogInfo);
			return deleteStatus;
		}
		catch(Exception e)
		{
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logger.error("something wrong when delete region!", e);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
	}

	@RequestMapping("getRegionInfo")
	@ResponseBody
	public RegionModel getRegionInfo(String regionId, HttpServletRequest request) {
		
		String optCondition = "where regionId =" + regionId;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request),
				LogType_Operate.type_query, optCondition, null);
		operationLogInfo.setOperateName("辖区管理");
		Long regionid = Long.valueOf(regionId);
		try
		{
			RegionModel regionModel = regionService.getRegionInfo(regionid);
			return regionModel;
		}
		catch(Exception e)
		{
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logger.error("something wrong when get region!", e);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
		

	}

	@RequestMapping("getReg")
	@ResponseBody
	public List<RegionModel> getReg() {
		return regionService.getReg();
	}

	// 辖区管理的编辑辖区的功能
	@RequestMapping(value = "editRegion")
	public String editRegion(String rCode1, String rID, String rName1, String rLevel1, String preRcode1,
			String desc1, String remk1, String del1,HttpServletRequest request) {

		String optCondition = "where regionId =" + rCode1;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request),
				LogType_Operate.type_update, optCondition, null);
		operationLogInfo.setOperateName("辖区管理");
		
		try
		{
			RegionModel editRegion = new RegionModel();
			editRegion.setRegionId(Long.valueOf(rID));
			editRegion.setRegionCode(rCode1);
			editRegion.setRegionName(rName1);
			editRegion.setRegionLevel(rLevel1);
			editRegion.setPreRegionCode(preRcode1);
			editRegion.setDescription(desc1);
			editRegion.setRemarkDesc(remk1);
			//		editRegion.setUpdateName(un1);
			editRegion.setDeleteStatus(del1);
			int reply = regionService.editRegion(editRegion);
			if (reply == 1) {
				
				logService.recordOperateLog(operationLogInfo);
				return "AreaManagement";
			} else {
				return "编辑辖区失败！";
			}
			
		}
		catch(Exception e)
		{
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			logger.error("something wrong when edit region", e);
			throw e;
		}
		
	}

	// 辖区管理的添加辖区的功能
	@RequestMapping(value = "addReg")
	@ResponseBody
	public String addReg(HttpServletRequest request) {
		
		String regionName = request.getParameter("regionName");
		String regionCode = request.getParameter("regionCode");
		String regionLevel =  request.getParameter("regionLevel");
		String preRegionCode = request.getParameter("preRegionCode");
		String remarkDesc = request.getParameter("remarkDesc");
		String description = request.getParameter("description");
		
		String optCondition = "regionName" + regionName + "regionCode" + regionCode + "regionLevel1" + regionLevel
				+ "preRegionCode1" + preRegionCode + "remarkDesc" + remarkDesc + "remarkDesc" + remarkDesc;
		
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request),
				LogType_Operate.type_insert, optCondition, null);
		operationLogInfo.setOperateName("辖区管理");
		
		try
		{
			RegionModel addRegion = new RegionModel();
			addRegion.setRegionId(CommUtil.getPrimaryKey());
			addRegion.setRegionName(regionName);
			addRegion.setRegionCode(regionCode);
			addRegion.setRegionLevel(regionLevel);
			addRegion.setPreRegionCode(preRegionCode);
			addRegion.setRemarkDesc(remarkDesc);
			addRegion.setDescription(description);
			addRegion.setCreateName("system");
			addRegion.setDeleteStatus("0");
			int reply = regionService.addRegion(addRegion);
			if (reply == 1) {
				return "AreaManagement";
			} else {
				return "添加辖区失败！";
			}
		}
		catch(Exception e)
		{
			logger.error("something wrong when add Region", e);
			operationLogInfo.setErrorCode("1000");
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			throw e;
		}
		
	}

	/**
	 * 导航栏：指向采集参数管理,
	 * 
	 * @return
	 */
	@RequestMapping(value = "collectParamManage")
	public ModelAndView getCollectParamManage(HttpServletRequest request) {
		ParamSet paramSet = new ParamSet();
		String errorCode = null;
		try {
			paramSet = service.getParamSet();
		} catch (Exception e) {
			errorCode = "1000";
		}

		OperationLogInfo operationLogInfo = new OperationLogInfo();
		StringBuilder sbOperCon = new StringBuilder("");

		// 日志记录
		operationLogInfo.setUserName(CommUtil.getUserName(request));
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateName("采集参数管理");
		operationLogInfo.setOperateCondition(sbOperCon.toString());
		operationLogInfo.setErrorCode(errorCode);
		operationLogInfo.setOperateType(LogType_Operate.type_query);
		if (paramSet == null) {

			pLog.recordOperateLog(operationLogInfo);
			/**
			 * 为查询到数据，flag为1，代表参数设置为初始化，
			 */
			request.setAttribute("flag", "1");
			return new ModelAndView("collectParamManage", "paramSet", null);
		}
		pLog.recordOperateLog(operationLogInfo);
		request.setAttribute("flag", "0");

		return new ModelAndView("collectParamManage", "paramSet", paramSet);
	}

	/**
	 * 导航栏：指向采集参数管理,
	 * 
	 * @return
	 */
	@RequestMapping(value = "Navigation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getNavigation(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ParamSet paramSet = new ParamSet();
		paramSet = service.getParamSet();
		if (paramSet == null) {
			/**
			 * 为查询到数据，flag为1，代表参数设置为初始化，
			 */
			map.put("flag", "1");
		} else {
			map.put("flag", "0");
		}
		map.put("paramSet", paramSet);
		return map;
	}

	@RequestMapping(value = "getCollectParamManage")
	public ModelAndView getCollectParamManageForm(HttpServletRequest request) {
		String ctrlSyncCycle = request.getParameter("ctrlSyncCycle");
		String totalSyncCycle = request.getParameter("totalSyncCycle");
		String faceCardCompAlarmVal = request.getParameter("faceCardCompAlarmVal");
		String faceCompAlarmVal = request.getParameter("faceCompAlarmVal");
		String faceCompareFlag = request.getParameter("faceCompareFlag");
		String cardCompareFlag = request.getParameter("cardCompareFlag");
		String faceCardCompFlag = request.getParameter("faceCardCompFlag");
		String remarkDesc = request.getParameter("remarkDesc");
		String flag = request.getParameter("flag");

		ParamSet setting = new ParamSet();
		setting = new ParamSet();
		setting.setParamId(1L);
		setting.setCtrlSyncCycle(Integer.valueOf(ctrlSyncCycle));
		setting.setFaceCardCompAlarmVal(Integer.valueOf(faceCardCompAlarmVal));
		setting.setTotalSyncCycle(Integer.valueOf(totalSyncCycle));
		setting.setFaceCompAlarmVal(Integer.valueOf(faceCompAlarmVal));
		setting.setCardCompareFlag(cardCompareFlag);
		setting.setFaceCardCompFlag(faceCardCompFlag);
		setting.setFaceCompareFlag(faceCompareFlag);
		setting.setRemarkDesc(remarkDesc);

		OperationLogInfo operationLogInfo = new OperationLogInfo();

		String errorCode = null;
		try {
			if ("1".equals(flag)) {
				service.insertParamSet(setting);
				operationLogInfo.setOperateType(LogType_Operate.type_insert);
				request.setAttribute("flag", "1");
			} else {
				service.updateParamSet(setting);
				operationLogInfo.setOperateType(LogType_Operate.type_update);
				request.setAttribute("flag", "0");
			}
		} catch (Exception e) {
			logger.error(e);
			errorCode = "1000";
		}

		StringBuilder sbOperCon = new StringBuilder("");
		sbOperCon.append(setting.toString());

		// 日志记录
		operationLogInfo.setUserName(CommUtil.getUserName(request));
		operationLogInfo.setOperateTime(format.format(new Date()));
		operationLogInfo.setOperateName("采集参数管理");
		operationLogInfo.setOperateCondition(sbOperCon.toString());
		operationLogInfo.setErrorCode(errorCode);
		pLog.recordOperateLog(operationLogInfo);

		return new ModelAndView("collectParamManage", "paramSet", setting);
	}

	@RequestMapping(value = "getRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRole(int page, String roleName, HttpServletRequest request) {
		
		String optCondition = "where roleName = " + roleName + "page" + page;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_query,optCondition ,null);
		operationLogInfo.setOperateName("角色权限管理");
		
		try
		{
			if (roleName != null && !roleName.equals("")) {
				roleName = "%" + roleName + "%";
			}

			Map<String, Object> map = new HashMap<String, Object>();
			List<RoleModel> list = roleService.getRole(page, roleName);
			int count = roleService.getRoleCount(roleName);
			logService.recordOperateLog(operationLogInfo);
			int pages = 0;
			if (count % 10 == 0) {
				pages = count / 10;
			} else {
				pages = count / 10 + 1;
			}
			map.put("list", list);
			map.put("count", count);
			map.put("pages", pages);
			return map;
		}
		catch(Exception e)
		{
			logger.error("something wrong when get Role", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
	}

	@RequestMapping("getRoleInfo")
	@ResponseBody
	public Map<String, Object> getRoleInfo(String roleID, HttpServletRequest request) {
		
		String optCondition = "where roleID = " + roleID ;
		OperationLogInfo operationLogInfo = getOperationLogInfo(CommUtil.getUserName(request), LogType_Operate.type_query,optCondition ,null);
		operationLogInfo.setOperateName("角色权限管理");
		
		Long id = Long.valueOf(roleID);
		
		try
		{
			RoleModel roleModel = roleService.getRoleByRoleID(id);
			logService.recordOperateLog(operationLogInfo);
			List<MenuDictModel> menuDictModels = new ArrayList<MenuDictModel>();
			Map<String, Object> map = new HashMap<String, Object>();
			if (null != roleModel) {
				menuDictModels = roleService.getAuthMenusByRoleId(id);
			}
			map.put("list", getMenuModels(menuDictModels));
			map.put("roleName", roleModel.getRoleName());
			map.put("roleID", roleModel.getRoleID());
			map.put("remarkDesc", roleModel.getRemarkDesc());
			map.put("desc", roleModel.getDescription());
			return map;
		}
		catch(Exception e)
		{
			logger.error("something wrong when get roleInfo", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
	}

	@RequestMapping("addRoleInfo")
	@ResponseBody
	public Map<String, Object> addRoleInfo() {

		Map<String, Object> map = new HashMap<String, Object>();
		List<MenuDictModel> menuDictModels = getAllMenus();
		map.put("list", constructMenuModels(menuDictModels));
		return map;
	}

	@RequestMapping("addNewRole")
	@ResponseBody
	public Map<String, Object> addNewRole(String menuIds, String roleName, String desc, String remark, HttpServletRequest request) {
		
		String optCondition = "roleName = " + roleName + "desc" + desc + "remark" + remark ;
		
		String createName = CommUtil.getUserName(request);
		OperationLogInfo operationLogInfo = getOperationLogInfo(createName, LogType_Operate.type_insert,optCondition ,null);
		operationLogInfo.setOperateName("角色权限管理");
		String[] menuIdList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(menuIds))
		{
			map.put("result", "success");
			return map;
		}
		try
		{
			menuIdList = menuIds.split(",");
			// 角色入库
			RoleModel roleModel = new RoleModel(roleName, desc, remark);
			roleModel.setCreateName(createName);
			roleModel.setUpdateName(createName);
			roleModel.setCreateTime(new Date());
			Long roleID = roleService.getRoleID();
			roleModel.setRoleID(roleID);
			roleService.saveRole(roleModel);
			List<RoleAuthModel> roleAuthModels = new ArrayList<RoleAuthModel>();
			for (int i = 0; i < menuIdList.length; i++) {
				// 菜单权限入库
				RoleAuthModel roleAuthModel = new RoleAuthModel(roleID, menuIdList[i]);
				roleAuthModel.setCreateName(createName);
				roleAuthModel.setUpdateName(createName);
				roleAuthModel.setCreateTime(new Date());
				roleAuthModels.add(roleAuthModel);
			}
			roleService.batchAddRoleAuth(roleAuthModels);
			logService.recordOperateLog(operationLogInfo);
			
			map.put("result", "success");
			return map;
		}
		catch(Exception e)
		{
			logger.error("something wrong when addNewRole", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}

		
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "modifyRole")
	public String modifyRole(HttpServletRequest request) {
		
		
		String[] menuIdList = request.getParameterValues("modifyAuthChecked");

		String roleName = request.getParameter("modifyRoleName");
		String remark = request.getParameter("modifyRoleRemark");
		String desc = request.getParameter("modifyRoleDesc");
		String roleID = request.getParameter("modifyRoleID");
		
		String optCondition = "where roleID = " + roleID ;
		
		String createName = CommUtil.getUserName(request);
		OperationLogInfo operationLogInfo = getOperationLogInfo(createName, LogType_Operate.type_update,optCondition ,null);
		operationLogInfo.setOperateName("角色权限管理");

		try
		{
			// 角色入库
			RoleModel roleModel = new RoleModel(roleName, desc, remark);
			roleModel.setCreateName(createName);
			roleModel.setUpdateName(createName);
			roleModel.setRoleID(Long.valueOf(roleID));
			roleService.updateRole(roleModel);

			// 获取已经有的权限
			List<MenuDictModel> menuDictModels = roleService.getAuthMenusByRoleId(Long.valueOf(roleID));

			// 获取已经有的菜单权限ID
			List<Long> authMenuIdsBefore = getAuthMenusBefore(menuDictModels);

			List<Long> stillAuthMenus = new ArrayList<Long>();
			List<RoleAuthModel> roleAuthModels = new ArrayList<RoleAuthModel>();
			for (int i = 0; i < menuIdList.length; i++)
			{
				// 新增的菜单权限
				if (!authMenuIdsBefore.contains(Long.valueOf(menuIdList[i])))
				{
					// 菜单权限入库
					RoleAuthModel roleAuthModel = new RoleAuthModel(Long.valueOf(roleID), menuIdList[i]);
					roleAuthModel.setCreateName(createName);
					roleAuthModel.setUpdateName(createName);
					roleAuthModel.setCreateTime(new Date());
					roleAuthModels.add(roleAuthModel);
				}

				else
				{
					stillAuthMenus.add(Long.valueOf(menuIdList[i]));
				}
			}

			List<Long> deleteIds = (List<Long>) CollectionUtils.disjunction(authMenuIdsBefore, stillAuthMenus);
			// String delIds = CommUtil.stringFormatter(deleteIds, ",");
			if (CollectionUtils.isNotEmpty(deleteIds))
			{
				// 删除之前有现在没有的权限
				roleService.deleteAuthMenus(deleteIds, Long.valueOf(roleID));
			}

			if (CollectionUtils.isNotEmpty(roleAuthModels))
			{
				// 添加之前有现在没有的权限
				roleService.batchAddRoleAuth(roleAuthModels);
			}
			logService.recordOperateLog(operationLogInfo);
			return "chmod";
		}
		catch(Exception e)
		{
			logger.error("something wrong when addNewRole", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
	}

	private List<Long> getAuthMenusBefore(List<MenuDictModel> menuDictModels) {
		List<Long> menuIds = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(menuDictModels)) {
			for (MenuDictModel model : menuDictModels) {
				menuIds.add(model.getMenuID());
			}
		}

		return menuIds;
	}

	// 角色管理的删除操作
	@RequestMapping("deleteRole")
	@ResponseBody
	public int deleteRole(Long roleID, HttpServletRequest request) {
		
		
		String createName = CommUtil.getUserName(request);
		String optCondition = "where roleID = " + roleID ;
		OperationLogInfo operationLogInfo = getOperationLogInfo(createName, LogType_Operate.type_delete,optCondition ,null);
		operationLogInfo.setOperateName("角色权限管理");
		try
		{
			int status = roleService.deleteRole(roleID);
			logService.recordOperateLog(operationLogInfo);
			return status;
		}
		catch(Exception e)
		{
			logger.error("something wrong when deleteRole", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
		
	}

	/**
	 * 获取需要用户选择的信息
	 * @return
	 */
	@RequestMapping("getUserSeletedInfo")
	@ResponseBody
	public Map<String, Object> getUserSeletedInfo() {
		Map<String, Object> map = new HashMap<String, Object>();

		List<RoleModel> roleModels = roleService.getRoles();

		List<InstitutionInfoModel> instutionInfoModels = institutionInfoService.getInstitutionInfos();

		map.put("roleModels", roleModels);
		map.put("instutionInfoModels", instutionInfoModels);
		return map;
	}

	/**
	 * 详情页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "checkUserName")
	@ResponseBody
	public Map<String, Object> checkUserName(String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.getUserByUserName(userName);

		if (null != user) {
			map.put("isExist", true);
		}

		return map;
	}

	@RequestMapping(value = "checkRoleName")
	@ResponseBody
	public Map<String, Object> checkRoleName(String roleName) {
		Map<String, Object> map = new HashMap<String, Object>();
		RoleModel model = roleService.getRoleByRoleName(roleName);

		if (null != model) {
			map.put("isExist", true);
		}

		return map;
	}

	@RequestMapping(value = "checkModifyRoleName")
	@ResponseBody
	public Map<String, Object> checkModifyRoleName(String roleId, String roleName) {
		Map<String, Object> map = new HashMap<String, Object>();
		RoleModel model = roleService.getRoleByRoleName(roleName);

		if (null != model && !model.getRoleID().equals(Long.valueOf(roleId))) {
			map.put("isExist", true);
		}

		return map;
	}
	
	
	@RequestMapping(value = "getCheckedMenus")
	@ResponseBody
	public Map<String, Object> getCheckedMenus(String menuIds, String checkId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 获取所有的
		List<MenuModel> menuModels = constructMenuModels(refreshAllMenus(getAllMenus()));
		if(StringUtils.isNotEmpty(menuIds))
		{
			String[] menuIdList = menuIds.split(",");
			List<String> selectedIds = getStrList(menuIdList);
			for (MenuModel model : menuModels)
			{
				if ("退出登录".equals(model.getMenuDictModel().getMenuName()) || "实时监控".equals(model.getMenuDictModel().getMenuName()))
				{
					continue;
				}
				
				else if (selectedIds.contains(String.valueOf(model.getMenuDictModel().getMenuID())))
				{
					model.getMenuDictModel().setIsAuth(true);
					for (MenuModel dictModel :  model.getSubMenuModels())
					{
						if (!selectedIds.contains(checkId) && String.valueOf(dictModel.getMenuDictModel().getMenuID()).equals(checkId))
						{
							dictModel.getMenuDictModel().setIsAuth(false);
							model.getMenuDictModel().setIsAuth(false);
						}
						else
						{
							dictModel.getMenuDictModel().setIsAuth(true);;
						}
					}
				}
				else
				{
					int i = 0;
					
					if (String.valueOf(model.getMenuDictModel().getMenuID()).equals(checkId))
					{
						model.getMenuDictModel().setIsAuth(false);
						for (MenuModel dictModel :  model.getSubMenuModels())
						{
							if (selectedIds.contains(String.valueOf(dictModel.getMenuDictModel().getMenuID())))
							{
								dictModel.getMenuDictModel().setIsAuth(false);
							}
						}
						
						continue;
					}
					
					for (MenuModel dictModel :  model.getSubMenuModels())
					{
						if (selectedIds.contains(String.valueOf(dictModel.getMenuDictModel().getMenuID())))
						{
							i++;
							dictModel.getMenuDictModel().setIsAuth(true);;
						}
					}
					
					if (i == model.getSubMenuModels().size())
					{
						model.getMenuDictModel().setIsAuth(true);
					}
					else
					{
						model.getMenuDictModel().setIsAuth(false);
					}
				}
			}
		}
		map.put("menuModels", menuModels);
		return map;
	}

	private List<String> getStrList(String [] strs)
	{
		List<String> strList = new ArrayList<String>();
		for (int i = 0; i < strs.length; i++)
		{
			strList.add(strs[i]);
		}
		
		return strList;
	}
	
	
	@RequestMapping(value = "isRoleRelatedUser")
	@ResponseBody
	public Map<String, Object> isRoleRelatedUser(HttpServletRequest request)
	{
		String roleID = request.getParameter("roleID");
		int count = userService.getCountByRoleId(roleID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		return map;
	}

	@RequestMapping(value = "getLogInfos")
	@ResponseBody
	public Map<String, Object> getLogInfos(HttpServletRequest request)
	{
		String keyWords = request.getParameter("keyWords");
		
		String startTime = request.getParameter("startTime");
		
		String endTime = request.getParameter("endTime");
		
		String page = request.getParameter("page");
		
		String optCondition = "where keyWords = " + keyWords +  "startTime" + startTime + "endTime" + endTime;
		
		String createName = CommUtil.getUserName(request);
		OperationLogInfo operationLogInfo = getOperationLogInfo(createName, LogType_Operate.type_query,optCondition ,null);
		operationLogInfo.setOperateName("日志管理");
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (keyWords != null && !keyWords.equals("")) {
			keyWords = "%" + keyWords + "%";
		}

		try
		{
			List<OperationLogInfo> listOptLogInfos = logService.getOperationLogInfos(startTime, endTime, keyWords, Integer.valueOf(page));
			int count = logService.getOperationLogInfosCount(startTime, endTime, keyWords);
			int pages = 0;
			if (count % 10 == 0) {
				pages = count / 10;
			} else {
				pages = count / 10 + 1;
			}
			map.put("list", listOptLogInfos);
			map.put("count", count);
			map.put("pages", pages);
			return map;

		}
		catch(Exception e)
		{
			logger.error("something wrong when get loginfo", e);
			operationLogInfo.setErrorCode(LogType_Operate.ERROR_CAUSE_SYSTEM);
			operationLogInfo.setOperateResult(LogType_Operate.FAILED_TYPE);
			logService.recordOperateLog(operationLogInfo);
			throw e;
		}
		
		
	}

	private List<MenuModel> getMenuModels(List<MenuDictModel> menuDictModels) {
		List<MenuDictModel> menuDictModelList = getAllMenus();
		List<MenuDictModel> newlList = new ArrayList<MenuDictModel>();
		if (CollectionUtils.isNotEmpty(menuDictModelList)) {
			for (MenuDictModel model : menuDictModelList) {
				MenuDictModel newModel = copyMenuDictModel(model);
				if (isContainsMeun(menuDictModels, newModel)) {
					newModel.setIsAuth(true);
				}
				newlList.add(newModel);
			}
		}

		return constructMenuModels(newlList);
	}

	private MenuDictModel copyMenuDictModel(MenuDictModel model) {
		if (null != model) {
			MenuDictModel menuDictModel = new MenuDictModel();
			menuDictModel.setAccessURL(model.getAccessURL());
			menuDictModel.setDeleteStatus(model.getDeleteStatus());
			menuDictModel.setFatherMenuID(model.getFatherMenuID());
			// 默认未选中
			menuDictModel.setIsAuth(false);
			menuDictModel.setMenuID(model.getMenuID());
			menuDictModel.setMenuName(model.getMenuName());
			menuDictModel.setMenuType(model.getMenuType());
			menuDictModel.setMenuValue(model.getMenuValue());
			menuDictModel.setRemarkDesc(model.getRemarkDesc());

			return menuDictModel;
		}

		return null;
	}

	/**
	 * 获取所有的菜单信息列表（同步方法，防止多线程访问时allMenus的内容大小不一致导致问题
	 * @return 菜单列表
	 */
	private synchronized List<MenuDictModel> getAllMenus() {
		if (CollectionUtils.isEmpty(allMenus)) {
			allMenus = roleService.getAllMenus();
		}

		return allMenus;
	}
	
	private List<MenuDictModel> refreshAllMenus(List<MenuDictModel> allMenus)
	{
		List<MenuDictModel> refreshAllMenus = new ArrayList<MenuDictModel>();
		if (CollectionUtils.isNotEmpty(allMenus))
		{
			for (MenuDictModel model : allMenus)
			{
				model.setIsAuth(false);
				refreshAllMenus.add(model);
			}
		}
		
		return refreshAllMenus;
	}

	private boolean isContainsMeun(List<MenuDictModel> menus, MenuDictModel menu) {
		for (MenuDictModel menuDictModel : menus) {
			if (menuDictModel.getMenuID().equals(menu.getMenuID())) {
				return true;
			}
		}

		return false;
	}

	private List<MenuModel> constructMenuModels(List<MenuDictModel> menuDictModels) {
		// 默认没有root根菜单从二级菜单检索
		List<MenuModel> menuList = new ArrayList<MenuModel>();
		if (CollectionUtils.isNotEmpty(menuDictModels)) {
			// 遍历出二级菜单
			for (MenuDictModel model : menuDictModels) {
				if (null == model.getFatherMenuID() && !"退出登录".equals(model.getMenuName())&& !"实时监控".equals(model.getMenuName())) {
					MenuModel menuModel = new MenuModel();
					menuModel.setMenuDictModel(model);
					menuList.add(menuModel);
				}
			}

			for (MenuModel menu : menuList) {
				List<MenuModel> menusModelList = new ArrayList<MenuModel>();

				for (MenuDictModel model1 : menuDictModels) {
					if (menu.getMenuDictModel().getMenuID().equals(model1.getFatherMenuID())
							&& null != model1.getFatherMenuID()) {
						MenuModel menuModel1 = new MenuModel();
						menuModel1.setMenuDictModel(model1);
						menusModelList.add(menuModel1);
					}

				}
				menu.setSubMenuModels(menusModelList);
			}
		}
		return menuList;
	}
	
	private OperationLogInfo getOperationLogInfo(String userName, int operateType,String optCondition,String optDesc)
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
