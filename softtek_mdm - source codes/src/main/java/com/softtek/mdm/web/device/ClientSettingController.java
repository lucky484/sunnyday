package com.softtek.mdm.web.device;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.ClientConfigModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.service.ClientConfigService;
import com.softtek.mdm.service.DeviceManagerService;

@Controller
@RequestMapping(value = "/terminal/clientConfig")
public class ClientSettingController {

	@Autowired
	private ClientConfigService clientConfigService;

	@Autowired
	private DeviceManagerService deviceManagerService;

	/**
	 * 
	 * @param request
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "/queryClientConfig", method = RequestMethod.POST)
	@ResponseBody
	public DeviceResultModel<ClientConfigModel> queryClientConfig(HttpServletRequest request, String orgId) {
		ClientConfigModel clientConfig = clientConfigService.queryClientConfig(Integer.valueOf(orgId));
		return new DeviceResultModel<ClientConfigModel>(200, "success", clientConfig);
	}

	/**
	 * 
	 * @param sn
	 * @return
	 */
	@RequestMapping(value = "/deleteDeviceBySn", method = RequestMethod.POST)
	@ResponseBody
	public <T> DeviceResultModel<T> deleteDeviceBySn(String sn) {
		DeviceResultModel<T> deviceResult = new DeviceResultModel<T>();
		int result = deviceManagerService.deleteDeviceBySn(sn);
		if (result > 0) {
			deviceResult.setStatus(200);
			deviceResult.setMsg("success");
		} else {
			deviceResult.setStatus(500);
			deviceResult.setMsg("fail");
		}
		return deviceResult;
	}
}
