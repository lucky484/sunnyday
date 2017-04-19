package com.f2b.sugar.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ServletContextAware;

import com.f2b.security.business.biz.AdminUserBiz;
import com.f2b.security.domain.AdminUser;

import javax.servlet.ServletContext;

/**
 * Author: 居泽平  Date: 14/12/20, 21:43
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent>, ServletContextAware {

	private final static Logger logger = LoggerFactory.getLogger(InstantiationTracingBeanPostProcessor.class);

	private ServletContext servletContext;

	@Autowired
	private AdminUserBiz adminUserBiz;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		logger.info("我在刚启动时执行-1");

		//以下这段代码在这里是失效的，可用于business中获取当前的Session
		//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		//这里的『Parent』是否执行，与『web.xml』中的配置有关系
		if (event.getApplicationContext().getParent() == null) {
			//root application context 没有parent，他就是老大.
			//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。

			//设定一个名为Application的变量
			servletContext.setAttribute("test", "test");
			logger.info("我在刚启动时执行-2");
		}

		//开始用户
		AdminUser adminUser = new AdminUser();
		adminUser.setLoginName("admin");
		adminUser.setLoginPassword("admin");
		adminUser.setRealName("超级管理员");
		adminUser.setEmail("if404@vt1314.com");
		if (adminUserBiz.totalRecord() < 1) {
			adminUserBiz.addOrUpdate(adminUser);
		}

		logger.info("我在刚启动时执行-3");
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("我在刚启动时执行-setServletContext");
		this.servletContext = servletContext;
	}
}
