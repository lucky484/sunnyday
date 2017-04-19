package com.softtek.mdm.web.device.ios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.abs.AbstractIosPush;
import com.softtek.mdm.model.DeviceAllInfoModel;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceIosBasicInfoModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.service.TerminalService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.util.CommUtil;

@Controller
@RequestMapping("/terminal/ios/baseinfo")
public class DeviceIosInfoController {
     
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private TerminalService terminalService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private SysmanageDeviceLogService sysmanageDeviceLogService;
	
	@Autowired
	private DeviceManagerService deviceManagerService;
	
	@Autowired
	@Qualifier("IosDeviceSettingNotifyServiceImpl")
	private AbstractIosPush abstractIosPush;
	
    /**
     * ios设备的信息上报，只存储ios能上报的信息
     * 
     * @param deviceAllInfo
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody DeviceResultModel<Integer> index(@RequestBody DeviceAllInfoModel deviceAllInfo,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取基本信息
		DeviceBasicInfoModel basic = deviceAllInfo.getDeviceBasicInfo();
        // 获取网络信息
		DeviceNetworkStatusModel deviceNetworkStatus = deviceAllInfo.getDeviceNetworkStatus();
        // 获取应用信息
		List<DeviceAppInfoModel> deviceAppInfoList = deviceAllInfo.getDeviceAppInfoList();
        // 获取设备安全
		DeviceSaftyModel deviceSafty = deviceAllInfo.getDeviceSafty();
		
		DeviceResultModel<Integer> deviceResult = new DeviceResultModel<Integer>();
		Map<String, Object> maps=new HashMap<String,Object>();
		
        // 新增状态下，保存应用信息
		if (CollectionUtils.isNotEmpty(deviceAppInfoList)) {
			for (int i = 0; i < deviceAppInfoList.size(); i++) {
				deviceAppInfoList.get(i).setBelongDevice(basic);
			}
            // 将应用信息放入map
			maps.put("deviceAppInfoList", deviceAppInfoList);
		}
		if (basic != null && basic.getIosUuid() != null) {
            // 根据用户信息查询是否已经存在，存在则更新，否则新增
			DeviceManagerModel deviceBasicInfo = deviceManagerService.queryIosIsExists(basic.getIosUuid());
			
			basic.setIp(CommUtil.getIp(request));
			basic.setLast_login_time(new Date());
			basic.setLast_collection_time(new Date());
			basic.setUpdate_time(new Date());
            String belong = messageSource.getMessage("tiles.views.institution.device.rule.table。belong.person", null,
                    LocaleContextHolder.getLocale());
			basic.setBelong(belong);
			if(basic.getIsProfile().equals("0")){
				basic.setDevice_status("3");
				basic.setIsActive(0);
            } else {
                basic.setDevice_status("4");
                basic.setIsActive(1);
			}
            // 将设备基本信息放入map
			maps.put("deviceBasicInfo", deviceBasicInfo);
			
			if (deviceBasicInfo == null) {
                // 初次绑定设备
				maps.put("basic", basic);
				maps.put("deviceNetworkStatus", deviceNetworkStatus);
				maps.put("deviceSafty", deviceSafty);
                // 保持设备信息，网络信息，安全信息
			    terminalService.baseInfo(maps);
			} else {
                // 设备已存在，更新基本信息
				basic.setId(deviceBasicInfo.getId());
				maps.put("basic", basic);
				maps.put("deviceNetworkStatus", deviceNetworkStatus);
				maps.put("deviceSafty", deviceSafty);
                // 更新设备信息
			    terminalService.update(maps);
			}
			deviceResult.setData(basic.getId());
			deviceResult.setStatus(200);
			deviceResult.setMsg("success");
			return deviceResult;
		}
		deviceResult.setStatus(400);
		deviceResult.setMsg("failed");
		return deviceResult;
	}
	
	@RequestMapping(value="/getDeviceBasicInfo",method=RequestMethod.POST)
	@ResponseBody
	public DeviceResultModel<DeviceIosBasicInfoModel> getDeviceBasicInfo(HttpServletRequest request){
		DeviceResultModel<DeviceIosBasicInfoModel> deviceResult = new DeviceResultModel<DeviceIosBasicInfoModel>();
		if(StringUtils.isNotEmpty(request.getParameter("iosUuid"))){
			DeviceIosBasicInfoModel deviceIosBasicInfo = deviceManagerService.queryIosDeviceBasicInfo(request.getParameter("iosUuid"));
			deviceResult.setData(deviceIosBasicInfo);
			deviceResult.setMsg("success");
			deviceResult.setStatus(200);
		}else{
			deviceResult.setMsg("fail");
			deviceResult.setStatus(400);
		}
		return deviceResult;
	}
    
	@RequestMapping(value="/removeProfile",method=RequestMethod.POST)
	@ResponseBody
	public DeviceResultModel<String> removeProfile(HttpServletRequest request){
		String iosUuid = request.getParameter("iosUuid");
		DeviceResultModel<String> deviceResult = new DeviceResultModel<String>();
		if(iosUuid != null && !iosUuid.equals("")){
			DeviceBasicInfoModel deviceIosBasicInfo = deviceBasicInfoService.findByIosUuid(iosUuid);
			if(deviceIosBasicInfo != null){
                // DeviceManagerModel model =
                // deviceManagerService.queryDeviceInfoBySn(deviceIosBasicInfo.getSn());
                // DeviceManagerModel device =
                // deviceManagerService.queryDeviceAllInfo(model.getId());
                // deviceManagerService.insertDevice(device);
				Map<String,Object> map = new HashMap<String, Object>();
				List<String> udidList = new ArrayList<String>();
				udidList.add(deviceIosBasicInfo.getUdid());
				map.put("udidList", udidList);
				map.put("command","RemoveProfile");
				abstractIosPush.nofity(map);
				deviceResult.setMsg("success");
				deviceResult.setStatus(200);
			}else{
				deviceResult.setMsg("fail");
				deviceResult.setStatus(400);
			}
		}else{
			deviceResult.setMsg("fail");
			deviceResult.setStatus(400);
		}
		return deviceResult;
	}
}
