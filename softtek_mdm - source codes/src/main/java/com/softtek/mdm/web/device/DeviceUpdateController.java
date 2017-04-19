package com.softtek.mdm.web.device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.ClientManagerModel;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.service.ClientManagerService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/terminal/update")
public class DeviceUpdateController {
	@Autowired
	private ClientManagerService clientManagerService;
	
	/**
	 * 对iOS或者Android进行检测，是否需要更新,需要更新会返回200，同时包含下载路径
	 * @param request
	 * @param session
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/check",method=RequestMethod.POST)
	public DeviceResultModel<String> ipaCheck(HttpServletRequest request, HttpSession session) {
		String version=StringUtils.trimToNull(request.getParameter("version"));
		String orgId=StringUtils.trimToNull(request.getParameter("orgId"));
		//android/ios
		String platForm=StringUtils.trimToNull(request.getParameter("platform"));
		if(version==null||orgId==null){
			return new DeviceResultModel<String>(500, "params error", null);
		}
		version=version.replaceAll(".", "");
		ClientManagerModel client=clientManagerService.findLastOne(orgId, platForm==null?"ios":platForm.toLowerCase());
		if(client==null){
			return new DeviceResultModel<String>(200, "aleady updated!", null);
		}
		if(client.getClientVersion().replaceAll(".", "").compareTo(version)>0){
			JSONObject object=new JSONObject();
			object.put("version", StringUtils.trimToEmpty(client.getClientVersion()));
			object.put("downloadUrl", StringUtils.trimToEmpty(client.getDownloadUrl()));
			object.put("force", client.getIsUpgrade()==null?0:client.getIsUpgrade());
			return new DeviceResultModel<String>(200,"have new version",object.toString());
		}
		return new DeviceResultModel<String>(200, "aleady updated!", null);
	}
}
