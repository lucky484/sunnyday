package com.softtek.mdm.web.device;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.DeviceLog;
import com.softtek.mdm.model.ClientCommandModel;
import com.softtek.mdm.model.DeviceLogOffModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.MessageSendModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.ClientCommandService;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.MessageSendService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.IosPushUtil;

@Controller
@RequestMapping(value="/terminal/deviceManager")
public class DeviceInterfaceController {
	
	@Autowired
    private MessageSendService messageSendService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DeviceManagerService deviceManagerService;
	
	@Autowired
	private ClientCommandService clientCommandService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	 @RequestMapping(value="/queryAll",method=RequestMethod.GET)
	 @ResponseBody
	 public DeviceResultModel<List<MessageSendModel>> queryAll(HttpServletRequest request){
		DeviceResultModel<List<MessageSendModel>> deviceResult = new DeviceResultModel<List<MessageSendModel>>();
		Integer userId = Integer.valueOf(request.getParameter("userId"));
		String deviceId = request.getParameter("deviceId");
		String lastDate=StringUtils.trimToNull(request.getParameter("lastDate"));
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("deviceId", deviceId);
		map.put("userId", userId);
		map.put("lastDate", lastDate);
		
		List<MessageSendModel> list = messageSendService.find(map);
		
		deviceResult.setData(list);
		deviceResult.setStatus(200);
	    deviceResult.setMsg("success");
	    return deviceResult;
	 }
	 
	 /**
	  * 
	  * @param request
	  * @return
	  */
	 @RequestMapping(value="deleteMessage",method=RequestMethod.POST)
	 @ResponseBody
	 public DeviceResultModel<Integer> deleteMessage(HttpServletRequest request){
		 List<Integer> list = new ArrayList<Integer>();
		 String id = request.getParameter("list");
		 String idStr = id.substring(1,id.length()-1);
		 String[] ids = idStr.split(",");
		 for(int i=0;i<ids.length;i++){
			 list.add(Integer.valueOf(ids[i]));
		 }
		 int result = messageSendService.deleteMessage(list);
		 DeviceResultModel<Integer> deviceResult = new DeviceResultModel<Integer>();
		 deviceResult.setData(result);
		 deviceResult.setStatus(200);
		 deviceResult.setMsg("success");
		 return deviceResult;
	 }
	  
