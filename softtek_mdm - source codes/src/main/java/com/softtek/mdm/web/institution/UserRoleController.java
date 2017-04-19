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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MenuModel;
import com.softtek.mdm.model.MenuNodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.RoleMenuModel;
import com.softtek.mdm.model.RoleModel;
import com.softtek.mdm.service.MenuService;
import com.softtek.mdm.service.RoleMenuService;
import com.softtek.mdm.service.RoleService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.MenuManager;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;

/**
 * 用户角色管理
 * 
 * @author color.wu
 *
 */
@Controller
@RequestMapping(value = "/institution/urole")
public class UserRoleController extends BaseController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private MenuManager menuManager;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private RoleService roleService;
	/**
	 * logger日志
	 */
	private Logger logger = Logger.getLogger(UserRoleController.class);


	/**
	 * 用户角色主页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Link(family = "institution", label = "web.institution.userrolecontroller.index.link.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.usercontroller.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public final String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Model model) throws IOException {
		try {
			List<MenuModel> list = menuService.findAllShowMenu();
			MenuNodeModel menuTree = menuManager.buildTree(list);
			model.addAttribute("menuTree", menuTree);
			return "institution/urole/index";
		} catch (Exception e) {
			logger.error("Method findAllShowMenu invoked by menuService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 新增角色
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.userrolecontroller.create.log.operatetype", operateContent = "web.institution.userrolecontroller.create.log.operatecontent", args = { "name" })
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public final String create(RoleModel role, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, RedirectAttributes attrs) throws IOException {
		Map<String, Object> map=new HashMap<String,Object>();
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		role.setOrganization(organization);
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		role.setCreateBy(manager.getId());
		role.setUpdateBy(manager.getId());
		map.put("organization", organization);
		map.put("rids", StringUtils.trimToEmpty(request.getParameter("rids")));
		map.put("role", role);
		try {
			roleService.create(map);
		} catch (Exception e) {
			logger.error("Method create invoked by roleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		addFlashMeesage(attrs, "web.institution.userrolecontroller.create.success", MessageType.success);
		return "redirect:/institution/urole";
	}

	/**
	 * ajax获取角色的分页
	 * 
	 * @param request
	 * @param response
	 * @param start
	 *            起始页
	 * @param length
	 *            每页显示的记录数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	public @ResponseBody Page roles(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			Integer start, Integer length) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String roleName = StringUtils.trimToEmpty(request.getParameter("searchrolename"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", roleName);
		map.put("pageNum", start == null ? 0 : start);
		map.put("pageSize", length == null ? 10 : length);
		map.put("roleName", roleName);
		map.put("orgId", organization.getId());
		try {
			Page page = roleService.findRecordsByPage(map);
			return page;
		} catch (Exception e) {
			logger.error("Method findRecordsByPage invoked by roleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * ajax获取角色的基本信息
	 * 
	 * @param request
	 * @param response
	 * @param start
	 *            起始页
	 * @param length
	 *            每页显示的记录数
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.userrolecontroller.roleinfo.log.operatetype", operateContent = "web.institution.userrolecontroller.roleinfo.log.operatecontent", args = { "rname" })
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> roleInfo(Integer rid, HttpServletRequest request, HttpServletResponse response) throws IOException {
		RoleModel role = roleService.findOne(rid);
		request.setAttribute("rname", role.getName());
		try {
			List<RoleMenuModel> list = roleMenuService.findListByRoleId(role);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("role", role);
			map.put("list", list);
			return map;
		} catch (Exception e) {
			logger.error("Method findListByRoleId invoked by roleMenuService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.userrolecontroller.update.log.operatetype", operateContent = "web.institution.userrolecontroller.update.log.operatecontent", args = { "name" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public final String update(RoleModel role, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model, RedirectAttributes attrs) throws IOException {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("rids", StringUtils.trimToEmpty(request.getParameter("m_rids")));
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		role.setOrganization(organization);
		map.put("role", role);
		try {
			roleService.update(map);
		} catch (Exception e) {
			logger.error("Method update invoked by roleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		addFlashMeesage(attrs, "web.institution.userrolecontroller.update.success", MessageType.success);
		return "redirect:/institution/urole";
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "web.institution.userrolecontroller.delete.log.operatetype", operateContent = "web.institution.userrolecontroller.delete.log.operatecontent", args = { "rname" })
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public final @ResponseBody JsonResult delete(Integer rid, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) throws IOException {
		JsonResult jsonResult = null;
		RoleModel role = roleService.findOne(rid);
		request.setAttribute("rname", role.getName());
		int flag = 0;
		try {
			flag = roleService.deleteWithPk(rid);
		} catch (Exception e) {
			logger.error("Method deleteWithPk invoked by roleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
		if (flag > 0) {
			jsonResult = createJsonResult(MessageType.success, "web.institution.userrolecontroller.delete.success", null, null);
		} else {
			jsonResult = createJsonResult(MessageType.danger, "web.institution.userrolecontroller.delete.failed", null,
					null);
		}
		return jsonResult;
	}

	/**
	 * 角色是否已经存在
	 * 
	 * @param email
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public final @ResponseBody boolean accountExists(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {

		String rid = request.getParameter("rid");
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		try {
			boolean flag = roleService.isExists(request.getParameter("name"), organization.getId());
			if (rid == null || rid.length() == 0) {
				return flag == true ? false : true;
			} else {
				try {
					RoleModel role = roleService.findOne(Integer.parseInt(rid));
					if (role.getName().equals(request.getParameter("name"))) {
						return true;
					} else {
						return flag == true ? false : true;
					}
				} catch (Exception e) {
					logger.error("Method findOne invoked by roleService cause error and the reason is: " + e.getMessage());
					throw e;
				}
				
			}
		} catch (Exception e) {
			logger.error("Method isExists invoked by roleService cause error and the reason is: " + e.getMessage());
			throw e;
		}
	}
}
