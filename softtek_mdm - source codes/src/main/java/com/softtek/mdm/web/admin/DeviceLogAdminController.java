package com.softtek.mdm.web.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.status.SessionStatus;

@Controller
@RequestMapping(value="/admin/deviceLog")
public class DeviceLogAdminController {
     
	 @Autowired
	 private SysmanageDeviceLogService sysmanageDeviceLogService;
	 
	 /**
	  * 
	  * @param request
	  * @return
	  */
	 @Link(family="admin",label="web.admin.deviceLog.manager.label")
	 @RequestMapping(method=RequestMethod.GET)
	 public String index(HttpServletRequest request){
      return "admin/deviceLog/index";
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param start
	  * @param length
	  * @param session
	  * @return
	  */
	 @RequestMapping(value="/queryAllDeviceLog",method=RequestMethod.GET)
	 @ResponseBody
	 public Page queryAllDeviceLog(HttpServletRequest request,Integer start, Integer length,HttpSession session){
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String event_type = StringUtils.trimToEmpty(request.getParameter("eventType"));
		Integer type = StringUtils.isEmpty(event_type) ? null : Integer.valueOf(event_type);
		String deviceName = StringUtils.trimToEmpty(request.getParameter("devicename"));
		String userName = StringUtils.trimToEmpty(request.getParameter("username"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("deviceName", deviceName);
		map.put("userName", userName);
		map.put("type", type);

		map.put("begin", start == null ? 0 : start);
		map.put("num", length == null ? 10 : length);
		map.put("orgId", organization == null ?null: organization.getId());
		Page page = sysmanageDeviceLogService.queryDeviceLog(map);
		return page;
	 }
}
