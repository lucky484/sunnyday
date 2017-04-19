package com.softtek.mdm.web.admin;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.SysmanageLogService;
import com.softtek.mdm.status.SessionStatus;

@Controller
@RequestMapping(value="/admin/sysmanageLog")
public class SysmanageLogAdminController {
   
	 @Autowired
	 private SysmanageLogService sysmanageLogService;
	 
	 /**
	  * 
	  * @param request
	  * @return
	  */
	 @Link(family="institution",label="tiles.admin.logger.manager.label")
	 @RequestMapping(method=RequestMethod.GET)
	 public String index(HttpServletRequest request){
       return "admin/sysmanageLog/index";
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param start
	  * @param length
	  * @param session
	  * @return
	  */
	 @RequestMapping(value="/queryAllOperateLog")
	 @ResponseBody
	 public Page queryAllOperateLog(HttpServletRequest request,Integer start, Integer length,HttpSession session){
		 OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		 String seletedStatus = StringUtils.isEmpty(request.getParameter("seletedStatus")) ? null : request.getParameter("seletedStatus");
		 String userName = StringUtils.trimToEmpty(request.getParameter("userName"));
		 ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("userType", manager.getUser_type());
		 map.put("seletedStatus", seletedStatus);
		 map.put("userName", userName);
		 map.put("begin", start==null?0:start);
		 map.put("num", length==null?10:length);
		 map.put("orgId", organization == null ? null : organization.getId());
		 
		 Page page = sysmanageLogService.queryAllOperateLog(map);

		 return page;
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param start
	  * @param length
	  * @return
	  */
	 @RequestMapping(value="/queryDetailLogByUserId",method=RequestMethod.GET)
	 @ResponseBody
	 public Page queryDetailLogByUserId(HttpServletRequest request,Integer start, Integer length){
		 Integer userId = Integer.valueOf(request.getParameter("userId"));
		 Page page = sysmanageLogService.queryDetailLogByUserId(start==null?0:start, length==null?10:length,userId);
		 return page;
	 }
	 
}
