package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserVirtualGroupModel;
import com.softtek.mdm.model.VirtualCollectionModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.service.ExportAndImportExcelService;
import com.softtek.mdm.service.UserVirtualGroupService;
import com.softtek.mdm.service.VirtualCollectionService;
import com.softtek.mdm.service.VirtualGroupService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.ExportData;
import com.softtek.mdm.web.BaseController;
import jodd.util.StringUtil;

/**
 * @author josen.yang
 *
 */
@Controller
@RequestMapping(value = "/institution/vtl")
public class VirtualController extends BaseController {
	@Autowired
	private VirtualGroupService virtualGroupService;
	@Autowired
	private VirtualCollectionService virtualCollectionService;
	@Autowired
	private UserVirtualGroupService userVirtualGroupService;
	@Autowired
	private ExportAndImportExcelService exportAndImportExcelService;
	@Autowired
	private MessageSource messageSource;
	private Logger logger = Logger.getLogger(VirtualController.class);

	/**
	 * 虚拟组首页
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Link(family = "institution", label = "web.institution.vtl.index.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.usercontroller.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model)
			throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		try {
			Map<String, Object> map = virtualCollectionService.findAllByOrgIdMap(organization.getId());
			model.addAttribute("vtllist", map.get("list"));
			model.addAttribute("vtlcollist", map.get("vtlcollist"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "institution/vtl/index";
	}

	/**
	 * 虚拟组成员管理首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Link(family = "institution", label = "tiles.institution.vtl.group.action.member.label", parent = "web.institution.homecontroller.index.link.label",belong = "web.institution.usercontroller.index.link.belong")
	@RequestMapping(value = "/member", method = RequestMethod.GET)
	public String member(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
		// 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
			// 查询所有的虚拟组集合和虚拟组
			List<VirtualCollectionModel> vtlcollist = (List<VirtualCollectionModel>) virtualCollectionService
					.findAllByOrgID(organization.getId());
			List<VirtualGroupModel> list = (List<VirtualGroupModel>) virtualGroupService
					.findByOrgIdMember(organization.getId(), idlist);
			// 根据虚拟组总人数总计集合的总人数
			for (int i = 0; i < vtlcollist.size(); i++) {
				vtlcollist.get(i).setMark("0");
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j).getVirtualCollection().getId().toString()
							.equals(vtlcollist.get(i).getId().toString())) {
						Integer count = Integer.parseInt(vtlcollist.get(i).getMark()) + list.get(j).getQuantity();
						vtlcollist.get(i).setMark(count.toString());
					}
				}
			}
			model.addAttribute("vtlcollist", vtlcollist);
			model.addAttribute("vtllist", list);
		return "institution/vtl/member";
	}

	/**
	 * 新增虚拟组集合以及虚拟组
	 * 
	 * @param virtualGroup
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@Log(operateType = "logs.virtualGroup.type.add", operateContent = "logs.virtualGroup.content.add", args = {
			"colname", "name" })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String create(VirtualGroupModel virtualGroup, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		try {
			virtualGroup.getVirtualCollection().setOrganization(organization);
			virtualGroup.setOrganization(organization);
			if(virtualGroup.getVirtualCollection()!=null){
			String colname = virtualGroup.getVirtualCollection().getName();
			String name = virtualCollectionService.save(virtualGroup.getVirtualCollection(), virtualGroup,managerModel.getId());
			request.setAttribute("colname", colname);
			request.setAttribute("name", name);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "redirect:/institution/vtl";
	}

	/**
	 * 查询虚拟组集合名称是否已经存在
	 * 
	 * @param virtualCollection
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exists", method = RequestMethod.GET)
	public @ResponseBody String isExists(VirtualGroupModel virtualGroup, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String name = virtualGroup.getVirtualCollection().getName();
		VirtualCollectionModel virtualCollection = new VirtualCollectionModel();
		virtualCollection.setName(name);
		virtualCollection.setOrganization(organization);
		boolean isext = virtualCollectionService.isExists(virtualCollection);
		if (isext == true) {
			return "failed";
		} else {
			return "success";
		}
	}

	/**
	 * 查询修改虚拟组集合名称是否已经存在
	 * 
	 * @param virtualCollection
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/existsedit", method = RequestMethod.GET)
	public @ResponseBody String existsEditName(VirtualGroupModel virtualGroup, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, String id) throws IOException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String name = virtualGroup.getVirtualCollection().getName();
		Integer cid = Integer.parseInt(id);
		VirtualCollectionModel virtualCollection = new VirtualCollectionModel();
		virtualCollection.setName(name);
		virtualCollection.setId(cid);
		virtualCollection.setOrganization(organization);
		Integer number = virtualCollectionService.existsEditName(virtualCollection);
		if (number >= 1) {
			return "failed";
		} else {
			return "success";
		}

	}

	/**
	 * 查询修改虚拟组名称是否已经存在
	 * 
	 * @param virtualCollection
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/existseditgroup", method = RequestMethod.GET)
	public @ResponseBody String existseditgroup(VirtualGroupModel virtualGroup, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, String gid, String cid) throws IOException {
		Integer ccid = Integer.parseInt(cid);
		Integer ggid = Integer.parseInt(gid);
		VirtualCollectionModel virtualCollection = new VirtualCollectionModel();
		virtualCollection.setId(ccid);
		virtualGroup.setVirtualCollection(virtualCollection);
		virtualGroup.setId(ggid);
		Integer number = virtualGroupService.existsEditName(virtualGroup);
		if (number >= 1) {
			return "failed";
		} else {
			return "success";
		}

	}

	/**
	 * 根据用户选择的类型 删除虚拟组集合或具体虚拟组
	 * 
	 * @param request
	 * @return
	 */
	@Log(operateType = "logs.virtualGroup.type.remove", operateContent = "logs.virtualGroup.content.removecol", args = {
			"name" })
	@RequestMapping(value = "/deletecol", method = RequestMethod.POST)
	public String deletecol(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		String type = request.getParameter("type");
		if (type.equals("parent")) {
			virtualCollectionService.deleteByCid(id);
		} else if (type.equals("children")) {
			virtualGroupService.delete(id);
		}
		return "redirect:/institution/vtl";
	}

