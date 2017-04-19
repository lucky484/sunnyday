package com.f2b.security.business.biz.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.biz.AdminUserBiz;
import com.f2b.security.business.dao.AdminUserDao;
import com.f2b.security.domain.AdminUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: val.jzp
 */
@Service("adminUserBiz")
public class AdminUserBizImpl implements AdminUserBiz {

	private Logger logger = LoggerFactory.getLogger(AdminUserBizImpl.class);

	@Autowired
	private AdminUserDao adminUserDao;

	@Override
	public AdminUser login(String loginName, String loginPassword) {

		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPassword)) {
			return null;
		}

		Map<String, String> queryHash = new HashMap<>();
		queryHash.put("loginName", loginName);
		queryHash.put("loginPassword", loginPassword);

		List<AdminUser> adminUserList = findList(queryHash);
		if (adminUserList != null && adminUserList.size() > 0) {
			return adminUserList.get(0);
		} else {
			return null;
		}
	}

	// ******************************************************************************
	// ********************************* CRUD START *********************************
	// ******************************************************************************

	@Override
	public Long totalRecord() {
		return this.totalRecord(null);
	}

	@Override
	public Long totalRecord(Map<String, String> queryHash) {
		return adminUserDao.totalRecord(queryHash);
	}

	@Override
	public List<AdminUser> findList() {
		return this.findList(0, 0, null);
	}

	@Override
	public List<AdminUser> findList(Map<String, String> queryHash) {
		return this.findList(0, 0, queryHash);
	}

	@Override
	public List<AdminUser> findList(Integer pageNow, Integer pageSize) {
		return this.findList(pageNow, pageSize, null);
	}

	@Override
	public List<AdminUser> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash) {
		return this.findList(pageNow, pageSize, "", queryHash);
	}

	@Override
	public List<AdminUser> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {
		return adminUserDao.findList(pageNow, pageSize, sqlOrder, queryHash);
	}

	@Override
	public AdminUser findModel(Long adminUserId) {
		return adminUserDao.findModel(adminUserId);
	}

	@Override
	@Transactional
	public void addOrUpdate(AdminUser model) {
		if (model.getAdminUserId() != null && model.getAdminUserId() > 0) {
			logger.info("当前需修改的AdminUser对象admin_user_id为[" + model.getAdminUserId() + "]");
			adminUserDao.update(model);
		} else {
			logger.info("AdminUserBizImpl添加adminUser！");
			adminUserDao.add(model);
		}
	}

	@Override
	@Transactional
	public void delete(Long adminUserId) {
		adminUserDao.delete(adminUserId);
	}

	// ******************************************************************************
	// ********************************** CRUD END **********************************
	// ******************************************************************************
}