package com.softtek.mdm.web.customer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.dao.SysmanageDeviceLogDao;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceLocationModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceSettingModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MessageSendModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.model.SysmanageLog;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.DeviceAppInfoService;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceLegalListService;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.MessageSendService;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.service.SysmanageLogService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.AuthStatus;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.util.UserPass;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;

@Controller
@RequestMapping(value="/customer")
public class IndexController extends BaseController {

	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceBasicInfoService deviceBasicService;
	@Autowired
	private DeviceAppInfoService deviceAppInfoService;
	@Autowired
    private MessageSendService messageSendService;	
	@Autowired
	private DeviceManagerService deviceManagerService;
	@Autowired
	private SysmanageDeviceLogService sysmanageDeviceLogService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private SysmanageLogService sysmanageLogService;
	@Autowired
	private DeviceLegalListService deviceLegalListService;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private SysmanageDeviceLogDao sysmanageDeviceLogDao;
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	@Qualifier("IosDeviceSettingNotifyServiceImpl")
	private AbstractIosPush abstractIosPush;
	
	private Date locationRequestDate;
	
	private void setModel(HttpServletRequest request,Model model){
		UserModel user = (UserModel) request.getSession().getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
		List<DeviceBasicInfoModel> deviceBacisList = null;
		if(user!=null){
			deviceBacisList =deviceBasicInfoService.findByUserId(user.getId());
		}
		model.addAttribute("deviceBasicInfoList", deviceBacisList);
		model.addAttribute("user", user);
	}
	
	
	/**
	 * 鏅�氱敤鎴烽椤�
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		UserModel user = (UserModel) request.getSession().getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
		List<DeviceBasicInfoModel> deviceBacisList =deviceBasicInfoService.findByUserId(user.getId());
		setModel(request, model);
		
		return CollectionUtils.isEmpty(deviceBacisList)?"redirect:/customer/personal":"customer/index/index";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@Link(family="customer",label="institution.home.index.label")
	//@Log(operateType="logs.customer.device.type.query",operateContent="logs.customer.device.content.query",args={"id"})
	@RequestMapping(value="/getDeviceInfo",method=RequestMethod.GET)
	@ResponseBody
	public DeviceBasicInfoModel getDeviceInfo(HttpServletRequest request,HttpServletResponse response,Integer id){
		DeviceBasicInfoModel deviceBasicInfoModel  = null;
		if(StringUtils.isNotBlank(String.valueOf(id))){
			request.getSession().setAttribute("device_id",id);
			deviceBasicInfoModel  = deviceBasicService.findOne(id);
		}
		return deviceBasicInfoModel;
	}
	
	/**
	 * 搴旂敤娓呭崟椤甸潰
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/appList",method=RequestMethod.GET)
	public String getAllAppList(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		setModel(request, model);
		return "customer/appList/index";
	}
	
	/**
	 * 
	 * @param id
	 * @param session
	 * @param request
	 * @param response
	 * @param start
	 * @param length
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	//@Log(operateType="logs.customer.app.type.query",operateContent="logs.customer.app.content.query",args={"id"})
	@RequestMapping(value="/appListPages",method=RequestMethod.GET)
	@ResponseBody
	public Page appListInfos(Integer id, HttpSession session,HttpServletRequest request,HttpServletResponse response,Integer start,Integer length) throws IOException{
		start=start==null?0:start;
		length=length==null?10:length;
		Page page=deviceAppInfoService.findByPage(id, start, length);
		return page;
	} 
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/position",method=RequestMethod.GET)
	public String getDevicePosition(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		setModel(request, model);
		return "customer/position/index";
	}
	
	
	/**
	 * 鍚堣鎬ф樉绀洪〉闈�
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/compliant",method=RequestMethod.GET)
	public String getAllCompliant(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		setModel(request, model);
		return "customer/compliant/index";
	}
	
	@Link(family="customer",label="institution.home.index.label")
	//@Log(operateType="logs.customer.compliant.type.query",operateContent="logs.customer.compliant.content.query",args={"id"})
	@RequestMapping(value="/compliantListPages",method=RequestMethod.POST)
	@ResponseBody
	public Page compliantLists(Integer did,Integer uid,HttpSession session,HttpServletRequest request,HttpServletResponse response,Integer start,Integer length) throws IOException{
		start=start==null?0:start;
		length=length==null?10:length;
		Page page = deviceLegalListService.findCompliantWithDeviceId(did,start,length);
		return page;
		
	} 
	
	/**
	 * 閰嶇疆鏂囦欢鏄剧ず椤甸潰
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/config",method=RequestMethod.GET)
	public String getAllConfig(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		setModel(request, model);
		return "customer/config/index";
	}
	
	/**
	 * 鑾峰彇涓汉淇℃伅
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/personal",method=RequestMethod.GET)
	public String getPersonalInfo(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		setModel(request, model);
		//浠庡悗鍙拌幏鍙朣ession涓殑鐢ㄦ埛淇℃伅鏄剧ず鍒扮敤鎴风晫闈笂
		UserModel user = (UserModel) request.getSession().getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
		model.addAttribute("personInfo",user);
		return "customer/personal/index";
	}
	
	/**
	 * 鎻愪氦淇敼鐢ㄦ埛淇℃伅
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws IOException
	 */
	//@Log(operateType="logs.customer.personal.type.update",operateContent="logs.customer.personal.content.update",args={"id"})
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/personal/update",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updatePersonalInfo(HttpServletRequest request,HttpServletResponse response,UserModel user) throws IOException{
		
