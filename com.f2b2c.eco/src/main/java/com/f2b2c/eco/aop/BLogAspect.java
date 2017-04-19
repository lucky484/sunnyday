package com.f2b2c.eco.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.f2b2c.eco.annotation.BLogAnnotation;
import com.f2b2c.eco.model.common.OperateLogModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.market.BUserOperateLogService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.CommonUtil;

public class BLogAspect {
    
	@Autowired
	private BUserOperateLogService bUserOperateLogService;
	
	/**
	 * 标注该方法体为环绕通知，当目标方法执行时执行该方法体
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	public Object doAroundMethodLog(ProceedingJoinPoint jp) throws Throwable{
		Object retVal = jp.proceed();  
		insertOpertLog(jp);
		return retVal;  
	}
	
	public void insertOpertLog(ProceedingJoinPoint jp) throws NoSuchMethodException, SecurityException, ClassNotFoundException{
		
		OperateLogModel operateLog = new OperateLogModel();
		String temp = jp.getStaticPart().toShortString();  
        String longTemp = jp.getStaticPart().toLongString();  
        jp.getStaticPart().toString();  
        String classType = jp.getTarget().getClass().getName();  
        String methodName = temp.substring(10, temp.length() - 1);  
        Class<?> className = Class.forName(classType);  
        // 日志动作  
        Class[] args = new Class[jp.getArgs().length];  
        String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(") + 1,  
                longTemp.length() - 2)).split(",");  
        for (int i = 0; i < args.length; i++) {  
            if (sArgs[i].endsWith("String[]")) {  
                args[i] = Array.newInstance(Class.forName("java.lang.String"),  
                        1).getClass();  
            } else if (sArgs[i].endsWith("Long[]")) {  
                args[i] = Array.newInstance(Class.forName("java.lang.Long"), 1)  
                        .getClass();  
            } else if (sArgs[i].indexOf(".") == -1) {  
                if (sArgs[i].equals("int")) {  
                    args[i] = int.class;  
                } else if (sArgs[i].equals("char")) {  
                    args[i] = char.class;  
                } else if (sArgs[i].equals("float")) {  
                    args[i] = float.class;  
                } else if (sArgs[i].equals("long")) {  
                    args[i] = long.class;  
                }  
            } else {  
                args[i] = Class.forName(sArgs[i]);  
            }  
        }  
        Method method = className.getMethod(  
                methodName.substring(methodName.indexOf(".") + 1,  
                        methodName.indexOf("(")), args);  
        BLogAnnotation log = null;
		if(method.isAnnotationPresent(BLogAnnotation.class)){
			log = method.getAnnotation(BLogAnnotation.class);
		}
		//当操作内容不为空的时候，记录日志
		if(log != null){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			FUserModel fUser = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
			operateLog.setId(CommonUtil.generate32UUID());
			operateLog.setUserId(fUser.getId());
			operateLog.setUserName(fUser.getRealName());
			operateLog.setOperateType(log.operateType());
			operateLog.setOperateContent("用户 "+fUser.getRealName()+"在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + log.operateContent());
			operateLog.setOperateTime(new Date());
			operateLog.setCreatedUser(fUser.getId());
			operateLog.setCreatedTime(new Date());
			operateLog.setUpdatedUser(fUser.getId());
			operateLog.setUpdatedTime(new Date());
			bUserOperateLogService.insertBUserOperateLog(operateLog);
		}
	}
}
