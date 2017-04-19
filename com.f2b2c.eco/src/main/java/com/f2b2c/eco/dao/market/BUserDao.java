package com.f2b2c.eco.dao.market;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.market.FToBUserModel;

public interface BUserDao extends CrudMapper<BUserModel, Serializable>{

	List<BUserModel> getUserListsByfUserId(Integer id);

	BUserModel getbUsyerById(Integer id);
   
	int insertUser(BUserModel bUser);
	
	public BUserModel queryUserIsExist(Map<String,Object> map);
	
	public int updatePassword(BUserModel bUser);

	BUserModel queryUserByAccountName(String account);

	BUserModel findOne(BUserModel user);
	
	FToBUserModel queryBUserByUserId(@Param(value="userId") Integer userId,@Param(value="date") Date date);

	BUserModel getBUserByPhone(String phone);

	/**
     * 主页统计
     * 
     * @return
     */
	int findAllBUserCount();
	
	/**
     * 根据id更新手机号
     * 
     * @param bUserModel
     * @return
     */
	int updatePhoneById(BUserModel bUserModel);
	
	void deleteUserById(Integer userId);

}
