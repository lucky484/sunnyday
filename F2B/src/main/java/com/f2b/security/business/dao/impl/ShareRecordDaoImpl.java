/**
* @ClassName: AccessTokenDaoImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Jacob Shen
* @date Aug 22, 2016 3:37:36 PM
*/
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

import com.f2b.security.business.dao.ShareRecordDao;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.data.QueryUtils;
import com.f2b.sugar.func.email.Email;

/**
 * @author jacob.shen
 *
 */
@Repository("shareRecordDao")
public class ShareRecordDaoImpl implements ShareRecordDao {

	private final static Logger logger = LoggerFactory.getLogger(ShareRecordDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	public Map<String, Object> getSearchCondition(Map<String, String> queryHash) {
		Map<String, Object> conditionHash = new HashMap<>();
		if (queryHash == null) {
			return conditionHash;
		}

		String awards = queryHash.get("awards");
		if (!org.apache.commons.lang.StringUtils.isEmpty(awards)) {
			conditionHash.put("awards = ?{paramIndex} ", awards);
		}

		return conditionHash;
	}

	@Override
	public List<ShareRecord> get(String openId, String shareOpenId) {
		TypedQuery<ShareRecord> typedQuery = entityManager.createQuery("select r from ShareRecord r where openId=?1 and shareOpenId=?2 order by r.createDate desc", ShareRecord.class)
				.setParameter(1, openId).setParameter(2, shareOpenId);
		List<ShareRecord> list = typedQuery.getResultList();
		return list;
	}

	@Override
	@Transactional
	public ShareRecord findModel(Long id) {
		return entityManager.find(ShareRecord.class, id);
	}

	@Override
	@Transactional
	public Integer add(ShareRecord model) {
		entityManager.persist(model);
		logger.info("添加分享记录成功");
		return 1;
	}

	@Override
	@Transactional
	public Integer update(ShareRecord model) {
		ShareRecord shareRecord = entityManager.find(ShareRecord.class, model.getShareRecordId());
		shareRecord.setOpenId(model.getOpenId());
		shareRecord.setNickname(model.getNickname());
		shareRecord.setSendRedPack(model.getSendRedPack());
		shareRecord.setNumber(model.getNumber());
		shareRecord.setUpdateDate(new Date());
		shareRecord.setShareOpenId(model.getShareOpenId());
		shareRecord.setShareNickName(model.getShareNickName());
		logger.info("修改分享记录成功");
		return 1;
	}

	@Override
	@Transactional
	public Integer delete(Long id) {
		ShareRecord existStore = entityManager.find(ShareRecord.class, id);
		try {
			entityManager.remove(existStore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Long totalRecord(Map<String, String> queryHash) {
    	Map<String, Object> conditions = getSearchCondition(queryHash);
        TypedQuery<Long> typedQuery = QueryUtils.getTypedQueryByCondition("select count(r) from ShareRecord r ", conditions, "", entityManager, Long.class);
        return typedQuery.getSingleResult();
	}

	@Override
	public List<ShareRecord> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {
		   if (StringUtils.isEmpty(sqlOrder)) {
	            sqlOrder = "order by r.shareRecordId desc ";
	        }

	        Map<String, Object> conditions = getSearchCondition(queryHash);
	        TypedQuery<ShareRecord> typedQuery = QueryUtils.getTypedQueryByCondition("select r from ShareRecord r ", conditions, sqlOrder, entityManager, ShareRecord.class);
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
	public List<ShareRecord> get(String openId) {
		TypedQuery<ShareRecord> typedQuery = entityManager.createQuery("select r from ShareRecord r where openId=?1 order by r.createDate desc", ShareRecord.class)
				.setParameter(1, openId);
		List<ShareRecord> list = typedQuery.getResultList();
		return list;
	}
	
	public List<ShareRecord> getMyShare(String shareOpenId) {
		TypedQuery<ShareRecord> typedQuery = entityManager.createQuery("select r from ShareRecord r where shareOpenId=?1 order by r.createDate desc", ShareRecord.class)
				.setParameter(1, shareOpenId);
		List<ShareRecord> list = typedQuery.getResultList();
		return list;
	}

	/**
	 * 导出线下支付
	 */
	@Override
	public List<ShareRecord> getShareRecordExcl1() {
		TypedQuery<ShareRecord> typedQuery = entityManager.createQuery(
				"select r from ShareRecord r where shareNickName is null and shareOpenId is not null order by r.createDate desc",
				ShareRecord.class);
		List<ShareRecord> list = typedQuery.getResultList();
		return list;
	}

	/**
	 * 导出全部正常分享的
	 */
	@Override
	public List<ShareRecord> getShareRecordExcl() {
		TypedQuery<ShareRecord> typedQuery = entityManager.createQuery(
				"select r from ShareRecord r where shareNickName is not null and shareOpenId is not null order by r.createDate desc",
				ShareRecord.class);
		List<ShareRecord> list = typedQuery.getResultList();
		return list;
	}

}
