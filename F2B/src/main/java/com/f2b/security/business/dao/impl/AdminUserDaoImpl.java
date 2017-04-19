package com.f2b.security.business.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.f2b.security.business.dao.AdminUserDao;
import com.f2b.security.domain.AdminUser;
import com.f2b.sugar.data.QueryUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: val.jzp
 */
@Repository("adminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {

	private final static Logger logger = LoggerFactory.getLogger(AdminUserDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	// ******************************************************************************
	// ********************************* CRUD START *********************************
	// ******************************************************************************

	public Map<String, Object> getSearchCondition(Map<String, String> queryHash) {

		Map<String, Object> conditionHash = new HashMap<>();
		if (queryHash == null) {
			return conditionHash;
		}

		String loginName = queryHash.get("loginName");
		if (!StringUtils.isEmpty(loginName)) {
			conditionHash.put("loginName = ?{paramIndex} ", loginName);
		}

		String loginPassword = queryHash.get("loginPassword");
		if (!StringUtils.isEmpty(loginPassword)) {
			conditionHash.put("loginPassword = ?{paramIndex} ", loginPassword);
		}

        /*String String = queryHash.get("String");
		if (!StringUtils.isEmpty(String)) {
			conditionHash.put("String like ?{paramIndex} ", "%" + String + "%");
		}
		Integer Integer = TypeConvertUtils.StringToInteger(queryHash.get("Integer"));
		if (Integer != null && Integer > -1) {
			conditionHash.put("Integer = ?{paramIndex} ", Integer);
		}
		Date Date = TypeConvertUtils.StringToDate(queryHash.get("Date"));
		if (Date != null) {
			conditionHash.put("Date >= ?{paramIndex} ", Date);
		}*/

		return conditionHash;
	}

	@Override
	public Long totalRecord(Map<String, String> queryHash) {

		Map<String, Object> conditions = getSearchCondition(queryHash);
		TypedQuery<Long> typedQuery = QueryUtils.getTypedQueryByCondition("select count(a) from AdminUser a ", conditions, "", entityManager, Long.class);
		return typedQuery.getSingleResult();
	}

	@Override
	public List<AdminUser> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {

		if (StringUtils.isEmpty(sqlOrder)) {
			sqlOrder = "order by a.adminUserId desc ";
		}

		Map<String, Object> conditions = getSearchCondition(queryHash);
		TypedQuery<AdminUser> typedQuery = QueryUtils.getTypedQueryByCondition("select a from AdminUser a ", conditions, sqlOrder, entityManager, AdminUser.class);
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
	public AdminUser findModel(Long adminUserId) {
		return entityManager.find(AdminUser.class, adminUserId);
	}

	@Override
	public Integer add(AdminUser adminUser) {
		adminUser.setCreateDate(new Date());
		entityManager.persist(adminUser);
		logger.info("AdminUserDaoImpl添加adminUser成功！");
		return 1;
	}

	@Override
	public Integer update(AdminUser adminUser) {
		AdminUser existAdminUser = entityManager.find(AdminUser.class, adminUser.getAdminUserId());
		existAdminUser.setLoginName(adminUser.getLoginName());
		existAdminUser.setLoginPassword(adminUser.getLoginPassword());
		existAdminUser.setRealName(adminUser.getRealName());
		existAdminUser.setEmail(adminUser.getEmail());
		existAdminUser.setUpdateDate(new Date());
		return 1;
	}

	@Override
	public Integer delete(Long adminUserId) {
		AdminUser existAdminUser = entityManager.find(AdminUser.class, adminUserId);
		entityManager.remove(existAdminUser);
		return 1;
	}

	// ******************************************************************************
	// ********************************** CRUD END **********************************
	// ******************************************************************************
}