package com.softtek.mdm.web.device.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.LocaleIn;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.SecurityEventLogModel;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.TerminalService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.DeviceTypeStatus;
import com.softtek.mdm.thread.LogThread;
import com.softtek.mdm.util.CommUtil;


/**
 * 处理设备登录和基本连接配置
 * 
 * @author color.wu
 */
@Controller
@RequestMapping(value = "/terminal/android")
public class DeviceAndroidLoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private TerminalService deviceLoginService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * 移动设备用户登录服务器
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody DeviceResultModel<Map<String, Object>> index(@RequestBody UserModel user,
			HttpServletRequest request, HttpServletResponse response, HttpSession session,@LocaleIn Locale localeLanguage) throws IOException {
		String mString="login error";
		
		if (user != null && !StringUtils.isBlank(user.getUsername()) && !StringUtils.isBlank(user.getPassword())) {
			String password = CommUtil.md5PasswordEncoderEncode(user.getPassword());
			
			// 获取机构id
			Integer orgId = Integer.valueOf(user.getMark());
			Integer userId = userService.checkUser(user.getUsername(), orgId);
			if (null == userId) {
				//用户名或密码错误
				userService.updateUser(user);
				
				mString=messageSource.getMessage("web.device.devicelogincontroller.index.user.name.error", null,"用户名/帐号错误", localeLanguage);
				return new DeviceResultModel<Map<String,Object>>(401, mString, null);
			}
			
			if (userId != null && userId > (Integer)0) {
				UserModel userModel = userService.checkPassword(user.getUsername(), orgId);
				
				//记录输入错误密码的次数
				int loginCount = userModel.getDeviceLoginCount() == null ? 0 : userModel.getDeviceLoginCount();
				
				if(!password.equals(userModel.getPassword())){
					//密码不正确
					loginCount ++ ;
					
					userService.updateUser(user);
					userService.updateDeviceLoginCount(loginCount,userId);
					userService.updateUser(user);

					mString=messageSource.getMessage("web.device.devicelogincontroller.index.password.error", null,"密码错误", localeLanguage);
					return new DeviceResultModel<Map<String,Object>>(401, mString, null);
				}else{
					// 当前用户密码和帐号都正确，允许登录
					UserModel usr = userService.findOne(userId);
					
					//检测license授权
					List<Integer> ids=new ArrayList<>();
					ids.add(usr.getOrganization().getId());
					DeviceResultModel<Map<String, Object>> deviceResult=checkLicense(ids,localeLanguage);
					if(deviceResult!=null){
						return deviceResult;
					}
					
					//当前已经绑定的设备数目
					int alreadyCount=deviceBasicInfoService.findCountWithoutCurrentByUser(usr);
					usr.setPhone(null);
					//验证用户策略
					PolicyModel policy=policyService.findOne(usr.getPolicy().getId());
					
					if(policy.getLogin_error_limit_times()== 1){
					  
						if(((float)(new Date().getTime() - usr.getUpdateTime().getTime())/60000) < (float)10){
							if(usr.getDeviceLoginCount() >= policy.getLogin_error_limit()){
							  //保存安全事件日志
							  SecurityEventLogModel securityEventLog = new SecurityEventLogModel();
							  Object[] obj={usr.getRealname(),user.getDeviceName(),policy.getLogin_error_limit_lock()};
							  String operateContent = messageSource.getMessage("web.device.devicelogincontroller.index.login.lock.msg",obj,localeLanguage);
							 
							  securityEventLog.setEventType("2");
							  securityEventLog.setLevel("2");
							  securityEventLog.setOperateContent(operateContent);
							  securityEventLog.setCreateBy(usr.getId());
							  securityEventLog.setUpdateBy(usr.getId());
							  taskExecutor.execute(new LogThread(null, securityEventLog, null));
							 
							  Object[] objects={policy.getLogin_error_limit(),policy.getLogin_error_limit_lock()};
							  mString=messageSource.getMessage("web.device.devicelogincontroller.index.policy.password.error", objects, localeLanguage);
							  return new DeviceResultModel<Map<String,Object>>(401, mString, null);
						  }
					  }
					}
					
					if(policy.getDevice_limit_count()<alreadyCount){
						Object[] objects={policy.getDevice_limit_count()};
						mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.limit", objects, localeLanguage);
						return new DeviceResultModel<Map<String,Object>>(401, mString, null);
					}
					
					DeviceBasicInfoModel basic=deviceBasicInfoService.findBySN(StringUtils.trimToEmpty(user.getPhone()));
					if (new Integer(1).equals(usr.getIs_lock())) {
						//已锁
						mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.lock", null,localeLanguage);
						return new DeviceResultModel<Map<String,Object>>(401, mString, null);
					}
					if (!new Integer(1).equals(usr.getIs_active())) {
						//未激活
						mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.unactive", null,localeLanguage);
						return new DeviceResultModel<Map<String,Object>>(401, mString, null);
					}
					
					Map<String, Object> maps=new HashMap<String,Object>();
					// 用户名和密码正确
					// 同时更新用户登录时间和ip
					usr.setLast_time(new Date());
					usr.setLast_ip(CommUtil.getIp(request));
					maps.put("user", usr);
					
					if(basic!=null){
						if (!usr.getId().equals(basic.getUser().getId())) {
							//当前手机不属于当前用户
							mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.notbelong", null, localeLanguage);
							return new DeviceResultModel<Map<String,Object>>(401, mString, null);
						}
						DeviceBasicInfoModel temp = new DeviceBasicInfoModel();
						temp.setId(basic.getId());
						temp.setLast_login_time(new Date());
						
						maps.put("deviceBasicInfo", temp);
						
						deviceLoginService.updateInfo(maps);
					}
					
					userService.updateUser(user);
					userService.updateDeviceLoginCount(0,userId);
					
					// 将用户的用户名，机构id和Token返回给客户端
					user.setPassword(null);
					user.setStructure(usr.getStructure());
					user.setId(usr.getId());
					user.setRealname(usr.getRealname());
					
					// ==========添加登录日志 start===============
					SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
					deviceLog.setEventType("10");
					DeviceBasicInfoModel basicInfo = deviceBasicInfoService.findBySN(user.getPhone());
					if (basicInfo != null) {
						deviceLog.setDeviceId(basicInfo.getId());
					}
					deviceLog.setDeviceName(user.getDeviceName());
					deviceLog.setUserName(usr.getRealname());
					deviceLog.setUserId(usr.getId());
					deviceLog.setCreateUser(usr.getId());
					deviceLog.setUpdateUser(usr.getId());
					// ==========添加登录日志 end=================
					
					Object[] objects = {usr.getRealname(),CommUtil.Date2String(new Date()),user.getDeviceName(),CommUtil.getIp(request)};
					String strMsg = messageSource.getMessage("logs.device.device.login.type.new", objects,localeLanguage);
					deviceLog.setOperateContent(strMsg);
					deviceLog.setOrgId(usr.getOrganization().getId());
					deviceLog.setOperateDate(new Date());
					deviceLog.setCreateDate(new Date());
					deviceLog.setUpdateDate(new Date());
					taskExecutor.execute(new LogThread(deviceLog, null, null));
					
					// 用户登录成功，将用户最新的用户策略和设备策略的信息返回给用户
					Map<String, Object> map = new HashMap<String, Object>();
					List<DeviceBasicInfoModel> deviceBasicInfos = deviceBasicInfoService.findByUserId(usr.getId());
					user.setWeight(CollectionUtils.isNotEmpty(deviceBasicInfos)?deviceBasicInfos.size():0);
					
					map.put("user", user);
					PolicyModel policy1 = policyService.findOne(usr.getPolicy().getId());
					map.put("policy", policy1);
					Map<String, Object> devicePolicyIdMap = deviceService.findUserDevicePolicy(usr.getId(),
							usr.getOrganization().getId());
					String str = (String) devicePolicyIdMap.get(DeviceTypeStatus.SOFTTEK_ANDROID.toString());
					if(StringUtils.isNotEmpty(str)){
						Integer pkey=Integer.valueOf(str);
						if(pkey!=null){
							AndroidDevicePolicy androidDevicePolicy=deviceService.findOneAndroidDevicePolicy(pkey);
							if(null==androidDevicePolicy){
								androidDevicePolicy=deviceService.getDefaultPolicy();
							}
							map.put("androidDevicePolicy", androidDevicePolicy);
							//当前安卓策略的黑白名单想jane获取
						}
					}else{
						map.put("androidDevicePolicy", deviceService.getDefaultPolicy());
					}
					// iOS的设备策略没有放入map
					mString=messageSource.getMessage("web.device.devicelogincontroller.index.login.success", null,localeLanguage);
					return new DeviceResultModel<Map<String,Object>>(200, mString, map);
				}
			}
			return new DeviceResultModel<Map<String,Object>>(401, mString, null);
		} else {
			mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.error", null,localeLanguage);
			return new DeviceResultModel<Map<String,Object>>(401, mString, null);
		}
	}
	
	/**
	 * license 校验
	 * @param ids
	 * @return
	 */
	private DeviceResultModel<Map<String,Object>> checkLicense(List<Integer>ids,Locale locale){
		String mString=null;
		//检测license
		switch (licenseService.checkLicense(ids)) {
		case 1:
			mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.license.beyond.date", null,"授权过期", locale);
			return new DeviceResultModel<Map<String,Object>>(401, mString, null);
		case 2:
			mString=messageSource.getMessage("security.myuserdetailserviceimpl.loaduserbyusername.license.beyond.count", null,"授权人数超过限制",locale);
			return new DeviceResultModel<Map<String,Object>>(401, mString, null);
		default:
			break;
		}
		return null;
	}
}
