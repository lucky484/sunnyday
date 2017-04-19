package com.f2b2c.eco.service.market;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.model.market.BGoodsModel;

public interface UserBrowerRecordService {

	/**
	 * 查询当前用户浏览过的所有的水果商品
	 * 
	 * @param userId
	 * @return
	 */
	List<BGoodsModel> queryAllBrowerRecord(Map<String, Object> map);

	int queryAllBrowerRecordCount(Map<String, Object> map);

	/**
	 * 根据用户的id查询浏览过的商品
	 * 
	 * @param userId
	 * @return
	 */
	List<BGoodsModel> queryBrowerRecordByUserId(Map<String,Object> map);

	/**
	 * 附近有店铺，没有匹配上浏览记录的数据
	 * 
	 * @param map
	 * @return
	 */
	List<BGoodsModel> queryShopGoods(Map<String, Object> map);

	int queryShopGoodsCount(Map<String, Object> map);

	/**
	 * 查询所有和浏览记录匹配的水果
	 * 
	 * @param map
	 * @return
	 */
	List<BGoodsModel> queryAllFeFruit(Map<String, Object> map);

	int queryAllFeFruitCount(Map<String, Object> map);

	/**
	 * 查询所有的非水果
	 * 
	 * @param map
	 * @return
	 */
	List<BGoodsModel> queryFeFruit(Map<String, Object> map);

	int queryFeFruitCount(Integer kindId);
}
