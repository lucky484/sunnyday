package com.f2b.security.business.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.dao.RecordDao;
import com.f2b.security.domain.Record;
import com.f2b.sugar.data.QueryUtils;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.tools.StringConverters;

/**
 * Created by Administrator on 2016/3/24.
 */
@Repository("RecordDao")
public class RecordDaoImpl implements RecordDao {

	private final static Logger logger = LoggerFactory.getLogger(RecordDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	public Map<String, Object> getSearchCondition(Map<String, String> queryHash) {
		Map<String, Object> conditionHash = new HashMap<>();
		if (queryHash == null) {
			return conditionHash;
		}
		String openId = queryHash.get("openid");
		if (!StringUtils.isEmpty(openId)) {
			conditionHash.put("openId = ?{paramIndex} ", openId);
		}

		String award = queryHash.get("award");
		if (!StringUtils.isEmpty(award)) {
			conditionHash.put("award = ?{paramIndex} ", award);
		}

		String orderNo = queryHash.get("orderNo");
		if (!StringUtils.isEmpty(orderNo)) {
			conditionHash.put("orderNo = ?{paramIndex} ", orderNo);
		}

		String lotteryDate1 = queryHash.get("lotteryDate");
		if (!StringUtils.isEmpty(lotteryDate1)) {
			Date lotteryDate = StringConverters.ToDate(queryHash.get("lotteryDate"));
			conditionHash.put("lotteryDate >= ?{paramIndex} ", DateTimeUtils.getBeginOfDay(lotteryDate));
			conditionHash.put("lotteryDate <= ?{paramIndex} ", DateTimeUtils.getEndOfDay(lotteryDate));
		}

		/*
		 * String String = queryHash.get("String"); if
		 * (!StringUtils.isEmpty(String)) { conditionHash.put(
		 * "String like ?{paramIndex} ", "%" + String + "%"); } Integer Integer
		 * = TypeConvertUtils.StringToInteger(queryHash.get("Integer")); if
		 * (Integer != null && Integer > -1) { conditionHash.put(
		 * "Integer = ?{paramIndex} ", Integer); } Date Date =
		 * TypeConvertUtils.StringToDate(queryHash.get("Date")); if (Date !=
		 * null) { conditionHash.put("Date >= ?{paramIndex} ", Date); }
		 */

		return conditionHash;
	}

	@Override
	public List<Record> findRecord() {
		TypedQuery<Record> typedQuery = entityManager.createQuery("select r from Record r where r.award <> :award and r.nickname <> :nickname order by r.recordId desc ", Record.class)
				.setParameter("award", "0").setParameter("nickname", "");
		typedQuery.setFirstResult(0).setMaxResults(20);
		return typedQuery.getResultList();
	}

	@Override
	public List<Record> findRecord(String openid) {
		TypedQuery<Record> typedQuery = entityManager.createQuery("select r from Record r where r.openId = :openId and r.award <> :award order by r.recordId desc ", Record.class)
				.setParameter("openId", openid).setParameter("award", "0");
		return typedQuery.getResultList();
	}

	@Override
	public Long totalRecord(Map<String, String> queryHash) {
		Map<String, Object> conditions = getSearchCondition(queryHash);
		TypedQuery<Long> typedQuery = QueryUtils.getTypedQueryByCondition("select count(r) from Record r ", conditions, "", entityManager, Long.class);
		return typedQuery.getSingleResult();
	}

	@Override
	public List<Record> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {
		if (StringUtils.isEmpty(sqlOrder)) {
			sqlOrder = "order by r.recordId desc ";
		}

		Map<String, Object> conditions = getSearchCondition(queryHash);
		TypedQuery<Record> typedQuery = QueryUtils.getTypedQueryByCondition("select r from Record r ", conditions, sqlOrder, entityManager, Record.class);
		// 判断是否需要分页，并提交分页方法
		if (pageSize > 0 && pageNow > 0) {
			logger.debug("提交了分页查询信息，pageNow为[" + pageNow + "]，pageSize为[" + pageSize + "]");
			int minLimit = pageSize * (pageNow - 1);
			int maxLimit = pageSize;
			typedQuery.setFirstResult(minLimit).setMaxResults(maxLimit);
		}
		// 返回查询结果
		return typedQuery.getResultList();
	}

	@Override
	public Record findModel(Long recordId) {
		return entityManager.find(Record.class, recordId);
	}

	@Override
	@Transactional
	public Integer add(Record model) {
		entityManager.persist(model);
		return 1;
	}

	@Override
	@Transactional
	public Integer update(Record model) {
		Record record = entityManager.find(Record.class, model.getRecordId());
		record.setAward(model.getAward());
		record.setLotteryDate(model.getLotteryDate());
		record.setOpenId(model.getOpenId());
		record.setDrawStatus(model.getDrawStatus());
		record.setNickname(model.getNickname());
		record.setIp(model.getIp());
		return null;
	}

	@Override
	@Transactional
	public Integer delete(Long recordId) {
		Record existStore = entityManager.find(Record.class, recordId);
		try {
			entityManager.remove(existStore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public List<Record> findByOpenId(String openId, List<String> orderNos) {
		String jpql = "select r from Record r where r.openId=:openId and r.orderNo in :orderNos";
		TypedQuery<Record> typedQuery = entityManager.createQuery(jpql, Record.class);
		typedQuery.setParameter("openId", openId);
		typedQuery.setParameter("orderNos", orderNos);
		return typedQuery.getResultList();
	}

	public Long findRecordNumByOrderNo(String orderNo) {
		String jpql = "select count(*) from Record r where r.orderNo=:orderNo";
		TypedQuery<Long> typedQuery = entityManager.createQuery(jpql, Long.class);
		typedQuery.setParameter("orderNo", orderNo);
		return typedQuery.getSingleResult();
	}
}
