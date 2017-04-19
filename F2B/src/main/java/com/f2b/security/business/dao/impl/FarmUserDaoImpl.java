package com.f2b.security.business.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.dao.FarmUserDao;
import com.f2b.security.domain.FarmOrder;
import com.f2b.security.domain.FarmUser;

@Repository
public class FarmUserDaoImpl implements FarmUserDao {

	private final static Logger logger = LoggerFactory.getLogger(FarmUserDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	/**
	 * 用户关注
	 * 
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	public Integer add(FarmUser user) {
		user.setCreateDate(new Date());
		user.setStatus("1");
		this.em.persist(user);
		logger.info("用户关注，发放抵用卷！");
		return 1;
	}

	/**
	 * 使用了抵用券
	 * 
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	public Integer update(FarmUser user) {
		FarmUser exist = em.find(FarmUser.class, user.getId());
		exist.setOpenId(user.getOpenId());
		exist.setStatus(user.getStatus());
		exist.setUpdateDate(new Date());
		logger.info("该用户已使用了抵用券！");
		return 1;
	}

	/**
	 * 判断是否还有抵用券
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public FarmUser getById(String openId) {
		TypedQuery<FarmUser> typedQuery = em
				.createQuery("select r from FarmUser r where openId=?1 and r.status =1", FarmUser.class)
				.setParameter(1, openId);
		List<FarmUser> list = typedQuery.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据openid查询抵用券
	 */
	@Override
	public List<FarmUser> getByOpenId(String openId) {
		TypedQuery<FarmUser> typedQuery = em
				.createQuery("select r from FarmUser r where r.openId=?1 order by createDate desc", FarmUser.class)
				.setParameter(1, openId);
		List<FarmUser> list = typedQuery.getResultList();
		return list;
	}

}
