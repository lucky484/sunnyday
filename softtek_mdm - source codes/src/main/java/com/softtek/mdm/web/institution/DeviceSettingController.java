package com.softtek.mdm.web.institution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.model.ClientCommandModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.model.DeviceSettingModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MessageSendModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.SecurityEventLogModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.model.SysmanageLog;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.ClientCommandService;
import com.softtek.mdm.service.DeletedDeviceService;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.DeviceSaftyService;
import com.softtek.mdm.service.MessageSendService;
import com.softtek.mdm.service.SecurityEventLogService;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.service.SysmanageLogService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.AuthStatus;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.IosPushUtil;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/institution/deviceManager")
public class DeviceSettingController {

	@Autowired
	private DeviceManagerService deviceManagerService;

	@Autowired
	private MessageSendService messageSendService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SysmanageLogService sysmanageLogService;

	@Autowired
	private SysmanageDeviceLogService sysmanageDeviceLogService;

	@Autowired
	private SecurityEventLogService securityEventLogService;

	@Autowired
	private ClientCommandService clientCommandService;

	@Autowired
	private DeletedDeviceService deletedDeviceService;

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private UserService userService;

	@Autowired
	private DeviceSaftyService deviceSaftyService;

	@Autowired
	@Qualifier("IosDeviceSettingNotifyServiceImpl")
	private AbstractIosPush abstractIosPush;

	public final static String TOPIC = "androidAll";

	private Logger logger = Logger.getLogger(DeviceSettingController.class);

	@RequestMapping(value = "sendCommandToClient", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendCommandToClient(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<StructureModel> strList = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		// 鑾峰彇鏈烘瀯
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		// 鑾峰彇绠＄悊鍛�
		Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		OrganizationModel organization = (OrganizationModel) orgObj;
		ManagerModel manager = (ManagerModel) obj;
		
		Integer flag = Integer.valueOf(request.getParameter("flag"));
		String selectVal = request.getParameter("selectVal");// 根据选择的类型来决定推送的方式
		Integer departmentId = Integer.valueOf(request.getParameter("departmentId"));// 查询部门下的人的设备
		String parentDepartmentId = request.getParameter("parentDepartmentId"); // 用来判断是否是机构
		String sns = request.getParameter("sn");
		String ids = "";
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			ids = request.getParameter("id");
		}
		String[] sn = null;
		String[] userId = null;
		String[] id = null;
		String userIds = request.getParameter("userId");
		List<DeviceManagerModel> list = DeviceManagerController.searchList;
		List<DeviceManagerModel> list1 = new ArrayList<DeviceManagerModel>();
		
		if ("2".equals(selectVal)) {
			if (list == null) {
				list1 = deviceManagerService.queryAllDeviceList(departmentId, organization.getId(), strList);
				id = new String[list1.size()];
				sn = new String[list1.size()];
				userId = new String[list1.size()];
				for (int i = 0; i < list1.size(); i++) {
					id[i] = list1.get(i).getId().toString();
					sn[i] = list1.get(i).getSn();
					userId[i] = list1.get(i).getUserId().toString();
				}
			}
		} else {
			id = ids.split(",");
			sn = sns.split(",");
			userId = userIds.split(",");
		}
		if (StringUtils.isNotEmpty(userIds)) {
			Integer result = null;
			if (flag != null) {
				switch (flag) {
				case 0:
					result = unlockDevice(selectVal, parentDepartmentId, list, result, organization, manager, id,
							userId, sn, list1);
					break;
				case 1:
					int lockStatus = Integer.valueOf(request.getParameter("lockStatus"));
					result = lockDevice(selectVal, parentDepartmentId, list, result, organization, manager, id, userId,
							sn, lockStatus, list1);
					break;
				case 3:
					refrashDeviceInfo(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn,
							list1);
					break;
				case 5:
					String lockPassword = request.getParameter("lockPassword");
					lockPassword(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn,
							lockPassword);
					break;
				case 6:
					lockScreen(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn, list1);
					break;
				case 7:
					defaultSetting(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn, list1);
					break;
				case 8:
					cleanPassword(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn, list1);
					break;
				case 9:
					deviceBell(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn, list1);
					break;
				case 10:
					logOff(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn, list1);
					break;
				case 11:
					deleteDevice(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn, request,
							list1);
					break;
				case 12:
					enableUnbundle(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn, list1);
					break;
				case 13:
					disableUnbundle(selectVal, parentDepartmentId, list, organization, manager, id, userId, sn, list1);
					break;
				default:
					break;
				}
				if(result!=null){
					map.put("result", result);
				}else{
					map.put("status", "success");
				}
			}
		}
		return map;
	}