	 /**
	  * 
	  * @param request
	  * @param messageSend
	  */
	 @RequestMapping(value="receiveData",method=RequestMethod.POST)
	 @ResponseBody
	 public void receiveData(HttpServletRequest request,@RequestBody MessageSendModel messageSend){
		 Map<String,String> extra = new HashMap<String, String>();
		 List<String> deviceToken = new ArrayList<String>();
		 if(messageSend != null){
			 messageSend.setUserId(messageSend.getUserId());
			 messageSend.setCreateTime(new Date());
			 messageSend.setUpdateTime(new Date());
			 messageSend.setCreateBy(messageSend.getUserId());
			 messageSend.setUpdateBy(messageSend.getUserId());
			 if(request.getParameter("iosUuid") != null && !request.getParameter("iosUuid").equals("")){
				 DeviceManagerModel deviceManager = deviceManagerService.queryIosIsExists(request.getParameter("iosUuid"));
				 deviceToken.add(deviceManager.getDeviceToken().replaceAll(" ", ""));
				 messageSend.setUdid(deviceManager.getSn());
			 }
			 messageSendService.saveMessage(messageSend);
		     if(request.getParameter("iosUuid") == null && request.getParameter("iosUuid").equals("")){
			     extra.put("messageSend", JSONObject.fromObject(messageSend).toString());
			     MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(messageSend.getUserId()), null, null, 2, extra);
			     taskExecutor.execute(mqProducerThread);
		     }
			 if(deviceToken.size() > 0){
				 extra.put("message", messageSend.getMessage());
				 extra.put("lastDate", DateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
				 extra.put("system", "0");
				 IosPushUtil.pushDataToIosTo(deviceToken, messageSend.getMessage_title(), 1, "default", extra);
			 }
		 }
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param deviceLogOff
	  */
	 @DeviceLog(deviceId = "deviceId", eventType = "eventType", operateContent = "logs.device.log.off.type", userId = "userId",args={"userType","realName","deviceName","date","ip"})
	 @RequestMapping(value="deviceLogOff",method=RequestMethod.POST)
	 public void deviceLogOff(HttpServletRequest request,@RequestBody DeviceLogOffModel deviceLogOff){
		   UserModel user = userService.queryUserInfoById(deviceLogOff.getUserId());  //查询用户信息
		   DeviceManagerModel deviceManager = deviceManagerService.queryDevice(deviceLogOff.getSn(),deviceLogOff.getUserId());
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   String userType = "";
		   if(user.getType() == 4){
			    userType = messageSource.getMessage("logs.device.user.manager.type",null, LocaleContextHolder.getLocale());
		   }else{
			    userType = messageSource.getMessage("logs.device.user.normal.type",null, LocaleContextHolder.getLocale());
		   }
		   request.setAttribute("date", sdf.format(new Date()));
		   request.setAttribute("userType",userType);
		   request.setAttribute("eventType","11");
		   request.setAttribute("orgId", user.getOrganization().getId());
		   request.setAttribute("userId",deviceLogOff.getUserId());
		   request.setAttribute("deviceId",deviceManager.getId());
		   request.setAttribute("deviceName",deviceLogOff.getDeviceName());
		   request.setAttribute("realName",user.getRealname());
		   request.setAttribute("ip", CommUtil.getIp(request));
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param password
	  * @param userId
	  * @param sn
	  * @return
	  */
	 @DeviceLog(deviceId = "deviceId", eventType = "eventType", operateContent = "logs.device.set.Lock.password", userId = "userId",args={"realName","deviceName","date","password"})
	 @RequestMapping(value="updatePassworgLog",method=RequestMethod.POST)
	 @ResponseBody
	 public <T> DeviceResultModel<T> updatePassworgLog(HttpServletRequest request,String password,Integer userId,String sn){
		 DeviceResultModel<T> deviceResult = new DeviceResultModel<T>();
		 if(userId != null){
			 UserModel user = userService.queryUserInfoById(userId);  //查询用户信息
			 DeviceManagerModel deviceManager = deviceManagerService.queryDevice(sn,userId);
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 request.setAttribute("date", sdf.format(new Date()));
			 request.setAttribute("eventType","16");
			 request.setAttribute("orgId", user.getOrganization().getId());
			 request.setAttribute("userId",userId);
			 request.setAttribute("deviceId",deviceManager.getId());
			 request.setAttribute("deviceName",deviceManager.getDevice_name());
			 request.setAttribute("realName",user.getRealname());
			 request.setAttribute("password",password);
			 deviceResult.setStatus(200);
			 deviceResult.setMsg("success");
		 }else{
			 deviceResult.setStatus(500);
			 deviceResult.setMsg("fail");
		 }
		 return deviceResult;
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param status
	  * @param sn
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	@RequestMapping(value="queryAndDeleteData",method=RequestMethod.POST)
	 @ResponseBody
	 public <T> DeviceResultModel<T> queryAndDeleteData(HttpServletRequest request,String status,String sn){
		 DeviceResultModel<T> deviceResult = new DeviceResultModel<T>();
		 if(status.equals("1")){
			 int result = clientCommandService.deleteDevice(sn);
			 if(result == 1){
				 deviceResult.setStatus(200);
				 deviceResult.setMsg("success");
				 deviceResult.setData(null);
			 }
		 }else{
			 ClientCommandModel clientCommand = clientCommandService.queryDeviceDelInfo(sn);
			 deviceResult.setStatus(200);
			 deviceResult.setMsg("success");
			 deviceResult.setData((T)clientCommand);
		 }
		 return deviceResult;
	 }
}
