package com.f2b.security.business.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.biz.FarmUserBiz;
import com.f2b.security.business.dao.FarmUserDao;
import com.f2b.security.domain.FarmUser;

@Service("farmUserBiz")
public class FarmUserBizImpl implements FarmUserBiz{

	@Autowired
	private FarmUserDao farmUserDao;
	
	/**
	 * 用户关注
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	public void addFarmUser(FarmUser user) {
		this.farmUserDao.add(user);
	}

	/**
	 * 使用了抵用券
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	public void updateFarmUser(FarmUser user) {
		farmUserDao.update(user);
	}

	/**
	 * 判断是否还有抵用券
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public FarmUser getById(String openId) {
		// TODO Auto-generated method stub
		return farmUserDao.getById(openId);
	}

	@Override
	public List<FarmUser> getByOpenId(String openId) {
		// TODO Auto-generated method stub
		return farmUserDao.getByOpenId(openId);
	}

}