	@RequestMapping(value = "sendInfoMessageBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendInfoMessageBatch(HttpServletRequest request, HttpSession session) {
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		@SuppressWarnings("unchecked")
		List<StructureModel> strList = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		String selectVal = request.getParameter("selectVal");
		Integer departmentId = Integer.valueOf(request.getParameter("departmentId"));
		String parentDepartmentId = request.getParameter("parentDepartmentId");
		List<DeviceManagerModel> list = DeviceManagerController.searchList;
		List<String> deviceToken = new ArrayList<String>();
		Map<String, String> customDictionary = new HashMap<String, String>();
		String userIds = "";
		String sns = "";
		String[] sn = null;
		String[] userId = null;
		List<DeviceManagerModel> list1 = null;
		if (!"2".equals(selectVal)) {
			userIds = request.getParameter("userId");
			sns = request.getParameter("sn");
			sn = sns.split(",");
			userId = userIds.split(",");
		} else {
			if (list == null) {
				list1 = deviceManagerService.queryAllDeviceList(departmentId, organization.getId(), strList);
				userId = new String[list1.size()];
				sn = new String[list1.size()];
				for (int i = 0; i < list1.size(); i++) {
					sn[i] = list1.get(i).getSn();
					userId[i] = list1.get(i).getUserId().toString();
				}
			}
		}
		String messageTitle = request.getParameter("messageTitle");
		String message = request.getParameter("message");
		MessageSendModel messageSend = new MessageSendModel();
		// 从session获取管理员的信息
		Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		ManagerModel manager = (ManagerModel) obj;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						Map<String, String> extra = new HashMap<String, String>();
						messageSend.setMessage_title(messageTitle);
						messageSend.setMessage(message);
						messageSend.setCreateBy(managerModel.getId());
						messageSend.setCreateTime(new Date());
						messageSend.setUpdateTime(new Date());
						messageSend.setUpdateBy(managerModel.getId());
						messageSend.setName(managerModel.getName());
						messageSend.setCreateDateStr(sdf.format(new Date()));
						messageSend.setUdid(list.get(i).getSn());
						messageSend.setDeviceId(list.get(i).getId());
						messageSend.setUserId(list.get(i).getUserId());
						messageSend.setNotification(1);
						int result = messageSendService.saveMessage(messageSend);
						if (result == 1) {
							DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
									list.get(i).getUserId());
							if (deviceInfo.getDevice_type().equals("android")) {
								extra.put("messageSend", JSONObject.fromObject(messageSend).toString());
								MqProducerThread mqProducerThread = new MqProducerThread(
										list.get(i).getUserId().toString(), null, null, 2, extra);
								taskExecutor.execute(mqProducerThread);
							} else if (deviceInfo.getDevice_type().equals("ios")) {
								deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
							}
							Object[] args = { message, deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
							String operateType = messageSource.getMessage("logs.device.operate.type.send.message", null,
									LocaleContextHolder.getLocale());
							String operateContent = messageSource.getMessage("logs.device.operate.send.message.content",
									args, LocaleContextHolder.getLocale());
							saveSystemLog(organization, operateContent, manager, operateType);
						}
					}
					if (deviceToken.size() > 0) {
						customDictionary.put("message", message);
						IosPushUtil.pushDataToIosTo(deviceToken, messageTitle, 1, "default", customDictionary);
					}
				} else {
					Map<String, String> extra = new HashMap<String, String>();
					for (DeviceManagerModel device : list1) {
						deviceToken.add(device.getDeviceToken().replaceAll(" ", ""));
						messageSend.setMessage_title(messageTitle);
						messageSend.setMessage(message);
						messageSend.setCreateBy(managerModel.getId());
						messageSend.setCreateTime(new Date());
						messageSend.setUpdateTime(new Date());
						messageSend.setUpdateBy(managerModel.getId());
						messageSend.setName(managerModel.getName());
						messageSend.setCreateDateStr(sdf.format(new Date()));
						messageSend.setUdid(device.getSn());
						messageSend.setDeviceId(device.getId());
						messageSend.setUserId(device.getUserId());
						messageSend.setNotification(1);
						messageSendService.saveMessage(messageSend);
					}
					extra.put("messageSend", JSONObject.fromObject(messageSend).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
					if (deviceToken.size() > 0) {
						customDictionary.put("message", message);
						IosPushUtil.pushDataToIosTo(deviceToken, messageTitle, 1, "default", customDictionary);
					}
					// 骞挎挱鏃跺彧瀛樹竴鏉℃棩蹇�
					Object[] args = { message };
					String operateType = messageSource.getMessage("logs.device.operate.type.send.message", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.send.message.all", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
				}
			} else {
				for (int i = 0; i < userId.length; i++) {
					Map<String, String> extra = new HashMap<String, String>();
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i])); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					messageSend.setMessage_title(messageTitle);
					messageSend.setMessage(message);
					messageSend.setCreateBy(managerModel.getId());
					messageSend.setCreateTime(new Date());
					messageSend.setUpdateTime(new Date());
					messageSend.setUpdateBy(managerModel.getId());
					messageSend.setName(managerModel.getName());
					messageSend.setCreateDateStr(sdf.format(new Date()));
					messageSend.setUdid(sn[i]);
					messageSend.setDeviceId(deviceInfo.getId());
					messageSend.setUserId(Integer.valueOf(userId[i]));
					messageSend.setNotification(1);
					int result = messageSendService.saveMessage(messageSend);
					if (result == 1) {
						if (deviceInfo.getDevice_type().equals("android")) {
							extra.put("messageSend", JSONObject.fromObject(messageSend).toString());
							MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
							taskExecutor.execute(mqProducerThread);
						} else if (deviceInfo.getDevice_type().equals("ios")) {
							deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
						}
						Object[] args = { message, deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
						String operateType = messageSource.getMessage("logs.device.operate.type.send.message", null,
								LocaleContextHolder.getLocale());
						String operateContent = messageSource.getMessage("logs.device.operate.send.message.content",
								args, LocaleContextHolder.getLocale());
						saveSystemLog(organization, operateContent, manager, operateType);
					}
				}
				if (deviceToken.size() > 0) {
					customDictionary.put("message", message);
					IosPushUtil.pushDataToIosTo(deviceToken, messageTitle, 1, "default", customDictionary);
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage());
			throw e;
		}
		map.put("messageSend", messageSend);
		return map;
	}

	// 标记设备丢失或找回
	@RequestMapping(value = "remarkDevice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> remarkDevice(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer type = Integer.valueOf(request.getParameter("type"));
		Integer departmentId = Integer.valueOf(request.getParameter("departmentId"));
		String selectVal = request.getParameter("selectVal");
		String parentDepartmentId = request.getParameter("parentDepartmentId");
		@SuppressWarnings("unchecked")
		List<StructureModel> strList = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		List<DeviceManagerModel> list1 = null;
		List<DeviceManagerModel> list = DeviceManagerController.searchList;
		List<String> deviceToken = new ArrayList<String>();
		Map<String, String> customDictionary = new HashMap<String, String>();
		String sns = request.getParameter("sn");
		String ids = request.getParameter("id");
		String userIds = request.getParameter("userId");
		String[] sn = null;
		String[] id = null;
		String[] userId = null;
		if ("2".equals(selectVal)) {
			if (list == null) {
				list1 = deviceManagerService.queryAllDeviceList(departmentId, organization.getId(), strList);
				sn = new String[list1.size()];
				id = new String[list1.size()];
				userId = new String[list1.size()];
				for (int i = 0; i < list1.size(); i++) {
					sn[i] = list1.get(i).getSn();
					id[i] = list1.get(i).getId().toString();
					userId[i] = list1.get(i).getUserId().toString();
				}
			}
		} else {
			sn = sns.split(",");
			id = ids.split(",");
			userId = userIds.split(",");
		}
		DeviceManagerModel deviceManager = new DeviceManagerModel();
		DeviceSettingModel deviceSetting = new DeviceSettingModel();
		DeviceSaftyModel deviceSafty = new DeviceSaftyModel();
		Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		ManagerModel manager = (ManagerModel) obj;
		try {
			if (type == 1) {
				deviceManager.setLost_status(0); // 标记设备丢失
				deviceSetting.setRemarkDevice(1); // 1表示设备丢失
				deviceManager.setVisit_status(0);// 在设备丢失时，同时锁定设备
				deviceSafty.setMdm(6);
				customDictionary.put("lockDevice", "1");
			} else {
				deviceSetting.setRemarkDevice(2); // 2表示设备找回
				deviceManager.setLost_status(null); // 标记设备找回
				deviceManager.setVisit_status(null);// 在设备找回时，同时解锁设备
				customDictionary.put("unLockDevice", "1");
			}
			if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						Map<String, String> extra = new HashMap<String, String>();
						DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
								list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
						deviceSetting.setSn(list.get(i).getSn());
						deviceManager.setId(list.get(i).getId());
						deviceSafty.setDevice_id(list.get(i).getId());
						deviceSaftyService.updateSaftyByDeviceId(deviceSafty);
						deviceManagerService.updateVisitStatus(deviceManager);
						int result = deviceManagerService.updateLostStatus(deviceManager);
						if (result == 1) {
							if (deviceInfo.getDevice_type().equals("android")) {
								extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
								MqProducerThread mqProducerThread = new MqProducerThread(
										list.get(i).getUserId().toString(), null, null, 2, extra);
								taskExecutor.execute(mqProducerThread);
							} else if (deviceInfo.getDevice_type().equals("ios")) {
								deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
							}
							Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
							String operateType = messageSource.getMessage("logs.device.operate.type.delete.device",
									null, LocaleContextHolder.getLocale());
							String operateContent = messageSource.getMessage(
									"logs.device.operate.remark.device.find.content", args,
									LocaleContextHolder.getLocale());
							saveSystemLog(organization, operateContent, manager, operateType);
							String userType = "";
							if (deviceInfo.getUserType() == 4) {
								userType = messageSource.getMessage("logs.device.user.normal.type", null,
										LocaleContextHolder.getLocale());
							} else {
								userType = messageSource.getMessage("logs.device.user.manager.type", null,
										LocaleContextHolder.getLocale());
							}
							String deciveOperateType = messageSource.getMessage("logs.device.operate.remark.find.type",
									null, LocaleContextHolder.getLocale());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
									deviceInfo.getDevice_name(), deciveOperateType };
							String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
									LocaleContextHolder.getLocale());
							String eventType = "15";
							saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(),
									deviceOperateContent, organization, manager);
						}
					}
					if (deviceToken.size() > 0) {
						IosPushUtil.pushDataToIos(deviceToken, null, 1, "", customDictionary);
					}
				} else {
					deviceManager.setOrgId(organization.getId());
					deviceSaftyService.updateAll(deviceSafty);
					deviceManagerService.updateLostStatusAll(deviceManager);
					Map<String, String> extra = new HashMap<String, String>();
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
					for (DeviceManagerModel d : list1) {
						if ("ios".equalsIgnoreCase(d.getDevice_type())) {
							deviceToken.add(d.getDeviceToken().replaceAll(" ", ""));
						}
					}
					if (deviceToken.size() > 0) {
						IosPushUtil.pushDataToIos(deviceToken, null, 1, "", customDictionary);
					}
				}
			} else {
				for (int i = 0; i < id.length; i++) {
					Map<String, String> extra = new HashMap<String, String>();
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i])); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					deviceSetting.setSn(sn[i]);
					deviceManager.setId(Integer.valueOf(id[i]));
					deviceSafty.setDevice_id(Integer.valueOf(id[i]));
					deviceSaftyService.updateSaftyByDeviceId(deviceSafty);
					deviceManagerService.updateVisitStatus(deviceManager);
					int result = deviceManagerService.updateLostStatus(deviceManager);
					if (result == 1) {
						if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
							extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
							MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
							taskExecutor.execute(mqProducerThread);
						} else if ("ios".equalsIgnoreCase(deviceInfo.getDevice_type())) {
							deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
						}
						Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
						String operateType = messageSource.getMessage("logs.device.operate.type.delete.device", null,
								LocaleContextHolder.getLocale());
						String operateContent = messageSource.getMessage(
								"logs.device.operate.remark.device.find.content", args,
								LocaleContextHolder.getLocale());
						saveSystemLog(organization, operateContent, manager, operateType);
						String userType = "";
						if (deviceInfo.getUserType() == 4) {
							userType = messageSource.getMessage("logs.device.user.normal.type", null,
									LocaleContextHolder.getLocale());
						} else {
							userType = messageSource.getMessage("logs.device.user.manager.type", null,
									LocaleContextHolder.getLocale());
						}
						String deciveOperateType = messageSource.getMessage("logs.device.operate.remark.find.type",
								null, LocaleContextHolder.getLocale());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
								deviceInfo.getDevice_name(), deciveOperateType };
						String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
								LocaleContextHolder.getLocale());
						String eventType = "15";
						saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
					}
				}
				if (deviceToken.size() > 0) {
					IosPushUtil.pushDataToIos(deviceToken, null, 1, "", customDictionary);
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return map;
	}

	/**
	 * 淇濆瓨绯荤粺鏃ュ織
	 * 
	 * @param organization
	 * @param operateContent
	 * @param manager
	 * @param operateType
	 */
	public void saveSystemLog(OrganizationModel organization, String operateContent, ManagerModel manager,
			String operateType) {
		SysmanageLog systemLog = new SysmanageLog();
		systemLog.setUsername(manager.getUsername());
		systemLog.setOrgId(organization.getId());
		systemLog.setOperateContent(operateContent);
		systemLog.setUserId(manager.getId());
		if (manager.getUser() == null) {
			systemLog.setUserType(AuthStatus.SOFTTEK_INSTITUTION.toString());
		} else {
			systemLog.setUserType(AuthStatus.SOFTTEK_DPT_MANAGER.toString());
		}
		systemLog.setOperateType(operateType);
		systemLog.setOperateContent(operateContent);
		systemLog.setCreateDate(new Date());
		systemLog.setCreateUser(manager.getId());
		systemLog.setUpdateDate(new Date());
		systemLog.setUpdateUser(manager.getId());
		systemLog.setOperateTime(new Date());
		sysmanageLogService.insertSelective(systemLog);
	}

	/**
	 * 淇濆瓨璁惧鏃ュ織
	 * 
	 * @param deviceInfo
	 * @param eventType
	 * @param userId
	 * @param deviceOperateContent
	 * @param organization
	 * @param manager
	 */
	public void saveDeviceLog(DeviceManagerModel deviceInfo, String eventType, String userId,
			String deviceOperateContent, OrganizationModel organization, ManagerModel manager) {
		SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
		deviceLog.setEventType(eventType); // 时间类型
		deviceLog.setDeviceId(deviceInfo.getId());
		deviceLog.setDeviceName(deviceInfo.getDevice_name());
		if (StringUtil.isNotBlank(userId)) {
			deviceLog.setUserId(Integer.valueOf(userId));
		}
		UserModel user = userService.findOne(Integer.valueOf(userId));
		deviceLog.setUserName(user.getRealname());
		deviceLog.setOperateContent(deviceOperateContent);
		deviceLog.setOrgId(organization.getId());
		deviceLog.setOperateDate(new Date());
		deviceLog.setCreateDate(new Date());
		deviceLog.setCreateUser(manager.getId());
		deviceLog.setUpdateDate(new Date());
		deviceLog.setUpdateUser(manager.getId());
		sysmanageDeviceLogService.insertSelective(deviceLog);
	}

	// 解锁设备
	public int unlockDevice(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list, Integer result,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = null;
		DeviceManagerModel deviceManager = new DeviceManagerModel();
		List<String> deviceToken = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting = new DeviceSettingModel();
					deviceSetting.setLockDevice(3);
					deviceSetting.setSn(list.get(i).getSn());
					deviceManager.setVisit_status(null);
					deviceManager.setId(list.get(i).getId());
					if (result == 1) {
						if ("android".equalsIgnoreCase(list.get(i).getDevice_type())) {
							extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
							MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
									null, null, 2, extra);
							taskExecutor.execute(mqProducerThread);
						} else if (list.get(i).getDevice_type().equals("ios")) {
							deviceToken.add(list.get(i).getDeviceToken().replaceAll(" ", ""));
						}
						// 璁板綍鏃ュ織
						DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
								list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
						Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
						String operateType = messageSource.getMessage("logs.device.operate.type.unlock", null,
								LocaleContextHolder.getLocale());
						String operateContent = messageSource.getMessage("logs.device.operate.unlock.content", args,
								LocaleContextHolder.getLocale());
						saveSystemLog(organization, operateContent, manager, operateType);
						String userType = "";
						if (deviceInfo.getUserType() == 4) {
							userType = messageSource.getMessage("logs.device.user.normal.type", null,
									LocaleContextHolder.getLocale());
						} else {
							userType = messageSource.getMessage("logs.device.user.manager.type", null,
									LocaleContextHolder.getLocale());
						}
						String deciveOperateType = messageSource.getMessage("logs.device.operate.type", null,
								LocaleContextHolder.getLocale());
						Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
								deviceInfo.getDevice_name(), deciveOperateType };
						String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
								LocaleContextHolder.getLocale());
						String eventType = "0";
						saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
								organization, manager);
					}
					if (deviceToken.size() > 0) {
						map.put("unLockDevice", "1");
						IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
					}
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setLockDevice(3);
				deviceManager.setOrgId(organization.getId());
				deviceManager.setVisit_status(null);
				deviceManagerService.updateVisitStatusAll(deviceManager);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				for (DeviceManagerModel d : list1) {
					if (d.getDevice_type().equals("ios")) {
						deviceToken.add(d.getDeviceToken().replaceAll(" ", ""));
					}
				}
				if (deviceToken.size() > 0) {
					map.put("unLockDevice", "1");
					IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
				}
			}
		} else {
			for (int i = 0; i < id.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting = new DeviceSettingModel();
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i]));
				deviceSetting.setLockDevice(3); // 0琛ㄧず瑙ｉ攣
				deviceSetting.setSn(sn[i]);
				deviceManager.setVisit_status(null);
				deviceManager.setId(Integer.valueOf(id[i]));
				result = deviceManagerService.updateVisitStatus(deviceManager);
				if (result == 1) {
					if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if ("ios".equalsIgnoreCase(deviceInfo.getDevice_type())) {
						deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
					}
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.unlock", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.unlock.content", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.type", null,
							LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "0";
					saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
				}
			}
			if (deviceToken.size() > 0) {
				map.put("unLockDevice", "1");
				IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
			}
		}
		return result;
	}

	// 锁定设备
	public int lockDevice(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list, Integer result,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			int lockStatus, List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = new DeviceSettingModel();
		DeviceManagerModel deviceManager = new DeviceManagerModel();
		List<String> deviceToken = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		String deciveOperateType = "";
		if (lockStatus == 2) {
			deviceSetting.setLockDevice(1);
			deviceManager.setVisit_status(0);
			deciveOperateType = messageSource.getMessage("logs.device.operate.lock.type", null,
					LocaleContextHolder.getLocale());
		} else if (lockStatus == 3) {
			deviceSetting.setLockDevice(2);
			deviceManager.setVisit_status(1);
			deciveOperateType = messageSource.getMessage("logs.device.operate.lock.other.type", null,
					LocaleContextHolder.getLocale());
		}
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting.setSn(list.get(i).getSn());
					deviceManager.setId(list.get(i).getId());
					result = deviceManagerService.updateVisitStatus(deviceManager);
					if (result == 1) {
						if ("android".equalsIgnoreCase(list.get(i).getDevice_type())) {
							extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
							MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
									null, null, 2, extra);
							taskExecutor.execute(mqProducerThread);
						} else if ("ios".equalsIgnoreCase(list.get(i).getDevice_type())) {
							deviceToken.add(list.get(i).getDeviceToken().replaceAll(" ", ""));
						}
						DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
								list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
						String lockType = messageSource.getMessage("logs.device.operate.lock.type.visit", null,
								LocaleContextHolder.getLocale());
						Object[] args = { lockType, deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
						String operateType = messageSource.getMessage("logs.root.department.name", null,
								LocaleContextHolder.getLocale());
						String operateContent = messageSource.getMessage("logs.device.operate.lock.content", args,
								LocaleContextHolder.getLocale());
						saveSystemLog(organization, operateContent, manager, operateType);
						String userType = "";
						if (deviceInfo.getUserType() == 4) {
							userType = messageSource.getMessage("logs.device.user.normal.type", null,
									LocaleContextHolder.getLocale());
						} else {
							userType = messageSource.getMessage("logs.device.user.manager.type", null,
									LocaleContextHolder.getLocale());
						}
						Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
								deviceInfo.getDevice_name(), deciveOperateType };
						String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
								LocaleContextHolder.getLocale());
						String eventType = "1";
						saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
								organization, manager);
						// 瀹夊叏浜嬩欢鏃ュ織淇濆瓨
						// 淇濆瓨瀹夊叏浜嬩欢鏃ュ織
						SecurityEventLogModel securityEventLog = new SecurityEventLogModel();
						Object[] object = { manager.getName(), sdf.format(new Date()), deviceInfo.getDevice_name() };
						String operateContent1 = messageSource.getMessage(
								"web.device.devicelogincontroller.index.device.lock.msg", object,
								LocaleContextHolder.getLocale());
						securityEventLog.setEventType("1");
						securityEventLog.setLevel("2");
						securityEventLog.setOperateContent(operateContent1);
						securityEventLog.setCreateBy(manager.getId());
						securityEventLog.setUpdateBy(manager.getId());
						securityEventLogService.insertSecurityEventLog(securityEventLog);
					}
					if (deviceToken.size() > 0) {
						map.put("lockDevice", "1");
						IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
					}
				}
			} else {
				deviceManager.setOrgId(organization.getId());
				deviceManagerService.updateVisitStatusAll(deviceManager);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				for (DeviceManagerModel d : list1) {
					if ("ios".equalsIgnoreCase(d.getDevice_type())) {
						deviceToken.add(d.getDeviceToken().replaceAll(" ", ""));
					}
				}
				if (deviceToken.size() > 0) {
					map.put("lockDevice", "1");
					IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
				}
			}
		} else {
			for (int i = 0; i < id.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting.setSn(sn[i]);
				deviceManager.setId(Integer.valueOf(id[i]));
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i])); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				result = deviceManagerService.updateVisitStatus(deviceManager);
				if (result == 1) {
					if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if ("ios".equalsIgnoreCase(deviceInfo.getDevice_type())) {
						deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
					}
					String lockType = messageSource.getMessage("logs.device.operate.lock.type.visit", null,
							LocaleContextHolder.getLocale());
					Object[] args = { lockType, deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.root.department.name", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.lock.content", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "1";
					saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
					// 瀹夊叏浜嬩欢鏃ュ織淇濆瓨
					// 淇濆瓨瀹夊叏浜嬩欢鏃ュ織
					SecurityEventLogModel securityEventLog = new SecurityEventLogModel();
					Object[] object = { manager.getName(), sdf.format(new Date()), deviceInfo.getDevice_name() };
					String operateContent1 = messageSource.getMessage(
							"web.device.devicelogincontroller.index.device.lock.msg", object,
							LocaleContextHolder.getLocale());
					securityEventLog.setEventType("1");
					securityEventLog.setLevel("2");
					securityEventLog.setOperateContent(operateContent1);
					securityEventLog.setCreateBy(manager.getId());
					securityEventLog.setUpdateBy(manager.getId());
					securityEventLogService.insertSecurityEventLog(securityEventLog);
				}
			}
			if (deviceToken.size() > 0) {
				map.put("lockDevice", "1");
				IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
			}
		}
		return result;
	}

	// 更新设备信息
	public void refrashDeviceInfo(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = new DeviceSettingModel();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> udidList = null;
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting = new DeviceSettingModel();
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setFrashInfo(1);
					if ("android".equalsIgnoreCase(list.get(i).getDevice_type())) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if ("ios".equalsIgnoreCase(list.get(i).getDevice_type())) {
						udidList = new ArrayList<String>();
						udidList.add(list.get(i).getUdid());
						map.put("udidList", udidList);
						map.put("command", "DeviceInformation");
						abstractIosPush.nofity(map);
					}
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
							list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.refrash", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.refrash.content", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.refrash.type", null,
							LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "2";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setFrashInfo(1);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				udidList = new ArrayList<String>();
				for (DeviceManagerModel d : list1) {
					if ("ios".equalsIgnoreCase(d.getDevice_type())) {
						udidList.add(d.getUdid());
					}
				}
				map.put("udidList", udidList);
				map.put("command", "DeviceInformation");
				abstractIosPush.nofity(map);
			}
		} else {
			for (int i = 0; i < userId.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting = new DeviceSettingModel();
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i])); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				deviceSetting.setSn(sn[i]);
				deviceSetting.setFrashInfo(1);
				if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
				} else {
					udidList = new ArrayList<String>();
					udidList.add(deviceInfo.getUdid());
					map.put("udidList", udidList);
					map.put("command", "DeviceInformation");
					abstractIosPush.nofity(map);
				}
				Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
				String operateType = messageSource.getMessage("logs.device.operate.type.refrash", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.refrash.content", args,
						LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.refrash.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "2";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
	}

	// 设置锁频密码
	public void lockPassword(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			String lockPassword) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = new DeviceSettingModel();
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting = new DeviceSettingModel();
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setLockPassword(lockPassword);
					deviceSetting.setUpdatePassword(1);
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(), null,
							null, 2, extra);
					taskExecutor.execute(mqProducerThread);
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
							list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.update.password", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.update.password.content",
							args, LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.update.password.type",
							null, LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "3";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setLockPassword(lockPassword);
				deviceSetting.setUpdatePassword(1);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
			}
		} else {
			for (int i = 0; i < userId.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setSn(sn[i]);
				deviceSetting.setLockPassword(lockPassword);
				deviceSetting.setUpdatePassword(1);
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i])); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
				String operateType = messageSource.getMessage("logs.device.operate.type.update.password", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.update.password.content", args,
						LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.update.password.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "3";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
	}

	// 锁定终端屏幕
	public void lockScreen(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = null;
		List<String> udidList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting = new DeviceSettingModel();
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setLockTermin(1);
					if ("android".equalsIgnoreCase(list.get(i).getDevice_type())) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if ("ios".equalsIgnoreCase(list.get(i).getDevice_type())) {
						udidList = new ArrayList<String>();
						udidList.add(list.get(i).getUdid());
						map.put("udidList", udidList);
						map.put("command", "DeviceLock");
						abstractIosPush.nofity(map);
					}
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
							list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.lock.screen", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.lock.screen.content", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.lock.screen.type", null,
							LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "4";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				udidList = new ArrayList<String>();
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setLockTermin(1);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				for (DeviceManagerModel d : list1) {
					if ("ios".equalsIgnoreCase(d.getDevice_type())) {
						udidList.add(d.getUdid());
					}
				}
				map.put("udidList", udidList);
				map.put("command", "DeviceLock");
				abstractIosPush.nofity(map);
			}
		} else {
			for (int i = 0; i < userId.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setSn(sn[i]);
				deviceSetting.setLockTermin(1);
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i])); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
				} else {
					udidList = new ArrayList<String>();
					udidList.add(deviceInfo.getUdid());
					map.put("udidList", udidList);
					map.put("command", "DeviceLock");
					abstractIosPush.nofity(map);
				}
				Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
				String operateType = messageSource.getMessage("logs.device.operate.type.lock.screen", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.lock.screen.content", args,
						LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.lock.screen.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "4";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
	}

	// 恢复出厂设置
	public void defaultSetting(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		DeviceSettingModel deviceSetting = null;
		DeviceManagerModel deviceManager = new DeviceManagerModel();
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					deviceSetting = new DeviceSettingModel();
					Map<String, String> extra = new HashMap<String, String>();
					deviceManager.setLost_status(0); // 鏍囪璁惧涓㈠け
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setRemarkDevice(1);
					deviceManager.setId(list.get(i).getId());
					deviceManager.setVisit_status(0);// 鍦ㄨ澶囦涪澶辨椂锛屽悓鏃堕攣瀹氳澶�
					deviceManagerService.updateVisitStatus(deviceManager);
					int result = deviceManagerService.updateLostStatus(deviceManager);
					if (result == 1) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
						DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
								list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
						Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
						String operateType = messageSource.getMessage("logs.device.operate.type.delete.device", null,
								LocaleContextHolder.getLocale());
						String operateContent = messageSource.getMessage(
								"logs.device.operate.remark.device.lost.content", args,
								LocaleContextHolder.getLocale());
						saveSystemLog(organization, operateContent, manager, operateType);
						String userType = "";
						if (deviceInfo.getUserType() == 4) {
							userType = messageSource.getMessage("logs.device.user.normal.type", null,
									LocaleContextHolder.getLocale());
						} else {
							userType = messageSource.getMessage("logs.device.user.manager.type", null,
									LocaleContextHolder.getLocale());
						}
						String deciveOperateType = messageSource.getMessage("logs.device.operate.remark.lost.type",
								null, LocaleContextHolder.getLocale());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
								deviceInfo.getDevice_name(), deciveOperateType };
						String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
								LocaleContextHolder.getLocale());
						String eventType = "9";
						saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
								organization, manager);
					}
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceManager.setLost_status(0);
				deviceManager.setOrgId(organization.getId());
				deviceManagerService.updateLostStatusAll(deviceManager);
				deviceSetting.setRemarkDevice(1);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
			}
		} else {
			for (int i = 0; i < id.length; i++) {
				deviceSetting = new DeviceSettingModel();
				Map<String, String> extra = new HashMap<String, String>();
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i]));
				deviceManager.setLost_status(0);
				deviceSetting.setSn(sn[i]);
				deviceSetting.setRemarkDevice(1);
				deviceManager.setId(Integer.valueOf(id[i]));
				deviceManager.setVisit_status(0);
				deviceManagerService.updateVisitStatus(deviceManager);
				int result = deviceManagerService.updateLostStatus(deviceManager);
				if (result == 1) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.delete.device", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.remark.device.lost.content",
							args, LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.remark.lost.type", null,
							LocaleContextHolder.getLocale());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "9";
					saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
				}
			}
		}
	}

	// 清除锁屏密码
	public void cleanPassword(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		DeviceSettingModel deviceSetting = null;
		List<String> udidList = null;
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting = new DeviceSettingModel();
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setCleanPassword(1);
					if ("android".equalsIgnoreCase(list.get(i).getDevice_type())) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if ("ios".equalsIgnoreCase(list.get(i).getDevice_type())) {
						udidList = new ArrayList<String>();
						udidList.add(list.get(i).getUdid());
						map.put("udidList", udidList);
						map.put("command", "ClearPasscode");
						abstractIosPush.nofity(map);
					}
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
							list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.clean.password", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.clean.password.content", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.clean.password.type", null,
							LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "6";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setCleanPassword(1);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				udidList = new ArrayList<String>();
				for (DeviceManagerModel d : list1) {
					if ("ios".equalsIgnoreCase(d.getDevice_type())) {
						udidList.add(d.getUdid());
					}
				}
				map.put("udidList", udidList);
				map.put("command", "ClearPasscode");
				abstractIosPush.nofity(map);
			}
		} else {
			for (int i = 0; i < userId.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting = new DeviceSettingModel();
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i]));
				deviceSetting.setSn(sn[i]);
				deviceSetting.setCleanPassword(1);
				if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
				} else if ("ios".equalsIgnoreCase(deviceInfo.getDevice_type())) {
					udidList = new ArrayList<String>();
					udidList.add(deviceInfo.getUdid());
					map.put("udidList", udidList);
					map.put("command", "ClearPasscode");
					abstractIosPush.nofity(map);
				}
				Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
				String operateType = messageSource.getMessage("logs.device.operate.type.clean.password", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.clean.password.content", args,
						LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.clean.password.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "6";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
	}

	// 设备响铃
	public void deviceBell(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = null;
		List<String> deviceToken = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceBell", "1");
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting = new DeviceSettingModel();
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
							list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setDeviceBell(1);
					if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else {
						deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
					}
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.delete.device", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.device。bell.content", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.device.bell.type", null,
							LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "7";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setDeviceBell(1);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				for (DeviceManagerModel d : list1) {
					if ("ios".equalsIgnoreCase(d.getDevice_type())) {
						deviceToken.add(d.getDeviceToken().replaceAll(" ", ""));
					}
				}
			}
		} else {
			for (int i = 0; i < userId.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting = new DeviceSettingModel();
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i])); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				deviceSetting.setSn(sn[i]);
				deviceSetting.setDeviceBell(1);
				if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);

				} else if ("ios".equalsIgnoreCase(deviceInfo.getDevice_type())) {
					deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
				}
				Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
				String operateType = messageSource.getMessage("logs.device.operate.type.delete.device", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.device。bell.content", args,
						LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.device.bell.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "7";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
		if (deviceToken.size() > 0) {
			IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
		}
	}

	// 注销设备
	public void logOff(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = null;
		DeviceManagerModel deviceManager = new DeviceManagerModel();
		DeviceSaftyModel deviceSafty = new DeviceSaftyModel();
		List<String> deviceToken = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("logOff", "1");
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting = new DeviceSettingModel();
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setLogOffDevice(1);
					deviceManager.setDevice_status(1);
					deviceManager.setIsActive(0);
					deviceManager.setId(list.get(i).getId());
					deviceSafty.setMdm(3);
					deviceSafty.setDevice_id(list.get(i).getId());
					deviceSaftyService.update(deviceSafty);
					deviceManagerService.updateDeviceStatus(deviceManager);
					deviceManagerService.updateIsActive(deviceManager);
					if ("android".equalsIgnoreCase(list.get(i).getDevice_type())) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if ("ios".equals(list.get(i).getDevice_type())) {
						deviceToken.add(list.get(i).getDeviceToken().replaceAll(" ", ""));
					}
					DeviceManagerModel deviceInfo = deviceManagerService.queryDeviceAndUserInfo(list.get(i).getId());
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei(), deviceInfo.getUserName(),
							deviceInfo.getRealName(), deviceInfo.getName() };
					String operateType = messageSource.getMessage("logs.device.operate.type.log.off", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.log.off.content", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.log.off.type", null,
							LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "8";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setLogOffDevice(1);
				deviceManager.setDevice_status(1);
				deviceManager.setIsActive(0);
				deviceManager.setOrgId(organization.getId());
				deviceManagerService.updateDeviceStatusAll(deviceManager);
				deviceManagerService.updateIsActiveAll(deviceManager);
				deviceSafty.setMdm(3);
				deviceSaftyService.updateAll(deviceSafty);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				for (DeviceManagerModel d : list1) {
					if (d.getDevice_type().equals("ios")) {
						deviceToken.add(d.getDeviceToken().replaceAll(" ", ""));
					}
				}
			}
		} else {
			for (int i = 0; i < id.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting = new DeviceSettingModel();
				DeviceManagerModel deviceInfo = deviceManagerService.queryDeviceAndUserInfo(Integer.valueOf(id[i]));
				deviceSetting.setSn(sn[i]);
				deviceSetting.setLogOffDevice(1);
				deviceManager.setDevice_status(1);
				deviceManager.setIsActive(0);
				deviceManager.setId(Integer.valueOf(id[i]));
				deviceSafty.setMdm(3);
				deviceSafty.setDevice_id(Integer.valueOf(id[i]));
				deviceSaftyService.update(deviceSafty);
				deviceManagerService.updateDeviceStatus(deviceManager);
				deviceManagerService.updateIsActive(deviceManager);
				if ("android".equalsIgnoreCase(deviceInfo.getDevice_type())) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
				} else if ("ios".equalsIgnoreCase(deviceInfo.getDevice_type())) {
					deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
				}
				Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei(), deviceInfo.getUserName(),
						deviceInfo.getRealName(), deviceInfo.getName() };
				String operateType = messageSource.getMessage("logs.device.operate.type.log.off", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.log.off.content", args,
						LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.log.off.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "8";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
		if (deviceToken.size() > 0) {
			IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
		}
	}

	// 彻底删除设备
	public void deleteDevice(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			HttpServletRequest request, List<DeviceManagerModel> list1) {
		ClientCommandModel clientCommand = new ClientCommandModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = null;
		DeviceManagerModel deviceManager = new DeviceManagerModel();
		List<String> udidList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if ("2".equals(selectVal) && "".equals(parentDepartmentId)) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> extra = new HashMap<String, String>();
					deviceSetting = new DeviceSettingModel();
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(list.get(i).getSn(),
							list.get(i).getUserId()); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					// 灏嗗垹闄ょ殑璁惧澶囦唤涓嬫潵
					DeviceManagerModel device = deviceManagerService.queryDeviceAllInfo(list.get(i).getId());
					deletedDeviceService.insertDevice(device);
					// 淇濆瓨鍒犻櫎璁惧鐨勬搷浣�
					clientCommand.setSn(list.get(i).getSn());
					clientCommand.setRemoveDevice(1);
					int count = clientCommandService.queryDeviceIsExits(list.get(i).getSn());
					if (count > 0) {
						clientCommandService.updateRemoveDevice(list.get(i).getSn());
					} else {
						clientCommandService.insertRemoveDevice(clientCommand);
					}
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setRemoveDevice(1);
					deviceManager.setId(list.get(i).getId());
					if(!"ios".equals(deviceInfo.getDevice_type())){
						deviceManagerService.deleteDevice(deviceManager);
					}
					if ("android".equalsIgnoreCase(list.get(i).getDevice_type())) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if ("ios".equalsIgnoreCase(list.get(i).getDevice_type())) {
						udidList = new ArrayList<String>();
						udidList.add(deviceInfo.getUdid());
						map.put("udidList", udidList);
						map.put("command", "RemoveProfile");
						abstractIosPush.nofity(map);
					}
					String deviceName = request.getParameter("deviceName");
					String imeino = request.getParameter("imeino");
					Object[] args = { deviceName, imeino };
					String operateType = messageSource.getMessage("logs.device.operate.type.delete.device", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.delete.device.content", args,
							LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.remove.mor.type", null,
							LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "14";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				for (DeviceManagerModel device : list1) {
					deviceSetting = new DeviceSettingModel();
					deletedDeviceService.insertDevice(device);
					clientCommand.setSn(device.getSn());
					clientCommand.setRemoveDevice(1);
					clientCommandService.insertRemoveDevice(clientCommand);
				}
				deviceManager.setOrgId(organization.getId());
				deviceManager.setDevice_type("android");
				deviceManagerService.deleteAllDevice(deviceManager);
				deviceSetting.setRemoveDevice(1);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				for (DeviceManagerModel d : list1) {
					if (d.getDevice_type().equals("ios")) {
						udidList = new ArrayList<String>();
						udidList.add(d.getUdid());
						map.put("udidList", udidList);
						map.put("command", "RemoveProfile");
						abstractIosPush.nofity(map);
					}
				}
			}
		} else {
			for (int i = 0; i < id.length; i++) {
				Map<String, String> extra = new HashMap<String, String>();
				deviceSetting = new DeviceSettingModel();
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn[i], Integer.valueOf(userId[i])); // 鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				// 灏嗗垹闄ょ殑璁惧澶囦唤涓嬫潵
				DeviceManagerModel device = deviceManagerService.queryDeviceAllInfo(Integer.valueOf(id[i]));
				deletedDeviceService.insertDevice(device);
				// 淇濆瓨鍒犻櫎璁惧鐨勬搷浣�
				clientCommand.setSn(sn[i]);
				clientCommand.setRemoveDevice(1);
				int count = clientCommandService.queryDeviceIsExits(sn[i]);
				if (count > 0) {
					clientCommandService.updateRemoveDevice(sn[i]);
				} else {
					clientCommandService.insertRemoveDevice(clientCommand);
				}
				deviceSetting.setSn(sn[i]);
				deviceSetting.setRemoveDevice(1);
				deviceManager.setId(Integer.valueOf(id[i]));
				if(!"ios".equals(device.getDevice_type())){
					deviceManagerService.deleteDevice(deviceManager);
				}
				if (deviceInfo.getDevice_type().equals("android")) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
				} else if (deviceInfo.getDevice_type().equals("ios")) {
					udidList = new ArrayList<String>();
					udidList.add(deviceInfo.getUdid());
					map.put("udidList", udidList);
					map.put("command", "RemoveProfile");
					abstractIosPush.nofity(map);
				}
				String deviceName = request.getParameter("deviceName");
				String imeino = request.getParameter("imeino");
				Object[] args = { deviceName, imeino };
				String operateType = messageSource.getMessage("logs.device.operate.type.delete.device", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.delete.device.content", args,
						LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.remove.mor.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "14";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
	}

	// 启用终端解绑
	public void enableUnbundle(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = null;
		DeviceManagerModel deviceManager = new DeviceManagerModel();
		List<String> deviceToken = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("enableunbundle", "1");
		if (selectVal.equals("2") && parentDepartmentId.equals("")) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					deviceSetting = new DeviceSettingModel();
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setEnableunbundle(1);
					deviceManager.setEnableUnbund(1);
					deviceManager.setSn(list.get(i).getSn());
					deviceManagerService.updateEnableUnbund(deviceManager);
					Map<String, String> extra = new HashMap<String, String>();
					if (list.get(i).getDevice_type().equals("android")) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if (list.get(i).getDevice_type().equals("ios")) {
						deviceToken.add(list.get(i).getDeviceToken().replaceAll(" ", ""));
					}
					DeviceManagerModel deviceInfo = deviceManagerService
							.queryDeviceAndUserInfo(list.get(i).getUserId());
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.enable.unbundle", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.type.enable.unbundle.content",
							args, LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.enable.unbundle.type",
							null, LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "15";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setEnableunbundle(1);
				deviceManager.setEnableUnbund(1);
				deviceManager.setOrgId(organization.getId());
				deviceManagerService.updateEnableUnbundAll(deviceManager);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				for (DeviceManagerModel d : list1) {
					if (d.getDevice_type().equals("ios")) {
						deviceToken.add(d.getDeviceToken().replaceAll(" ", ""));
					}
				}
			}
		} else {
			for (int i = 0; i < userId.length; i++) {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setSn(sn[i]);
				deviceSetting.setEnableunbundle(1);
				deviceManager.setEnableUnbund(1);
				deviceManager.setSn(sn[i]);
				deviceManagerService.updateEnableUnbund(deviceManager);
				Map<String, String> extra = new HashMap<String, String>();
				DeviceManagerModel deviceInfo = deviceManagerService.queryDeviceAndUserInfo(Integer.valueOf(id[i]));
				if (deviceInfo.getDevice_type().equals("android")) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
				} else {
					deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
				}
				Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
				String operateType = messageSource.getMessage("logs.device.operate.type.enable.unbundle", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.type.enable.unbundle.content",
						args, LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.enable.unbundle.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "15";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
		if (deviceToken.size() > 0) {
			IosPushUtil.pushDataToIos(deviceToken, null, 1, "", map);
		}
	}

	// 禁用终端解绑
	public void disableUnbundle(String selectVal, String parentDepartmentId, List<DeviceManagerModel> list,
			OrganizationModel organization, ManagerModel manager, String[] id, String[] userId, String[] sn,
			List<DeviceManagerModel> list1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DeviceSettingModel deviceSetting = null;
		DeviceManagerModel deviceManager = new DeviceManagerModel();
		List<String> deviceToken = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("enableunbundle", "0");
		if (selectVal.equals("2") && parentDepartmentId.equals("")) {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					deviceSetting = new DeviceSettingModel();
					deviceSetting.setSn(list.get(i).getSn());
					deviceSetting.setEnableunbundle(0);
					deviceManager.setEnableUnbund(0);
					deviceManager.setSn(list.get(i).getSn());
					deviceManagerService.updateEnableUnbund(deviceManager);
					Map<String, String> extra = new HashMap<String, String>();
					if (list.get(i).getDevice_type().equals("android")) {
						extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						MqProducerThread mqProducerThread = new MqProducerThread(list.get(i).getUserId().toString(),
								null, null, 2, extra);
						taskExecutor.execute(mqProducerThread);
					} else if (list.get(i).getDevice_type().equals("ios")) {
						deviceToken.add(list.get(i).getDeviceToken().replaceAll(" ", ""));
					}
					DeviceManagerModel deviceInfo = deviceManagerService.queryDeviceAndUserInfo(list.get(i).getId());
					Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
					String operateType = messageSource.getMessage("logs.device.operate.type.enable.unbundle", null,
							LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.type.enable.unbundle.content",
							args, LocaleContextHolder.getLocale());
					saveSystemLog(organization, operateContent, manager, operateType);
					String userType = "";
					if (deviceInfo.getUserType() == 4) {
						userType = messageSource.getMessage("logs.device.user.normal.type", null,
								LocaleContextHolder.getLocale());
					} else {
						userType = messageSource.getMessage("logs.device.user.manager.type", null,
								LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.disable.unbundle.type",
							null, LocaleContextHolder.getLocale());
					Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
							deviceInfo.getDevice_name(), deciveOperateType };
					String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
							LocaleContextHolder.getLocale());
					String eventType = "16";
					saveDeviceLog(deviceInfo, eventType, list.get(i).getUserId().toString(), deviceOperateContent,
							organization, manager);
				}
			} else {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setEnableunbundle(0);
				deviceManager.setEnableUnbund(0);
				deviceManager.setOrgId(organization.getId());
				deviceManagerService.updateEnableUnbundAll(deviceManager);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				MqProducerThread mqProducerThread = new MqProducerThread(TOPIC, null, null, 2, extra);
				taskExecutor.execute(mqProducerThread);
				for (DeviceManagerModel d : list1) {
					if (d.getDevice_type().equals("ios")) {
						deviceToken.add(d.getDeviceToken().replaceAll(" ", ""));
					}
				}
			}
		} else {
			for (int i = 0; i < userId.length; i++) {
				deviceSetting = new DeviceSettingModel();
				deviceSetting.setSn(sn[i]);
				deviceSetting.setEnableunbundle(0);
				deviceManager.setEnableUnbund(0);
				deviceManager.setSn(sn[i]);
				deviceManagerService.updateEnableUnbund(deviceManager);
				Map<String, String> extra = new HashMap<String, String>();
				extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
				DeviceManagerModel deviceInfo = deviceManagerService.queryDeviceAndUserInfo(Integer.valueOf(id[i]));
				if (deviceInfo.getDevice_type().equals("android")) {
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId[i], null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
				} else {
					deviceToken.add(deviceInfo.getDeviceToken().replaceAll(" ", ""));
				}
				Object[] args = { deviceInfo.getDevice_name(), deviceInfo.getEsnoOrImei() };
				String operateType = messageSource.getMessage("logs.device.operate.type.enable.unbundle", null,
						LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.type.enable.unbundle.content",
						args, LocaleContextHolder.getLocale());
				saveSystemLog(organization, operateContent, manager, operateType);
				String userType = "";
				if (deviceInfo.getUserType() == 4) {
					userType = messageSource.getMessage("logs.device.user.normal.type", null,
							LocaleContextHolder.getLocale());
				} else {
					userType = messageSource.getMessage("logs.device.user.manager.type", null,
							LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.disable.unbundle.type", null,
						LocaleContextHolder.getLocale());
				Object[] arg = { userType, deviceInfo.getRealName(), sdf.format(new Date()),
						deviceInfo.getDevice_name(), deciveOperateType };
				String deviceOperateContent = messageSource.getMessage("logs.device.operate.content", arg,
						LocaleContextHolder.getLocale());
				String eventType = "16";
				saveDeviceLog(deviceInfo, eventType, userId[i], deviceOperateContent, organization, manager);
			}
		}
		if (deviceToken.size() > 0) {
			IosPushUtil.pushDataToIosTr(deviceToken, null, 1, "", map);
		}
	}
}
