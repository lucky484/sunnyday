package com.f2b.security.business.biz;

import java.util.List;

import com.f2b.security.domain.FarmUser;

/**
 * 
 * @author mozzie.chu
 *
 */
public interface FarmUserBiz {

	/**
	 * 用户关注,发放抵用券
	 * @param user
	 */
	public void addFarmUser(FarmUser user);
	
	public void updateFarmUser(FarmUser user);
	
	public FarmUser getById(String openId);
	public List<FarmUser> getByOpenId(String openId);
}
