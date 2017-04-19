package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.RoleModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.DptManagerService;
import com.softtek.mdm.service.RoleService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.service.UserRoleDepartmentService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * @author josen.yang
 *
 */
@Controller
@RequestMapping(value = "/institution/dptmanager")
public class DptManagerController extends BaseController {
	@Autowired
	private DptManagerService dptManagerService;
	@Autowired
	private StructureService structureService;
	@Autowired
	private TreeManager treeManager;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleDepartmentService userRoleDepartmentService;
	
	private Logger logger = Logger.getLogger(VirtualController.class);
	
    /**
     * 部门管理员首页
     * 
     * @param request
     * @param response
     * @param session
     * @param model
     * @return
     * @throws IOException
     */
    @Link(family = "institution", label = "logs.user.type.department.manager", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.usercontroller.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model,
			Integer start, Integer length) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		// Page
		// page=dptManagerService.findRecordsByPage(organization.getId(),start,length);
		List<StructureModel> list = (List<StructureModel>) structureService.findAllByOrgID(organization.getId());
		NodeModel nodes = treeManager.buildTree(list);
		String jsonStr = "[" + JSONObject.fromObject(nodes).toString() + "]";
		jsonStr = StringUtil.replace(jsonStr, "\"nodes\":[],", "");
		model.addAttribute("tree", jsonStr);
		return "institution/dptmanager/index";
	}

	/**
     * ajax获取角色的分页
     * 
     * @param start
     *            起始页
     * @param length
     *            每页显示的记录数
     * @return page
     * @throws IOException
     */
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	public @ResponseBody Page dptmanager(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			Integer start, Integer length) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String searchAccountName = StringUtils.trimToEmpty(request.getParameter("searchaccountname"));
		String searchRealName = StringUtils.trimToEmpty(request.getParameter("searchrealname"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchAccountName", searchAccountName);
		map.put("searchRealName", searchRealName);
		map.put("orgId", organization.getId());
		map.put("pageNum", start==null ? 0 : start);
		map.put("pageSize", length==null? 10 : length);
		Page page = dptManagerService.queryByParamsMap(map);
		return page;
	}

	/**
     * 激活或者取消激活单个用户
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@Log(operateType = "logs.dptmanager.type", operateContent = "logs.dptmanager.content.active", args = {
			"managerName", "active" })
	@RequestMapping(value = "/activeuser", method = RequestMethod.POST)
	public @ResponseBody JsonResult activeuser(String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserRoleDepartmentModel userRoleDepartment = new UserRoleDepartmentModel();
		UserModel user = new UserModel();
		user.setId(Integer.parseInt(id));
		userRoleDepartment.setUser(user);
		JsonResult jsonResult = null;
		Map<String, Object> map;
		try {
			map = dptManagerService.update(userRoleDepartment, user);
			request.setAttribute("managerName", map.get("managerName"));
            // request.setAttribute("active", map.get("active"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		int flag = (int) map.get("flag");
		int islock= (int) map.get("islock");
		if (!StringUtil.isBlank(user.getId().toString())) {
			if (flag > 0) {
				if (islock == 1) {
					jsonResult = createJsonResult(MessageType.success, "web.institution.dptmanager.lock.message", null, null);
				} else {
					jsonResult = createJsonResult(MessageType.success, "web.institution.dptmanager.enable.message", null,
							null);
				}
			} else {
				jsonResult = createJsonResult(MessageType.success, "web.institution.dptmanager.enable.message", null, null);
			}
			return jsonResult;
		} else {
			jsonResult = createJsonResult(MessageType.success, "web.institution.dptmanager.enable.message", null, null);
			return jsonResult;
		}
	}

	/**
     * 删除该部门管理员
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@Log(operateType="logs.dptmanager.type.delete",operateContent="logs.dptmanager.content.delete",args={"managerName"})
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody JsonResult delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			JsonResult jsonResult = null;
			String uid = request.getParameter("uid");
			String urd = request.getParameter("urd");
			UserRoleDepartmentModel userRoleDepartment = new UserRoleDepartmentModel();
			Integer userid=Integer.parseInt(uid);
			userRoleDepartment = dptManagerService.findOne(userid); 
			String managerName=userRoleDepartment.getUser().getRealname();
			request.setAttribute("managerName", managerName);
			if (!StringUtil.isBlank(urd)) {
				int flag=dptManagerService.delete(Integer.parseInt(uid),Integer.parseInt(urd));
				if (flag > 0) {
					jsonResult = createJsonResult(MessageType.success, "web.institution.dpt.delete.message", null, null);
				} else {
					jsonResult = createJsonResult(MessageType.success, "web.institution.dpt.undelete.message", null, null);
				}
				return jsonResult;
			} else {
				jsonResult = createJsonResult(MessageType.success, "web.institution.dpt.undelete.message", null, null);
				return jsonResult;
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
    
    /**
     * 查询所有的用户角色
     * 
     * @param request
     * @param session
     * @return
     */
	@RequestMapping(value = "/queryRoleByUid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRoleByUid(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer id = Integer.valueOf(request.getParameter("id"));
		UserRoleDepartmentModel userRoleDepartment = dptManagerService.queryRoleByUid(id);
		Integer auth=dptManagerService.queryAuthByUid(id);
		List<RoleModel> list = allRoles(session, request);
		map.put("userRoleDepartment", userRoleDepartment);
		map.put("auth",auth);
		map.put("list", list);
		return map;
	}

	public List<RoleModel> allRoles(HttpSession session, HttpServletRequest request) {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		return (List<RoleModel>) roleService.findAllByOrgId(organization.getId());
	}

	/**
     * 修改部门管理员
     * 
     * @param pid
     * @param session
     * @param request
     * @param response
     */
	@Log(operateType="logs.dptmanager.type.modify",operateContent="logs.dptmanager.content.type.modify",args={"managerName","roleName","departName","mark"})
	@RequestMapping(value = "/promotion", method = RequestMethod.POST)
	public @ResponseBody JsonResult promotion(UserRoleDepartmentModel userRoleDepartment, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonResult jsonResult = null;
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String departmentIds = request.getParameter("departmentIds");
		Integer auth=Integer.parseInt(request.getParameter("auth"));
		try {
			Map<String, Object> map = userRoleDepartmentService.update(userRoleDepartment,departmentIds,organization,auth);
				request.setAttribute("managerName", map.get("managerName"));
				request.setAttribute("departName",map.get("departName"));
				request.setAttribute("roleName", map.get("roleName"));
				jsonResult = createJsonResult(MessageType.success, "web.institution.dpt.promotion.label.message", null, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
			return jsonResult;
	}
}