	/**
	 * 根据用户选择的类型 删除虚拟组集合或具体虚拟组
	 * 
	 * @param request
	 * @return
	 */
	@Log(operateType = "logs.virtualGroup.type.remove", operateContent = "logs.virtualGroup.content.remove", args = {
			"name" })
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String type = request.getParameter("type");
			if (type.equals("parent")) {
				virtualCollectionService.deleteByCid(id);
			} else if (type.equals("children")) {
				virtualGroupService.delete(id);
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "redirect:/institution/vtl";
	}

	/**
	 * 更新虚拟组集合
	 * 
	 * @param request
	 * @return
	 */
	@Log(operateType = "logs.virtualGroup.type.modify", operateContent = "logs.virtualGroup.content.modifycol", args = {
			"colname", "namegp" })
	@RequestMapping(value = "/updateCol", method = RequestMethod.POST)
	public String update(VirtualGroupModel virtualGroup, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// 具体根据什么来分割，可以自己替换修改
		try {
			OrganizationModel organization = (OrganizationModel) session
					.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			virtualGroup.setOrganization(organization);
			virtualGroup.setCreateBy(managerModel.getId());
			String namegp = virtualCollectionService.update(virtualGroup.getVirtualCollection(), virtualGroup);
			String colname = virtualGroup.getVirtualCollection().getName();
			request.setAttribute("namegp", namegp);
			request.setAttribute("colname", colname);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "redirect:/institution/vtl";
	}

	/**
	 * 更新虚拟组
	 * 
	 * @param request
	 * @return
	 */
	@Log(operateType = "logs.virtualGroup.type.modify", operateContent = "logs.virtualGroup.content.modify", args = {
			"name" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updategroup(VirtualGroupModel virtualGroup, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String name = virtualGroup.getName();
			virtualGroupService.updateName(virtualGroup);
			request.setAttribute("name", name);
		}  catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "redirect:/institution/vtl";
	}

	/**
	 * 根据查询虚拟组表中的用户和不在虚拟组表中的用户前10条（多选）
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/querymember", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querymember(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer orgid = organization.getId();
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
		// 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		if (StringUtil.isBlank(name)) {
			name = "";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserModel> existlist = (List<UserModel>) virtualGroupService.queryExistMember(id, name, idlist);
		List<UserModel> noexistentlist = (List<UserModel>) virtualGroupService.queryNoExistMember(orgid, id, name,
				idlist);
		int max = virtualGroupService.queryNeMemberMaxPage(orgid, id, name, idlist);
		int maxpageleft = max % 10 == 0 ? max / 10 : max / 10 + 1;
		int max2 = virtualGroupService.queryEMemberMaxPage(id, name, idlist);
		int maxpageright = max2 % 10 == 0 ? max2 / 10 : max2 / 10 + 1;
		map.put("existlist", existlist);
		map.put("noexistentlist", noexistentlist);
		map.put("page", 1);
		map.put("maxpageleft", maxpageleft);
		map.put("maxpageright", maxpageright);
		map.put("totalityleft", max);
		map.put("totalityright", max2);
		return map;
	}

	/**
	 * 根据ID，name查询虚拟组表中的用户和不在虚拟组表中的用户前10条（单选）
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/querymemberradio", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querymemberradio(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer orgid = organization.getId();
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
		// 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		if (StringUtil.isBlank(name)) {
			name = "";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserModel> existlist = (List<UserModel>) virtualGroupService.queryExistMember(id, name, idlist);
		List<UserModel> noexistentlist = (List<UserModel>) virtualGroupService.queryNoExistMemberRadio(orgid, id, name,
				idlist);
		int max = virtualGroupService.queryNeMemberMaxPageRadio(orgid, id, name, idlist);
		int maxpageleft = max % 10 == 0 ? max / 10 : max / 10 + 1;
		int max2 = virtualGroupService.queryEMemberMaxPage(id, name, idlist);
		int maxpageright = max2 % 10 == 0 ? max2 / 10 : max2 / 10 + 1;
		map.put("existlist", existlist);
		map.put("noexistentlist", noexistentlist);
		map.put("page", 1);
		map.put("maxpageleft", maxpageleft);
		map.put("maxpageright", maxpageright);
		map.put("totalityleft", max);
		map.put("totalityright", max2);
		return map;
	}

	/**
	 * 根据ID，索引(前台计算好的index)，name查看不存在用户（多选） 虚拟组
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querymbpagene", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> querymbpagene(HttpServletRequest request, HttpSession session) {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer orgid = organization.getId();
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
		// 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int index = Integer.parseInt(request.getParameter("index"));
		String name = request.getParameter("name");
		if (StringUtil.isBlank(name)) {
			name = "";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserModel> noexistentlist = (List<UserModel>) virtualGroupService.queryNoExistMemberPage(orgid, id, index,
				name, idlist);
		int max = virtualGroupService.queryNeMemberMaxPage(orgid, id, name, idlist);
		int maxpage = max % 10 == 0 ? max / 10 : max / 10 + 1;
		map.put("noexistentlist", noexistentlist);
		map.put("maxpage", maxpage);
		return map;
	}

	/**
	 * 根据ID，索引(前台计算好的index)，name查看不存在用户（单选）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querymbpageneradio", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> querymbpageneradio(HttpServletRequest request, HttpSession session) {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer orgid = organization.getId();
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
		// 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int index = Integer.parseInt(request.getParameter("index"));
		String name = request.getParameter("name");
		if (StringUtil.isBlank(name)) {
			name = "";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserModel> noexistentlist = (List<UserModel>) virtualGroupService.queryNoExistMemberPageRadio(orgid, id,
				index, name, idlist);
		int max = virtualGroupService.queryNeMemberMaxPageRadio(orgid, id, name, idlist);
		int maxpage = max % 10 == 0 ? max / 10 : max / 10 + 1;
		map.put("noexistentlist", noexistentlist);
		map.put("maxpage", maxpage);
		return map;
	}

	/**
	 * 根据ID，索引(前台计算好的index)，name查看不存在用户（多选）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querymbpagee", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> querymbpagee(HttpServletRequest request, HttpSession session) {
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
		// 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int index = Integer.parseInt(request.getParameter("index"));
		String name = request.getParameter("name");
		if (StringUtil.isBlank(name)) {
			name = "";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserModel> existentlist = (List<UserModel>) virtualGroupService.queryExistMemberPage(id, index, name,
				idlist);
		int max = virtualGroupService.queryEMemberMaxPage(id, name, idlist);
		int maxpage = max % 10 == 0 ? max / 10 : max / 10 + 1;
		map.put("existentlist", existentlist);
		map.put("maxpage", maxpage);
		return map;
	}

	/**
	 * 虚拟组增加用户 从前台获取 集合ID,组ID,用户ID并执行插入
	 * 
	 * @param request
	 * @return
	 */
	@Log(operateType = "logs.virtualGroup.member.type.add", operateContent = "logs.virtualGroup.member.content.add", args = {
			"gname", "username" })
	@RequestMapping(value = "/insertmember", method = RequestMethod.POST)
	public @ResponseBody void insertMember(HttpServletRequest request, HttpSession session) {
		try {
			// session取机构ID
			OrganizationModel organization = (OrganizationModel) session
					.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
			// new model
			List<UserVirtualGroupModel> modelList = new ArrayList<UserVirtualGroupModel>();
			VirtualCollectionModel virtualCollection = new VirtualCollectionModel();
			VirtualGroupModel virtualGroup = new VirtualGroupModel();
			// 设置组ID
			virtualGroup.setId(Integer.parseInt(request.getParameter("groupid")));
			// 集合ID
			virtualCollection.setId(Integer.parseInt(request.getParameter("colid")));
			String[] strArr = (StringUtil.split(request.getParameter("userid"), ","));
			String[] strArr2 = (StringUtil.split(request.getParameter("uname"), ","));
			String username = "";
			if (strArr.length >= 1) {
				for (int i = 0; i < strArr.length; i++) {
					UserVirtualGroupModel uservirtualgroup = new UserVirtualGroupModel();
					uservirtualgroup.setVirtualGroup(virtualGroup);
					uservirtualgroup.setVirtualCollection(virtualCollection);
					UserModel user = new UserModel();
					// 设置机构ID
					uservirtualgroup.setOrganization(organization);
					Integer userid = Integer.parseInt(strArr[i]);
					username += strArr2[i] + ",";
					user.setId(userid);
					uservirtualgroup.setUser(user);
					modelList.add(uservirtualgroup);
				}
				userVirtualGroupService.insertMembers(modelList);
				username = username.substring(0, username.length() - 1);
			}
			request.setAttribute("username", username);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 根据集合ID,组ID,用户ID 删除虚拟组存在的用户
	 * 
	 * @param request
	 */
	@Log(operateType = "logs.virtualGroup.member.type.remove", operateContent = "logs.virtualGroup.member.content.remove", args = {
			"gname", "username" })
	@RequestMapping(value = "/deletemember", method = RequestMethod.POST)
	@ResponseBody
	public void deletemember(HttpServletRequest request) {
		try {
			Integer groupid = Integer.parseInt(request.getParameter("groupid"));
			Integer colid = Integer.parseInt(request.getParameter("colid"));
			String[] strArr = (StringUtil.split(request.getParameter("userid"), ","));
			String[] strArr2 = (StringUtil.split(request.getParameter("uname"), ","));
			String username = "";
			if (strArr.length >= 1) {
				for (int i = 0; i < strArr.length; i++) {
					Integer userid = Integer.parseInt(strArr[i]);
					username += strArr2[i] + ",";
					userVirtualGroupService.deleteByid(userid, groupid, colid);
				}
				username = username.substring(0, username.length() - 1);
			}
			request.setAttribute("username", username);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 下载虚拟组模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getvirmodel", method = RequestMethod.GET)
	@ResponseBody
	public void getvirmodel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExportData exportData = new ExportData();
		
		String headers[][] = { { messageSource.getMessage("tiles.institution.vtl.group.excel.name",null, LocaleContextHolder.getLocale()), "String" }, { messageSource.getMessage("tiles.institution.vtl.group.excel.username",null, LocaleContextHolder.getLocale()), "String" } };
		SXSSFWorkbook workbook = exportData.getwb(headers, "sheet1");
		// XSSFWorkbook workbook = new XSSFWorkbook();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
		OutputStream os = null;
		String fileName = messageSource.getMessage("tiles.institution.vtl.group.excel.fileName",null, LocaleContextHolder.getLocale())+".xlsx";
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		os = response.getOutputStream();
		workbook.write(os);
		os.flush();
		os.close();
	}
	
	/**
	 * 导入虚拟组成员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Log(operateType = "logs.virtualGroup.member.type.import", operateContent = "logs.virtualGroup.content.member.import")
	@RequestMapping(value = "/importvirmember", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> importvirmember(MultipartFile files, String id,String colid,String isradio,HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		//判断是xls还是xlsx
		Integer filetype=0;
		if(files.getOriginalFilename().endsWith("xls")){
			 filetype=03; 
		}
		if(files.getOriginalFilename().endsWith("xlsx")){
			 filetype=07; 
		}
		//获取机构id
		OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer orgid = organization.getId();
		//获取操作人信息
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
		//获得createById
		//Integer managerId=managerModel.getId();
		// 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
		InputStream ins = files.getInputStream();
		Map<String, Integer> map=new HashMap<>();
		map.put("groupid",Integer.parseInt(id));
		map.put("orgid",orgid);
		map.put("colId",Integer.parseInt(colid));
		map.put("isradio",Integer.parseInt(isradio));
		map.put("filetype",filetype);
		Map<String, Object> resultMap= exportAndImportExcelService.importvirmember(ins,idlist,map);
		return resultMap;
	
		 
	}
}
