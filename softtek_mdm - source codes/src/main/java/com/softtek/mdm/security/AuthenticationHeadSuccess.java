package com.softtek.mdm.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MenuModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.RoleMenuModel;
import com.softtek.mdm.model.SysmanageLog;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.MenuService;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.service.RoleMenuService;
import com.softtek.mdm.service.SysmanageLogService;
import com.softtek.mdm.service.UserRoleDepartmentService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.AuthStatus;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.LogThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.MenuManager;
import com.softtek.mdm.util.TreeManager;


/**
 * 用户名和密码都认证成功，处理相应的权限信息
 * @author color.wu
 *
 */
public class AuthenticationHeadSuccess extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ManagerService managerService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private TreeManager treeManager;
	@Autowired
	private MenuManager menuManager;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserRoleDepartmentService userRoleDepartmentService;
	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private SysmanageLogService sysmanageLogService;
	@Resource(name="messageSourceService")
	private MessageSource messageSource;
	@Autowired
	private TaskExecutor taskExecutor;
	
	
	/**
	 * 部门管理员和普通用户认证成功
	 * 如果用户名密码认证认证成功
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		super.onAuthenticationSuccess(request, response, authentication);
		Integer orgId=Integer.valueOf(request.getParameter("j_organization"));
		LocaleContextHolder.setLocale(request.getLocale());
		UserDetails userDetails= (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		ManagerModel manager=managerService.findOneByOrgAndName(orgId, userDetails.getUsername());
		if(manager!=null){
			//部门管理员或者机构管理员
			if(manager.getUser()!=null){
				ManagerModel mag=new ManagerModel();
				mag.setId(manager.getId());
				mag.setLogin_count(0);
				mag.setLast_login_time(new Date());
				managerService.update(mag);
				
				//部门管理员
				//将用户的权限和其他一些信息，存入session中
				OrganizationModel organization=organizationService.findOne(manager.getOrganization().getId());
				request.getSession().setAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString(), organization);
				request.getSession().setAttribute(SessionStatus.SOFTTEK_MANAGER.toString(), manager);
				request.getSession().setAttribute("aside","0");
				
				Integer userId=manager.getUser().getId();
				UserModel user=userService.findOne(userId);
				
				request.getSession().setAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString(), user);
				
				Set<String> privilege=null;
				if(authentication!=null){
					privilege=AuthorityUtils.authorityListToSet(authentication.getAuthorities());
				}
				if(privilege.contains(AuthStatus.AUTH_INSTITUTION.toString())){
					HttpSession session=request.getSession();
					
					treeManager.InitTreeSession(session);
					
					UserRoleDepartmentModel uRD=userRoleDepartmentService.findOneByMaps(manager.getOrganization().getId(), manager.getUser().getId());
					//用户未被锁住，允许登录
					if(uRD.getIslock()==new Integer(0)){
						//菜单
						List<RoleMenuModel> menuList=roleMenuService.findListByRoleId(uRD.getRole());
						List<MenuModel> menus=menuService.findAllMenus();
						for (int i=0;i<menus.size();i++) {
							for ( int j=0;j<menuList.size();j++) {
								RoleMenuModel l=menuList.get(j);
								if(l.getMenu().getId().equals(menus.get(i).getId())){
									menus.get(i).setIsshow(-1);
								}
							}
						}
						List<MenuModel> menuslist=menuManager.sortAndTransformToInternational(menus);
						session.setAttribute("menu", menuslist);
						
						//==============写入日志 start=============
						SysmanageLog log=new SysmanageLog();
						log.setUserId(manager.getId());
						log.setUsername(manager.getUsername());
						log.setOrgId(organization.getId());
						Object[] objects={manager.getUsername(),CommUtil.getIp(request)};
						log.setUserType(AuthStatus.SOFTTEK_DPT_MANAGER.toString());
						log.setOperateType(messageSource.getMessage("security.authenticationsuccess.onauthenticationsuccess.log.operatetype", null, LocaleContextHolder.getLocale()));
						log.setCreateUser(manager.getId());
						log.setOperateContent(messageSource.getMessage("security.authenticationheadsuccess.onauthenticationsuccess.depart.log.operatecontent",objects, LocaleContextHolder.getLocale()));
						log.setCreateDate(new Date());
						log.setUpdateDate(new Date());
						log.setOperateTime(new Date());
						taskExecutor.execute(new LogThread(null, null, log));
						//==============写入日志 end=============
					}
				}
			}
			return;
		}
		//普通用户
		UserModel user=userService.findUser(userDetails.getUsername().trim(),orgId);
		if(user!=null){
			UserModel u=new UserModel();
			u.setId(user.getId());
			u.setLogin_count(0);
			u.setLast_ip(CommUtil.getIp(request));
			userService.update(u);
			
			//将用户的权限和其他一些信息，存入session中
			OrganizationModel organization=organizationService.findOne(user.getOrganization().getId());
			request.getSession().setAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString(), organization);
			request.getSession().setAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString(), user);
			
			//写入日志
			SysmanageLog log=new SysmanageLog();
			log.setUserId(user.getId());
			log.setUsername(user.getUsername());
			log.setOrgId(organization.getId());
			Object[] objects={user.getUsername(),CommUtil.getIp(request)};
			log.setUserType(AuthStatus.SOFTTEK_CUSTOMER.toString());
			log.setOperateType(messageSource.getMessage("security.authenticationheadsuccess.onauthenticationsuccess.user.log.operatetype", null, LocaleContextHolder.getLocale()));
			log.setOperateContent(messageSource.getMessage("security.authenticationheadsuccess.onauthenticationsuccess.user.log.operatecontent",objects, LocaleContextHolder.getLocale()));
			log.setCreateDate(new Date());
			log.setUpdateDate(new Date());
			log.setOperateTime(new Date());
			sysmanageLogService.insertSelective(log);
		}else{
			return;
		}
	}
}
