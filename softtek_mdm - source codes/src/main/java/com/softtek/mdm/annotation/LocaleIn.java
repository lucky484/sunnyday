package com.softtek.mdm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 语言注解，只能用在参数上
 * @author color.wu
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LocaleIn {

	/**
	 * 给参数加入语言代码名称
	 * @return
	 */
	String value() default "Accept-Language";
	/**
	 * 语言代码
	 * @return
	 */
	String language() default "zh";
	/**
	 * 国家代码
	 * @return
	 */
	String country() default "CN";
}
