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

import com.softtek.mdm.model.LicenseInfoModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MenuModel;
import com.softtek.mdm.model.OrgManagerRelationModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.SysmanageLog;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.service.MenuService;
import com.softtek.mdm.service.OrgManagerRelationService;
import com.softtek.mdm.service.OrganizationService;
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
public class AuthenticationSuccess extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ManagerService managerService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private TreeManager treeManager;
	@Autowired
	private MenuManager menuManager;
	@Resource(name="messageSourceService")
	private MessageSource messageSource;
	@Autowired
	private OrgManagerRelationService orgManagerRelationService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private TaskExecutor taskExecutor;
	
	/**
	 * 如果用户名密码认证认证成功
	 * 机构管理员登录界面
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		super.onAuthenticationSuccess(request, response, authentication);
		LocaleContextHolder.setLocale(request.getLocale());
		UserDetails userDetails= (UserDetails) SecurityContextHolder
		.getContext().getAuthentication().getPrincipal();
		
		LocaleContextHolder.setLocale(request.getLocale());
		//再次验证查询是否是构管理员
		ManagerModel manager=managerService.findOneInstitution(userDetails.getUsername());
		
		ManagerModel mag=new ManagerModel();
		mag.setId(manager.getId());
		mag.setLogin_count(0);
		mag.setLast_login_time(new Date());
		managerService.update(mag);
		
		if(manager!=null){
			//将机构的权限和其他一些信息，存入session中
			request.getSession().setAttribute(SessionStatus.SOFTTEK_MANAGER.toString(), manager);
			request.getSession().setAttribute("aside","0");
			
			Set<String> privilege=null;
			if(authentication!=null){
				privilege=AuthorityUtils.authorityListToSet(authentication.getAuthorities());
			}
			if(privilege.contains(AuthStatus.AUTH_ADMIN.toString())){
				//判断license是否为激活可用
				LicenseInfoModel license=licenseService.queryLicenseInfo();
				if(null!=license){
					request.getSession().setAttribute("islicense", true);
				}
				//写入日志
				SysmanageLog log=new SysmanageLog();
				log.setUserId(manager.getId());
				log.setUsername(manager.getUsername());
				Object[] objects={manager.getUsername(),CommUtil.getIp(request)};
				log.setUserType(AuthStatus.SOFTTEK_AMDIN.toString());
				log.setOperateType(messageSource.getMessage("security.authenticationsuccess.onauthenticationsuccess.log.operatetype", null, LocaleContextHolder.getLocale()));
				log.setOperateContent(messageSource.getMessage("security.authenticationsuccess.onauthenticationsuccess.log.operatecontent",objects, LocaleContextHolder.getLocale()));
				log.setCreateDate(new Date());
				log.setUpdateDate(new Date());
				log.setOperateTime(new Date());
				taskExecutor.execute(new LogThread(null, null, log));
				return ;
			}
			if(privilege.contains(AuthStatus.AUTH_INSTITUTION.toString())){
				List<OrgManagerRelationModel> orgList=orgManagerRelationService.findRecordsByManagerId(manager.getId());
				OrganizationModel organization=organizationService.findOne(orgList.get(0).getOrganization().getId());
				request.getSession().setAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString(), organization);
				
				HttpSession session=request.getSession();
				treeManager.InitTreeSession(session);
				
				//判断管理员是否被锁住或者未激活
				if(manager.getUser()==null){
					List<MenuModel> menus=menuService.findAllMenus();
					for(int k=0;k<menus.size();k++){
						//-1用来表示显示该菜单可以使用//show（1），hide（0），use（-1）
						menus.get(k).setIsshow(-1);
					}
					List<MenuModel> menuslist=menuManager.sortAndTransformToInternational(menus);
					session.setAttribute("menu", menuslist);
					
					//写入日志
					SysmanageLog log=new SysmanageLog();
					log.setUserId(manager.getId());
					log.setUsername(manager.getUsername());
					log.setOrgId(organization.getId());
					Object[] objects={manager.getUsername(),CommUtil.getIp(request)};
					log.setUserType(AuthStatus.SOFTTEK_INSTITUTION.toString());
					log.setOperateType(messageSource.getMessage("security.authenticationsuccess.onauthenticationsuccess.log.operatetype", null, LocaleContextHolder.getLocale()));
					log.setOperateContent(messageSource.getMessage("security.authenticationsuccess.onauthenticationsuccess.log.operatecontent",objects, LocaleContextHolder.getLocale()));
					log.setCreateDate(new Date());
					log.setUpdateDate(new Date());
					log.setOperateTime(new Date());
					taskExecutor.execute(new LogThread(null, null, log));
				}
			}
		}
	}
}
