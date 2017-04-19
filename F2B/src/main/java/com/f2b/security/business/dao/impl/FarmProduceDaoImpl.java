package com.f2b.security.business.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.f2b.security.business.dao.FarmProduceDao;
import com.f2b.security.domain.FarmProduce;

@Repository
public class FarmProduceDaoImpl implements FarmProduceDao{
	private final static Logger logger = LoggerFactory.getLogger(FarmProduceDaoImpl.class);

	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 商品列表页
	 * @param request
	 * @return
	 */
	@Override
	public List<FarmProduce> getProduceList(FarmProduce model) {
		TypedQuery<FarmProduce> typedQuery = em.createQuery("from FarmProduce p order by p.creatDate desc", FarmProduce.class);
		List<FarmProduce> list = typedQuery.getResultList();
		return list;
	}

	/**
	 * 跳转商品详情页
	 * @param produceId
	 * @return
	 */
	public List<FarmProduce> getFarmProducePage(String produceId) {
		TypedQuery<FarmProduce> typedQuery = em.createQuery("from FarmProduce p where p.produceId =?0", FarmProduce.class).setParameter(0, produceId);
		List<FarmProduce> list = typedQuery.getResultList();
		return list;
	}
}
