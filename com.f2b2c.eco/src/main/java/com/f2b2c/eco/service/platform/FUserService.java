package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.platform.FMenuModel;
import com.f2b2c.eco.model.platform.FUserModel;

/**
 * 平台用户服务类
 * 
 * @author brave.chen
 *
 */
public interface FUserService {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param accountName
	 *            用户名
	 * @return true:存在；false:不存在
	 */
	boolean checkIsExist(String accountName);

	/**
	 * 保存用户对象
	 * 
	 * @param fUserModel
	 *            用户对象
	 */
	void saveFUserModel(FUserModel fUserModel);

	/**
	 * 更新用户对象
	 * 
	 * @param fUserModel
	 *            平台用户对象模型
	 */
	void updateFUserModelInfo(FUserModel fUserModel);

	/**
	 * 删除用户（假删除，更新deleted字段）
	 * 
	 * @param userId
	 *            用户ID
	 */
	void delete(Integer userId);

	/**
	 * 根据用户ID获取参数map对象
	 * 
	 * @param userId
	 *            用户ID
	 * @return 参数map对象
	 */
	Map<String, Object> queryParamsByUserId(Integer userId);

	/**
	 * 分页获取用户信息
	 * 
	 * @param pageable
	 *            分页对象
	 * @param paramMap
	 *            查询参数对象
	 * @return 分页查询的对象列表
	 */
	Page<FUserModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);

	/**
	 * 根据用户ID获取用户对象
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户对象
	 */
	FUserModel queryFUserModelById(Integer userId);

	/**
	 * 根据用户名获取用户对象
	 * 
	 * @param accountName
	 *            账户名称
	 * @return 用户对象
	 */
	FUserModel queryFUserModelByAccountName(String accountName);

	/**
	 * 根据实体类对象查询是否有该实体类对象，有的话将会查出该对象，没有则返回null
	 * 
	 * @param user
	 * @return 对象或null
	 */
	FUserModel queryOne(FUserModel user);

	/**
	 * 根据名称查找
	 * 
	 * @param account
	 * @return
	 */
	FUserModel findOneByAccount(String account);

	/**
	 * 根据用户的id，获取用户的权限对于的菜单列表，
	 * 
	 * @param userId
	 * @return
	 */
	List<FMenuModel> getAuthcationMenus(String userId);

	/**
	 * 根据id查找用户
	 * 
	 * @param id
	 * @return
	 */
	FUserModel findOneById(Integer id);

	/**
	 * 根据店铺id查询担保人信息
	 * 
	 * @param shopId
	 * @return
	 */
	FUserModel getCreateUserInfoByShopId(Integer shopId);

	FUserModel getUserByAreaId(Integer areaId, String roleId);

  
    Integer checkPartner(Map<String, Object> paramMap);

	Integer getCityIdByAreaId(Integer areaId);

	Integer getProvinceIdByAreaId(Integer areaId);

	FUserModel getUserByCityId(Integer cityId, String roleId);

	FUserModel getUserByProvinceId(Integer provinceId, String roleId);
   
	List<Integer> queryUserIdByAreaId(List<Integer> list);

	List<Integer> queryUserId(List<Integer> list);
}
