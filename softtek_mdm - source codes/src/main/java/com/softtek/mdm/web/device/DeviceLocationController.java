package com.softtek.mdm.web.device;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.DeviceLocationModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.service.DeviceManagerService;

@Controller
@RequestMapping(value="/terminal/deviceLocation")
public class DeviceLocationController {
	  
	@Autowired
	private DeviceManagerService deviceManagerService;
	/**
	 * 客户端返回坐标存储
	 * 
	 * @param did
	 * @param deviceStrategy
	 * @param androidDeviceUsers
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/insertDeviceLocation", method = RequestMethod.POST)
	@ResponseBody
	public <T> DeviceResultModel<T> insertDeviceLocation(@RequestBody DeviceLocationModel deviceLocation ,HttpServletRequest request,
			HttpServletResponse response)  {
		 DeviceResultModel<T> deviceResult = new DeviceResultModel<T>();
		 DeviceManagerModel deviceManager = deviceManagerService.queryDeviceInfoBySn(deviceLocation.getSn());
		 deviceLocation.setDeviceId(deviceManager.getId());
		 int result = deviceManagerService.insertDeviceLocation(deviceLocation);
		 if(result>0){
			 deviceResult.setStatus(200);
			 deviceResult.setMsg("success");
		 }else{
			 deviceResult.setStatus(400);
			 deviceResult.setMsg("fail");
		 }
	    return deviceResult;
	}
}
