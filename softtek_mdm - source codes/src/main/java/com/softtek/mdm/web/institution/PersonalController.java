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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.MessageType;
@Controller
@RequestMapping(value="/institution/personal")
public class PersonalController extends BaseController{

	@Autowired
	private ManagerService managerService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	/**
	 * 进入首页
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Link(family="institution",label="institution.person.index.label",parent="institution.home.index.label")
	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		manager=managerService.findOne(manager.getId());
		model.addAttribute("manager",manager);
		@SuppressWarnings("unchecked")
		List<StructureModel> list=(List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		if(list!=null&&list.size()>0){
			model.addAttribute("departNum", list.size()-1);
		}else{
			model.addAttribute("departNum", 0);
		}
		
		List<Integer> ids=new ArrayList<Integer>();
		for (StructureModel ste : list) {
			if(!ids.contains(ste.getId())){
				ids.add(ste.getId());
			}
		}
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("orgId", organization.getId());
		map.put("user", manager.getUser());
		int userCount=userService.findCountByDid(map);
		model.addAttribute("userNum", userCount);
		int deviceCount=deviceBasicInfoService.findCountByDepartIds(map);
		model.addAttribute("deviceNum", deviceCount);
		return "institution/personal/index";
	}
	
	/**
	 * 更新用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(ManagerModel manager,HttpSession session,HttpServletRequest request,HttpServletResponse response,RedirectAttributes attrs) throws IOException{
		ManagerModel managerSession=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		manager.setId(managerSession.getId());
		String sourcePwd=request.getParameter("source_password");
		if(!StringUtil.isBlank(sourcePwd)){
			Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
			ManagerModel temp=managerService.findOne(managerSession.getId());
			if(StringUtil.equals(md5PasswordEncoder.encodePassword(sourcePwd, null),temp.getPassword())){
				if(!StringUtil.isBlank(manager.getPassword())){
					manager.setPassword(md5PasswordEncoder.encodePassword(manager.getPassword(), null));
				}
			}else{
				addFlashMeesage(attrs, "defes.institution.person.pwd.failed", MessageType.danger);
				return "redirect:/institution/personal";
			}
		}
        int flag=managerService.updatePerson(manager);
		if(flag>0){
			ManagerModel mgr=managerService.findOne(managerSession.getId());
			session.setAttribute(SessionStatus.SOFTTEK_MANAGER.toString(), mgr);
			addFlashMeesage(attrs, "defes.institution.person.update.success", MessageType.success);
		}else{
			addFlashMeesage(attrs, "defes.institution.person.update.failed", MessageType.danger);
		}
		return "redirect:/institution/personal";
	}
}
