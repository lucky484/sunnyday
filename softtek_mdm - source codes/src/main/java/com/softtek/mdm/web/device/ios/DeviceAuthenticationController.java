package com.softtek.mdm.web.device.ios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.softtek.mdm.service.IosDevicePushService;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.XmlUtils;

/**
 * 设备认证
 * @author jane.hui
 *
 */
@Controller
@RequestMapping(value = "/terminal/device")
public class DeviceAuthenticationController {
	
	private static Logger logger=LoggerFactory.getLogger(DeviceAuthenticationController.class);
    
	@Autowired
	private IosDevicePushService iosDevicePushService;
	
	/**
	 * 权限认证
	 * @param plistMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/checkIn",method={RequestMethod.PUT})
	public void index(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> plistMap = XmlUtils.parseAuthenticate(XmlUtils.returnBody(request));
		plistMap.put("userId", request.getParameter("userId"));
		plistMap.put("uuid", request.getParameter("uuid"));
		plistMap.put("iosUuid",request.getParameter("iosUuid"));
		
		String msg=iosDevicePushService.checkIn(plistMap);
		if("401".equals(msg)){
			response.setStatus(401);
			return ;
		}
		if(Constant.MESSAGE_TYPE.AUTHENTICATE.equals(msg)){
			response.setHeader("content-type", "application/xml;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=Mbaike_Blank.plist");
			try {
				PrintWriter sos = response.getWriter();
				sos.write(XmlUtils.getBlankPList());
				sos.flush();
				sos.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/serverUrl",method={RequestMethod.PUT})
	public void serverUrl(HttpServletRequest request,HttpServletResponse response){
		String res = XmlUtils.returnBody(request);
		Map<String, Object> plist = XmlUtils.parseXmlByDom4j(res);
		String result=iosDevicePushService.serverUrl(plist);
		if(StringUtils.isNotEmpty(result)&&result.contains("xml")){
			response.setHeader("content-type", "application/xml;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=MDM_plist.plist");
			try {
				PrintWriter sos = response.getWriter();
				sos.write(result);
				sos.flush();
				sos.close();
			} catch (IOException e) {
				logger.error("setResponse error cause:"+e.getMessage());
			}
		}
	}
}
