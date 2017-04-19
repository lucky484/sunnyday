package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.web.BaseController;

@Controller
@RequestMapping(value="/institution/policy")
public class PolicyController extends BaseController{
    
	@Autowired
	private PolicyService policyService;
	@Autowired
	private TreeManager treeManager;
	@Autowired
	private StructureService structureService;
	
	private Logger logger = Logger.getLogger(PolicyController.class);

	/**
	 * 用户策略首页
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Link(family="institution",label="tiles.institution.user.policy",parent="web.institution.homecontroller.index.link.label",belong="web.institution.usercontroller.index.link.belong")
	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
 		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer defaultPolicyId = policyService.queryDefaultPolicy(organization.getId());
		List<StructureModel> list = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		List<NodeModel> nodes = treeManager.buildTreeIncludePolicy(list,defaultPolicyId);
		
		StringBuilder jsonStrBulider=new StringBuilder("[");
		if(nodes!=null){
			int i = 1;
			for(NodeModel node : nodes){
				jsonStrBulider.append(JSONObject.fromObject(node).toString());
				if(i++!=nodes.size()){
					jsonStrBulider.append(",");
				}
			}
		}
		jsonStrBulider.append("]");
		String jsonStr=StringUtil.replace(jsonStrBulider.toString(), "\"nodes\":[],", "");
		model.addAttribute("tree", jsonStr);
		return "institution/policy/index";
	}
	
	
	/**
	 * 新增策略
	 * @param request
	 * @param policy
	 * @param session
	 * @return
	 */
	@Log(operateType="logs.user.policy.type.insert",operateContent="logs.user.policy.insert.content",args={"name"})
	@RequestMapping(value = "save",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request,PolicyModel policy,HttpSession session){
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		@SuppressWarnings("unchecked")
		List<StructureModel> list = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			String id = policy.getNodeIds();
			policy.setOrganization(organization);
			if(manager.getUser() != null){
				policy.setCreateBy(manager.getUser().getId());
				policy.setUpdateBy(manager.getUser().getId());
			}else{
				policy.setCreateBy(manager.getId());
				policy.setUpdateBy(manager.getId());
			}
			int result = policyService.save(policy,id,list);
			treeManager.InitTreeSession(session);
			map.put("result", result);
		} catch (Exception e) {
		   logger.error(e.getMessage());
		}
		return map;
	}
	
	/**
	 * 
	 * @param start
	 * @param length
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "queryAll")
	@ResponseBody
	public Page queryAll(Integer start, Integer length,HttpServletRequest request,HttpSession session) {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String searchPolicyName = StringUtils.trimToEmpty(request.getParameter("searchpolicyname"));
		Page page = new Page();
		try {
			String searchPolicyType = StringUtils.trimToNull(request.getParameter("searchpolicytype"));
			Integer type = StringUtils.isEmpty(searchPolicyType) ? null : Integer.valueOf(searchPolicyType);
			
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("searchPolicyName", searchPolicyName);
			paramsMap.put("searchPolicyType", type);
			paramsMap.put("start", start == null ? 0 : start);
			paramsMap.put("length", length == null ? 10 : length);
			paramsMap.put("orgId", organization.getId());
			
			page = policyService.queryByParams(paramsMap);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		}
		return page; 
		
	}
	
	/**
	 * 删除策略
	 * @param request
	 * @return
	 */
	@Log(operateType="logs.user.policy.type.delete",operateContent="logs.user.policy.delete.content",args={"name"})
	@RequestMapping(value = "deletePolicy")
	@ResponseBody
	public Map<String,Object> deletePolicy(HttpServletRequest request,HttpSession session){
		  Map<String,Object> map = new HashMap<String,Object>();
		  try {
			  Integer id  = Integer.valueOf(request.getParameter("id"));
			  
			  String name =  policyService.queryNameById(id);
			  request.setAttribute("name", name);
			  
			  OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			  List<StructureModel> list = (List<StructureModel>) structureService.findAllByOrgID(organization.getId());
			  
			  int result = policyService.deletePolicy(id,list,organization.getId());
			 
			  treeManager.InitTreeSession(session);
			  map.put("result", result);
			 
		} catch (NumberFormatException e) {
		      logger.error(e.getMessage());
		}
		  return map;
	}
	
	/**
	 * 查询策略详情(包含部门名称)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryInfoById")
	@ResponseBody
	public Map<String,Object> queryInfoById(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<String> list = new ArrayList<String>();
			List<String> listArry = new ArrayList<String>();
			PolicyModel policy = new PolicyModel();
			
			Integer id = Integer.valueOf(request.getParameter("id"));
			policy = policyService.queryInfoById(id);
			String departmentIds = policy.getDeparmentId();
			if(departmentIds != null){
				StringBuilder departmentBulider = new StringBuilder();
				String[] departmentId = departmentIds.split(",");
				for(int i=0;i<departmentId.length;i++){
					if(StringUtils.isEmpty(departmentId[i])){
						continue;
					}
					
					String departmentName = structureService.buildDepartmentName(Integer.valueOf(departmentId[i].toString()));
					//将拿到的部门名称拆开，在拼接
					String[] departmentNames = departmentName.split("/");
					List<String> list1 = Arrays.asList(departmentNames);
					Collections.reverse(list1);
					list1.toArray(departmentNames);
					
					departmentBulider.delete(0, departmentBulider.length());
					for(String s : departmentNames){
						departmentBulider.append(s+"/");
					}
					list.add(departmentBulider.substring(0,departmentBulider.length()-1));
					listArry.add(departmentNames[departmentNames.length-1]);
				}
			}
			map.put("policy", policy);
			map.put("list", list);
			map.put("listArry", listArry);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		}
	    return map;
	}
	
	/**
	 * 修稿策略
	 * @param request
	 * @param policy
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Log(operateType="logs.user.policy.type.update",operateContent="logs.user.policy.update.content",args={"name"})
	@RequestMapping(value = "updatePolicyInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePolicyInfo(HttpServletRequest request,PolicyModel policy,HttpServletResponse response,HttpSession session) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			String ids = request.getParameter("treeId");
			OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			List<StructureModel> list = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			String departmentIds = null;
			if(!CommUtil.isEmpty(request.getParameter("departmentId"))){
				departmentIds = request.getParameter("departmentId");
			}
			policy.setOrganization(organization);
			int result = policyService.updatePolicyInfo(policy,ids,departmentIds,list);
			treeManager.InitTreeSession(session);
			map.put("result", result);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}
	
	/**
	 * 校验策略名称是否存在
	 */
	@RequestMapping(value = "queryIsExitsName",method=RequestMethod.GET)
	@ResponseBody
	public Boolean queryIsExitsName(HttpServletRequest request,String policyName){
		try {
			int result = policyService.queryCountByName(policyName);
			return result != 0?true:false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		
	}
	/**
	 * 校验修改是名称是否已经存在
	 */
	@RequestMapping(value = "checkName",method=RequestMethod.GET)
	@ResponseBody
	public Boolean checkName(HttpServletRequest request,String policyNameUpdate,String firstName){
		if (!(policyNameUpdate.trim()).equals(firstName.trim())) {
			try {
				int count = policyService.queryCountByName(policyNameUpdate);
				return count != 0?true:false;
			} catch (Exception e) {
				logger.error(e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}
}
