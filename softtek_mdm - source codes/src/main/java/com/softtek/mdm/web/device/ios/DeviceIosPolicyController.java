package com.softtek.mdm.web.device.ios;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.IosDevicePolicy;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.DeviceTypeStatus;
import jodd.util.StringUtil;

@Controller
@RequestMapping("/terminal/ios")
public class DeviceIosPolicyController {

	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private DeviceService deviceService;

	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("iosSinglePolicyNotifyService")
	private AbstractIosPush abstractIosPush;
	
	@RequestMapping(value = "/getUserPolicy", method = RequestMethod.POST)
	@ResponseBody
	public DeviceResultModel<PolicyModel> getUserPolicy(HttpServletRequest request) {
		DeviceResultModel<PolicyModel> deviceResult = new DeviceResultModel<PolicyModel>();
		if (StringUtils.isNotEmpty(request.getParameter("userId"))) {
			Integer userId = Integer.valueOf(request.getParameter("userId"));
			PolicyModel policy = policyService.queryPolicyByUserId(userId);
			deviceResult.setStatus(200);
			deviceResult.setData(policy);
			deviceResult.setMsg("success");
		} else {
			deviceResult.setStatus(500);
			deviceResult.setMsg("fail");
		}
		return deviceResult;
	}

	/**
	 * 根据用户id获取ios设备策略
	 * @param request
	 * @return 返回最新设备策略结果
	 */
	@RequestMapping(value = "/getIosPolicy", method = RequestMethod.GET)
	@ResponseBody
	public DeviceResultModel<IosDevicePolicy> getIosPolicy(HttpServletRequest request) {
		DeviceResultModel<IosDevicePolicy> deviceResult = new DeviceResultModel<IosDevicePolicy>();
		String userId =  request.getParameter("userId");
		String policyId = request.getParameter("policyId");
		if (StringUtil.isNotBlank(userId)) {
			Integer intUserId = Integer.valueOf(userId);
			// 当前用户密码和帐号都正确，允许登录
			UserModel usr = userService.findOne(intUserId);
			if(usr != null) {
				Map<String, Object> devicePolicyIdMap = deviceService.findUserDevicePolicy(usr.getId(),
						usr.getOrganization().getId());
				String str = (String) devicePolicyIdMap.get(DeviceTypeStatus.SOFTTEK_IOS.toString());
				if(StringUtil.isNotBlank(str)){
					Integer pkey=Integer.parseInt(str);
					if(pkey!=null){
						IosDevicePolicy iosDevicePolicy = deviceService.findOneIosDevicePolicy(pkey);
						Map<String, Object> map = new HashMap<String, Object>();
						if(iosDevicePolicy!=null&&!iosDevicePolicy.getId().equals(policyId)) {
							map.put("userId", usr.getId());
							map.put("policyId", iosDevicePolicy.getId().toString());
							abstractIosPush.nofity(map);
						} else if(StringUtil.isNotEmpty(policyId)) {
							map.put("userId", usr.getId());
							map.put("policyId", 0);
							abstractIosPush.nofity(map);
						}
						deviceResult.setData(iosDevicePolicy);
				    }
				}
			}
		} else {
			deviceResult.setStatus(500);
			deviceResult.setMsg("fail");
		}
		deviceResult.setMsg("success");
		deviceResult.setStatus(200);
		return deviceResult;
	}
}
