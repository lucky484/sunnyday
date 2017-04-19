package com.softtek.mdm.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.softtek.mdm.annotation.DeviceLog;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;

import jodd.util.StringUtil;

/**
 * 设备日志拦截器
 * @author jane.hui
 *
 */
public class DeviceLogInterceptor  extends HandlerInterceptorAdapter {

	@Autowired
	private SysmanageDeviceLogService sysmanageDeviceLogService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private UserDao userDao; 

	/**
	 * 后置拦截器
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Annotation[] declaredAnnotations = getDeclaredAnnotationsForHandler(handler);
		if(declaredAnnotations!=null){
			for (Annotation annotation : declaredAnnotations) {
				if (annotation.annotationType().equals(DeviceLog.class)) {
					DeviceLog log=(DeviceLog) annotation;
					if(log!=null){
						// 事件类型
                        String eventType = request.getParameter(log.eventType());
                       if(StringUtil.isBlank(eventType)){
                        	Object objEventType = request.getAttribute(log.eventType());
                        	if(objEventType!=null&&!Constant.EMPTY_STR.equals(objEventType)){
                        		eventType = String.valueOf(objEventType);
                        	}
                        }
                        // 设备名称
                        String strLogDeviceId = String.valueOf(log.deviceId());
                        String deviceId = request.getParameter(strLogDeviceId);
                        Integer intDeviceId = 0;
                        if(StringUtil.isBlank(deviceId)) {
                        	Object objDeviceId = request.getAttribute(strLogDeviceId);
                        	if(objDeviceId!=null&&!Constant.EMPTY_STR.equals(objDeviceId)){
                        		intDeviceId = Integer.valueOf(objDeviceId.toString());
                            }
                        }
                        // 用户id
                        String userId = request.getParameter(log.userId());
                        if(StringUtil.isBlank(userId)) {
                        	Object objUserId = request.getAttribute(log.userId());
                        	if(objUserId!=null&&!Constant.EMPTY_STR.equals(objUserId)){
                        		userId = String.valueOf(objUserId);
                        	}
                        }
                        
                        //机构id
                        Integer orgId = (Integer) request.getAttribute("orgId");
                        // 动态参数
						String[] reqArgs = log.args();
						String[] args = new String[reqArgs.length];
						String name = "";
						Object objName = null;
						String deviceName = "";
						for (int i=0;i<reqArgs.length;i++){
							name = request.getParameter(reqArgs[i]);
							if(StringUtil.isNotBlank(name)){
								args[i]= name;
							} else {
								objName = request.getAttribute(reqArgs[i]);
								if(objName!=null&&!Constant.EMPTY_STR.equals(objName)){
									name = String.valueOf(objName);
									args[i] = name;
								}
							}
							if("deviceName".equals(reqArgs[i])){
								deviceName =  (String) request.getAttribute(reqArgs[i]);
							}
						}
						// 操作内容国际化
						String operateContent = messageSource.getMessage(log.operateContent(), args,LocaleContextHolder.getLocale());
						SysmanageDeviceLog deviceLog = new SysmanageDeviceLog();
						deviceLog.setId(CommUtil.getPrimaryKey());
					    deviceLog.setEventType(eventType);
					    deviceLog.setDeviceId(intDeviceId);
						if(StringUtil.isNotBlank(userId)){
							Integer intuserId = Integer.valueOf(userId);
							deviceLog.setUserId(intuserId);
							UserModel user = userDao.findOne(intuserId);
							deviceLog.setUserName(user.getRealname());
						}
						deviceLog.setCreateUser(Integer.valueOf(userId));
						deviceLog.setUpdateUser(Integer.valueOf(userId));
						deviceLog.setOperateContent(operateContent);
						deviceLog.setDeviceName(deviceName);
						deviceLog.setOrgId(Integer.valueOf(orgId));
						deviceLog.setOperateDate(new Date());
						deviceLog.setCreateDate(new Date());
						deviceLog.setUpdateDate(new Date());
						sysmanageDeviceLogService.insertSelective(deviceLog);
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
