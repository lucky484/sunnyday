package com.softtek.mdm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Token防止重复表单提交
 * @author jane.hui
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

	/**
	 * 保存Token
	 * @return
	 */
	boolean needSaveToken() default false;
	
	/**
	 * 删除Token
	 * @return
	 */
	boolean needRemoveToken() default false;
}
