package com.softtek.mdm.web.device;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.LocaleIn;
import com.softtek.mdm.bean.IsNetworkAvailableBean;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.util.Constant;

/**
 * 处理移动端获取设备策略信息
 * @author jane.hui
 *
 */
@Controller
@RequestMapping(value="/terminal/policy")
public class DeviceUserPolicyController {

	@Autowired
	private DeviceService deviceService;
	
    /**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSourceService;
	
	/**
	 * 根据策略id获取策略
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public @ResponseBody DeviceResultModel<Map<String, Object>> index(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,@LocaleIn Locale localeLanguage){
		HashMap<String, Object> map = new HashMap<String, Object>();
		DeviceResultModel<Map<String, Object>> deviceResult=new DeviceResultModel<Map<String, Object>>();
		
		String id = request.getParameter("policyId");
		AndroidDevicePolicy policy = new AndroidDevicePolicy();
		// 存放黑白名单URL
		if(StringUtils.isNotBlank(id)){
			if(Constant.DEFAULT_POLICY_ID.equals(id)){
			   policy = getDefaultPolicy(localeLanguage);
			} else {
			   Integer intId = Integer.valueOf(id);
			   policy = deviceService.getPolicy(intId);
			}
			deviceResult.setStatus(200);
			deviceResult.setMsg("success");
			map.put("androidDevicePolicy", policy);
			deviceResult.setData(map);
		} else {
			deviceResult.setStatus(404);
			deviceResult.setMsg(messageSourceService.getMessage("web.institution.application.upload.version.resultdto.message.notexsits",null,localeLanguage));
		}
		return deviceResult;
	}
	
	// 获取默认策略
	public AndroidDevicePolicy getDefaultPolicy(Locale localeLanguage){
		AndroidDevicePolicy defaultPolicy = new AndroidDevicePolicy();
		defaultPolicy.setId(0);
		defaultPolicy.setName(messageSourceService.getMessage("tiles.institution.policy.default.policy",null, localeLanguage));
		defaultPolicy.setDescription(messageSourceService.getMessage("tiles.institution.policy.default.policy",null, localeLanguage));
		defaultPolicy.setIsEnable(Constant.YES);
		defaultPolicy.setPasswordLength(Constant.DEFAULT_VALUE);
		defaultPolicy.setPasswordComplexity(Constant.DEFAULT_VALUE);
		defaultPolicy.setLockLongestTime(Constant.DEFAULT_VALUE);
		defaultPolicy.setPasswordValidity(Constant.DEFAULT_VALUE);
		defaultPolicy.setPasswordHistory(Constant.DEFAULT_VALUE);
		defaultPolicy.setAttemptTimes(Constant.DEFAULT_VALUE);
		defaultPolicy.setDeviceEncryption(Constant.NO);
		defaultPolicy.setSdEncryption(Constant.NO);
		defaultPolicy.setAllowUseCamera(Constant.YES);
		defaultPolicy.setAllowUseWifi(Constant.YES);
		defaultPolicy.setAllowUseBluetooth(Constant.YES);
		defaultPolicy.setAllowMicrophone(Constant.YES);
		defaultPolicy.setIsNetLimit(Constant.NO);
		return defaultPolicy;
	}
	
	/**
	 * 根据策略id获取策略
	 */
	@RequestMapping(value="/getState",method=RequestMethod.GET)
	public @ResponseBody DeviceResultModel<String> index(HttpServletRequest request,@LocaleIn Locale localeLanguage){
		DeviceResultModel<String> deviceResult=new DeviceResultModel<String>();
		String id = request.getParameter("userId");
		
		if(StringUtils.isNotBlank(id)){
			String result = deviceService.getPolicyByUserId(id);
			deviceResult.setData(result);
			deviceResult.setStatus(200);
			deviceResult.setMsg("success");
		} else {
			deviceResult.setStatus(404);
			deviceResult.setMsg(messageSourceService.getMessage("web.institution.application.upload.version.resultdto.message.notexsits",null,localeLanguage));
		}
		return deviceResult;
	}
	
	/**
	 * 根据用户id获取最新策略的url list
	 */
	@RequestMapping(value="/getTimeStrAndUrlList",method=RequestMethod.GET)
	public @ResponseBody DeviceResultModel<IsNetworkAvailableBean> getTimeStrAndUrlList(HttpServletRequest request,@LocaleIn Locale localeLanguage){
		DeviceResultModel<IsNetworkAvailableBean> deviceResult=new DeviceResultModel<IsNetworkAvailableBean>();
		String id = request.getParameter("userId");
		if(StringUtils.isNotBlank(id)){
			deviceResult.setData(deviceService.getTimeStrAndUrlList(id));
			deviceResult.setStatus(200);
			deviceResult.setMsg("success");
		} else {
			deviceResult.setStatus(404);
			deviceResult.setMsg(messageSourceService.getMessage("web.institution.application.upload.version.resultdto.message.notexsits",null,localeLanguage));
		}
		return deviceResult;
	}
}
