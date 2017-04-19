package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FUserModel;

/**
 * 用户数据处理类
 * 
 * @author brave.chen
 *
 */
public interface FUserDao extends CrudMapper<FUserModel, Serializable> {

	/**
	 * 根据账户查询用户对象
	 * 
	 * @param accountName
	 *            账户名称
	 * @return 用户对象模型
	 */
	FUserModel queryUserByAccountName(String accountName);

	/**
	 * 用户对象
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户对象
	 */
	FUserModel queryUserById(Integer userId);

	/**
	 * 根据条件查询数量
	 * 
	 * @param paramMap
	 *            参数map
	 * @return 查询总数
	 */
	int queryCountByCondition(Map<String, Object> paramMap);

	/**
	 * 根据对象返回匹配的第一个对象
	 * 
	 * @param user
	 * @return 对象或null
	 */
	FUserModel findOne(FUserModel user);

	FUserModel findOneById(Integer id);

	/**
	 * 根据店铺id查询担保人信息
	 * 
	 * @param shopId
	 * @return
	 */
	FUserModel getCreateUserInfoByShopId(Integer shopId);
	
	FUserModel getUserByAreaId(Map<Object, Object> paramMap);
	
    Integer checkPartner(Map<String, Object> paramMap);

	Integer getCityIdByAreaId(Integer areaId);

	Integer getProvinceIdByAreaId(Integer areaId);

	FUserModel getUserByCityId(Map<Object, Object> paramMap);

	FUserModel getUserByProvinceId(Map<Object, Object> paramMap);

	List<Integer> queryUserIdByAreaId(List<Integer> list);

	List<Integer> queryUserId(List<Integer> list);

}