/**
 * Project Name:F2B
 * File Name:OrderScheduledDaoImpl.java
 * Package Name:com.f2b.security.business.dao.impl
 * Date:Aug 19, 201611:33:09 AM
 * Copyright (c) 2016,Curry.Su@softtek.com All Rights Reserved
 *
 */

package com.f2b.security.business.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.dao.OrderScheduledDao;

/**
 * Description: 类描述
 * date: Aug 19, 2016 11:33:09 AM
 *
 * @author curry.su
 * @version 1.0
 * @since JDK 1.8.0
 */
@Repository
public class OrderScheduledDaoImpl implements OrderScheduledDao
{
	@PersistenceContext
	private EntityManager em;

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.f2b.security.business.dao.OrderScheduledDao#updateStatus(java.util.Date)
	 */
	@Override
	@Transactional
	public int updateStatus(Date date)
	{

		int result = em
				.createQuery(
						"update FarmOrder set status=?1 where status=?2 and date_format(createDate,'%Y-%m-%d')<=?3")
				.setParameter(1, 0).setParameter(2, -1)
				.setParameter(3, new SimpleDateFormat("yyyy-MM-dd").format(date)).executeUpdate();

		return result;
	}

}
