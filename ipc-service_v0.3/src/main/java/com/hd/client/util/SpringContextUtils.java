package com.hd.client.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
* @ClassName: SpringContextUtils 
* @Description: TODO 
* @author kaishen
* @date Nov 18, 2015 12:20:58 PM 
*
 */

	public class SpringContextUtils implements ApplicationContextAware {  
	  
	    // Spring  ApplicationContext
	    private static ApplicationContext applicationContext;  
	  
	    /**
	     *  
	     */
	    public void setApplicationContext(ApplicationContext applicationContext) {  
	        SpringContextUtils.applicationContext = applicationContext;  
	    }  
	  
	    /** 
	     * @return ApplicationContext 
	     */  
	    public static ApplicationContext getApplicationContext() {  
	        return applicationContext;  
	    }  
	  
	    /**
	     * 
	    * @Title: getBean 
	    * @Description: TODO
	    * @param @param name
	    * @param @return
	    * @param @throws BeansException  
	    * @return Object  
	    * @throws
	     */
	    public static Object getBean(String name) throws BeansException {  
	        return applicationContext.getBean(name);  
	    } 
	}
  

