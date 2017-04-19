package com.softtek.mdm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softtek.mdm.model.ClientManagerModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.RoleModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.ClientManagerService;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.service.RoleService;
import com.softtek.mdm.service.UserRoleDepartmentService;
import com.softtek.mdm.status.AuthStatus;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.web.http.MessageType;

/**
 * 处理和登录有关的操作，权限验证将在Spring Security中处理
 * 
 * @author color.wu
 * @version 1.0
 * @time 2016/03/10
 *
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserRoleDepartmentService userRoleDepartmentService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ClientManagerService clientManagerService;
	
	/**
	 * 普通用户和部门管理员登录首页
	 * @param request
	 * @param response
	 * @param session
	 * @param attrs
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/", method = RequestMethod.GET)
	public final String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			RedirectAttributes attrs, Model model) {
		//用户登录首页显示下拉菜单
		List<OrganizationModel> orgList=organizationService.findAll();
		model.addAttribute("orgs", orgList);
		if(SecurityContextHolder.getContext().getAuthentication()==null){
			return "login";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Set<String> privilege = null;
		if (authentication != null) {
			privilege = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		}
		
		if (privilege.contains(AuthStatus.AUTH_INSTITUTION.toString())) {
			ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			if (manager != null&&manager.getUser()!=null) {
				UserRoleDepartmentModel uRD = userRoleDepartmentService
						.findOneByMaps(manager.getOrganization().getId(), manager.getUser().getId());
				if (uRD != null) {
					RoleModel role = roleService.findOne(uRD.getRole().getId());
					if (role == null) {
						addMessage(model, "login.user.role.delete.msg", MessageType.danger);
						return "login";
					}
				}
				
				// 用户已被锁住，不能登录
				if (uRD.getIslock()!=null&&uRD.getIslock() > 0) {
					addMessage(model, "login.user.lock.msg", MessageType.warning);
					return "login";
				}
				return "redirect:/institution";
			}
		}
		
		if (privilege.contains(AuthStatus.AUTH_CUSTOMER.toString())) {
			UserModel um=(UserModel) session.getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
			return um!=null?"redirect:/customer":"login";
		}
		
		return "login";
	}
	
	/**
	 * 管理员/超级管理员登录首页
	 * @param request
	 * @param response
	 * @param session
	 * @param attrs
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/manager", method = RequestMethod.GET)
	public final String manager(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			RedirectAttributes attrs, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(SecurityContextHolder.getContext().getAuthentication()==null){
			return "login";
		}
		Set<String> privilege = null;
		if (authentication != null) {
			privilege = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		}else{
			return "manager/login";
		}
		if(privilege.contains(AuthStatus.AUTH_ADMIN.toString())){
			//超级管理员
			return "redirect:/admin";
		}
		if (privilege.contains(AuthStatus.AUTH_INSTITUTION.toString())) {
			ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
			if (manager != null) {
				if (manager.getUser() == null) {
					return "redirect:/institution";
				}else{
					//部门管理员
					addMessage(model, "login.user.depart.msg", MessageType.warning);
				}
			}
		} 
		return "manager/login";
	}
	
	/**
	 * 通过orgid查询QR图片
	 * @param request
	 * @param orgId
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value="/queryImgByOrgId",method=RequestMethod.POST)
	 public Map<String,Object> queryImgByOrgId(HttpServletRequest request,String orgId){
		 Map<String,Object> map = new HashMap<String, Object>();
		 List<ClientManagerModel> list = clientManagerService.queryImgByOrgId();
		 boolean flag =false,exists= false;
		 int j = 0, num = 0;
		 for(ClientManagerModel clientManager : list){
			 if(StringUtils.isNotEmpty(clientManager.getBelongOrg())){
				 String[] orgIds = clientManager.getBelongOrg().split(",");
				 for(int i=0;i<orgIds.length;i++){
					 if(StringUtils.isNotEmpty(orgIds[i])&&orgId.equals(orgIds[i])){
						 flag = true;
						 map.put("clientManager", clientManager);
						 break;
					 }
				 }
				 if(flag == true){
					 break;
				 }
			 }else{
				 j = num;
				 exists = true;
			 }
			 num++;
		 }
		 if(!flag){
			 map.put("clientManager", exists?list.get(j):null);
		 }
		 return map;
	 }
	 
	 /**
	  * 查询QrCode
	  * @return
	  */
	 @RequestMapping(value="/queryQrCode",method=RequestMethod.POST)
	 @ResponseBody
	 public Map<String,Object> queryQrCode(){
		 Map<String,Object> map = new HashMap<String, Object>();
		 ClientManagerModel clientManager = clientManagerService.queryQrCode();
		 map.put("clientManager", clientManager != null?clientManager:null);
		 return map;
	 }
}
