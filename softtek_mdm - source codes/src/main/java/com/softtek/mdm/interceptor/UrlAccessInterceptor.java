package com.softtek.mdm.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MenuModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.SecurityEventLogModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.SecurityEventLogService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.service.UserDepartmentService;
import com.softtek.mdm.service.UserRoleDepartmentService;
import com.softtek.mdm.status.AuthStatus;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.TreeManager;

import jodd.util.StringUtil;

/**
 * 用于处理url权限访问控制
 * @author color.wu
 *
 */
public class UrlAccessInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private StructureService structureService;
	@Autowired
	private UserRoleDepartmentService userRoleDepartmentService;
	@Autowired
	private UserDepartmentService userDepartmentService;
	@Autowired
	private SecurityEventLogService securityEventLogService;
	@Autowired
	private MessageSource messageSource;
	
	@SuppressWarnings("unchecked")
	private void InitTreeSession(HttpSession session){
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		OrganizationModel organizaiton=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		if(manager!=null&&organizaiton!=null&&manager.getUser_type()==Integer.parseInt(AuthStatus.SOFTTEK_MANAGER.toString())){
			List<StructureModel> list=(List<StructureModel>) structureService.findAllByOrgID(organizaiton.getId());
			//判断管理员是否被锁住或者未激活
			if(manager.getUser()==null){
				session.setAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString(), list);
			}else{
				UserRoleDepartmentModel uRD=userRoleDepartmentService.findOneByMaps(organizaiton.getId(), manager.getUser().getId());
				if(uRD==null){
					return;
				}
				List<UserDepartmentModel> uRDlist=userDepartmentService.findByFId(uRD.getId());
				List<Integer> ids=(List<Integer>) CollectionUtils.collect(uRDlist, new Transformer() {
					@Override
					public Object transform(Object input) {
						if(input instanceof UserDepartmentModel){
							UserDepartmentModel urd=(UserDepartmentModel) input;
							return urd.getStructure().getId();
						}
						return null;
					}
				});
				List<StructureModel> currentList=structureService.findByIds(ids);
				TreeManager tree=new TreeManager();
				List<StructureModel> sessionDepartmentList=tree.onlyGenerateList(list, currentList);
				session.setAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString(), sessionDepartmentList);
			}
		}
	}
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LocaleContextHolder.setLocale(request.getLocale());
		this.InitTreeSession(request.getSession());
		String uri=request.getRequestURI();
		//如果访问的是管理内容
		ManagerModel manager=(ManagerModel) request.getSession().getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		if(manager!=null&&manager.getUser()==null){
			//机构管理员访问普通用户页面
			if(uri.indexOf("/customer")>0){
				SecurityContextHolder.clearContext();
				request.getRequestDispatcher("/").forward(request, response);
				return true;
			}
		}
		String str="institution";
		if(StringUtil.indexOf(uri, str, 0, uri.length())>0){
			uri=uri.substring(uri.indexOf(str)+str.length());
			if(manager!=null&&manager.getUser()!=null){
				if(uri.indexOf("/tocustomer")==0){
					return true;
				}
			}
			if(uri.length()>0){
				HttpSession session=request.getSession();
				@SuppressWarnings("unchecked")
				List<MenuModel> menus=(List<MenuModel>) session.getAttribute("menu");
				if(menus!=null&&menus.size()>0){
					boolean flag=false;
					boolean type=false;
					for (MenuModel menu : menus) {
						if(menu.getAddress()!=null){
							if(uri.indexOf(menu.getAddress().trim())==0||uri.indexOf("/personal")==0){
								if(menu.getIsshow().equals(-1)){
									flag=true;//允许访问
									break;
								}else{
									type=true;
								}
							}
						}
					}
					if(flag==false){
						SecurityEventLogModel securityEventLog=new SecurityEventLogModel();
						securityEventLog.setEventType("3");
						securityEventLog.setLevel("1");
						Object[] objects={manager.getName(),CommUtil.getIp(request),DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),request.getRequestURI()};
						String operateContent=messageSource.getMessage("security.urlzccesstnterceptor.unauth", objects, LocaleContextHolder.getLocale());
						securityEventLog.setOperateContent(operateContent);
						securityEventLog.setCreateTime(new Date());
						securityEventLog.setUpdateTime(new Date());
						securityEventLog.setCreateBy(manager.getId());
						securityEventLog.setUpdateBy(manager.getId());
						securityEventLogService.insertSecurityEventLog(securityEventLog);
						if(type){
							
							response.sendRedirect(request.getContextPath()+"/error?type=401");
						}else{
							response.sendRedirect(request.getContextPath()+"/error?type=404");
						}
						return true;
					}
				}
			}
			return true;
		}else {
			return true;
		}
		
	}

}
