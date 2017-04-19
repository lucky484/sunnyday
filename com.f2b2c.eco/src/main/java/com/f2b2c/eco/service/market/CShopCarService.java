package com.f2b2c.eco.service.market;

import java.util.List;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.CShopCarModel;

/**
 * 订单接口
 * @author jane.hui
 *
 */
public interface CShopCarService {
	
	/**
	 * 获取购物车表数据
	 * @param userId:消费者外键
	 * @return 返回购物车中所有的商品
	 */
	List<CShopCarModel> getShopCarByUserId(Integer userId);
	
	/**
	 * 删除购物车中的商品
	 * @param 购物车表主键
	 * @param 返回删除购物车中的商品是否成功标
	 */
	int deleteShopCartById(Integer id);
	
	/**
	 * 修改购物车中的商品
	 * @param 购物车中表主键
	 * @param 修改某个商品的数量
	 * @return 返回修改购物车中的商品是否成功
	 */
	int modifyShopCart(String id,Integer quantity);
	
	/**
	 * 检验购物车中的商品
	 * @param cartList:购物车中的要结算的商品
	 * @return 返回购物车中版本升级的商品
	 */
	ApiResultModel<List<CShopCarModel>> checkShopCart(List<CShopCarModel> cartList);	
	
	/**
	 * 根据用户外键获取收货地址
	 * @param userId:用户外键
	 * @return:返回收货地址
	 */
	List<CDeliveryAddressModel> getCDeliveryAddressList(Integer userId);
}
