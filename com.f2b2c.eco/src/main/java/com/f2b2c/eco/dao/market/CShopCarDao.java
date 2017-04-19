package com.f2b2c.eco.dao.market;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.CShopCarModel;
import com.f2b2c.eco.model.market.Page;

/**
 * 购物车dao
 * 
 * @author jane.hui
 *
 */
public interface CShopCarDao extends CrudMapper<CShopCarModel, Serializable>{
   
	/**
	 * 根据用户id获取购物车
	 * 
	 * @param userId
	 * @return:获取购物车中所有的商品
	 */
	List<CShopCarModel> selectShopCarByUserId(Page page);
	
	/**
	 * 获取购物车中最新的商品
	 * 
	 * @param list:购物车id
	 *            list
	 * @return 返回购物车中最新的商品
	 */
    List<CShopCarModel> selectNewstGoodsByList(List<Integer> list);
    
    /**
	 * 更新可选的购物车商品
	 * 
	 * @param cShopCarModel
	 * @return
	 */
    int updateByPrimaryKeySelective(CShopCarModel cShopCarModel);
    
    /**
	 * 批量更新购物车数量
	 * @param list:购物车商品list
	 * @return 返回更新购物车数量是否成功
	 */
    int updateBatchShopCart(List<CShopCarModel> list);
    
    /**
	 * 根据主键删除购物车
	 * 
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

	int selectcShopCarSizByUserId(Integer userId, String goodsNo);

	/**
	 * 插入购物车 author josen yang
	 * 
	 * @param cShopCarModel
	 * @return
	 */
	int insertInto(CShopCarModel cShopCarModel);
	
	/**
	 * 根据用户id批量删除购物车商品
	 * 
	 * @param list:购物车主键id
	 *            list
	 * @return 返回批量删除是否成功
	 */
	int delBatchShopCartByUserId(List<Integer> list);

	int selectGoodsQty(Integer userId, String goodsNo);

	int updateGoodsQty(CShopCarModel cShopCarModel);
	
	/**
	 * 根据用户id查询商品数量
	 * @param cShopCarModel
	 * @return
	 */
	Integer selectGoodsQtyByUserId(String goodsNo);
	
    /**
     * 根据购物车id获取商品库存
     * @param id:购物车主键
     * @return 返回商品库存
     */
    Integer queryQuantityByShopCartId(Integer id);
    
    /**
     * 查询购物车的总数和种类
     * @param userId
     * @return
     */
    Map<String,Object> queryCarCountAndKindByUserId(Integer userId);
}