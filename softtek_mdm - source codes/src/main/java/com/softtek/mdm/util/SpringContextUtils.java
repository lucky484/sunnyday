package com.softtek.mdm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring bean获取工具类
 * date: Apr 19, 2016 11:16:01 AM
 *
 * @author brave.chen
 */
public class SpringContextUtils implements ApplicationContextAware
{

	/**
	 * Spring 上下文应用对象
	 */
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		SpringContextUtils.applicationContext = applicationContext;  
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	/**
	 * 根据bean名称获取bean对象
	 *
	 * @author brave.chen
	 * @param name bean的id名称
	 * @return id对象的bean对象
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException
	{
		return applicationContext.getBean(name);
	}

}
