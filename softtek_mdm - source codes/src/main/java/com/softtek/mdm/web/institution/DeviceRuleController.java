package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.DeviceRuleDepartmentModel;
import com.softtek.mdm.model.DeviceRuleItemRecordModel;
import com.softtek.mdm.model.DeviceRuleItemRelationModel;
import com.softtek.mdm.model.DeviceRuleModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRecordModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;
import com.softtek.mdm.model.DeviceRuleUserModel;
import com.softtek.mdm.model.DeviceRuleVirtualGroupModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NameList;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.queue.MqttQueue;
import com.softtek.mdm.service.DeviceLegalListService;
import com.softtek.mdm.service.DeviceLegalRecordService;
import com.softtek.mdm.service.DeviceRuleDepartmentService;
import com.softtek.mdm.service.DeviceRuleItemRelationService;
import com.softtek.mdm.service.DeviceRuleOperationItemRelationService;
import com.softtek.mdm.service.DeviceRuleService;
import com.softtek.mdm.service.DeviceRuleUserService;
import com.softtek.mdm.service.DeviceRuleVirtualGroupService;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.NameListService;
import com.softtek.mdm.service.ParamSettingService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.service.VirtualGroupService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqttPushThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * 设备规则控制器
 * 
 * @author color.wu 处理和设备规则相关的操作
 */
@Controller
@RequestMapping(value = "/institution/device/rules")
public class DeviceRuleController extends BaseController {

	@Autowired
	private NameListService nameListService;
	@Autowired
	private TreeManager treeManager;
	@Autowired
	private VirtualGroupService virtualGroupService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceRuleService deviceRuleService;
	@Autowired
	private DeviceRuleDepartmentService deviceRuleDepartmentService;
	@Autowired
	private DeviceRuleVirtualGroupService deviceRuleVirtualGroupService;
	@Autowired
	private DeviceRuleUserService deviceRuleUserService;
	@Autowired
	private DeviceRuleItemRelationService deviceRuleItemRelationService;
	@Autowired
	private DeviceRuleOperationItemRelationService deviceRuleOperationItemRelationService;
	@Autowired
	private DeviceLegalListService deviceLegalListService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DeviceLegalRecordService deviceLegalRecordService;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private ParamSettingService paramSettingService;
	
	@Autowired
	@Qualifier("iosDeviceRuleNotifyService")
	private AbstractIosPush abstractIosPush;
	/**
	 * logger日志
	 */
	private Logger logger = Logger.getLogger(DeviceRuleController.class);

