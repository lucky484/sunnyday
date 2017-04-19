package com.softtek.mdm.web.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.UserPass;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;
@SuppressWarnings("deprecation")
@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController extends BaseController {

	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * 机构首页
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	
	@Link(family = "admin", label = "web.institution.IndexController.admin.index.label.belong")
	@RequestMapping(method=RequestMethod.GET)
	public String organization(HttpServletRequest request, HttpServletResponse response,HttpSession session)
			throws IOException {
		return null==session.getAttribute("islicense")?"redirect:/system/license/manager":"admin/index/organization/index";
	}
	
	/**
	 * 机构管理员首页
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@Link(family = "admin", label = "web.institution.IndexController.admin.manger.index.label")
	@RequestMapping(value="/index/manager",method=RequestMethod.GET)
	public String manager(HttpServletRequest request, HttpServletResponse response,Model model)
			throws IOException {
		//加载所有的机构信息
		model.addAttribute("organizationList", organizationService.findAllOrganization());
		return "admin/index/manager/index";
	}
	
	/**
	 * 异步校验机构类型
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/validOrgType",method=RequestMethod.GET)
	@ResponseBody
	public String validOrgType(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("org_type",request.getParameter("orgType"));
		map.put("id",StringUtils.trimToNull(request.getParameter("orgId")));
		map.put("create_by",manager.getId());
		
		OrganizationModel organization = organizationService.findOrganizationByMap(map);
		return (organization == null)?"false":"true";
	}
	
	/**
	 * 异步校验机构名称
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/validOrgName",method=RequestMethod.GET)
	@ResponseBody
	public String validOrgName(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name",request.getParameter("name"));
		map.put("id",StringUtils.trimToNull(request.getParameter("orgId")));
		map.put("create_by",manager.getId());
		
		OrganizationModel organization = organizationService.findOrganizationByMap(map);
		return (organization == null)?"false":"true";
	}
	
	/**
	 * 根据机构id查询机构的基本信息
	 * @param request
	 * @param response
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/getOrganizationById",method=RequestMethod.POST)
	@ResponseBody
	public OrganizationModel getOrganizationById(HttpServletRequest request,HttpServletResponse response){
		
		String id = StringUtils.trim(request.getParameter("id"));
		OrganizationModel organization = organizationService.findOne(Integer.parseInt(id));
		List<OrganizationModel> lists = managerService.getManagerListsById(Integer.parseInt(id));
		organization.setLists(lists);
		return organization;
	}
	
	/**
	 * 保存机构信息
	 * @param request
	 * @param response
	 * @param session
	 * @param organization
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/saveOrganization",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult saveOrganization(HttpServletRequest request,HttpServletResponse response,HttpSession session,OrganizationModel organization){
		
		JsonResult jsonResult = null;
		ManagerModel sessionManager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		String[] managers = request.getParameterValues("managers");
		organization.setCreateBy(sessionManager.getId());
		//这里需要加一个判断，当前台用户输入的licenseCount超过了系统剩余的数量的时候后台也是需要校验的，2016-08-24 jackson
		//获取当前机构中已经被分配的license数量
		Integer totalLicense = licenseService.getGenereateMemberCount();
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("create_by", manager.getId());
		Integer useLicense = organizationService.getUserLicenseByMap(paramMap);
		Integer remainderLicense = totalLicense - useLicense;
		if(remainderLicense < organization.getLicenseCount()){
			jsonResult = createJsonResult(MessageType.danger,"web.institution.message.success.label", null,null);
		}else{
			organizationService.saveOrganization(organization,managers);
			jsonResult = createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
		}
		return jsonResult;
	}
	
	/**
	 * 获取当前系统中所有剩余的license数目
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/getRemainderLicense",method=RequestMethod.GET)
	@ResponseBody
	public JsonResult getRemainderLicense(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		JsonResult jsonResult = new JsonResult();
		Integer totalLicense = licenseService.getGenereateMemberCount();
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("create_by", manager.getId());
		Integer useLicense = organizationService.getUserLicenseByMap(paramMap);
		if(useLicense!=null){
			jsonResult.setContent(totalLicense - useLicense);
		}else{
			jsonResult.setContent(totalLicense);
		}
		return jsonResult;
	}
	
	/**
	 * 更新组织机构信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/updateOrganization",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateOrganization(HttpServletRequest request,HttpServletResponse response,HttpSession session,OrganizationModel organization){
		
		JsonResult jsonResult = null;
		ManagerModel sessionManager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		String[] managers = request.getParameterValues("editManagers");
		organization.setCreateBy(sessionManager.getId());
		Integer totalLicense = licenseService.getGenereateMemberCount();
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("create_by", manager.getId());
		Integer useLicense = organizationService.getUserLicenseByMap(paramMap);
		//获取系统目前所有的剩余license数量
		Integer remainderLicense = totalLicense - useLicense;
		OrganizationModel org = organizationService.findOne(organization.getId());
		if(organization.getLicenseCount() > remainderLicense + org.getLicenseCount()){
			jsonResult = createJsonResult(MessageType.danger,"web.institution.message.success.label", null,null);
		}else{
			organizationService.updateOrganization(organization,managers);
			jsonResult = createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
		}
		return jsonResult;
		
	}
	
	 /**
	  * 删除机构信息
	  * @param request
	  * @param response
	  * @param session
	  * @return
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/index/deleteOrganization",method=RequestMethod.POST)
	 @ResponseBody
	 public JsonResult deleteOrganization(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
			//删除机构之后需要把所有机构下的所有都需要删除掉
			ManagerModel sessionManager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			OrganizationModel organization = new OrganizationModel();
			organization.setId(Integer.parseInt(StringUtils.trim(request.getParameter("id"))));
			organization.setUpdateBy(sessionManager.getId());
			organization.setDeleteTime(new Date());
			organization.setUpdateTime(new Date());
			organizationService.deleteOrgization(organization);
		 	return createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
	 }
	
	 /**
	  * 锁定机构
	  * @param request
	  * @param response
	  * @param session
	  * @return
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/index/lockOrganization",method=RequestMethod.POST)
	 @ResponseBody
	 public JsonResult lockOrganization(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 	
		 	ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			OrganizationModel organization = new OrganizationModel();
			organization.setId(Integer.parseInt(StringUtils.trim(request.getParameter("id"))));
			organization.setUpdateBy(manager.getId());
			organization.setUpdateTime(new Date());
			//disabled 0
			organization.setStatus("0");
			organizationService.updateLockOrganization(organization);
		 	return createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
	 	
	 }
	 
	 /**
	  * 解锁机构
	  * @param request
	  * @param response
	  * @param session
	  * @return
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/index/unLockOrganization",method=RequestMethod.POST)
	 @ResponseBody
	 public JsonResult unLockOrganization(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 	
		 	ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			OrganizationModel organization = new OrganizationModel();
			organization.setId(Integer.parseInt(StringUtils.trim(request.getParameter("id"))));
			organization.setUpdateBy(manager.getId());
			organization.setUpdateTime(new Date());
			//enabled
			organization.setStatus("1");
			organizationService.updateLockOrganization(organization);
		 	return createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
	 }
	
	/**
	 * 获取所有的组织机构列表
	 * @param id
	 * @param session
	 * @param request
	 * @param response
	 * @param start
	 * @param length
	 * @return
	 * @throws IOException
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/organizationLists",method=RequestMethod.POST)
	@ResponseBody
	public Page organizationLists(HttpSession session,HttpServletRequest request,HttpServletResponse response,Integer start,Integer length) throws IOException{
		
		//获取当前登录的超级管理员帐号信息
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Integer create_by = manager.getId();
		String orgType = request.getParameter("orgType");
		String name = request.getParameter("name");
		start = start == null ? 0:start;
		length = length == null ? 10:length;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pageNum", start);
	    paramMap.put("pageSize", length);
	    paramMap.put("create_by",create_by);
	    paramMap.put("orgType",StringUtils.trimToNull(orgType));
	    paramMap.put("name", StringUtils.trimToNull(name));
		Page page = organizationService.findOrganizationListsByMap(paramMap);
		return page;
	} 
	
	/**
	 * 机构管理员中登录帐号的重复校验
	 * @param request
	 * @param response
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/validUsername",method=RequestMethod.GET)
	@ResponseBody
	public String validUsername(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username",request.getParameter("username"));
		String id = request.getParameter("orgId");
		map.put("id",StringUtils.trimToNull(id));
		ManagerModel manager = managerService.getManagerByMap(map);
		return  manager == null?"false":"true";
	}
	
	/**
	 * 保存机构管理员
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/saveManager",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult saveManager(HttpServletRequest request,HttpServletResponse response,HttpSession session,ManagerModel managerModel){
		ManagerModel sessionManager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		String[] orgs = request.getParameterValues("orgs");
		managerModel.setCreateBy(sessionManager.getId());
		managerService.saveManager(managerModel,orgs);
		return createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
	}
	
	/**
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @param start
	 * @param length
	 * @return
	 * @throws IOException
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/managerLists",method=RequestMethod.POST)
	@ResponseBody
	public Page managerLists(HttpSession session,HttpServletRequest request,HttpServletResponse response,Integer start,Integer length) throws IOException{
		
		//获取当前登录的超级管理员帐号信息
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Integer create_by = manager.getId();
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String orgId = request.getParameter("orgId");
		start = start == null ? 0:start;
		length = length == null ? 10:length;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pageNum", start);
	    paramMap.put("pageSize", length);
	    paramMap.put("create_by",create_by);
	    paramMap.put("username",StringUtils.trimToNull(username));
	    paramMap.put("name", StringUtils.trimToNull(name));
	    paramMap.put("orgId",StringUtils.trimToNull(orgId));
		Page page = managerService.findManagerListsByMap(paramMap);
		return page;
	} 
	
	/**
	 * 根据机构管理员id获取机构基本信息
	 * @param request
	 * @param response
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/getManagerById",method=RequestMethod.POST)
	@ResponseBody
	public ManagerModel getManagerById(HttpServletRequest request,HttpServletResponse response){
		
		String id = StringUtils.trim(request.getParameter("id"));
		ManagerModel manager = managerService.findOne(Integer.parseInt(id));
		List<OrganizationModel> organizationLists = organizationService.findOrgManagerListsById(Integer.parseInt(id));
		manager.setLists(organizationLists);
		return manager;
	}
	
	/**
	 * 获取所有的机构列表
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/getAllOrganizationLists",method=RequestMethod.GET)
	@ResponseBody
	public List<OrganizationModel> getAllOrganizationLists(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		ManagerModel sessionManager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("create_by",sessionManager.getId());
		List<OrganizationModel> organizationLists = organizationService.getOrganizationListsByMap(paramMap);
		return organizationLists;
	}
	
	/**
	 * 获取所有的机构管理员列表
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/getAllManagerLists",method=RequestMethod.GET)
	@ResponseBody
	public List<ManagerModel> getAllManagerLists(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		ManagerModel sessionManager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("create_by",sessionManager.getId());
		List<ManagerModel> managerLists = managerService.getManagerListsByMap(paramMap);
		return managerLists;
	}
	
	/**
	 * 修改机构管理员
	 * @param request
	 * @param response
	 * @param session
	 * @param managerModel
	 * @return
	 */
	@Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(value="/index/updateManager",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateManager(HttpServletRequest request,HttpServletResponse response,HttpSession session,ManagerModel managerModel){
		
		ManagerModel sessionManager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		String[] orgs = request.getParameterValues("editOrgs");
		managerModel.setCreateBy(sessionManager.getId());
		managerService.updateManager(managerModel,orgs);
		return createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
	}
	
	 /**
	  * 禁用机构管理员
	  * @param request
	  * @param response
	  * @param session
	  * @return
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/index/lockManager",method=RequestMethod.POST)
	 @ResponseBody
	 public JsonResult lockManager(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 	
		 	ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			String id = request.getParameter("id");
			ManagerModel managerModel = new ManagerModel();
			managerModel.setId(Integer.parseInt(StringUtils.trim(id)));
			managerModel.setUpdateBy(manager.getId());
			managerModel.setUpdateTime(new Date());
			//disabled
			managerModel.setStatus("0");
			managerService.updateManagerWithLock(managerModel);
			return createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
	 }
	 
	 /**
	  * 启用机构管理员
	  * @param request
	  * @param response
	  * @param session
	  * @return
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/index/unLockManager",method=RequestMethod.POST)
	 @ResponseBody
	 public JsonResult unLockManager(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 	
		 	ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			String id = request.getParameter("id");
			ManagerModel managerModel = new ManagerModel();
			managerModel.setId(Integer.parseInt(StringUtils.trim(id)));
			managerModel.setUpdateBy(manager.getId());
			managerModel.setUpdateTime(new Date());
			//enabled
			managerModel.setStatus("1");
			managerService.updateManagerWithLock(managerModel);
			return createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
	 }
	 
	 /**
	  * 删除机构管理员
	  * @param request
	  * @param response
	  * @param session
	  * @return
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/index/deleteManager",method=RequestMethod.POST)
	 @ResponseBody
	 public JsonResult deleteManager(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
			String id = request.getParameter("id");
			//删除机构之后需要把所有机构下的所有都需要删除掉
			ManagerModel sessionManager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			ManagerModel manager = new ManagerModel();
			manager.setId(Integer.parseInt(StringUtils.trim(id)));
			manager.setUpdateBy(sessionManager.getId());
			manager.setDeleteTime(new Date());
			manager.setUpdateTime(new Date());
			managerService.deleteManager(manager);
		 	return createJsonResult(MessageType.success,"web.institution.message.success.label", null,null);
	 	
	 }
	 
	 /**
	  * 进入超级管理员个人基本信息修改页面
	  * @param request
	  * @param response
	  * @param session
	  * @param model
	  * @return
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/personal",method=RequestMethod.GET)
	 public String getPersonInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
		 //把用户的基本信息显示到界面上
		 ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		 model.addAttribute("personInfo",manager);
		 return "admin/personal/basic/index";
	 }
	 
	 /**
	  * 进入超级管理员密码修改页面
	  * @param request
	  * @param response
	  * @param session
	  * @param model
	  * @return
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/password",method=RequestMethod.GET)
	 public String getPassword(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
		 //把用户的基本信息显示到界面上
		 ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		 model.addAttribute("personInfo",manager);
		 return "admin/personal/password/index";
	 }
	 
	 /**
	  * 修改超级管理员基本信息
	  * @param request
	  * @param response
	  * @param managerModel
	  * @param session
	  * @return
	  * @throws IOException
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/personal/update",method=RequestMethod.POST)
	 @ResponseBody
	 public JsonResult updatePersonalInfo(HttpServletRequest request,HttpServletResponse response,ManagerModel managerModel,HttpSession session) throws IOException{
		 
		 ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		 ManagerModel newManager = new ManagerModel();
		 newManager.setId(manager.getId());
		 newManager.setName(managerModel.getName());
		 newManager.setPhone(managerModel.getPhone());
		 newManager.setEmail(managerModel.getEmail());
		 newManager.setMark(managerModel.getMark());
		 managerService.update(newManager);
		 ManagerModel sessionManager = managerService.findOne(newManager.getId());
		 session.setAttribute(SessionStatus.SOFTTEK_MANAGER.toString(), sessionManager);
		 
		 return createJsonResult(MessageType.success, "institution.user.update.label", null,null);
		 
	 }
	 
	 /**
	  * 修改超级管理员密码
	  * @param session
	  * @param request
	  * @param response
	  * @param user
	  * @return
	  * @throws IOException
	  */
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/password/update",method=RequestMethod.POST)
	 @ResponseBody
	 public JsonResult updatePersonalPass(HttpSession session,HttpServletRequest request,HttpServletResponse response,UserPass user) throws IOException{
		 
		 JsonResult jsonResult = null;
		 ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		 ManagerModel newManager = new ManagerModel();
		 if(StringUtils.isNotBlank(user.getPassword())){
			 Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
			 if(StringUtils.equals(md5PasswordEncoder.encodePassword(user.getPassword(), null), manager.getPassword())){
				 String newPwdStr = md5PasswordEncoder.encodePassword(user.getNewPassword(), null);
				 newManager.setId(manager.getId());
				 newManager.setPassword(newPwdStr);
				 managerService.update(newManager);
				 ManagerModel sessionManager = managerService.findOne(newManager.getId());
				 session.setAttribute(SessionStatus.SOFTTEK_MANAGER.toString(), sessionManager);
				 jsonResult =  createJsonResult(MessageType.success, "defes.institution.person.update.success", null,null);
			 }else{
				 jsonResult =  createJsonResult(MessageType.danger, "defes.institution.person.pwd.failed", null,null);
			 }
		 }
		 return jsonResult;
	 }
	 
	 @Link(family = "admin", label = "web.institution.homecontroller.index.link.label")
	 @RequestMapping(value="/index/export",method=RequestMethod.POST)
	 public void exportOrganization(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		 
		//首先获取到所有机构的信息
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Integer create_by = manager.getId();
		String orgType = request.getParameter("orgType");
		String name = request.getParameter("name");
		Map<String,Object> paramMap = new HashMap<String,Object>();
	    paramMap.put("create_by",create_by);
	    paramMap.put("orgType",StringUtils.trimToNull(orgType));
	    paramMap.put("name", StringUtils.trimToNull(name));
		List<OrganizationModel> lists = organizationService.findOrganizationListByMap(paramMap);
		//导出excel文件
		//机构列表
		String sheetName =	messageSource.getMessage("web.institution.indexcontroller.exportshhetname", null, LocaleContextHolder.getLocale());
		String[] headNames = {	messageSource.getMessage("web.institution.indexcontroller.export.title1", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.indexcontroller.export.title2", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.indexcontroller.export.title3", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.indexcontroller.export.title4", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.indexcontroller.export.title5", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.indexcontroller.export.title6", null, LocaleContextHolder.getLocale()),
								messageSource.getMessage("web.institution.indexcontroller.export.title7", null, LocaleContextHolder.getLocale()),
								};
		exportExcel(sheetName,headNames,lists,response);
	 }

	private void exportExcel(String sheetName, String[] headNames, List<OrganizationModel> lists,HttpServletResponse response) {
		
		OutputStream out = null;
		try{  
            HSSFWorkbook workbook = new HSSFWorkbook();                     // 创建工作簿对象  
            HSSFSheet sheet = workbook.createSheet(sheetName);                  // 创建工作表  
            // 产生表格标题行  
            HSSFRow rowm = sheet.createRow(0);  
            HSSFCell cellTiltle = rowm.createCell(0);  
            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】  
            HSSFCellStyle columnTopStyle = CommUtil.getColumnTopStyle(workbook);//获取列头样式对象  
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (headNames.length-1)));    
            cellTiltle.setCellStyle(columnTopStyle);  
            cellTiltle.setCellValue(sheetName);  
            // 定义所需列数  
            int columnNum = headNames.length;  
            HSSFRow rowRowName = sheet.createRow(2);                // 在索引2的位置创建行(最顶端的行开始的第二行)  
            // 将列头设置到sheet的单元格中  
            for(int n=0;n<columnNum;n++){  
                HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格  
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型  
                HSSFRichTextString text = new HSSFRichTextString(headNames[n]);  
                cellRowName.setCellValue(text);                                 //设置列头单元格的值  
                cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式  
            }  
            //将查询出的数据设置到sheet对应的单元格中  
            HSSFDataFormat format = workbook.createDataFormat();
            short formatDate=format.getFormat("yyyy-MM-dd hh:mm:ss");
            for(int i=0;i<lists.size();i++){  
            	HSSFRow row = sheet.createRow(i+3);//创建所需的行数  
            	OrganizationModel obj = lists.get(i);//遍历每个对象  
            	/*row.createCell(0).setCellValue(obj.getOrgType());*/
            	row.createCell(0).setCellValue(obj.getName());
            	row.createCell(1).setCellValue(obj.getCreateName());
            	row.createCell(2).setCellValue(obj.getTotalUsers()==null?0:obj.getTotalUsers());
            	row.createCell(3).setCellValue(obj.getTotalDevices()==null?0:obj.getTotalDevices());
            	row.createCell(4).setCellValue(obj.getLicenseCount()==null?0:obj.getLicenseCount());
            	row.createCell(5).setCellValue(obj.getUseUsers()==null?0:obj.getUseUsers());
            	HSSFCell cell = row.createCell(6);
            	cell.setCellValue(obj.getCreateTime());
            	HSSFCellStyle cellStyle = workbook.createCellStyle();
            	cellStyle.setDataFormat(formatDate);
            	cell.setCellStyle(cellStyle);
            }  
            //让列宽随着导出的列长自动适应  
            for (int colNum = 0; colNum < columnNum; colNum++) {  
                int columnWidth = sheet.getColumnWidth(colNum) / 256;  
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {  
                    HSSFRow currentRow;  
                    //当前行未被使用过  
                    currentRow=(sheet.getRow(rowNum) == null)?sheet.createRow(rowNum):sheet.getRow(rowNum); 
                    if (currentRow.getCell(colNum) != null) {  
                        HSSFCell currentCell = currentRow.getCell(colNum);  
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {  
                            int length = currentCell.getStringCellValue().getBytes().length;  
                            if (columnWidth < length) {  
                                columnWidth = length;  
                            }  
                        }  
                    }  
                } 
                sheet.setColumnWidth(colNum, (colNum == 0)?(columnWidth* 256):((columnWidth+10) * 256));  
            }  
            if(workbook !=null){  
                try{  
                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";  
                    String headStr = "attachment; filename=\"" + fileName + "\"";  
                    response.setContentType("application/octet-stream");  
                    response.setHeader("Content-Disposition", headStr);  
                    out = response.getOutputStream();  
                    workbook.write(out);
                }  
                catch (IOException e){  
                   logger.error(e.getMessage());
                }finally{
                	if(out!=null){
                		out.close();
                	}
                }  
            }  
        }catch(Exception e){  
        	logger.error(e.getMessage());
        }  
	}
}