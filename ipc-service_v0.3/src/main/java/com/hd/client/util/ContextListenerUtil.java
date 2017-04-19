package com.hd.client.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hd.client.job.PushFaceAndIDCardJob;

/**
 * 监听容器的关闭， 然后释放一些占用的资源
 * 
 * @author ligang.yang
 *
 */
public class ContextListenerUtil implements ServletContextListener {

	/**
	 * 关闭线程池， 释放资源
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (PushFaceAndIDCardJob.pool != null)
			PushFaceAndIDCardJob.pool.shutdownNow();
		if (IDCardUtil.serviceReader != null)
			IDCardUtil.serviceReader.shutdownNow();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

	}

}
