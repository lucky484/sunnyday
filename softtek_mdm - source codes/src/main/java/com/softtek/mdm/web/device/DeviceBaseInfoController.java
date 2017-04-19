package com.softtek.mdm.web.device;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.LocaleIn;
import com.softtek.mdm.model.DeviceAllInfoModel;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.service.TerminalService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.util.CommUtil;

import jodd.util.StringUtil;

/**
 * 与设备端连接处理基本信息
 * 
 * @author color.wu
 *
 */
@Controller
@RequestMapping(value = "/terminal/baseinfo")
public class DeviceBaseInfoController {

	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private SysmanageDeviceLogService sysmanageDeviceLogService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private TerminalService terminalService;

	/**
     * 保存用户与设备有关的信息
     * 
     * @param basic
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody DeviceResultModel<Map<String,Object>> index(@RequestBody DeviceAllInfoModel deviceAllInfo,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取基本信息
		DeviceBasicInfoModel basic = deviceAllInfo.getDeviceBasicInfo();
        // 获取网络信息
		DeviceNetworkStatusModel deviceNetworkStatus = deviceAllInfo.getDeviceNetworkStatus();
        // 获取应用信息
		List<DeviceAppInfoModel> deviceAppInfoList = deviceAllInfo.getDeviceAppInfoList();
        // 获取设备安全
		DeviceSaftyModel deviceSafty = deviceAllInfo.getDeviceSafty();
		
		DeviceResultModel<Map<String,Object>> deviceResult = new DeviceResultModel<Map<String,Object>>();
		Map<String, Object> maps=new HashMap<String,Object>();
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
        // 新增状态下，保存应用信息
		if (deviceAppInfoList != null && deviceAppInfoList.size() > 0) {
			for (int i = 0; i < deviceAppInfoList.size(); i++) {
				deviceAppInfoList.get(i).setBelongDevice(basic);
			}
            // 将应用信息放入map
			maps.put("deviceAppInfoList", deviceAppInfoList);
		}
		if (basic != null && basic.getSn() != null) {
            // 根据用户信息查询是否已经存在，存在则更新，否则新增
			DeviceBasicInfoModel deviceBasicInfo = deviceBasicInfoService.findBySN(basic.getSn());
			basic.setIp(CommUtil.getIp(request));
			basic.setLast_login_time(new Date());
			basic.setLast_collection_time(new Date());
			basic.setUpdate_time(new Date());
			basic.setDevice_status("0");
            // 将设备基本信息放入map
			maps.put("deviceBasicInfo", deviceBasicInfo);
			
			if (deviceBasicInfo == null) {
                // 初次绑定设备
				maps.put("basic", basic);
				maps.put("deviceNetworkStatus", deviceNetworkStatus);
				maps.put("deviceSafty", deviceSafty);
                // 保持设备信息，网络信息，安全信息
				int result = terminalService.baseInfo(maps);
				if(result > 0){
					DeviceBasicInfoModel deviceBasic = deviceBasicInfoService.findBySN(basic.getSn());
					
//					deviceResult.setData(deviceBasic.getId());
					resultMap.put("deviceId", basic.getId());
					SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
					String operateContent = "";
					UserModel user = userService.queryUserInfoById(basic.getUser().getId());
					
					
					if (basic.getIsActive() == 1 && null!=user) {
						Object[] args = { user.getRealname(), CommUtil.Date2String(new Date()), basic.getDevice_name() };
						operateContent = messageSource.getMessage("log.device.join.mon.type", args,
                                LocaleContextHolder.getLocale());// 从国际化资源文件中读取操作内容
                        deviceLog.setEventType("13"); // 时间类型 13代表监控接入
						
                        // =================设备日志=============
						deviceLog.setDeviceId(deviceBasic.getId());
						deviceLog.setDeviceName(deviceBasic.getDevice_name());
						deviceLog.setUserName(user.getRealname());
						deviceLog.setUserId(basic.getUser().getId());
						deviceLog.setCreateUser(basic.getUser().getId());
						deviceLog.setUpdateUser(basic.getUser().getId());
						deviceLog.setOperateContent(operateContent);
						deviceLog.setOrgId(basic.getOrgId());
						deviceLog.setOperateDate(new Date());
						deviceLog.setCreateDate(new Date());
						deviceLog.setUpdateDate(new Date());
                        sysmanageDeviceLogService.insertSelective(deviceLog); // 插入设备日志
                        // ==================设备日志=============
					} 
				}
			} else {
                // 设备已存在，更新基本信息
				basic.setId(deviceBasicInfo.getId());
				basic.setDevice_status("0");
				if(StringUtils.trimToNull(deviceBasicInfo.getImsi())!=null){
				   basic.setImsi(null);
				}
				maps.put("basic", basic);
				maps.put("deviceNetworkStatus", deviceNetworkStatus);
				maps.put("deviceSafty", deviceSafty);
				resultMap.put("deviceId", basic.getId());
                // 更新设备信息
				int result = terminalService.update(maps);
				if(result > 0){
//					String eventType = sysmanageDeviceLogService.queryLogIsExite(basic.getId());
					SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
					UserModel user = userService.queryUserInfoById(basic.getUser().getId());
					Object[] args = { user.getRealname(),CommUtil.Date2String(new Date()), basic.getDevice_name() };
                    String operateContent = messageSource.getMessage("log.device.join.mon.type", args,
                            LocaleContextHolder.getLocale());// 从国际化资源文件中读取操作内容
                    deviceLog.setEventType("13"); // 时间类型 13代表监控接入
						deviceLog.setDeviceId(basic.getId());
						deviceLog.setDeviceName(basic.getDevice_name());
						deviceLog.setUserId(basic.getUser().getId());
						deviceLog.setUserName(user.getRealname());
						deviceLog.setCreateUser(basic.getUser().getId());
						deviceLog.setUpdateUser(basic.getUser().getId());
						deviceLog.setOperateContent(operateContent);
						deviceLog.setOrgId(basic.getOrgId());
						deviceLog.setOperateDate(new Date());
						deviceLog.setCreateDate(new Date());
						deviceLog.setUpdateDate(new Date());
                    sysmanageDeviceLogService.insertSelective(deviceLog); // 插入设备日志
				}
			}
            resultMap.put("currentTime", CommUtil.Date2String(new Date())); // 在上报信息成功后，将系统的当前时间返回给客户端
			deviceResult.setData(resultMap);
			deviceResult.setStatus(200);
			deviceResult.setMsg("success");
			return deviceResult;
		}
		deviceResult.setStatus(400);
		deviceResult.setMsg("failed");
		return deviceResult;
	}

	/**
     * 设备修改密码
     * 
     * @param deviceAllInfo
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/modifypwd", method = RequestMethod.POST)
	public @ResponseBody DeviceResultModel<DeviceBasicInfoModel> changePwd(HttpServletRequest request,
			HttpServletResponse response,@LocaleIn Locale localeLanguage) throws IOException {
		String uid = request.getParameter("uid");
		String old_pwd = request.getParameter("password");
		String password = request.getParameter("newpassword");
		DeviceResultModel<DeviceBasicInfoModel> deviceResult = new DeviceResultModel<DeviceBasicInfoModel>();
		String mString="";
		if (uid != null && uid.length() > 0) {
			UserModel user = userService.findOne(Integer.parseInt(uid));
			if (user != null) {
				Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
				if (user.getPassword().equals(md5PasswordEncoder.encodePassword(old_pwd, null))) {
					UserModel temp = new UserModel();
					temp.setId(user.getId());
					temp.setPassword(md5PasswordEncoder.encodePassword(password, null));
					userService.update(temp);
					
					deviceResult.setStatus(200);
					mString=messageSource.getMessage("web.device.devicebaseinfocontroller.changepwd.password.success", null,localeLanguage);
					deviceResult.setMsg(mString);
				} else {
					deviceResult.setStatus(404);
					mString=messageSource.getMessage("web.device.devicebaseinfocontroller.changepwd.password.failed", null,localeLanguage);
					deviceResult.setMsg(mString);
				}
			} else {
				deviceResult.setStatus(404);
				mString=messageSource.getMessage("web.device.devicebaseinfocontroller.changepwd.password.notexists", null,localeLanguage);
				deviceResult.setMsg(mString);
			}
		} else {
			deviceResult.setStatus(404);
			mString=messageSource.getMessage("web.device.devicebaseinfocontroller.changepwd.password.failed", null,localeLanguage);
			deviceResult.setMsg(mString);
		}
		return deviceResult;
	}

	/**
     * 返回用户最新设备策略和用户策略
     * 
     * @param deviceAllInfo
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/lpolicy", method = RequestMethod.GET)
	public @ResponseBody DeviceResultModel<DeviceAllInfoModel> lastPlocy(DeviceAllInfoModel deviceAllInfo,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		DeviceResultModel<DeviceAllInfoModel> deviceResult = new DeviceResultModel<DeviceAllInfoModel>();
		String sn = request.getParameter("sn");
		
		if(StringUtils.isEmpty(sn)){
			String iosUuid=request.getParameter("iosUuid");
			DeviceBasicInfoModel temp=deviceBasicInfoService.findByIosUuid(iosUuid);
			sn=temp.getSn();
		}
		if (!StringUtil.isBlank(sn)) {
			DeviceBasicInfoModel deviceBasicInfo = deviceBasicInfoService.findBySN(sn);
			if(null!=deviceBasicInfo){
				UserModel user = userService.findOne(deviceBasicInfo.getUser().getId());
                if (user != null) {
                    PolicyModel policy = policyService.findOne(user.getPolicy().getId());
                    deviceAllInfo.setUserPolicy(policy);
                    Map<String, Object> map = deviceService.findUserDevicePolicy(user.getId(),
                            user.getOrganization().getId());
                    deviceAllInfo.setDevicePolicy(map);
                }
			}
		}
		deviceResult.setStatus(200);
		deviceResult.setMsg("success");
		deviceResult.setData(deviceAllInfo);
		return deviceResult;
	}

	/**
     * 返回IMSISIM SIM唯一标识
     * 
     * @param deviceAllInfo
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/getimsi", method = RequestMethod.GET)
	public @ResponseBody DeviceResultModel<String> getimsi(String sn,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		DeviceResultModel<String> deviceResult = new DeviceResultModel<String>();
		String imsi="";
		if (!StringUtil.isBlank(sn)) {
			DeviceBasicInfoModel deviceBasicInfo = deviceBasicInfoService.findBySN(sn);
			if(null!=deviceBasicInfo){
				imsi=deviceBasicInfo.getImsi();
				deviceResult.setStatus(200);
				deviceResult.setMsg("success");
				deviceResult.setData(imsi);
			}else{
				deviceResult.setStatus(400);
			}
		}
	
		
		return deviceResult;
	}

	
}
