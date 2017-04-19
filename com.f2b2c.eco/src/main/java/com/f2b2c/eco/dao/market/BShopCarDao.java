package com.f2b2c.eco.dao.market;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.model.market.BShopCarModel;
import com.f2b2c.eco.model.market.CShopCarModel;
import com.f2b2c.eco.model.platform.FGoodsToShopCarModel;

/**
 * B端购物车管理
 * @author jane.hui
 *
 */
public interface BShopCarDao{
	
	/**
	 * 根据需要的参数获取购物车
	 * @param page 参数
	 * @return 返回购物车对象
	 */
	BShopCarModel selectShopCarByParams(BShopCarModel shopCar);
	
	/**
	 * 更新购物车表对象
	 * @param bShopCarModel:购物车对象
	 * @return 返回更新是否成功
	 */
	int updateShopCarById(BShopCarModel bShopCarModel);
	
	/**
	 * 插入购物表数据
	 * @param bShopCarModel:购物车表对象
	 * @return 返回插入购物车是否成功
	 */
	int insertShopCar(BShopCarModel bShopCarModel);
	
	/**
	 * 根据用户id获取购物车商品
	 * @param userId:用户id
	 * @return 返回购物车商品list
	 */
	List<FGoodsToShopCarModel> selectShopCartGoodsByUserId(Integer userId);
	
	/**
	 * 根据用户id批量删除购物车商品
	 * @param list:购物车主键id list
	 * @return 返回批量删除是否成功
	 */
	int delBatchShopCartByUserId(List<Integer> list);
    
    /**
	 * 批量更新购物车数量
	 * @param list:购物车商品list
	 * @return 返回更新购物车数量是否成功
	 */
    int updateBatchShopCart(List<CShopCarModel> list);
    
	/**
	 * 获取购物车中最新的商品
	 * @param list:购物车id list
	 * @return 返回购物车中最新的商品
	 */
    List<FGoodsToShopCarModel> selectNewstGoodsByList(List<Integer> list);
    
    /**
     * 根据商品id获取商品信息
     * @param id:商品主键
     * @return 返回商品信息
     */
    FGoodsToShopCarModel selectNewstGoodsById(Integer id);
    
    /**
     * 根据商品编号查找商品数量
     * @param goodsNo:商品编号
     * @return 返回商铺数量
     */
    Integer selectGoodsQtyByGoodsNo(String goodsNo);
    
    void deleteBatch(Map<String,Object> map);
    
    /**
     * 查询购物车的总数和种类
     * @param userId
     * @return
     */
    Map<String,Object> queryCarCountAndKindByUserId(Integer userId);
    
    /**
     * 根据购物车id获取商品库存
     * @param id:购物车主键
     * @return 返回商品库存
     */
    Integer queryQuantityByShopCartId(Integer id);
}