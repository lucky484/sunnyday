/**
 * Project Name:F2B
 * File Name:OrderScheduled.java
 * Package Name:com.f2b.security.business.biz.impl
 * Date:Aug 19, 201610:33:15 AM
 * Copyright (c) 2016,Curry.Su@softtek.com All Rights Reserved
 *
 */

package com.f2b.security.business.biz.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b.security.business.dao.OrderScheduledDao;

/**
 * Description: 类描述 date: Aug 19, 2016 10:33:15 AM
 *
 * @author jacob.shen
 * @version 1.0
 * @since JDK 1.8.0
 */
@Service("orderScheduled")
public class OrderScheduled {
	private Logger logger = Logger.getLogger(OrderScheduled.class);

	@Autowired
	private OrderScheduledDao orderScheduledDao;

	/**
	 * 
	 * Description:更新当日之前的所有未发货的订单
	 *
	 * @author curry.su
	 */
	public void updateStatus() {
		Date date = new Date();
		try {
			int count = orderScheduledDao.updateStatus(date);
			logger.info(new SimpleDateFormat("yyyy-MM-dd").format(date) + "已有【" + count + "】笔订单发货");
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
