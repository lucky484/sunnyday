package com.f2b2c.eco.dao.market;

import java.io.Serializable;
import java.util.List;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.InsertCDeliveryAddressModel;
import com.f2b2c.eco.model.market.Page;

/**
 * B2C收货地址表
 * 
 * @author jane.hui
 *
 */
public interface CDeliveryAddressDao extends CrudMapper<CDeliveryAddressModel, Serializable> {

	/**
	 * 根据用户外键获取收货地址
	 * 
	 * @param userId
	 * @return 返回获取收货地址是否成功状态
	 */
	List<CDeliveryAddressModel> selectDeliveryAddressByUserId(Page page);
	
	/**
	 * 获取用户的默认收货地址
	 * @param cUserId
	 * @return
	 */
	CDeliveryAddressModel getDefaultDeliveryAddress(Integer cUserId);

	/**
	 * 插入可选的收货地址表
	 * 
	 * @param record:插入收货地址对象
	 * @return 返回插入收货地址是否成功状态
	 */
	int insertDeliveryAddress(InsertCDeliveryAddressModel record);

	/**
	 * 更新可选的收获地址表
	 * 
	 * @param record:收获地址更新对象
	 * @return 返回更新收货地址是否成功状态
	 */
	int updateDeliveryAddressById(InsertCDeliveryAddressModel record);

	/**
	 * 根据用户外键取消默认收货地址
	 * 
	 * @param record:取消默认收货地址对象
	 * @return 返回取消默认收货地址是否成功状态
	 */
	int updateDeliveryAddressByUserId(InsertCDeliveryAddressModel record);
}