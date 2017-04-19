package com.f2b2c.eco.service.market;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.model.market.BGoodsInfoToCModel;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BShopInfoToCModel;
import com.f2b2c.eco.model.market.Page;

/**
 * @author josen.yang
 *
 */
public interface BShopInfoService {

	/**
	 * 根据店铺ID查询店铺信息并获取默认的商品列表
	 */
	Map<String, Object> findShopInfoById(Integer userId, Integer shopId);

	/**
	 * 根据店铺ID查询店铺信息并获取商品列表
	 */
	List<BGoodsInfoToCModel> findBgoodsListByKindId(Integer shopId, Integer kindId);

	/**
	 * 查询所有店铺
	 * 
	 * @return
	 */
	List<BShopInfoToCModel> queryAllShop();

	BShopInfoModel findShopInfoByUserId(Integer id);

	Integer selectEnableByUserId(Integer userId);

	BShopInfoToCModel queryShopInfo(Integer shopId);

	Page queryFavoriteShop(Map<String, Object> map);
	
	/**
	 * 查询店铺详情
	 * @param userId
	 * @return
	 */
	BShopInfoModel queryShopNameByUserId(Integer userId);

}
