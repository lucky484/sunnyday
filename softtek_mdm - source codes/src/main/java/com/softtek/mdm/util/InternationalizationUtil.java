package com.softtek.mdm.util;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

/**
 * 国际化公共类
 * date: Apr 19, 2016 11:05:34 AM
 *
 * @author brave.chen
 */
public class InternationalizationUtil
{
	/**
	 * 国际化信息资源实例
	 */
	private static MessageSource messageSource = null;
	
	/**
	 * 默认加载类的时候一次获取该bean
	 */
	static
	{
		messageSource = (MessageSource) SpringContextUtils.getBean("messageSource");
	}
	
	public static String getMessage(MessageSourceResolvable resolvable, Locale locale)
	{
		return messageSource.getMessage(resolvable, locale);
	}
	
	
	public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale)
	{
		if (StringUtils.isEmpty(defaultMessage))
		{
			return messageSource.getMessage(code, args, locale);
		}
		else
		{
			return messageSource.getMessage(code, args, defaultMessage, locale);
		}
	}
}