		UserModel userModel = (UserModel) request.getSession().getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
		UserModel newUser = new UserModel();
		JsonResult jsonResult = null;
		try {
			if(userModel != null){
				newUser.setId(userModel.getId());
				newUser.setRealname(user.getRealname());
				newUser.setPhone(user.getPhone());
				newUser.setEmail(user.getEmail());
				newUser.setMark(user.getMark());
			}
			int count = userService.updateUser(newUser);
			if(count>0){
				UserModel sessionUser = userService.findOne(newUser.getId());
				request.getSession().setAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString(), sessionUser);
				ManagerModel manager=(ManagerModel) request.getSession().getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
				ManagerModel temp=new ManagerModel();
				if(manager!=null && manager.getUser()!=null){
					temp.setId(manager.getId());
					temp.setName(user.getRealname());
					temp.setPhone(user.getPhone());
					temp.setEmail(user.getEmail());
					temp.setMark(user.getMark());
					managerService.update(temp);
				}
				ManagerModel managerModel = managerService.findOne(temp.getId());
				if(managerModel!=null){
					managerModel.setUser(sessionUser);
					request.getSession().setAttribute(SessionStatus.SOFTTEK_MANAGER.toString(), managerModel);
				}
				Object[] args={sessionUser.getRealname()};
				String operateType = messageSource.getMessage("logs.customer.personal.type.update",null, LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.customer.personal.content.update",args, LocaleContextHolder.getLocale());
				saveLog(sessionUser,args,operateType,operateContent,request);
				jsonResult = createJsonResult(MessageType.success, "institution.user.update.label", null,null);
			}else{
				jsonResult = createJsonResult(MessageType.danger, "institution.user.update.label", null,null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return jsonResult;
	}
	
	
	private void saveLog(UserModel user,Object[] args,String operateType,String operateContent,HttpServletRequest request){
		//鑾峰彇鏈烘瀯鐨勫熀鏈俊鎭�
		 Object orgObj = request.getSession().getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		 OrganizationModel organization = (OrganizationModel)orgObj;
		 SysmanageLog systemLog = new SysmanageLog();
		 try {
			systemLog.setOperateType(operateType);
			 systemLog.setOperateContent(operateContent);
			 //璁剧疆鏈烘瀯
			 systemLog.setOrgId(organization.getId());
			 systemLog.setUserId(user.getId());
			 systemLog.setUsername(user.getUsername()); 
			 systemLog.setUserType(AuthStatus.SOFTTEK_CUSTOMER.toString());
			 systemLog.setOperateType(operateType);
			 systemLog.setOperateContent(operateContent);
			 systemLog.setCreateDate(new Date());
			 systemLog.setCreateUser(user.getId());
			 systemLog.setUpdateDate(new Date());
			 systemLog.setUpdateUser(user.getId());
			 systemLog.setOperateTime(new Date());
			 sysmanageLogService.insertSelective(systemLog);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	
	/**
	 * 瀵嗙爜鍙樻洿椤甸潰
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/password",method=RequestMethod.GET)
	public String getChangePass(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		setModel(request, model);
		//浠庡悗鍙拌幏鍙朣ession涓殑鐢ㄦ埛淇℃伅鏄剧ず鍒扮敤鎴风晫闈笂
		UserModel user = (UserModel) request.getSession().getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
		model.addAttribute("personInfo",user);
		return "customer/password/index";
	}
	/**
	 * 鎻愪氦鐢ㄦ埛瀵嗙爜
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws IOException
	 */
	//@Log(operateType="logs.customer.password.type.update",operateContent="logs.customer.password.content.update",args={"id"})
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/password/update",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updatePersonalPass(HttpSession session,HttpServletRequest request,HttpServletResponse response,UserPass user) throws IOException{
		JsonResult jsonResult = null;
		UserModel userModel = (UserModel) request.getSession().getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
		UserModel newUser = null;
		try {
			if(userModel != null){
				//鍒ゆ柇鐢ㄦ埛杈撳叆鐨勫師濮嬪瘑鐮佹槸鍚︽纭�
				if(StringUtils.isNotBlank(user.getPassword())){
					Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
					if(StringUtils.equals(md5PasswordEncoder.encodePassword(user.getPassword(), null), userModel.getPassword())){
						String newPwdStr=md5PasswordEncoder.encodePassword(user.getNewPassword(), null);
						newUser = new UserModel();
						newUser.setId(userModel.getId());
						newUser.setPassword(newPwdStr);
						//鍚屾鏇存柊manager
						ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
						if(manager!=null && manager.getUser()!=null){
							ManagerModel temp=new ManagerModel();
							temp.setId(manager.getId());
							temp.setPassword(newPwdStr);
							managerService.update(temp);
						}
						int count = userService.update(newUser);
						if(count>0){
							UserModel  sessionUser = userService.findOne(newUser.getId());
							request.getSession().setAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString(), sessionUser);
							Object[] args={sessionUser.getRealname()};
							String operateType = messageSource.getMessage("logs.customer.password.type.update",null, LocaleContextHolder.getLocale());
							String operateContent = messageSource.getMessage("logs.customer.password.content.update",args, LocaleContextHolder.getLocale());
							saveLog(sessionUser, args, operateType, operateContent,request);
							jsonResult = createJsonResult(MessageType.success, "defes.institution.person.update.success", null,null);
						}else{
							jsonResult = createJsonResult(MessageType.danger, "defes.institution.person.update.failed", null,null);
						}
					}else{
						jsonResult = createJsonResult(MessageType.danger, "defes.institution.person.pwd.failed", null,null);
						return jsonResult;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return jsonResult;
	}
	
	
	/**
	 * 璁惧鏃ュ織椤甸潰
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/device",method=RequestMethod.GET)
	public String getDeviceInfo(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		setModel(request, model);
		return "customer/device/index";
	}
	
	//@Log(operateType="logs.customer.deviceLogs.type.query",operateContent="logs.customer.deviceLogs.content.query",args={"did,uid"})
	@Link(family="customer",label="institution.home.index.label")
	@RequestMapping(value="/getAllDeviceLogs",method=RequestMethod.POST)
	@ResponseBody
	public Page getAllDeviceLogs(HttpServletRequest request,HttpServletResponse response,Integer start,Integer pageLength) throws IOException{
		
		String eventType = request.getParameter("eventType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String did = request.getParameter("did");
		String uid = request.getParameter("uid");
		start = start == null ? 0 : start;
	    pageLength = pageLength == null ? 10 : pageLength;
	    Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
        paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, pageLength);
        paramMap.put("eventType", StringUtils.trimToNull(eventType));
        paramMap.put("startTime", StringUtils.trimToNull(startTime));
        paramMap.put("endTime", StringUtils.trimToNull(endTime));
        paramMap.put("did", StringUtils.trimToNull(did));
        paramMap.put("uid",StringUtils.trimToNull(uid));
	    Page page = sysmanageDeviceLogService.queryLogsByParams(paramMap);
		return page;
	}
	
	
	//====================浠ヤ笅鎵�鏈夌殑璇锋眰閮芥槸aside椤甸潰宸︿晶鐨勮彍鍗曡姹俢ontroller====================
	 
	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	 @Log(operateType="logs.customer.sendMessage.type.query",operateContent="logs.customer.sendMessage.content.query",args={"udid,message"})
	 @Link(family="customer",label="institution.home.index.label")
	 @RequestMapping(value="sendInfoMessage",method=RequestMethod.POST)
	 @ResponseBody
	 public Map<String,Object> sendInfoMessage(HttpServletRequest request,HttpSession session){
		 UserModel userModel = (UserModel) session.getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
		 Map<String,Object> map = new HashMap<String, Object>();
		 Map<String,String> extra = new HashMap<String, String>();
		 MessageSendModel messageSend = new MessageSendModel();
		 List<String> deviceToken = new ArrayList<String>();
		try {
            //udid涓簊n鍙�
             String udid = request.getParameter("udid");
			 String uid = request.getParameter("uid");
			 String id = request.getParameter("id");
			 String messageTitle = request.getParameter("messageTitle");
			 String message = request.getParameter("message");
			
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	            //udid涓簊n鍙�
			 messageSend.setUdid(udid);
			 messageSend.setMessage_title(messageTitle);
			 messageSend.setMessage(message);
			 messageSend.setCreateBy(userModel.getId());
			 messageSend.setCreateTime(new Date());
			 messageSend.setUpdateTime(new Date());
			 messageSend.setUpdateBy(userModel.getId());
			 messageSend.setName(userModel.getRealname());
			 messageSend.setCreateDateStr(sdf.format(new Date()));
			 messageSend.setUserId(Integer.valueOf(uid));
			 //messageSend.setId(CommUtil.getPrimaryKey()); 
			 int result = messageSendService.saveMessage(messageSend);
			 if(result == 1){
				 DeviceBasicInfoModel deviceBasicInfo = deviceBasicInfoService.findOne(Integer.valueOf(id));
				 extra.put("messageSend", JSONObject.fromObject(messageSend).toString());
				 if(deviceBasicInfo.getDevice_type().equals("android")){
					 MqProducerThread mqProducerThread = new MqProducerThread(uid, null, null, 2, extra);
					 taskExecutor.execute(mqProducerThread);
				 }else if(deviceBasicInfo.getDevice_type().equals("ios")){
					 Map<String,String> customDictionary = new HashMap<String, String>();
					 customDictionary.put("message",message);
					 deviceToken.add(deviceBasicInfo.getDeviceToken().replaceAll(" ", ""));
					 IosPushUtil.pushDataToIosTo(deviceToken,messageTitle, 1, "default", customDictionary);
				 }
				 Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
				 // 鑾峰彇绠＄悊鍛�
				 Object obj = session.getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
				 OrganizationModel organization = (OrganizationModel)orgObj;
				 UserModel user = (UserModel) obj;
				 SysmanageLog systemLog = new SysmanageLog();
				 //udid涓簊n鍙�
//				 DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(udid,Integer.valueOf(uid));  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				 Object[] args = {message,deviceBasicInfo.getDevice_name(),deviceBasicInfo.getEsnorimei()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.send.message",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.send.message.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog,operateType,operateContent,user,organization);
			 }
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
		 map.put("messageSend", messageSend);
		 return map;
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param session
	  * @return
	  */
	 @Link(family="customer",label="institution.home.index.label")
	 @RequestMapping(value="sendCommandToClient",method=RequestMethod.POST)
	 @ResponseBody
	 public Map<String,Object> sendCommandToClient(HttpServletRequest request,HttpSession session){
		 Map<String,Object> map = new HashMap<String, Object>();
		 DeviceSettingModel deviceSetting = new DeviceSettingModel();
		 DeviceManagerModel deviceManager = new DeviceManagerModel();
		 //获取机构管理员
		 Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		 //获取普通用户
		 Object obj = session.getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
		 OrganizationModel organization = (OrganizationModel)orgObj;
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Map<String,String> extra = new HashMap<String, String>();
		 Integer flag = Integer.valueOf(request.getParameter("flag"));
		 String sn = request.getParameter("sn");
		 String userId = request.getParameter("uid");
		 String id = request.getParameter("id");
		 DeviceBasicInfoModel deviceBasicInfo = deviceBasicInfoService.findOne(Integer.valueOf(id));
		 List<String> udidList = new ArrayList<String>();
		 List<String> deviceToken = null;
		 String status = "";
		 try {
			if(flag != null){
				if(flag == 3){ //更新设备信息
					 deviceSetting.setSn(sn);
					 deviceSetting.setFrashInfo(1);
					 if(deviceBasicInfo.getDevice_type().equals("android")){
						 extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						 MqProducerThread mqProducerThread = new MqProducerThread(userId, null, null, 2, extra);
						 taskExecutor.execute(mqProducerThread);
					 }else if(deviceBasicInfo.getDevice_type().equals("ios")){
						 udidList.add(deviceBasicInfo.getUdid());
						 map.put("udidList",udidList);
						 map.put("command", "DeviceInformation");
						 abstractIosPush.nofity(map);
					 }
					 status = "success";
					 map.put("status", status);
				 }else if(flag == 5){  //    设置锁频密码
					 String lockPassword = request.getParameter("lockPassword");
					 deviceSetting.setSn(sn);
					 deviceSetting.setLockPassword(lockPassword);
					 deviceSetting.setUpdatePassword(1);
					 extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					 MqProducerThread mqProducerThread = new MqProducerThread(userId, null, null, 2, extra);
					 taskExecutor.execute(mqProducerThread);
					 status = "success";
					 map.put("status", status);
				 }else if(flag == 6){  //锁定终端屏幕
					 deviceSetting.setSn(sn);
					 deviceSetting.setLockTermin(1);
					 if(deviceBasicInfo.getDevice_type().equals("android")){
						 extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						 MqProducerThread mqProducerThread = new MqProducerThread(userId, null, null, 2, extra);
						 taskExecutor.execute(mqProducerThread);
					 }else if(deviceBasicInfo.getDevice_type().equals("ios")){
						 udidList.add(deviceBasicInfo.getUdid());
						 map.put("udidList",udidList);
						 map.put("command", "DeviceLock");
						 abstractIosPush.nofity(map);
					 }
					 status = "success";
					 map.put("status", status);
				 }else if(flag == 7){  //恢复出厂设置
				     deviceSetting.setSn(sn);
					 deviceSetting.setDefaultSet(1);
					 if(deviceBasicInfo.getDevice_type().equals("android")){
						 extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						 MqProducerThread mqProducerThread = new MqProducerThread(userId, null, null, 2, extra);
						 taskExecutor.execute(mqProducerThread);
					 }else if(deviceBasicInfo.getDevice_type().equals("ios")){
						 udidList.add(deviceBasicInfo.getUdid());
						 map.put("udidList",udidList);
						 map.put("command", "EraseDevice");
						 abstractIosPush.nofity(map);
					 }
					 status = "success";
					 map.put("status", status);
				 }else if(flag == 8){   //清除锁屏密码
					 deviceSetting.setSn(sn);
					 deviceSetting.setCleanPassword(1);
					 if(deviceBasicInfo.getDevice_type().equals("android")){
						 extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						 MqProducerThread mqProducerThread = new MqProducerThread(userId, null, null, 2, extra);
						 taskExecutor.execute(mqProducerThread);
					 }else if(deviceBasicInfo.getDevice_type().equals("ios")){
						 udidList.add(deviceBasicInfo.getUdid());
						 map.put("udidList",udidList);
						 map.put("command", "ClearPasscode");
						 abstractIosPush.nofity(map);
					 }
					 status = "success";
					 map.put("status", status);
				 }else if(flag == 9){  //设备响铃
					 deviceSetting.setSn(sn);
					 deviceSetting.setDeviceBell(1);
					 extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					 if(deviceBasicInfo.getDevice_type().equals("android")){
						 MqProducerThread mqProducerThread = new MqProducerThread(userId, null, null, 2, extra);
						 taskExecutor.execute(mqProducerThread);
					 }else if(deviceBasicInfo.getDevice_type().equals("ios")){
						 deviceToken = new ArrayList<String>();
						 Map<String,String> customDictionary = new HashMap<String, String>();
						 customDictionary.put("deviceBell","1");
						 deviceToken.add(deviceBasicInfo.getDeviceToken().replaceAll(" ", ""));
						 IosPushUtil.pushDataToIosTo(deviceToken, null, 1, "default", extra);
					 }
					 status = "success";
					 map.put("status", status);
				 }else if(flag == 10){//注销设备
					 deviceSetting.setSn(sn);
					 deviceSetting.setLogOffDevice(1);
					 deviceManager.setDevice_status(1);
					 deviceManager.setIsActive(0);
					 deviceManager.setId(Integer.valueOf(id));
					 deviceManagerService.updateDeviceStatus(deviceManager);
					 deviceManagerService.updateIsActive(deviceManager);
					 if(deviceBasicInfo.getDevice_type().equals("android")){
						 extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
						 MqProducerThread mqProducerThread = new MqProducerThread(userId, null, null, 2, extra);
						 taskExecutor.execute(mqProducerThread);
					 }else if(deviceBasicInfo.getDevice_type().equals("ios")){
						 deviceToken = new ArrayList<String>();
						 Map<String,String> customDictionary = new HashMap<String, String>();
						 customDictionary.put("logOff","1");
						 deviceToken.add(deviceBasicInfo.getDeviceToken().replaceAll(" ", ""));
						 IosPushUtil.pushDataToIos(deviceToken, null, 1, "default", extra);
					 }
				 }/*else if(flag == 11){
					 deviceSetting.setSn(sn);
					 deviceSetting.setLockDevice(3);
					 deviceManager.setVisit_status(null);
					 deviceManager.setId(Integer.valueOf(id));
					 deviceManagerService.updateVisitStatus(deviceManager);
				 }*/
			 }
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	
		 UserModel user = (UserModel) obj;
		 SysmanageLog systemLog = new SysmanageLog();
		 SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
		 if(flag != null){
			 if(flag == 0){       //瑙ｉ攣瀹㈡埛绔搷浣滄棩蹇楄褰�
				DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
				String operateType = messageSource.getMessage("logs.device.operate.type.unlock",null, LocaleContextHolder.getLocale());
				String operateContent = messageSource.getMessage("logs.device.operate.unlock.content",args, LocaleContextHolder.getLocale());
				saveSysLog(systemLog,operateType,operateContent,user,organization);
				String userType = "";
				if(deviceInfo.getUserType() == 4){
				   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
				}else{
				   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
				}
				String deciveOperateType = messageSource.getMessage("logs.device.operate.type",null, LocaleContextHolder.getLocale());
				Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
				String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
				saveSysDeviceLog("0",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
			 }else if(flag == 1){  //閿佸畾瀹㈡埛绔搷浣滄棩蹇楄褰�
				 Integer lockStatus = Integer.valueOf(request.getParameter("lockStatus"));
				if(lockStatus == 2){
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					String lockType = messageSource.getMessage("logs.device.operate.lock.type.visit",null, LocaleContextHolder.getLocale());
					Object[] args = {lockType,deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
					String operateType = messageSource.getMessage("logs.root.department.name",null, LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.lock.content",args, LocaleContextHolder.getLocale());
					saveSysLog(systemLog,operateType,operateContent,user,organization);
					String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.lock.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("1",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
				}else if(lockStatus == 3){    //寮曠垎閿佸畾
					DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
					String lockType = messageSource.getMessage("logs.device.operate.lock.type.ignite",null, LocaleContextHolder.getLocale());
					Object[] args = {lockType,deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
					String operateType = messageSource.getMessage("logs.device.operate.type.lock",null, LocaleContextHolder.getLocale());
					String operateContent = messageSource.getMessage("logs.device.operate.lock.content",args, LocaleContextHolder.getLocale());
					saveSysLog(systemLog,operateType,operateContent,user,organization);
					String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.lock.other.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("1",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
				}
			 }else if(flag == 3){   //璺熸柊璁惧埆淇℃伅鎿嶄綔鏃ュ織璁板綍
				 DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				 Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.refrash",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.refrash.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog,operateType,operateContent,user,organization);
				 String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.refrash.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("2",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
			 }else if(flag == 5){   //淇敼閿佸睆瀵嗙爜鎿嶄綔鏃ュ織璁板綍
				 DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				 Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.update.password",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.update.password.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog,operateType,operateContent,user,organization);
				 String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.update.password.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("3",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
			 }else if(flag == 6){   //閿佸畾缁堢灞忓箷
				 DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				 Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.lock.screen",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.lock.screen.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog,operateType,operateContent,user,organization);
				 String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.lock.screen.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("4",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
			 }else if(flag == 7){    //鎭㈠鍑哄巶璁剧疆
				 DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				 Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.delete.device",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.default.set.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog,operateType,operateContent,user,organization);
				 String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.default.set.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("5",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
			 }else if(flag == 8){     //娓呴櫎閿佸睆瀵嗙爜
				 DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				 Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.clean.password",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.clean.password.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog,operateType,operateContent,user,organization);
				 String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.clean.password.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("6",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
			 }else if(flag == 9){    //缁堢璁惧鍝嶉搩
				 DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,user.getId());  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				 Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.delete.device",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.device銆俠ell.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog,operateType,operateContent,user,organization);
				 String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.device.bell.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("7",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
			 }else if(flag == 10){   //娉ㄩ攢鐧诲綍
				 DeviceManagerModel deviceInfo = deviceManagerService.queryDeviceAndUserInfo(Integer.valueOf(id));
				 Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei(),deviceInfo.getUserName(),deviceInfo.getRealName(),deviceInfo.getName()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.log.off",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.log.off.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog,operateType,operateContent,user,organization);
				 String userType = "";
					if(deviceInfo.getUserType() == 4){
					   userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
					}else{
					   userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
					}
					String deciveOperateType = messageSource.getMessage("logs.device.operate.log.off.type",null, LocaleContextHolder.getLocale());
					Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
					String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
					saveSysDeviceLog("8",deviceInfo,userId,deviceOperateContent,organization,user,deviceLog);
			 }
		 }
		return map;
	 }
	
	 private void saveSysDeviceLog(String type, DeviceManagerModel deviceInfo, String userId,
			String deviceOperateContent, OrganizationModel organization, UserModel user, SysmanageDeviceLog deviceLog) {
		    try {
				deviceLog.setEventType(type);   //浜嬩欢绫诲瀷琛ㄧず瑙ｉ攣閿佸畾
				deviceLog.setDeviceId(deviceInfo.getId());
				deviceLog.setDeviceName(deviceInfo.getDevice_name());
				if(StringUtil.isNotBlank(userId)){
					deviceLog.setUserId(Integer.valueOf(userId));
				}
				deviceLog.setUserName(user.getUsername());
				deviceLog.setOperateContent(deviceOperateContent);
				deviceLog.setOrgId(organization.getId());
				deviceLog.setOperateDate(new Date());
				deviceLog.setCreateDate(new Date());
				deviceLog.setCreateUser(user.getId());
				deviceLog.setUpdateDate(new Date());
				deviceLog.setUpdateUser(user.getId());
				sysmanageDeviceLogDao.insertSelective(deviceLog);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	}


	private void saveSysLog(SysmanageLog systemLog, String operateType, String operateContent,UserModel user,OrganizationModel organization) {
			try {
				systemLog.setOperateType(operateType);
				systemLog.setOrgId(organization.getId());
				systemLog.setOperateContent(operateContent);
				systemLog.setUserId(user.getId());
				systemLog.setUsername(user.getUsername()); 
				systemLog.setUserType(AuthStatus.SOFTTEK_CUSTOMER.toString());
				systemLog.setOperateType(operateType);
				systemLog.setOperateContent(operateContent);
				systemLog.setCreateDate(new Date());
				systemLog.setCreateUser(user.getId());
				systemLog.setUpdateDate(new Date());
				systemLog.setUpdateUser(user.getId());
				systemLog.setOperateTime(new Date());
				sysmanageLogService.insertSelective(systemLog);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	}


	//鏍囪璁惧涓㈠け
	 @Log(operateType="logs.customer.remarkDevice.type.lost",operateContent="logs.customer.remarkDevice.content.lost",args={"id"})
	 @Link(family="customer",label="institution.home.index.label")
	 @RequestMapping(value="remarkDevice",method=RequestMethod.POST)
	 @ResponseBody
	 public Map<String,Object> remarkDevice(HttpServletRequest request){
		 Map<String,Object> map = new HashMap<String, Object>();
		 Map<String,String> customDictionary = new HashMap<String, String>();
		 List<String> deviceToken = null;
		 Integer type = Integer.valueOf(request.getParameter("type"));
		 String sn = request.getParameter("sn");
		 String id = request.getParameter("id");
		 String userId = request.getParameter("uid");
		 DeviceBasicInfoModel deviceBasicInfo = deviceBasicInfoService.findOne(Integer.valueOf(id));
		 DeviceManagerModel deviceManager = new DeviceManagerModel();
		 DeviceSettingModel deviceSetting = new DeviceSettingModel();
		 Map<String,String> extra = new HashMap<String, String>();
		 try {
			if(type == 1){
				 deviceManager.setLost_status(0); //标记设备丢失
			     deviceSetting.setRemarkDevice(1); //1表示设备丢失
			     deviceManager.setVisit_status(0);//在设备丢失时，同时锁定设备
			     customDictionary.put("lockDevice", "1");
			 }else{
				 deviceSetting.setRemarkDevice(2);  //2表示设备找回
		    	 deviceManager.setLost_status(null);   //标记设备找回
				 deviceManager.setVisit_status(null);//在设备找回时，同时解锁设备
				 customDictionary.put("unLockDevice", "1");
			 }
			deviceSetting.setSn(sn);
			deviceSetting.setRemarkDevice(1);
			deviceManager.setId(Integer.valueOf(id));
			int result = deviceManagerService.updateLostStatus(deviceManager);
			if(result == 1){
				if(deviceBasicInfo.getDevice_type().equals("andorid")){
					extra.put("deviceSetting", JSONObject.fromObject(deviceSetting).toString());
					MqProducerThread mqProducerThread = new MqProducerThread(userId, null, null, 2, extra);
					taskExecutor.execute(mqProducerThread);
				}else if(deviceBasicInfo.getDevice_type().equals("ios")){
					deviceToken = new ArrayList<String>();
					deviceToken.add(deviceBasicInfo.getDeviceToken().replaceAll(" ", ""));
					IosPushUtil.pushDataToIos(deviceToken,null, 1, "", customDictionary);
				}
			}
			 Object orgObj = request.getSession().getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
				// 鑾峰彇绠＄悊鍛�
			 Object obj = request.getSession().getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
			 OrganizationModel organization = (OrganizationModel)orgObj;
			 UserModel user = (UserModel) obj;
			 SysmanageLog systemLog = new SysmanageLog();
			 SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
			 if(type == 1){
				 DeviceManagerModel deviceInfo = deviceManagerService.queryDevice(sn,Integer.valueOf(userId));  //鏌ヨ璁惧鍚嶇О鍜孍SN/IMEI鍙�
				 Object[] args = {deviceInfo.getDevice_name(),deviceInfo.getEsnoOrImei()};
				 String operateType = messageSource.getMessage("logs.device.operate.type.delete.device",null, LocaleContextHolder.getLocale());
				 String operateContent = messageSource.getMessage("logs.device.operate.remark.device.lost.content",args, LocaleContextHolder.getLocale());
				 saveSysLog(systemLog, operateType, operateContent, user, organization);
				 String userType = "";
				 if(deviceInfo.getUserType() == 4){
				    userType =	messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
				 }else{
				    userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
				 }
				 String deciveOperateType = messageSource.getMessage("logs.device.operate.remark.lost.type",null, LocaleContextHolder.getLocale());
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 Object[] arg = {userType,deviceInfo.getRealName(),sdf.format(new Date()),deviceInfo.getDevice_name(),deciveOperateType};
				 String deviceOperateContent =  messageSource.getMessage("logs.device.operate.content",arg, LocaleContextHolder.getLocale());
				 saveSysDeviceLog("9", deviceInfo, userId, deviceOperateContent, organization, user, deviceLog);
			 }
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		 return map;
	 }
	 @RequestMapping(value="/deviceLocation")
	    @ResponseBody
		public Map<String,Object> deviceLocation(Integer did,Integer userId,HttpServletRequest request){
		 	Date date = new Date();
	    	locationRequestDate = date;
	    	Map<String,Object> dateMap = new HashMap<String,Object>();
	    	DeviceManagerModel deviceManager = deviceManagerService.queryDeviceInfoById(did);
	    	Map<String, String> map = new HashMap<String, String>();
	    	List<String> deviceToken = new ArrayList<String>();
			if (userId != null) {
				map.put("uploadxy", "1");
				map.put("sn", deviceManager.getSn());
				if(deviceManager.getDevice_type().equals("android")){
					MqProducerThread mqProducerThread=new MqProducerThread(userId.toString(), null, null, 2, map);
					taskExecutor.execute(mqProducerThread);
				}else if(deviceManager.getDevice_type().equals("ios")){
					deviceToken.add(deviceManager.getDeviceToken().replaceAll(" ", ""));
				}
				if(deviceToken.size() > 0){
					IosPushUtil.pushDataToIosTr(deviceToken, null, 1, "", map);
				}
		   }
			dateMap.put("locationTime", CommUtil.Date2String(date));
			return dateMap;
		}
	    
	    @RequestMapping(value="/getLocation")
	    @ResponseBody
		public Map<String, Object> getLocation(String did,Integer times, HttpServletRequest request, HttpServletResponse response) {
			Map<String, Object> map = new HashMap<String, Object>();
			if(times > 0){
			   DeviceLocationModel deviceLocation = deviceManagerService.queryDeviceLocation(Integer.valueOf(did));
			   if(deviceLocation != null){
				   if(deviceLocation.getCreateTime().getTime() > locationRequestDate.getTime()){
					   map.put("locationStatus","success");
					   map.put("deviceLocation", deviceLocation);  //瀹氫綅鎴愬姛
					   map.put("locationRequestDate", CommUtil.Date2String(locationRequestDate));
				   }else{
					   map.put("locationStatus","locationing");  //瀹氫綅涓�
					   map.put("locationRequestDate", CommUtil.Date2String(locationRequestDate));
				   }
			   }
			}else{
				map.put("locationStatus", "fail");//鍊掕鏃跺凡缁忓埌浜嗭紝瀹氫綅澶辫触
				map.put("locationRequestDate", CommUtil.Date2String(locationRequestDate));
			}
			return map;
		} 
	 
}
