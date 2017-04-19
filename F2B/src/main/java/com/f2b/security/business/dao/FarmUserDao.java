package com.f2b.security.business.dao;

import java.util.List;

import com.f2b.security.domain.FarmUser;

/**
 * 关注过的用户 - 操作
 * @author mozzie.chu
 *
 */
public interface FarmUserDao {

	/**
	 * 用户关注
	 * @param user
	 * @return
	 */
	public Integer add(FarmUser user);
	
	/**
	 * 使用了抵用券
	 * @param user
	 * @return
	 */
	public Integer update(FarmUser user);
	
	/**
	 * 判断是否还有抵用券
	 * @param id
	 * @return
	 */
	public FarmUser getById(String openId);
	
	public List<FarmUser> getByOpenId(String openId);	
}
