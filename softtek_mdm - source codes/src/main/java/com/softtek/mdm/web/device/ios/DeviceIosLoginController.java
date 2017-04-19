package com.softtek.mdm.web.device.ios;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.SecurityEventLogModel;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.TerminalService;
import com.softtek.mdm.service.TokenUpdateInfoService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.DirectionStatus;
import com.softtek.mdm.thread.LogThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.DateUtil;
import com.softtek.mdm.util.MDMProtocolUtils;
import com.softtek.mdm.util.SignFileUtil;
import com.softtek.mdm.web.institution.OrganizationController;
import jodd.util.StringUtil;

/**
 * 处理设备登录和基本连接配置
 * 
 * @author color.wu
 */
@Controller
@RequestMapping(value = "/terminal/ios")
public class DeviceIosLoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private TokenUpdateInfoService tokenUpdateInfoService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private TerminalService deviceLoginService;
	@Autowired
	private TaskExecutor taskExecutor;
	
	private Logger logger = Logger.getLogger(OrganizationController.class);

	/**
	 * 移动设备用户登录服务器
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return	
	 * @throws IOException..
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody DeviceResultModel<Map<String, Object>> index(@RequestBody UserModel user,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		String mString="";
		String filePath = "";
		String newFilePath = "";
		String date = DateUtil.getyyyyMMddHHmmss(new Date());
		Locale localeLanguage = request.getLocale();
		//如果必要的参数不为空
		if (user != null 
			&& StringUtil.isNotEmpty(user.getUsername()) 
			&& StringUtil.isNotEmpty(user.getPassword())
			&& StringUtil.isNotEmpty(user.getIosUuid())) {
			// 获取机构id
			Integer orgId = Integer.valueOf(user.getMark());
			//检测当前机构id下，当然帐号名是否存在
			Integer userId = userService.checkUser(user.getUsername(), orgId);
			if (null == userId) {
				userService.updateUser(user);
				//帐号错误
				mString=messageSource.getMessage("web.device.devicelogincontroller.index.user.name.error", null,"帐号错误",localeLanguage);
				return new DeviceResultModel<Map<String,Object>>(401, mString, null);
			}
			
			//用户存在，检测密码是否正确
			UserModel userModel = userService.findOne(userId);
					//userService.checkPassword(user.getUsername(), orgId);
			//密码加密
			String password = CommUtil.md5PasswordEncoderEncode(user.getPassword());
			if(!password.equals(userModel.getPassword())){
				//记录输入错误密码的次数
				int loginCount = userModel.getDeviceLoginCount() == null ? 0 : userModel.getDeviceLoginCount();
				user.setDeviceLoginCount(++loginCount);
				user.setId(userId);
				userService.updateUser(user);
				
				//密码错误，授权不通过
				mString=messageSource.getMessage("web.device.devicelogincontroller.index.password.error", null,localeLanguage);
				return new DeviceResultModel<Map<String,Object>>(401, mString, null);
			} else {
				// 当前用户密码和帐号都正确，允许登录
				UserModel usr = userModel;
				//userService.findOne(userId);
				if(usr.getIs_active() == 0){
					mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.unactive", null,localeLanguage);
					return new DeviceResultModel<Map<String,Object>>(401, mString, null);
				}
				//检测license授权
				List<Integer> ids=new ArrayList<>();
				ids.add(usr.getOrganization().getId());
				
				//检测license
				DeviceResultModel<Map<String, Object>> drMap=checkLicense(ids,localeLanguage);
				if(drMap!=null){
					return drMap;
				}
				
				PolicyModel policy = policyService.findOne(usr.getPolicy().getId());
				usr.setIosUuid(user.getIosUuid());
				//验证用户策略
				if(policy.getLogin_error_limit_times() == 1){
				 if(new DateTime(usr.getUpdateTime().getTime()).plusMinutes(10).isAfter(DateTime.now().getMillis())){
					  if((usr.getDeviceLoginCount()==null?0:usr.getDeviceLoginCount()) >= policy.getLogin_error_limit()){
						  saveSecuLog(usr,policy,localeLanguage);
						  Object[] objects={policy.getLogin_error_limit(),policy.getLogin_error_limit_lock()};
						  mString=messageSource.getMessage("web.device.devicelogincontroller.index.policy.password.error", objects, localeLanguage);
						  return new DeviceResultModel<Map<String,Object>>(401, mString, null);
					  }
				  }
				}
				
				//当前已经绑定的设备数目
				int alreadyCount = deviceBasicInfoService.findCountWithoutCurrentByUserAndIosUuid(usr);
				if(policy.getDevice_limit_count()<alreadyCount){
					Object[] objects={policy.getDevice_limit_count()};
					mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.limit", objects, localeLanguage);
					return new DeviceResultModel<Map<String,Object>>(401, mString, null);
				}
				
				DeviceBasicInfoModel basic=deviceBasicInfoService.findByIosUuid(StringUtils.trimToEmpty(user.getIosUuid()));
				if (basic != null) {
					if (!usr.getId().equals(basic.getUser().getId())) {
						//当前手机不属于当前用户
						mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.notbelong", null,"您不是该手机的主人", localeLanguage);
						return new DeviceResultModel<Map<String,Object>>(401, mString, null);
					}
				}
				if (new Integer(1).equals(usr.getIs_lock())) {
					//已锁
					mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.lock", null,"手机已被锁定",localeLanguage);
					return new DeviceResultModel<Map<String,Object>>(401, mString, null);
				}
				if (!new Integer(1).equals(usr.getIs_active())) {
					//未解锁
					mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.unactive", null,"帐号未激活",localeLanguage);
					return new DeviceResultModel<Map<String,Object>>(401, mString, null);
				}

				Map<String, Object> maps=new HashMap<String,Object>();
				// 用户名和密码正确
				// 生成Token存入数据库
				String token = CommUtil.generateToken(user.getUsername(), password, orgId.toString());
				usr.setToken(token);
				// 同时更新用户登录时间和ip
				usr.setLast_time(new Date());
				usr.setLast_ip(CommUtil.getIp(request));
				maps.put("user", usr);
				
				if(basic!=null){
					DeviceBasicInfoModel temp = new DeviceBasicInfoModel();
					temp.setId(basic.getId());
					temp.setLast_login_time(new Date());
					maps.put("deviceBasicInfo", temp);
					deviceLoginService.updateInfo(maps);
				}
				userService.updateUser(user);
				userService.updateDeviceLoginCount(0,userId);
				
				saveDeviLog(basic,usr,CommUtil.getIp(request),localeLanguage);
				
				//开始安装描述文件的的操作
				if(user.getIsProfile() == 0){   
					//为了防止客户端离线卸载描述文件，每次需要安装描述文件之前，先删除tokenupdate表中的数据，如果没有数据也一样更新，不影响业务
					tokenUpdateInfoService.deleteTokenUpdateInfoByUuid(user.getIosUuid());
				}
				//获取.mobileconfig文件字符串，并修改其中的checkIn和serverUrl地址(带上userId)
				String str = "";
				TokenUpdateInfo tokenUpdateInfo = tokenUpdateInfoService.queryIsProfileByIosUuid(user.getIosUuid());
				
				if(tokenUpdateInfo == null){
					String uuid = CommUtil.generate32UUID();
					Map<String, Entry<DirectionStatus, String>> mapConfig =new HashMap<>();
					mapConfig.put("CheckInURL", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, String.format("%s?userId=%s&uuid=%s&iosUuid=%s", CommUtil.CHECK_IN_URL,userId.toString(),uuid,user.getIosUuid())));
					mapConfig.put("ServerURL", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT, String.format("%s?userId=%s&uuid=%s&iosUuid=%s", CommUtil.SERVER_URL,userId.toString(),uuid,user.getIosUuid())));
					mapConfig.put("/plist/dict/PayloadIdentifier", new AbstractMap.SimpleEntry<DirectionStatus, String>(DirectionStatus.NEXT,"mdm.softtek.com" + uuid));
					str = MDMProtocolUtils.getMobileConfigWithMap(mapConfig);
				}
				/**
				 * 将组装好的str写成文件
				 * 然后对文件进行签名
				 */
				if(StringUtils.isNotEmpty(str)){
					filePath = String.format("mobileconfig/unSign_%s.mobileconfig", date);
				    newFilePath = String.format("new_mobileconfig/unSign_%s.mobileconfig", date);
				    // 将未签名文件的内容写入已签名的文件(用openssl签名)
				    SignFileUtil.signFile(str,filePath,newFilePath);
				}
				//授权通过
				Map<String,Object> map = new HashMap<String,Object>();
				if(StringUtils.isNotEmpty(str)){
					usr.setConfigFile(filePath);
					usr.setSignConfigFile(newFilePath);
					userService.updateUser(usr);    
					//将生成的未签名的描述文件和签过名的描述文件的路径存在数据库中，方便在用户安装好描述文件之后，删除这些描述文件
					map.put("certBody", CommUtil.ROOT_HTTP_PATH+newFilePath);
				}else{
					map.put("certBody", "");
				}
				map.put("user", usr);
				//用户策略
				PolicyModel policy1 = policyService.findOne(usr.getPolicy().getId());
				map.put("policy", policy1);
                mString=messageSource.getMessage("web.device.devicelogincontroller.index.login.success", null, localeLanguage);
                return new DeviceResultModel<Map<String,Object>>(200, mString, map);
			}
		} else {
			//不允 许授权
			mString=messageSource.getMessage("web.device.devicelogincontroller.index.account.error", null, localeLanguage);
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
	
	/**
	 * 曹操设备登录安全日志信息
	 * @param user
	 * @param policy
	 * @param locale
	 */
	private void saveSecuLog(UserModel user,PolicyModel policy,Locale locale){
		if(user==null||policy==null){
			return ;
		}
	  //保存安全事件日志
	  SecurityEventLogModel securityEventLog = new SecurityEventLogModel();
	  Object[] obj={user.getRealname(),StringUtils.trimToEmpty(user.getDeviceName()),policy.getLogin_error_limit_lock()};
	  String operateContent = messageSource.getMessage("web.device.devicelogincontroller.index.login.lock.msg",obj,locale);
	  securityEventLog.setEventType("2");
	  securityEventLog.setLevel("2");
	  securityEventLog.setOperateContent(operateContent);
	  securityEventLog.setCreateBy(user.getId());
	  securityEventLog.setUpdateBy(user.getId());
	  taskExecutor.execute(new LogThread(null, securityEventLog,null));
	}
	
	/**
	 * 保存设备登录日志
	 * @param basic
	 * @param user
	 * @param ip
	 * @param local
	 */
	private void saveDeviLog(DeviceBasicInfoModel basic,UserModel user,String ip,Locale local){
		SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
		deviceLog.setEventType("10");
		if (basic != null) {
			deviceLog.setDeviceId(basic.getId());
		}
		
		deviceLog.setDeviceName(user.getDeviceName());
		deviceLog.setUserName(user.getRealname());
		deviceLog.setUserId(user.getId());
		deviceLog.setCreateUser(user.getId());
		deviceLog.setUpdateUser(user.getId());
		Object[] objects = {user.getRealname(),CommUtil.Date2String(new Date()),user.getDeviceName(),ip};
		String strMsg = messageSource.getMessage("logs.device.device.login.type.new", objects,local);
		deviceLog.setOperateContent(strMsg);
		deviceLog.setOrgId(user.getOrganization().getId());
		deviceLog.setOperateDate(new Date());
		deviceLog.setCreateDate(new Date());
		deviceLog.setUpdateDate(new Date());
		taskExecutor.execute(new LogThread(deviceLog, null,null));
	}
}
