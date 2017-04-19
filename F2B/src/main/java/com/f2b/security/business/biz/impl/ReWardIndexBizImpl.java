package com.f2b.security.business.biz.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.biz.ReWardIndexBiz;
import com.f2b.security.business.dao.RewardIndexDao;
import com.f2b.security.domain.RewardIndex;

/**
 * Created by Administrator on 2016/3/24.
 */
@Service("ReWardIndexBiz")
public class ReWardIndexBizImpl implements ReWardIndexBiz {

	private Logger logger = LoggerFactory.getLogger(ReWardIndexBizImpl.class);

	@Autowired
	private RewardIndexDao rewardIndexDao;

	public static Date lastPaid;

	@Override
	public RewardIndex findModel(Long recordId) {
		return rewardIndexDao.findModel(recordId);
	}

	@Override
	@Transactional
	public void addOrUpdate(RewardIndex model) {
		if (model.getId() != null && model.getId() > 0) {
			rewardIndexDao.update(model);
		} else {
			rewardIndexDao.add(model);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		rewardIndexDao.delete(id);
	}
}