	/**
	 * 首页
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Link(family = "institution", label = "web.institution.devicerulecontroller.index.link.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.device.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)
			throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		@SuppressWarnings("unchecked")
		List<StructureModel> list = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		NodeModel nodes = treeManager.bulidTreeIncludeUser(list, organization.getId());
		String jsonStr = "[" + JSONObject.fromObject(nodes).toString() + "]";
		jsonStr = StringUtil.replace(jsonStr, "\"nodes\":[],", "");
		model.addAttribute("tree", jsonStr);
		List<VirtualGroupModel> listGroups = null;
		try {
			listGroups = (List<VirtualGroupModel>) virtualGroupService.findByOrgId(organization.getId());
		} catch (Exception e) {
			logger.error("Method findByOrgId invoked by virtualGroupService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
		model.addAttribute("listGroups", listGroups);
		List<StructureModel> structureList = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		List<Integer> ids = new ArrayList<Integer>();
		if (CollectionUtils.isNotEmpty(structureList)) {
			ids=(List<Integer>) CollectionUtils.collect(structureList, new Transformer() {
				@Override
				public Object transform(Object input) {
					if(input instanceof StructureModel){
						StructureModel ste = (StructureModel) input;
						return ste==null?null:ste.getId();
					}
					return null;
				}
			});
		}
		try {
			List<UserModel> users = userService.findUserNamesByMaps(organization.getId(), ids);
			model.addAttribute("users", users);
			return "institution/device/rule";
		} catch (Exception e) {
			logger.error("Method findUserNamesByMaps invoked by userService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
	 * 获取users
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<UserModel> getUsers(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		List<StructureModel> structureList = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		List<Integer> ids = new ArrayList<Integer>();
		if (CollectionUtils.isNotEmpty(structureList)) {
			ids=(List<Integer>) CollectionUtils.collect(structureList, new Transformer() {
				@Override
				public Object transform(Object input) {
					if(input instanceof StructureModel){
						StructureModel ste = (StructureModel) input;
						return ste==null?null:ste.getId();
					}
					return null;
				}
			});
		}
		try {
			List<UserModel> users = userService.findUserNamesByMaps(organization.getId(), ids);
			return users;
		} catch (Exception e) {
			logger.error("Method findUserNamesByMaps invoked by userService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
	 * 黑白名单
	 * 
	 * @param listType
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/namelist", method = RequestMethod.GET)
	public @ResponseBody List<NameList> getNameListByType(Integer listType, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		try {
			List<NameList> list = nameListService.findListByType(listType, organization.getId());
			return list;
		} catch (Exception e) {
			logger.error("Method findListByType invoked by nameListService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
	 * 获取设备策略
	 * 
	 * @param type
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/devicepolicy", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> findAllDevicePolicyByType(String type, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (type.equals("1")) {
			try {
				List<AndroidDevicePolicy> androidDevicePolics = deviceService.findAll(organization.getId());
				if (CollectionUtils.isNotEmpty(androidDevicePolics)) {
					for (AndroidDevicePolicy androidDevicePolicy : androidDevicePolics) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", androidDevicePolicy.getId());
						map.put("name", androidDevicePolicy.getName());
						list.add(map);
					}
				}
			} catch (Exception e) {
				logger.error(
						"Method findAll invoked by deviceService cause error and the reason is: " + e.getMessage());
				throw e;
			}
		}
		return list;
	}

	/**
	 * 新增/更新设备规则
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.devicerulecontroller.save.log.operatetype", operateContent = "web.institution.devicerulecontroller.save.log.operatecontent", args = {
			"strMsg" })
	@RequestMapping(value = "/saveorupdate", method = RequestMethod.POST)
	public @ResponseBody String save(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int ruleCount = 0;
		int operationCount = 0;
		@SuppressWarnings("rawtypes")
		Enumeration pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			String name = (String) pNames.nextElement();
			if (StringUtil.indexOf(name, "[rule][items]", 0, name.length()) > 0) {
				ruleCount++;
			}
			if (StringUtil.indexOf(name, "[operation][items]", 0, name.length()) > 0) {
				operationCount++;
			}
		}
		//得到规则的指令数目
		ruleCount /= 3;
		//得到操作的指令数目
		operationCount /= 3;

		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		map.put("organization", organization);
		map.put("manager", manager);
		map.put("matchId", StringUtils.trimToEmpty(request.getParameter("data[rule][match]")));
		map.put("ruleId", StringUtils.trimToEmpty(request.getParameter("data[detail][id]")));
		map.put("detail_name", StringUtils.trimToEmpty(request.getParameter("data[detail][name]")));
		map.put("detail_decribe", StringUtils.trimToEmpty(request.getParameter("data[detail][decribe]")));
		map.put("arrange_platform", StringUtils.trimToEmpty(request.getParameter("data[arrange][platform]")));
		map.put("arrange_departIds", StringUtils.trimToEmpty(request.getParameter("data[arrange][departIds]")));
		map.put("arrange_vtlIds", StringUtils.trimToEmpty(request.getParameter("data[arrange][vtlIds]")));
		map.put("arrange_userIds", StringUtils.trimToEmpty(request.getParameter("data[arrange][userIds]")));
		
		//获取设定的规则
		List<DeviceRuleItemRecordModel> list = new ArrayList<DeviceRuleItemRecordModel>();
		List<DeviceRuleItemRelationModel> listRelation = new ArrayList<DeviceRuleItemRelationModel>();
		for (int i = 0; i < ruleCount; i++) {
			DeviceRuleItemRecordModel itemRecord = new DeviceRuleItemRecordModel();
			String str = request.getParameter("data[rule][items][" + i + "][type]");
			itemRecord.setType(StringUtil.isBlank(str) == true ? -1 : Integer.parseInt(str));
			str = request.getParameter("data[rule][items][" + i + "][condition]");
			itemRecord.setCondition(StringUtil.isBlank(str) == true ? -1 : Integer.parseInt(str));
			itemRecord.setValue(request.getParameter("data[rule][items][" + i + "][value]"));
			list.add(itemRecord);
			
			DeviceRuleItemRelationModel itemRelation = new DeviceRuleItemRelationModel();
			itemRelation.setDeviceRuleItemRecord(itemRecord);
			listRelation.add(itemRelation);
		}
		map.put("list", list);
		map.put("listRelation", listRelation);

		//获取设定的操作
		List<DeviceRuleOperationItemRecordModel> listItem = new ArrayList<DeviceRuleOperationItemRecordModel>();
		List<DeviceRuleOperationItemRelationModel> listItemRelation = new ArrayList<DeviceRuleOperationItemRelationModel>();
		for (int i = 0; i < operationCount; i++) {
			DeviceRuleOperationItemRecordModel itemRecord = new DeviceRuleOperationItemRecordModel();
			String str = request.getParameter("data[operation][items][" + i + "][type]");
			itemRecord.setType(StringUtil.isBlank(str) == true ? -1 : Integer.parseInt(str));
			str = request.getParameter("data[operation][items][" + i + "][condition]");
			itemRecord.setCondition(StringUtil.isBlank(str) == true ? -1 : Integer.parseInt(str));
			itemRecord.setValue(request.getParameter("data[operation][items][" + i + "][value]"));
			listItem.add(itemRecord);
			
			DeviceRuleOperationItemRelationModel itemRelation = new DeviceRuleOperationItemRelationModel();
			itemRelation.setDeviceRuleOperationRecord(itemRecord);
			listItemRelation.add(itemRelation);
		}

		map.put("listItem", listItem);
		map.put("listItemRelation", listItemRelation);
		try {
			String result = deviceRuleService.save(map);
			Object[] objects = { result };
			String strMsg = "";
			if (!StringUtil.isBlank(StringUtils.trimToEmpty(request.getParameter("data[detail][id]")))) {
				strMsg = messageSource.getMessage("web.institution.devicerulecontroller.save.strmsg.modify", objects,
						LocaleContextHolder.getLocale());
			} else {
				strMsg = messageSource.getMessage("web.institution.devicerulecontroller.save.strmsg.create", objects,
						LocaleContextHolder.getLocale());
			}
			request.setAttribute("strMsg", strMsg);
			return "success";
		} catch (Exception e) {
			logger.error("Method save invoked by deviceRuleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 首页分页信息
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public @ResponseBody Page getPage(Integer start, Integer length, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());

		String roleType = StringUtils.trimToEmpty(request.getParameter("roletype"));
		Integer type = StringUtils.isEmpty(roleType) ? null : Integer.valueOf(roleType);
		String deviceRuleName = StringUtils.trimToEmpty(request.getParameter("deviceRuleName"));
		String platform=StringUtils.trimToNull(request.getParameter("platform"));

		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNum", start == null ? 0 : start);
		map.put("pageSize", length == null ? 10 : length);
		map.put("orgId", organization.getId());
		map.put("type", type);
		map.put("deviceRuleName", deviceRuleName);
		map.put("user", manager.getUser());
		map.put("platform", platform);
		try {
			Page page = deviceRuleService.findWithPagination(map);
			return page;
		} catch (Exception e) {
			logger.error("Method findWithPagination invoked by deviceRuleService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
	 * 分页显示违规信息
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/legalpage", method = RequestMethod.GET)
	public @ResponseBody Page getLegalPage(Integer rid, Integer start, Integer length, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			return deviceLegalListService.findWithPagation(rid, start, length);
		} catch (Exception e) {
			logger.error("Method findWithPagation invoked by deviceLegalListService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
	 * 分页显示设备违规详情信息
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/legaldetailpage", method = RequestMethod.GET)
	public @ResponseBody Page getLegalDetailPage(Integer rid, Integer did, Integer start, Integer length,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			return deviceLegalRecordService.findLegalRecordByRidAndDid(rid, did, start, length);
		} catch (Exception e) {
			logger.error(
					"Method findLegalRecordByRidAndDid invoked by deviceLegalRecordService cause error and the reason is: "
							+ e.getMessage());
			throw e;
		}
	}

	/**
	 * 删除分页显示设备违规详情信息
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/dellegal", method = RequestMethod.POST)
	public @ResponseBody JsonResult deleteLegalDetailPage(Integer id, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JsonResult jsonResult = null;
		try {
			int flag = deviceLegalRecordService.deleteWithPk(id);
			if (flag > 0) {
				jsonResult = createJsonResult(MessageType.success,
						"web.institution.devicerulecontroller.deletelegaldetailpage.delete.success", null, null);
			} else {
				jsonResult = createJsonResult(MessageType.success,
						"web.institution.devicerulecontroller.deletelegaldetailpage.delete.failed", null, null);
			}
			return jsonResult;
		} catch (Exception e) {
			logger.error("Method deleteWithPk invoked by deviceLegalRecordService cause error and the reason is: "
					+ e.getMessage());
			throw e;
		}
	}

	/**
	 * 查看规则
	 * 
	 * @param rid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/newly", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> newly(Integer rid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取基本信息
		try {
			DeviceRuleModel deviceRule = deviceRuleService.findOne(rid);
			map.put("deviceRule", deviceRule);
			if (deviceRule != null) {
				List<DeviceRuleDepartmentModel> department = deviceRuleDepartmentService
						.findAllByRuleId(deviceRule.getId());
				map.put("deviceRuleDepartment", department);
				List<DeviceRuleVirtualGroupModel> virtualGroup = deviceRuleVirtualGroupService
						.findAllByRuleId(deviceRule.getId());
				map.put("deviceRuleVirtualGroup", virtualGroup);
				List<DeviceRuleUserModel> users = deviceRuleUserService.findAllByRuleId(deviceRule.getId());
				map.put("deviceRuleUser", users);
				List<DeviceRuleItemRelationModel> itemRelation = deviceRuleItemRelationService
						.findAllByRuleId(deviceRule.getId());
				map.put("deviceRuleItemRelation", itemRelation);
				List<DeviceRuleOperationItemRelationModel> operationItemRelation = deviceRuleOperationItemRelationService
						.findAllByRuleId(deviceRule.getId());
				map.put("deviceRuleOperationItemRelation", operationItemRelation);
			}
			return map;
		} catch (Exception e) {
			logger.error(
					"Method findOne invoked by deviceRuleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 启用/禁用规则
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.devicerulecontroller.toggle.log.operatetype", operateContent = "web.institution.devicerulecontroller.toggle.log.operatecontent", args = {
			"strMsg" })
	@RequestMapping(value = "/toggle", method = RequestMethod.POST)
	public @ResponseBody JsonResult toggle(Integer rid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		DeviceRuleModel deviceRule = deviceRuleService.findOne(rid);
		Integer isactive = deviceRule.getIsactive() > 0?0:1;
		DeviceRuleModel temp = new DeviceRuleModel();
		temp.setId(deviceRule.getId());
		temp.setIsactive(isactive);
		try {
			int flag=deviceRuleService.update(temp);
			if(flag>0){
				if(isactive==1){
					//当前规则原先是禁用，现在启用了
					List<Integer> ids = deviceRuleService.findUserIdsByPk(rid);
					Map<String, String> data=new HashMap<String, String>();
					data.put("deviceRule", JSONObject.fromObject(deviceRule).toString());
					// 将设备规则推送给用户
					if (CollectionUtils.isNotEmpty(ids)) {
						List<KeyValue> kvs=MqttQueue.generateDatas(ids, data);
						MqttPushThread mqttPush=new MqttPushThread(kvs);
						taskExecutor.execute(mqttPush);
						
						//（仅iOS设备有效）
						iosDeviceRulePush(deviceRule.getId(),ids);
					}
					
				}
			}
			
		} catch (Exception e) {
			logger.error("Method update invoked by deviceRuleService cause error and the reason is: " + e.getMessage());
		}
		
		// 更新操作前台提示
		JsonResult jsonResult = null;
		Object[] objects = { deviceRule.getName() };
		String strMsg = "";
		if (isactive > 0) {
			strMsg = messageSource.getMessage("web.institution.devicerulecontroller.toggle.strmsg.enable", objects,
					LocaleContextHolder.getLocale());
			jsonResult = createJsonResult(MessageType.success, "web.institution.devicerulecontroller.toggle.enable",
					null, null);
		} else {
			strMsg = messageSource.getMessage("web.institution.devicerulecontroller.toggle.strmsg.disable", objects,
					LocaleContextHolder.getLocale());
			jsonResult = createJsonResult(MessageType.success, "web.institution.devicerulecontroller.toggle.disable",
					null, null);
		}
		request.setAttribute("strMsg", strMsg);
		return jsonResult;
	}

	/**
	 * 进行iOS设备规则的检查和处理
	 */
	private void iosDeviceRulePush(Integer rid,List<Integer> uids){
		if(CollectionUtils.isEmpty(uids)){
			return ;
		}
		//是否需要消息推送
		boolean isNeedNotify=false;
		//是否需要命令推送
		boolean isNeedCommand=false;
		List<DeviceRuleItemRelationModel>  list=deviceRuleItemRelationService.findAllByRuleId(rid);
		if(CollectionUtils.isNotEmpty(list)){
			for (DeviceRuleItemRelationModel itemR : list) {
				DeviceRuleItemRecordModel item=itemR.getDeviceRuleItemRecord();
				if(item.getType()==1){
					isNeedCommand=true;
				}else{
					isNeedNotify=true;
				}
			}
		}
		if(isNeedNotify){
			//发送消息通知给iOS，走APNS推送
			Map<String, Object> map=new HashMap<>();
			map.put("deviceRuleId", rid);
			map.put("userIds", uids);
			deviceRuleService.notifyToIos(map);
		}
		if(isNeedCommand){
			//发送Command命令，走MDM Server推送
			Map<String, Object> cmdMap=new HashMap<>();
			cmdMap.put("userIds", uids);
			List<String> cmds=new ArrayList<>();
			cmds.add(PlistUtil.RequestType.InstalledApplicationList);
			cmdMap.put("cmds", cmds);
			cmdMap.put("deviceRuleId", rid);
			abstractIosPush.nofity(cmdMap);
		}
		
	}
	
	
	/**
	 * 删除规则
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.devicerulecontroller.delete.log.operatetype", operateContent = "web.institution.devicerulecontroller.delete.log.operatecontent", args = {
			"name" })
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody JsonResult delete(Integer rid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			DeviceRuleModel deviceRule = deviceRuleService.findOne(rid);
			request.setAttribute("name", deviceRule.getName());
			List<Integer> ids = deviceRuleService.findUserIdsByPk(rid);
			Map<String, String> data=new HashMap<String, String>();
			data.put("deviceRuleDelete", JSONObject.fromObject(deviceRule).toString());
			// 推送用户策略
			if (CollectionUtils.isNotEmpty(ids)) {
				List<KeyValue> kvs=MqttQueue.generateDatas(ids, data);
				MqttPushThread mqttPush=new MqttPushThread(kvs);
				taskExecutor.execute(mqttPush);
			}
			deviceRuleService.truncateWithPk(rid);
			JsonResult jsonResult = null;
			jsonResult = createJsonResult(MessageType.success, "web.institution.devicerulecontroller.delete.success",
					null, null);
			return jsonResult;
		} catch (Exception e) {
			logger.error(
					"Method findOne invoked by deviceRuleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 复制规则
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.devicerulecontroller.copy.log.operatetype", operateContent = "web.institution.devicerulecontroller.copy.log.operatecontent", args = {
			"name" })
	@RequestMapping(value = "/copy", method = RequestMethod.POST)
	public @ResponseBody JsonResult copy(Integer rid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// 保存复制的规则
		DeviceRuleModel copy_rule = deviceRuleService.findOne(rid);
		request.setAttribute("name", copy_rule.getName());
		String sbStr=messageSource.getMessage("tiles.views.institution.device.rule.copy.fb", null, LocaleContextHolder.getLocale());
		copy_rule.setName(copy_rule.getName() + sbStr);
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		copy_rule.setCreateBy(manager.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rid", rid);
		map.put("copy_rule", copy_rule);
		try {
			deviceRuleService.copy(map);
		} catch (Exception e) {
			logger.error("Method copy invoked by deviceRuleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		JsonResult jsonResult = null;
		jsonResult = createJsonResult(MessageType.success, "web.institution.devicerulecontroller.copy.success", null,
				null);
		return jsonResult;
	}

	/**
	 * 检测规则
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.devicerulecontroller.testing.log.operatetype", operateContent = "web.institution.devicerulecontroller.testing.log.operatecontent", args = {
			"name" })
	@RequestMapping(value = "/testing", method = RequestMethod.POST)
	public @ResponseBody JsonResult testing(Integer rid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, String> data = new HashMap<String, String>();
		DeviceRuleModel temp = new DeviceRuleModel();
		
		DeviceRuleModel test_rule = deviceRuleService.findOne(rid);
		temp.setId(test_rule.getId());
		DateTime date = DateTime.now();
		temp.setUpdateTime(date.toDate());
		SystemParamSetModel sysParam = paramSettingService.querySysParamSetting();
		date = date.plusMinutes(sysParam.getIllegalInfoCollectTime());
		temp.setNext_check_time(date.toDate());
		try {
			deviceRuleService.update(temp);
		} catch (Exception e) {
			logger.error("Method update invoked by deviceRuleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		request.setAttribute("name", test_rule.getName());
		data.put("deviceRule", JSONObject.fromObject(temp).toString());
		/*data.put("lastDate",DateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
		data.put("system", "0");*/
		// 获取规则下的用户id列表
		List<Integer> ids = deviceRuleService.findUserIdsByPk(rid);
		// 推送用户策略
		if (CollectionUtils.isNotEmpty(ids)) {
			List<KeyValue> kvs=MqttQueue.generateDatas(ids, data);
			MqttPushThread mqttPush=new MqttPushThread(kvs);
			taskExecutor.execute(mqttPush);
		}
		//推送给ios设备
		iosDeviceRulePush(test_rule.getId(),ids);
		JsonResult jsonResult = createJsonResult(MessageType.success,
				"web.institution.devicerulecontroller.testing.success", null, null);
		return jsonResult;
	}

	/**
	 * 设备违规日志列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@Link(family = "institution", label = "web.institution.devicerulecontroller.devicelog.link.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.devicerulecontroller.index.link.belong")
	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public String deviceLog(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)
			throws IOException {
		return "institution/device/rule/log";
	}

	/**
	 * 获取页面数据
	 * 
	 * @param start
	 * @param length
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logpage", method = RequestMethod.GET)
	public @ResponseBody Page getLogData(Integer start, Integer length, HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String roleName = StringUtils.trimToEmpty(request.getParameter("rolename"));
		String userName = StringUtils.trimToEmpty(request.getParameter("rolename"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", roleName);
		map.put("userName", userName);
		map.put("orgId", organization.getId());
		map.put("pageNum", start == null ? 0 : start);
		map.put("pageSize", length == null ? 10 : length);
		try {
			return deviceLegalRecordService.queryLegalRecordByParams(map);
		} catch (Exception e) {
			logger.error(
					"Method queryLegalRecordByParams invoked by deviceLegalRecordService cause error and the reason is: "
							+ e.getMessage());
			throw e;
		}
	}

	@RequestMapping(value = "/del-logs", method = RequestMethod.POST)
	public @ResponseBody JsonResult dellogs(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		deviceLegalRecordService.deleteRecordsByOrgId(organization.getId());
		return createJsonResult(MessageType.success, "institution.device.rule.illegal.delete.success.label", null,
				null);
	}
}
