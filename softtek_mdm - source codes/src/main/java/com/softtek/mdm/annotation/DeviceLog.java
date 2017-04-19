package com.softtek.mdm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设备日志注解
 * @author jane.hui
 *
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeviceLog {
	
	 /**
	  * 操作事件内容
	  * @return
	  */
	 String operateContent();
	 
	 /**
	  * 事件类型
	  * @return
	  */
	 String eventType();
	 
	 /**
	  * 设备名称
	  */
	 String deviceId();
	
	 /**
	  * 用户id
	  * @return
	  */
	 String userId();
	 
	 /**
	  * 操作内容参数
	  * @return
	  */
	 String[] args() default "";
}
