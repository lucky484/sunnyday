package com.f2b2c.eco.job;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.f2b2c.eco.service.market.BSalesOrderService;
import com.f2b2c.eco.util.DateUtil;
import com.f2b2c.eco.util.SpringContextUtils;

/**
 * tomcat定时任务
 * @author jing.liu
 *
 */
public class TimerTaskManager implements ServletContextListener{
    
	private Timer timer;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("定时任务执行结束..............");
//		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		BSalesOrderService bSalesOrderService = (BSalesOrderService) SpringContextUtils.getBean("BSalesOrderServiceImpl");
		OrderTask task = new OrderTask(bSalesOrderService);
		DealUnReceiveOrderTask task1 = new DealUnReceiveOrderTask(bSalesOrderService);
		timer = new Timer();
		System.out.println("定时任务开始...........");
		System.out.println(DateUtil.getyyyyMMddhhmmss());
		timer.schedule(task,0,450000);  //每隔7分30秒执行一次定时任务
		timer.schedule(task1,0,450000);  //每隔7分30秒执行一次定时任务
	}

}
