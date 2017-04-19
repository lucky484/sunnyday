package com.softtek.mdm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @author jane.hui
 *
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	 
	 /**
	  * 操作内容
	  * @return
	  */
	 String operateContent();
	 
	 /**
	  * 操作类型
	  * @return
	  */
	 String operateType();
	 
	 /**
	  * 操作内容参数
	  * @return
	  */
	 String[] args() default "";
}
