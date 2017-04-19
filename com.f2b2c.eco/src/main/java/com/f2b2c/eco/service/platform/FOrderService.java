package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FGoodsToShopCarModel;

/**
 * 购物车-订单管理
 * @author jane.hui
 *
 */
public interface FOrderService {

	/**
	 * 加入购物车
	 * @param goodsNo:商品编号 
	 * @param quantity:商品数量
	 * @param userId:用户外键
	 * @return 返回加入购物车是否成功
	 */
	DataToCResultModel<Object> addShopCar(String goodsNo,Integer quantity,Integer userId);
	
	/**
	 * 根据用户id获取购物车商品
	 * @param userId
	 * @return
	 */
	DataToCResultModel<List<FGoodsToShopCarModel>> getShopCar(Integer userId);
	
	/**
	 * 删除购物车中的商品
	 * 
	 * @param 购物车表主键
	 * @param 返回删除购物车中的商品是否成功标
	 */
	int deleteShopCartById(String id);
	
	/**
	 * 修改购物车中的商品
	 * 
	 * @param 购物车中表主键
	 * @param 修改某个商品的数量
	 * @return 返回修改购物车中的商品是否成功
	 */
	ApiResultModel<Integer> modifyShopCart(String strQuantity);
	
	/**
	 * 检验购物车中的商品
	 * @param cartList:购物车中的要结算的商品
	 * @return 返回购物车中版本升级的商品
	 */
	ApiResultModel<Map<String, Object>> checkShopCart(String shopCartStr,Integer userId);
	
	/**
	 * 立即购买
	 * @param id:商品主键
	 * @param userId:用户外键
	 * @return 返回立即购买跳转后确认订单页面的商品和收货地址
	 */
	ApiResultModel<Map<String, Object>> purchase(Integer id,Integer userId,Integer quantity);
	
	int updateOrderStatus(Map<String, Object> map);
	
	/**
	 * 查询购物车的总数和种类
	 * @param userId
	 * @return
	 */
	Map<String,Object> queryCarCountAndKindByUserId(Integer userId);
}
