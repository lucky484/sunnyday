package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.service.UserRoleDepartmentService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.web.BaseController;

/**
 * 组织架构
 * 
 * @author color.wu
 *
 */
@Controller
@RequestMapping(value = "/institution/org")
public class OrganizationController extends BaseController {

	@Autowired
	private StructureService structureService;

	@Autowired
	private TreeManager treeManager;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PolicyService policyService; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleDepartmentService userRoleDepartmentService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	private Logger logger = Logger.getLogger(OrganizationController.class);

	/**
	 * ???
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Link(family = "institution", label = "institution.org.index.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.usercontroller.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response, HttpSession session, Model model)throws IOException {
		String jsonStr;
		try {
			OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			List<StructureModel> list = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
//		List<StructureModel> list=(List<StructureModel>) structureService.findAllByOrgID(organization.getId());
			NodeModel nodes = treeManager.bulidTreeContainUser(list,organization.getId(),manager.getUser());
			jsonStr = "[" + JSONObject.fromObject(nodes).toString() + "]";
			jsonStr = StringUtil.replace(jsonStr, "\"nodes\":[],", "");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		model.addAttribute("tree", jsonStr);
		return "institution/org/index";

	}

	/**
	 * 新建部门，每次新建的部门的策略都设置成继承上级的策略
	 * @param structureModel
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@Log(operateType="logs.department.type.insert",operateContent="logs.department.insert.content",args={"name"})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String create(StructureModel structure, Model model,HttpSession session,RedirectAttributes attr,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			Integer parentId = Integer.valueOf(request.getParameter("parent.id"));
			UserDepartmentModel userDepartmentModel = new UserDepartmentModel();
			Integer orgId = structureService.queryOrgId(parentId);
//			Integer departmentId = CommUtil.getPrimaryKey();
			PolicyModel policy = new PolicyModel();
			if(orgId != null){
				if(orgId.equals(Integer.valueOf(organization.getId()))){
					Integer policyId = policyService.queryDefaultPolicy(organization.getId());
					policy.setId(policyId);
				}else{
					Integer policyId = structureService.queryPolicyIdById(parentId);
					policy.setId(policyId);
				}
			}else{
				Integer policyId = policyService.queryDefaultPolicy(organization.getId());
				policy.setId(policyId);
			}
//			structure.setId(departmentId);
			String createTypeStr = messageSource.getMessage(
					"institution.org.create.createtype", null,
					LocaleContextHolder.getLocale());
			structure.setCreateType(createTypeStr);
			structure.setPolicy(policy);
			structure.setOrganization(organization);
			if(manager.getUser() != null){
				structure.setCreateBy(manager.getUser().getId());
				structure.setUpdateBy(manager.getUser().getId());
			}else{
				structure.setCreateBy(manager.getId());
				structure.setUpdateBy(manager.getId());
			}
			if(manager.getUser() != null){
				UserRoleDepartmentModel uRD = userRoleDepartmentService.findOneByMaps(manager.getOrganization().getId(), manager.getUser().getId());
//				userDepartmentModel.setId(CommUtil.getPrimaryKey());
				userDepartmentModel.setOrganization(organization);
				userDepartmentModel.setUserRoleDepartment(uRD);
				userDepartmentModel.setStructure(structure);
				userDepartmentModel.setCreateBy(manager.getId());
				userDepartmentModel.setUpdateBy(manager.getId());
			}
			structureService.save(structure,userDepartmentModel);
			treeManager.InitTreeSession(session);
			attr.addFlashAttribute("departmentId", structure.getId());
		} catch (NumberFormatException e) {
			 logger.error(e.getMessage());
			 throw e;
		} catch (NoSuchMessageException e) {
		     logger.error(e.getMessage());
		     throw e;
		}
		return "redirect:/institution/org";
	} 
	@Log(operateType="logs.department.type.update",operateContent="logs.department.update.content",args={"name"})
	@RequestMapping(value = "/updateStructure", method = RequestMethod.POST)
	public String updateStructure(StructureModel structure,HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		try {
			structureService.update(structure);
			treeManager.InitTreeSession(session);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "redirect:/institution/org";
	}

	@RequestMapping(value = "/queryParentById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryParentById(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			StructureModel structure = structureService.queryParentById(id);
			map.put("structure", structure);
		} catch (Exception e) {
		    logger.error(e.getMessage());
		    throw e;
		}
		return map;
	}
	@Log(operateType="logs.department.type.move",operateContent="logs.department.move.content",args={"parentName","name"})
	@RequestMapping(value = "/updateParentIdById", method = RequestMethod.POST)
	public String updateParentIdById(HttpServletRequest request,HttpSession session,
			HttpServletResponse response) {
		try {
			OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			int parentId = Integer.parseInt(request.getParameter("parentId"));
			int id = Integer.parseInt(request.getParameter("id"));
			String name = structureService.queryNameById(id);
			String parentName = structureService.queryNameById(parentId);
			String rootName = messageSource.getMessage("logs.root.department.name",null, LocaleContextHolder.getLocale());
			if(organization.getName().equals(name)){
				request.setAttribute("name", rootName);
			}else{
				request.setAttribute("name", name);
			}
			request.setAttribute("parentName", parentName);
			structureService.updateParentIdById(id, parentId);
			treeManager.InitTreeSession(session);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		    throw e;
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return null;

	}
    /**
     * 删除部门
     * @param request
     * @param session
     * @return
     */
	@SuppressWarnings("unchecked")
	@Log(operateType="logs.department.type.delete",operateContent="logs.department.delete.content",args={"name"})
	@RequestMapping(value = "/deleteStructureById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStructureById(HttpServletRequest request,HttpSession session) {
		Map<String, Object> map =  new HashMap<String, Object>();
		try {
			List<StructureModel> list = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			UserRoleDepartmentModel uRD = null;
			if(manager.getUser() != null){
				uRD = userRoleDepartmentService.findOneByMaps(manager.getOrganization().getId(), manager.getUser().getId());
			}
			Integer id = Integer.parseInt(request.getParameter("id"));
			String name = structureService.queryNameById(id);
			request.setAttribute("name", name);
			List<Integer> idList = new ArrayList<Integer>();
			structureService.queryAllChildrenId(id,list,idList);
			idList.add(id);
			int result = structureService.deleteStructureById(uRD,idList);
			treeManager.InitTreeSession(session);
			map.put("idList", idList);
			map.put("result", result);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return map;
	}
    
	/**
	 * 设置部门的策略，如果有下级部门，并且下级部门的策略也是继承上级，则连下级部门的策略一起修改
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Log(operateType="logs.department.policy.type.update",operateContent="logs.department.policy.update.content",args={"departmentName","policyName"})
	@RequestMapping(value = "/updatePolicyById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePolicyById(HttpServletRequest request,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,String> resultMap = new HashMap<String, String>();
		try {
			OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			List<StructureModel> list = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			List<StructureModel> idList = new ArrayList<StructureModel>();
			List<Integer> departmentIdList = new ArrayList<Integer>();
			List<String> deviceToken = null;
//			ExecutorService pool = Executors.newFixedThreadPool(100);//创建一个线程池
			Map<String,String> extra = new HashMap<String, String>();//存放推送的数据
			StructureModel structure = new StructureModel();
			UserModel user = new UserModel();
			String policyId = request.getParameter("policyId");
			Integer currentPolicyId = Integer.valueOf(request.getParameter("currentPolicyId"));
			Integer departmentId = Integer.valueOf(request.getParameter("departmentId"));
			String departmentName = request.getParameter("departmentName");
			String policName = request.getParameter("policyName");
			String orgId = structureService.queryParentByparentId(departmentId);
			request.setAttribute("departmentName", departmentName);
			request.setAttribute("policyName", policName);
			structureService.queryAllChildrenPolicyId(departmentId,list,idList); //找出当前部门的所有下级部门
			for(StructureModel s : idList){
				if(currentPolicyId.equals(s.getPolicy().getId())){
					departmentIdList.add(s.getId());  //遍历所有的袭击部门，取出所有下级部门和上级部门策略一样的id
				}
			}
			departmentIdList.add(departmentId);  //把当前部门放到list中，用于和下级部门一起修改
			if (StringUtils.isNotEmpty(policyId)) {    //没有选择继承上级策略
				int result = structureService.updatePolicyByList(Integer.valueOf(policyId),departmentIdList);//修改当前部门的策略和下级部门和上级部门策略一样的策略
				if(result == departmentIdList.size()){
					deviceToken = new ArrayList<String>();
					PolicyModel policy = policyService.queryAllInfoById(Integer.valueOf(policyId));  //查询出要修改的策略的详细信息 
					extra.put("policy", JSONObject.fromObject(policy).toString());
					for(int i=0;i<departmentIdList.size();i++){
						structure.setId(departmentIdList.get(i));
					    user.setPolicy(policy);
						user.setStructure(structure);
						userService.updatePolicyById(user);
					}
					Map<String,Object> userMap = new HashMap<String, Object>();
					userMap.put("idList",departmentIdList);
					List<PolicyModel> userIdList = policyService.queryUserIdbyDepartmentId(userMap);
					resultMap.put("policy", JSONObject.fromObject(policy).toString());
					if(CollectionUtils.isNotEmpty(userIdList)){
						for(PolicyModel p : userIdList){
			    			if(p.getDeviceManager().getDevice_type().equals("andorid")){
			    				MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(p.getUser().getId()), null, null, 2, resultMap);
			    				taskExecutor.execute(mqProducerThread);
			    			}else if(p.getDeviceManager().getDevice_type().equals("ios")){
			    				deviceToken.add(p.getDeviceManager().getDeviceToken().replaceAll(" ", ""));
			    			}
			    		}
					}
		    		if(deviceToken.size() > 0){
		    			IosPushUtil.pushDataToIos(deviceToken,null,1,"",resultMap);
		    		}
				}
				map.put("result", result);
			} else {    //选择了继承上级的策略
				Integer parentPolicyId = null;
				if(StringUtils.isNotEmpty(orgId)){
					parentPolicyId = structureService.queryParentPolicyById(departmentId);
				}else{
					parentPolicyId = policyService.queryDefaultPolicy(organization.getId()); //查询出当前部门的上级部门的策略
				}
				int result = structureService.updatePolicyByList(parentPolicyId,departmentIdList);
				if(result == departmentIdList.size()){
					deviceToken = new ArrayList<String>();
					PolicyModel policy = policyService.queryAllInfoById(parentPolicyId);  //查询出要修改的策略的详细信息 
					extra.put("policy", JSONObject.fromObject(policy).toString());
					for(int i=0;i<departmentIdList.size();i++){
						structure.setId(departmentIdList.get(i));
					    user.setPolicy(policy);
						user.setStructure(structure);
						userService.updatePolicyById(user);
					}
					Map<String,Object> userMap = new HashMap<String, Object>();
					userMap.put("idList",departmentIdList);
					List<PolicyModel> userIdList = policyService.queryUserIdbyDepartmentId(userMap);
					resultMap.put("policy", JSONObject.fromObject(policy).toString());
					if(CollectionUtils.isNotEmpty(userIdList)){
						for(PolicyModel p : userIdList){
			    			if(p.getDeviceManager().getDevice_type().equals("andorid")){
			    				MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(p.getUser().getId()), null, null, 2, resultMap);
			    				taskExecutor.execute(mqProducerThread);
			    			}else if(p.getDeviceManager().getDevice_type().equals("ios")){
			    				deviceToken.add(p.getDeviceManager().getDeviceToken().replaceAll(" ", ""));
			    			}
			    		}
					}
		    		if(deviceToken.size() > 0){
		    			IosPushUtil.pushDataToIos(deviceToken,null,1,"",resultMap);
		    		}
				}
				map.put("result", result);
			}
			treeManager.InitTreeSession(session); 
			map.put("departmentIdList", departmentIdList);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return map;
	}
     
	/**
	 * 删除部门之前，先校验他的所有夏季部门中有没有人
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryExitsUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExitsUser(HttpServletRequest request,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer departmentId = Integer.valueOf(request.getParameter("id"));
			@SuppressWarnings("unchecked")
			List<StructureModel> list = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			List<Integer> idList = new ArrayList<Integer>();
			structureService.queryAllChildrenId(departmentId,list,idList);
			idList.add(departmentId);
			int count = structureService.queryUserById(idList);
			map.put("count", count);
		} catch (NumberFormatException e) {
		    logger.error(e.getMessage());
		    throw e;
		}
		return map;
	}

	@RequestMapping(value = "/queryExitsName", method = RequestMethod.GET)
	@ResponseBody
	public Boolean queryExitsName(HttpServletRequest request, String id,
			String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Integer.valueOf(id));
		map.put("name", name.trim());
		int count;
		try {
			count = structureService.queryCountByName(map);
			return count>0?true:false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@RequestMapping(value = "/checkName")
	@ResponseBody
	public Boolean checkName(HttpServletRequest request, String id,
			String nameInit, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!(name.trim()).equals(nameInit.trim())) {
			map.put("id", Integer.valueOf(id));
			map.put("name", name.trim());
			int count;
			try {
				count = structureService.queryCountByName(map);
				return count>0?true:false;
			} catch (Exception e) {
			    logger.error(e.getMessage());
			    throw e;
			}
		} else {
			return false;
		}
	}
	
	@RequestMapping(value="/queryChildName",method=RequestMethod.POST)
	@ResponseBody
	public Boolean queryChildName(HttpServletRequest request){
		String name = request.getParameter("name");
		Integer id = Integer.valueOf(request.getParameter("id"));
		List<String> list = null;
		try {
			list = structureService.queryChildName(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		if(list!=null&&list.contains(name)){
			return false;
		}else{
			return true;
		}
	}
}
