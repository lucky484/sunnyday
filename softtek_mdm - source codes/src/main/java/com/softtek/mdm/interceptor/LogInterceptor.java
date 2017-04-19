package com.softtek.mdm.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.SysmanageLog;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.SysmanageLogService;
import com.softtek.mdm.status.AuthStatus;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.Constant;
import jodd.util.StringUtil;

/**
 * 日志拦截器
 * @author jane.hui
 *
 */
public class LogInterceptor  extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(LogInterceptor.class);
	
	@Autowired
	private SysmanageLogService sysmanageLogService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * 后置拦截器
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Annotation[] declaredAnnotations = getDeclaredAnnotationsForHandler(handler);
		if(declaredAnnotations!=null){
			for (Annotation annotation : declaredAnnotations) {
				if (annotation.annotationType().equals(Log.class)) {
					try {
						Log log=(Log) annotation;
						if(log!=null){
							HttpSession session = request.getSession();
							// 获取机构
							Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
							// 获取管理员
							Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
							// 普通管理员
							Object objCustomer = session.getAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString());
							OrganizationModel organization = (OrganizationModel)orgObj;
							String[] reqArgs = log.args();
							String[] args = new String[reqArgs.length];
							String name = "";
							Object objName = null;
							for (int i=0;i<reqArgs.length;i++){
								name = request.getParameter(reqArgs[i]);
								if(StringUtil.isNotBlank(name)){
									args[i]= name;
								} else {
									objName = request.getAttribute(reqArgs[i]);
									if(objName!=null&&!Constant.EMPTY_STR.equals(objName)){
									name = String.valueOf(objName);
									args[i]= name;
									}
								}
							}
							String operateType = messageSource.getMessage(log.operateType(), null,LocaleContextHolder.getLocale());
							String operateContent = messageSource.getMessage(log.operateContent(), args,LocaleContextHolder.getLocale());
							SysmanageLog systemLog = new SysmanageLog();
							systemLog.setOperateType(log.operateType());
							systemLog.setOrgId(organization.getId());
							systemLog.setOperateContent(operateContent);
	                        	if(obj!=null){
	                        		ManagerModel manager = (ManagerModel) obj;
	                        		systemLog.setUserId(manager.getId());
	                        		systemLog.setUsername(manager.getUsername());
	                        		if(manager.getUser() != null){
	                        			systemLog.setUserType(AuthStatus.SOFTTEK_DPT_MANAGER.toString());
	                        		}else{
	                        			systemLog.setUserType(AuthStatus.SOFTTEK_INSTITUTION.toString());
	                        		}
	                        		systemLog.setCreateUser(manager.getId());
	                        		systemLog.setUpdateUser(manager.getId());
	                        	} else {
	                        		UserModel userModel = (UserModel)objCustomer;
	                        		systemLog.setUserId(userModel.getId());
	                        		systemLog.setUsername(userModel.getUsername());
	                        		systemLog.setUserType(AuthStatus.SOFTTEK_CUSTOMER.toString());
	                        		systemLog.setCreateUser(userModel.getId());
	                        		systemLog.setUpdateUser(userModel.getId());
	                        	}
							systemLog.setOperateType(operateType);
							systemLog.setOperateContent(operateContent);
							systemLog.setCreateDate(new Date());
							systemLog.setUpdateDate(new Date());
							systemLog.setOperateTime(new Date());
							sysmanageLogService.insertSelective(systemLog);
						}
					} catch(Exception e){
						logger.error("something wrong when add system log,exception message is " + e.getMessage());
					}
				}
			}
		}
	}
	
	private Annotation[] getDeclaredAnnotationsForHandler(Object handler) {
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
			return declaredAnnotations;
		}else{
			return null;
		}
	}
}
