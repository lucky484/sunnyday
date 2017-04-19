package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.PageUtil;
import com.softtek.mdm.util.TreeManager;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/institution/dptPolicy")
public class DptPolicyController {
	
	@Autowired
	private TreeManager treeManager;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private MessageSource messageSource;
	
	private Logger logger = Logger.getLogger(DptPolicyController.class);
    
	/**
	 * 拼接带有策略名称的树
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Link(family="institution",label="institution.org2.index.label",parent="web.institution.homecontroller.index.link.label",belong="web.institution.usercontroller.index.link.belong")
	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws IOException{
		/**
		 * 以后这里的id将会从session中获取
		 */
		try {
			OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			List<StructureModel> list= (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			Integer defaultPolicyId = policyService.queryDefaultPolicy(organization.getId());
			List<NodeModel> nodes = treeManager.buildTreeIncludePolicy(list,defaultPolicyId);
			Integer orgId = null;
			for(StructureModel str :list){
				if(str.getParent() == null){
					orgId = str.getId();
				}
			}
			String jsonStr="[";
			int i = 1;
			Integer policyId = null;
			if(nodes.size() > 0){
				policyId = nodes.get(0).getTags().getPolicy().getId();
			}
			for(NodeModel node : nodes){
				jsonStr+=JSONObject.fromObject(node).toString();
				if(i!=nodes.size()){
					jsonStr += ",";
				}
				i++;
			}
			jsonStr += "]";
			jsonStr=StringUtil.replace(jsonStr, "\"nodes\":[],", "");
			Map<String,Object> map = queryAllPolicyInit(organization,manager);
			model.addAttribute("tree", jsonStr); 
			model.addAttribute("dataList", map.get("list"));
			model.addAttribute("page", map.get("page"));
			model.addAttribute("policyId", policyId);
			model.addAttribute("orgId", orgId);
			model.addAttribute("defaultPolicyId", defaultPolicyId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "institution/departmentPolicy/index";
		
	}
    
	/**
	 * 自定义的策略的分页查询
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="queryAllPolicy",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAllPolicy(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		try {
			Integer page = Integer.valueOf(request.getParameter("page"));
			if(page == null){
				page = 1;
			}
			int totalCount = policyService.findAllPolicyCount(organization.getId(),manager.getUser());
			PageUtil p = new PageUtil(totalCount,page);
			List<PolicyModel> list = policyService.findAllPolicy(p.getStart(), p.getPageSize(),organization.getId(),manager.getUser());
			for(PolicyModel policy : list){
				String s = "";
				String str = "";
				if(policy.getVisit_on_worktime() != null && policy.getVisit_on_worktime() == 1){
					 Object[] args = {policy.getVisit_time_start(),policy.getVisit_time_end()};
					s += messageSource.getMessage("tiles.institution.department.policy.work.time.visit",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getAllow_wifi() !=null && policy.getAllow_wifi() == 1){
					s +=  messageSource.getMessage("tiles.institution.department.policy.visit.wifi",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getOnly_comp_wifi()!= null && policy.getOnly_comp_wifi() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.visit.only.company.wifi",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getLogin_limit()!= null && policy.getLogin_limit() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.forbid.login",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getAuto_login_limit() != null && policy.getAuto_login_limit() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.forbid.auto.login",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getLogin_error_limit() != null && policy.getLogin_error_limit() == 1){
					Object[] args = {policy.getLogin_error_limit_times(),policy.getLogin_error_limit_lock()};
					s += messageSource.getMessage("tiles.institution.department.policy.allow.error",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getDevice_limit() != null && policy.getDevice_limit() == 1){
					Object[] args = {policy.getDevice_limit_count()};
					s += messageSource.getMessage("tiles.institution.department.policy.user.registration",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getAccess_resource_limit() != null && policy.getAccess_resource_limit() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.forbid.visit",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getDevice_password_interval() != null){
					Object[] args = {policy.getDevice_password_interval()};
					s += messageSource.getMessage("tiles.institution.department.policy.password.effect",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getDoc_space() != null){
					Object[] args = {policy.getDoc_space()};
					s += messageSource.getMessage("tiles.institution.department.policy.doc.spance",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getForce_password() != null && policy.getForce_password() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.set.password",null, LocaleContextHolder.getLocale())+",";
				}
				if(!s.equals("") && s != null){
					 str = s.substring(0,s.length()-1);
				}
				policy.setFlag(str);
			}
				map.put("list", list);
				map.put("page",p);
		} catch (NumberFormatException e) {
		        logger.error(e.getMessage());
		        throw e;
		}
		   return map;
	}
	
	public Map<String,Object> queryAllPolicyInit(OrganizationModel organization,ManagerModel manager){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			int page = 1;
			int totalCount = policyService.findAllPolicyCount(organization.getId(),manager.getUser());
			PageUtil p = new PageUtil(totalCount,page);
			List<PolicyModel> list = policyService.findAllPolicy(p.getStart(), p.getPageSize(),organization.getId(),manager.getUser());
			for(PolicyModel policy : list){
				String s = "";
				String str = "";
				if(policy.getVisit_on_worktime() != null && policy.getVisit_on_worktime() == 1){
					 Object[] args = {policy.getVisit_time_start(),policy.getVisit_time_end()};
					s += messageSource.getMessage("tiles.institution.department.policy.work.time.visit",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getAllow_wifi() !=null && policy.getAllow_wifi() == 1){
					s +=  messageSource.getMessage("tiles.institution.department.policy.visit.wifi",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getOnly_comp_wifi()!= null && policy.getOnly_comp_wifi() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.visit.only.company.wifi",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getLogin_limit()!= null && policy.getLogin_limit() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.forbid.login",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getAuto_login_limit() != null && policy.getAuto_login_limit() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.forbid.auto.login",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getLogin_error_limit() != null && policy.getLogin_error_limit() == 1){
					Object[] args = {policy.getLogin_error_limit_times(),policy.getLogin_error_limit_lock()};
					s += messageSource.getMessage("tiles.institution.department.policy.allow.error",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getDevice_limit() != null && policy.getDevice_limit() == 1){
					Object[] args = {policy.getDevice_limit_count()};
					s += messageSource.getMessage("tiles.institution.department.policy.user.registration",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getAccess_resource_limit() != null && policy.getAccess_resource_limit() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.forbid.visit",null, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getDevice_password_interval() != null){
					Object[] args = {policy.getDevice_password_interval()};
					s += messageSource.getMessage("tiles.institution.department.policy.password.effect",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getDoc_space() != null){
					Object[] args = {policy.getDoc_space()};
					s += messageSource.getMessage("tiles.institution.department.policy.doc.spance",args, LocaleContextHolder.getLocale())+",";
				}
				if(policy.getForce_password() != null && policy.getForce_password() == 1){
					s += messageSource.getMessage("tiles.institution.department.policy.set.password",null, LocaleContextHolder.getLocale())+",";
				}
				if(!s.equals("") && s != null){
					 str = s.substring(0,s.length()-1);
				}
				policy.setFlag(str);
			}
				map.put("list", list);
				map.put("page",p);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		   return map;
	}
}
