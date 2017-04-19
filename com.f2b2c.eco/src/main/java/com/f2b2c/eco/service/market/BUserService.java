package com.f2b2c.eco.service.market;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.market.FToBUserModel;

public interface BUserService {

	List<BUserModel> getUserListsByfUserId(Integer id);
	
	int insertUser(BUserModel bUser);
	
	public BUserModel queryUserIsExist(Map<String,Object> map);

	public int updatePassword(BUserModel bUser);

	boolean checkIfExist(String account);

	BUserModel queryOne(BUserModel user);
	
	BUserModel findOneByAccount(String account);
	
	FToBUserModel queryBUserByUserId(@Param(value="userId") Integer userId,@Param(value="date") Date date);

	BUserModel getBUserByPhone(String phone);
	
	/**
	 * 主页统计
	 * @return
	 */
	int findAllBUserCount();
}
